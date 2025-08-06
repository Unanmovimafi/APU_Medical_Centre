package controller.appointment;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import model.appointment.Appointment;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

@WebServlet(name = "StaffGetAvailableSlots", urlPatterns = {"/staff/get-available-slots"})
public class StaffGetAvailableSlots extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StaffGetAvailableSlots.class.getName());

    @EJB
    private DoctorFacade doctorFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dateStr = request.getParameter("appointmentDate");
        String doctorIdStr = request.getParameter("doctorId");

        LOGGER.info("Requested Date: " + dateStr);
        LOGGER.info("Requested Doctor ID: " + doctorIdStr);

        // ✅ Validate input
        if (dateStr == null || dateStr.isEmpty() || doctorIdStr == null || doctorIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid or missing parameters");
            return;
        }

        try {
            // ✅ Parse date safely
            LocalDate selectedDate = LocalDate.parse(dateStr);

            // ✅ Fetch doctor
            Integer doctorId = Integer.parseInt(doctorIdStr);
            Doctor doctor = doctorFacade.find(doctorId);
            if (doctor == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Doctor not found");
                return;
            }

            // ✅ Get available slots
            List<String> slots = getAvailableTimeSlots(selectedDate, doctor);

            // ✅ Return JSON
            String json = slots.stream()
                    .map(slot -> "\"" + slot + "\"")
                    .collect(Collectors.joining(", ", "[", "]"));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        } catch (Exception e) {
            LOGGER.severe("Error fetching available slots: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generate available time slots for a doctor on a given date.
     */
    private List<String> getAvailableTimeSlots(LocalDate selectedDate, Doctor doctor) {
        List<String> availableSlots = new ArrayList<>();

        // ✅ Define working hours (9 AM to 5 PM)
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // ✅ Filter booked appointments for that day
        List<Appointment> bookedAppointments = doctor.getAppointmentCollection().stream()
                .filter(appt -> {
                    LocalDate apptDate = appt.getAppointmentStartDatetime()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return apptDate.equals(selectedDate);
                })
                .collect(Collectors.toList());

        // ✅ Loop through all possible 30-min slots
        while (!start.isAfter(end.minusMinutes(30))) {
            LocalTime slotStartTime = start;

            boolean isBooked = bookedAppointments.stream().anyMatch(appt -> {
                LocalTime bookedStartTime = appt.getAppointmentStartDatetime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime()
                        .withSecond(0)
                        .withNano(0);
                return bookedStartTime.equals(slotStartTime);
            });

            if (!isBooked) {
                String slotLabel = slotStartTime.format(timeFormatter) + " - " +
                                   slotStartTime.plusMinutes(30).format(timeFormatter);
                availableSlots.add(slotLabel);
            }

            start = start.plusMinutes(30);
        }

        return availableSlots;
    }
}

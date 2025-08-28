package controller.customer;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 * Servlet to get available time slots for a doctor on a specific date (Customer
 * version)
 * 
 * @author khong
 */
@WebServlet(name = "CustomerGetAvailableSlots", urlPatterns = { "/customer/get-available-slots" })
public class CustomerGetAvailableSlots extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CustomerGetAvailableSlots.class.getName());

    @EJB
    private DoctorFacade doctorFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dateStr = request.getParameter("appointmentDate");
        String doctorIdStr = request.getParameter("doctorId");

        LOGGER.info("Customer requested Date: " + dateStr);
        LOGGER.info("Customer requested Doctor ID: " + doctorIdStr);

        if (dateStr == null || dateStr.isEmpty() || doctorIdStr == null || doctorIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("[]");
            return;
        }

        try {
            int doctorId = Integer.parseInt(doctorIdStr);
            LocalDate appointmentDate = LocalDate.parse(dateStr);

            // Verify doctor exists
            Doctor doctor = doctorFacade.find(doctorId);
            if (doctor == null) {
                LOGGER.warning("Doctor not found for ID: " + doctorId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("[]");
                return;
            }

            LOGGER.info("Found Doctor: " + doctor.getName() + " (" + doctor.getUsername() + ")");

            // Define working hours (9 AM to 5 PM)
            LocalTime startTime = LocalTime.of(9, 0);
            LocalTime endTime = LocalTime.of(17, 0);
            int slotDurationMinutes = 30;

            // Generate all possible time slots for the day
            List<String> allTimeSlots = new ArrayList<>();
            LocalTime currentTime = startTime;

            while (currentTime.isBefore(endTime)) {
                LocalTime slotEndTime = currentTime.plusMinutes(slotDurationMinutes);
                if (slotEndTime.isAfter(endTime)) {
                    break; // Don't create slots that extend beyond working hours
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeSlot = currentTime.format(formatter) + "-" + slotEndTime.format(formatter);
                allTimeSlots.add(timeSlot);

                currentTime = currentTime.plusMinutes(slotDurationMinutes);
            }

            LOGGER.info("Generated " + allTimeSlots.size() + " potential time slots");

            // Get appointments from database for this doctor and date
            List<Appointment> allAppointments = appointmentFacade.findAll();

            LOGGER.info("Total appointments in database: " + allAppointments.size());

            // Filter appointments for this doctor on this date, excluding rejected/finished
            List<Appointment> bookedAppointments = allAppointments.stream()
                    .filter(appointment -> appointment != null
                            && appointment.getDoctor() != null
                            && appointment.getAppointmentStartDatetime() != null)
                    .filter(appointment -> appointment.getDoctor().getId().equals(doctorId))
                    .filter(appointment -> {
                        // Convert appointment date to LocalDate for comparison
                        LocalDate apptDate = appointment.getAppointmentStartDatetime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return apptDate.equals(appointmentDate);
                    })
                    .filter(appointment -> {
                        // Exclude only REJECTED and FINISHED appointments - all others block slots
                        String status = appointment.getStatus();
                        boolean isActive = !"REJECTED".equals(status) && !"FINISHED".equals(status);
                        LOGGER.info("Appointment ID " + appointment.getId() + " status: " + status + ", isActive: "
                                + isActive);
                        return isActive;
                    })
                    .collect(Collectors.toList());

            LOGGER.info("Active appointments for Doctor " + doctorId + " on " + appointmentDate + ": "
                    + bookedAppointments.size());

            // Log each booked appointment for debugging
            for (Appointment appt : bookedAppointments) {
                LocalDateTime startDateTime = appt.getAppointmentStartDatetime()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endDateTime = appt.getAppointmentEndDatetime() != null
                        ? appt.getAppointmentEndDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        : startDateTime.plusMinutes(30);
                LOGGER.info("Booked: " + appt.getId() + " from " + startDateTime.toLocalTime() + " to "
                        + endDateTime.toLocalTime() + " (Status: " + appt.getStatus() + ")");
            }

            // Filter out unavailable time slots
            List<String> availableSlots = allTimeSlots.stream()
                    .filter(slot -> {
                        // Parse slot times
                        String[] times = slot.split("-");
                        LocalTime slotStartTime = LocalTime.parse(times[0]);
                        LocalTime slotEndTime = LocalTime.parse(times[1]);

                        // Check if this slot conflicts with any booked appointments
                        boolean isAvailable = bookedAppointments.stream().noneMatch(appointment -> {
                            LocalDateTime bookedStart = appointment.getAppointmentStartDatetime()
                                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            LocalDateTime bookedEnd = appointment.getAppointmentEndDatetime() != null
                                    ? appointment.getAppointmentEndDatetime().toInstant().atZone(ZoneId.systemDefault())
                                            .toLocalDateTime()
                                    : bookedStart.plusMinutes(30); // Default 30-minute duration

                            LocalTime bookedStartTime = bookedStart.toLocalTime();
                            LocalTime bookedEndTime = bookedEnd.toLocalTime();

                            // Mathematical overlap check: two intervals [a,b] and [c,d] overlap if a < d
                            // and c < b
                            boolean hasOverlap = slotStartTime.isBefore(bookedEndTime)
                                    && bookedStartTime.isBefore(slotEndTime);

                            if (hasOverlap) {
                                LOGGER.info("Slot " + slot + " conflicts with appointment " + appointment.getId() + " ("
                                        + bookedStartTime + "-" + bookedEndTime + ")");
                            }

                            return hasOverlap;
                        });

                        LOGGER.info("Slot " + slot + " availability: " + isAvailable);
                        return isAvailable;
                    })
                    .collect(Collectors.toList());

            LOGGER.info("Available slots: " + availableSlots.size());

            // Return as JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < availableSlots.size(); i++) {
                json.append("\"").append(availableSlots.get(i)).append("\"");
                if (i < availableSlots.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            response.getWriter().write(json.toString());

        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid doctor ID format: " + doctorIdStr);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("[]");
        } catch (Exception e) {
            LOGGER.severe("Error getting available slots: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("[]");
        }
    }
}

package controller.staff;

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
 * Servlet to get available time slots for a doctor on a specific date
 * 
 * @author khong
 */
@WebServlet(name = "StaffGetAvailableSlots", urlPatterns = { "/staff/get-available-slots" })
public class StaffGetAvailableSlots extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StaffGetAvailableSlots.class.getName());

    @EJB
    private DoctorFacade doctorFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dateStr = request.getParameter("appointmentDate");
        String doctorIdStr = request.getParameter("doctorId");

        LOGGER.info("Requested Date: " + dateStr);
        LOGGER.info("Requested Doctor ID: " + doctorIdStr);

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Validate input
        if (dateStr == null || dateStr.isEmpty() || doctorIdStr == null || doctorIdStr.isEmpty()) {
            LOGGER.warning("Missing parameters - Date: " + dateStr + ", Doctor ID: " + doctorIdStr);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid or missing parameters\"}");
            return;
        }

        try {
            // Parse date safely
            LocalDate selectedDate = LocalDate.parse(dateStr);

            // Check if selected date is in the past
            if (selectedDate.isBefore(LocalDate.now())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Cannot book appointments for past dates\"}");
                return;
            }

            // Fetch doctor
            Integer doctorId = Integer.parseInt(doctorIdStr);
            Doctor doctor = doctorFacade.find(doctorId);
            if (doctor == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Doctor not found\"}");
                return;
            }

            // Get available slots using fresh database data
            List<String> slots = getAvailableTimeSlots(selectedDate, doctor);

            // Return JSON array
            String json = slots.stream()
                    .map(slot -> "\"" + slot + "\"")
                    .collect(Collectors.joining(", ", "[", "]"));

            LOGGER.info("Returning " + slots.size() + " available slots for doctor " + doctor.getName() + " on "
                    + selectedDate);
            response.getWriter().write(json);

        } catch (Exception e) {
            LOGGER.severe("Error fetching available slots: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Server error: " + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    /**
     * Generate available time slots for a doctor on a given date.
     * Working hours: 9:00 AM to 5:00 PM
     * Slot duration: 30 minutes
     */
    private List<String> getAvailableTimeSlots(LocalDate selectedDate, Doctor doctor) {
        List<String> availableSlots = new ArrayList<>();

        // Define working hours (9 AM to 5 PM)
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Get doctor's appointments for the selected date using AppointmentFacade
        // This ensures we get fresh data from the database
        List<Appointment> bookedAppointments = new ArrayList<>();
        try {
            // Get all appointments from database and filter for this doctor and date
            List<Appointment> allAppointments = appointmentFacade.findAll();
            if (allAppointments != null) {
                bookedAppointments = allAppointments.stream()
                        .filter(appt -> appt != null &&
                                appt.getAppointmentStartDatetime() != null &&
                                appt.getDoctor() != null &&
                                appt.getDoctor().getId().equals(doctor.getId()))
                        .filter(appt -> {
                            LocalDate apptDate = appt.getAppointmentStartDatetime()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                            
                            // Consider all active appointment statuses as blocking slots
                            // Only exclude REJECTED and FINISHED appointments
                            boolean isActiveStatus = !"REJECTED".equals(appt.getStatus()) && 
                                                   !"FINISHED".equals(appt.getStatus());
                            
                            boolean isCorrectDate = apptDate.equals(selectedDate);
                            
                            LOGGER.info("Checking appointment: Date=" + apptDate + ", Status=" + appt.getStatus() + 
                                       ", IsActive=" + isActiveStatus + ", CorrectDate=" + isCorrectDate);
                            
                            return isCorrectDate && isActiveStatus;
                        })
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            LOGGER.warning("Error fetching appointments from database: " + e.getMessage());
            // Fall back to empty list if database query fails
            bookedAppointments = new ArrayList<>();
        }

        LOGGER.info("Found " + bookedAppointments.size() + " booked appointments for doctor " + doctor.getId() + " on date " + selectedDate);
        
        // Log all booked appointments for debugging
        for (Appointment appt : bookedAppointments) {
            LocalTime startTime = appt.getAppointmentStartDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            LocalTime endTime = appt.getAppointmentEndDatetime() != null ? 
                appt.getAppointmentEndDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime() :
                startTime.plusMinutes(30);
            LOGGER.info("Booked slot: " + startTime + " - " + endTime + " (Status: " + appt.getStatus() + ")");
        }

        // Generate 30-minute time slots
        while (!start.isAfter(end.minusMinutes(30))) {
            LocalTime slotStartTime = start;

            // Check if this slot conflicts with any booked appointment
            boolean isBooked = bookedAppointments.stream().anyMatch(appt -> {
                LocalTime bookedStartTime = appt.getAppointmentStartDatetime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime()
                        .withSecond(0)
                        .withNano(0);

                LocalTime bookedEndTime = appt.getAppointmentEndDatetime() != null ? 
                        appt.getAppointmentEndDatetime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime()
                                .withSecond(0)
                                .withNano(0) : 
                        bookedStartTime.plusMinutes(30);

                LocalTime slotEndTime = slotStartTime.plusMinutes(30);
                
                // Simple overlap check: 
                // Two intervals [a,b) and [c,d) overlap if and only if a < d and c < b
                // In our case: slotStart < bookedEnd AND bookedStart < slotEnd
                boolean overlaps = slotStartTime.isBefore(bookedEndTime) && bookedStartTime.isBefore(slotEndTime);
                
                LOGGER.info("Checking slot " + slotStartTime + "-" + slotEndTime + 
                           " against booked " + bookedStartTime + "-" + bookedEndTime + 
                           " -> overlaps: " + overlaps);
                
                return overlaps;
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

    /**
     * Alternative method to get appointments for a specific doctor and date
     * This could be optimized further with a custom query in the facade
     */
    private List<Appointment> getAppointmentsByDoctorAndDate(Doctor doctor, LocalDate date) {
        try {
            // Convert LocalDate to Date range for the entire day
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

            // Get all appointments and filter (could be optimized with a specific query
            // method in facade)
            return appointmentFacade.findAll().stream()
                    .filter(appt -> appt != null &&
                            appt.getDoctor() != null &&
                            appt.getAppointmentStartDatetime() != null &&
                            appt.getDoctor().getId().equals(doctor.getId()) &&
                            !appt.getAppointmentStartDatetime().before(startDate) &&
                            !appt.getAppointmentStartDatetime().after(endDate) &&
                            ("PENDING".equals(appt.getStatus()) || "APPROVED".equals(appt.getStatus())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.warning("Error fetching appointments by doctor and date: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 *
 * @author System
 */
@WebServlet(name = "CustomerAppointmentRequest", urlPatterns = { "/customer/appointment/request" })
public class CustomerAppointmentRequest extends HttpServlet {

    @EJB
    private DoctorFacade doctorFacade;
    @EJB
    private AppointmentFacade appointmentFacade;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if customer is logged in
        HttpSession session = request.getSession(false);
        Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

        if (loggedCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Fetch all doctors for customer to choose from
            List<Doctor> doctors = doctorFacade.findAll();

            request.setAttribute("doctors", doctors);
            request.setAttribute("customer", loggedCustomer);

            // Forward to JSP
            request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading doctors: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if customer is logged in
        HttpSession session = request.getSession(false);
        Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

        if (loggedCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Get form parameters
            String doctorIdStr = request.getParameter("doctorId");
            String appointmentDate = request.getParameter("appointmentDate");
            String appointmentTime = request.getParameter("appointmentTime");

            // Validation
            if (doctorIdStr == null || doctorIdStr.trim().isEmpty() ||
                    appointmentDate == null || appointmentDate.trim().isEmpty() ||
                    appointmentTime == null || appointmentTime.trim().isEmpty()) {

                // Reload doctors for the form
                List<Doctor> doctors = doctorFacade.findAll();
                request.setAttribute("doctors", doctors);
                request.setAttribute("customer", loggedCustomer);
                request.setAttribute("errorMessage", "Please fill in all required fields.");
                request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            int doctorId = Integer.parseInt(doctorIdStr);
            Doctor selectedDoctor = doctorFacade.find(doctorId);

            if (selectedDoctor == null) {
                // Reload doctors for the form
                List<Doctor> doctors = doctorFacade.findAll();
                request.setAttribute("doctors", doctors);
                request.setAttribute("customer", loggedCustomer);
                request.setAttribute("errorMessage", "Selected doctor not found.");
                request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            // Parse the time slot - handle both formats: "HH:mm" and "HH:mm-HH:mm"
            Date startDate, endDate;

            if (appointmentTime.contains("-")) {
                // Format: "09:00-09:30"
                String[] timeParts = appointmentTime.split("-");
                String startTimeOnly = timeParts[0].trim();
                String endTimeOnly = timeParts[1].trim();

                LocalDateTime startLdt = LocalDateTime.parse(appointmentDate + "T" + startTimeOnly);
                LocalDateTime endLdt = LocalDateTime.parse(appointmentDate + "T" + endTimeOnly);

                startDate = Date.from(startLdt.atZone(ZoneId.systemDefault()).toInstant());
                endDate = Date.from(endLdt.atZone(ZoneId.systemDefault()).toInstant());
            } else {
                // Format: "09:00" - assume 30 minute slots
                LocalDateTime startLdt = LocalDateTime.parse(appointmentDate + "T" + appointmentTime);
                LocalDateTime endLdt = startLdt.plusMinutes(30);

                startDate = Date.from(startLdt.atZone(ZoneId.systemDefault()).toInstant());
                endDate = Date.from(endLdt.atZone(ZoneId.systemDefault()).toInstant());
            }

            // ✅ Check if customer already has an appointment at this time slot
            boolean hasConflict = appointmentFacade.findAll().stream()
                    .filter(appt -> appt != null && appt.getCustomer() != null
                            && appt.getAppointmentStartDatetime() != null)
                    .filter(appt -> appt.getCustomer().getId().equals(loggedCustomer.getId()))
                    .filter(appt -> !"REJECTED".equals(appt.getStatus()) && !"FINISHED".equals(appt.getStatus())) // Only
                                                                                                                  // active
                                                                                                                  // appointments
                    .anyMatch(appt -> {
                        // Check if appointment times overlap with the requested slot
                        Date existingStart = appt.getAppointmentStartDatetime();
                        Date existingEnd = appt.getAppointmentEndDatetime() != null ? appt.getAppointmentEndDatetime()
                                : new Date(existingStart.getTime() + 30 * 60 * 1000); // Add 30 minutes if no end time

                        // Check for overlap: startDate < existingEnd AND existingStart < endDate
                        return startDate.before(existingEnd) && existingStart.before(endDate);
                    });

            if (hasConflict) {
                // Reload doctors for the form
                List<Doctor> doctors = doctorFacade.findAll();
                request.setAttribute("doctors", doctors);
                request.setAttribute("customer", loggedCustomer);
                request.setAttribute("errorMessage",
                        "You already have an appointment scheduled during this time slot. Please select a different time.");
                // Preserve form data for user convenience
                request.setAttribute("selectedDate", appointmentDate);
                request.setAttribute("selectedDoctorId", doctorIdStr);
                request.setAttribute("selectedTimeSlot", appointmentTime);
                request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            // Create new appointment request
            Appointment appointment = new Appointment();
            appointment.setCustomer(loggedCustomer);
            appointment.setDoctor(selectedDoctor);

            // Set the parsed start and end dates
            appointment.setAppointmentStartDatetime(startDate);
            appointment.setAppointmentEndDatetime(endDate);

            appointment.setStatus("Pending"); // Set initial status as pending

            // Set audit fields
            Date now = new Date();
            appointment.setVersionTime(0);
            appointment.setCreationDatetime(now);
            appointment.setCreateBy(loggedCustomer.getName());
            appointment.setLastUpdateDatetime(now);
            appointment.setLastUpdateBy(loggedCustomer.getName());

            // Save appointment
            appointmentFacade.create(appointment);

            // Set success message and redirect
            session.setAttribute("successMessage",
                    "Appointment request submitted successfully! Please wait for confirmation.");
            response.sendRedirect(request.getContextPath() + "/customer/appointment/request");

        } catch (NumberFormatException e) {
            // Reload doctors for the form
            List<Doctor> doctors = doctorFacade.findAll();
            request.setAttribute("doctors", doctors);
            request.setAttribute("customer", loggedCustomer);
            request.setAttribute("errorMessage", "Invalid doctor selection.");
            request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            // Reload doctors for the form
            List<Doctor> doctors = doctorFacade.findAll();
            request.setAttribute("doctors", doctors);
            request.setAttribute("customer", loggedCustomer);
            request.setAttribute("errorMessage", "Error processing appointment request: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/customer/request-appointment.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

}

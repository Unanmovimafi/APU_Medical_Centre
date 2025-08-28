/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffCreateAppointment", urlPatterns = { "/staff/appointment/new" })
public class StaffCreateAppointment extends HttpServlet {

    @EJB
    private DoctorFacade doctorFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private AppointmentFacade appointmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Fetch all doctors & customers
        List<Doctor> doctors = doctorFacade.findAll();
        List<Customer> customers = customerFacade.findAll();

        request.setAttribute("doctors", doctors);
        request.setAttribute("customers", customers);

        // Forward to JSP
        request.setAttribute("pageContent", "/WEB-INF/views/staff/create-appointment.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String dateStr = request.getParameter("appointmentDate");
            String timeStr = request.getParameter("appointmentTime");
            String doctorIdStr = request.getParameter("doctorId");
            String customerIdStr = request.getParameter("customerId");

            if (dateStr == null || timeStr == null || doctorIdStr == null || customerIdStr == null) {
                request.setAttribute("errorMessage", "Please select all required fields.");
                doGet(request, response);
                return;
            }

            Integer doctorId = Integer.valueOf(doctorIdStr);
            Integer customerId = Integer.valueOf(customerIdStr);

            // ✅ Split into start & end time
            String[] timeParts = timeStr.split("-");
            String startTimeOnly = timeParts[0].trim(); // e.g. "09:00"
            String endTimeOnly = timeParts[1].trim(); // e.g. "09:30"

            // ✅ Combine with date
            LocalDateTime startLdt = LocalDateTime.parse(dateStr + "T" + startTimeOnly);
            LocalDateTime endLdt = LocalDateTime.parse(dateStr + "T" + endTimeOnly);

            Date startDate = Date.from(startLdt.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endLdt.atZone(ZoneId.systemDefault()).toInstant());

            // ✅ Fetch doctor & customer
            Doctor doctor = doctorFacade.find(doctorId);
            System.out.println("DEBUG => doctor: " + (doctor != null ? doctor.getUsername() : "NULL"));

            Customer customer = customerFacade.find(customerId);
            System.out.println("DEBUG => customer: " + (customer != null ? customer.getUsername() : "NULL"));

            if (doctor == null || customer == null) {
                request.setAttribute("errorMessage", "Invalid doctor or customer!");
                doGet(request, response);
                return;
            }

            // ✅ Check if customer already has an appointment at this time slot
            boolean hasConflict = appointmentFacade.findAll().stream()
                    .filter(appt -> appt != null && appt.getCustomer() != null
                            && appt.getAppointmentStartDatetime() != null)
                    .filter(appt -> appt.getCustomer().getId().equals(customer.getId()))
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
                request.setAttribute("errorMessage", "Customer '" + customer.getName()
                        + "' already has an appointment scheduled during this time slot. Please select a different time.");
                // Preserve form data for user convenience
                request.setAttribute("selectedDate", dateStr);
                request.setAttribute("selectedDoctorId", doctorIdStr);
                request.setAttribute("selectedCustomerId", customerIdStr);
                request.setAttribute("selectedTimeSlot", timeStr);
                doGet(request, response);
                return;
            }

            // ✅ Create appointment
            Appointment appointment = new Appointment();
            appointment.setVersionTime(1);
            appointment.setCreationDatetime(new Date());
            appointment.setCreateBy("STAFF"); // later use logged-in staff
            appointment.setLastUpdateDatetime(new Date());
            appointment.setLastUpdateBy("STAFF");
            appointment.setAppointmentStartDatetime(startDate);
            appointment.setAppointmentEndDatetime(endDate);
            appointment.setCharge(0L);
            appointment.setDoctor(doctor);
            appointment.setCustomer(customer);
            appointment.setStatus("PENDING");

            appointmentFacade.create(appointment);

            // Redirect to appointment list with success message
            request.getSession().setAttribute("successMessage", "Appointment created successfully!");
            response.sendRedirect(request.getContextPath() + "/staff/appointment/list");

        } catch (Exception txEx) {
            if (txEx.getClass().getSimpleName().contains("EJBTransaction")) {
                request.setAttribute("errorMessage", "❌ DB Transaction rolled back: " + txEx.getMessage());
                txEx.printStackTrace(); // Check GlassFish logs for details
                doGet(request, response);
            } else {
                request.setAttribute("errorMessage", "❌ Failed: " + txEx.getMessage());
                txEx.printStackTrace();
                doGet(request, response);
            }
        }
    }
}

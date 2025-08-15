/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;
import model.doctor.Doctor;

/**
 *
 * @author khong
 */
@WebServlet(name = "DoctorDashboard", urlPatterns = { "/doctor/dashboard" })
public class DoctorDashboard extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private CustomerFacade customerFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        Doctor doctor = (Doctor) session.getAttribute("doctorSession");

        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        try {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

            // Convert to Date objects for database queries
            Date monthStart = java.sql.Date.valueOf(firstDayOfMonth);
            Date todayStart = java.sql.Date.valueOf(currentDate);
            Date todayEnd = java.sql.Date.valueOf(currentDate.plusDays(1));

            // Get all appointments for this doctor
            List<Appointment> doctorAppointments = appointmentFacade.findByDoctor(doctor);

            // Get all customers
            List<Customer> allCustomers = customerFacade.findAll();

            // Calculate statistics for this doctor
            long appointmentsToday = doctorAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(todayStart) &&
                            a.getAppointmentStartDatetime().before(todayEnd))
                    .count();

            long appointmentsThisMonth = doctorAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(monthStart))
                    .count();

            // Get customers who have appointments this month with this doctor
            long customersThisMonth = doctorAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(monthStart))
                    .map(a -> a.getCustomer().getId())
                    .distinct()
                    .count();

            // Get newest 5 pending appointments for this doctor today
            List<Appointment> todayPending = doctorAppointments.stream()
                    .filter(a -> "PENDING".equals(a.getStatus()) &&
                            a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(todayStart) &&
                            a.getAppointmentStartDatetime().before(todayEnd))
                    .sorted((a1, a2) -> a2.getCreationDatetime().compareTo(a1.getCreationDatetime()))
                    .limit(5)
                    .collect(Collectors.toList());

            // Get newest 5 customers who have appointments with this doctor
            List<Customer> newestCustomers = doctorAppointments.stream()
                    .map(Appointment::getCustomer)
                    .distinct()
                    .sorted((c1, c2) -> c2.getCreationDatetime().compareTo(c1.getCreationDatetime()))
                    .limit(5)
                    .collect(Collectors.toList());

            // Set attributes for JSP
            request.setAttribute("appointmentsToday", appointmentsToday);
            request.setAttribute("appointmentsThisMonth", appointmentsThisMonth);
            request.setAttribute("customersThisMonth", customersThisMonth);
            request.setAttribute("todayPending", todayPending);
            request.setAttribute("newestCustomers", newestCustomers);
            request.setAttribute("todayPendingCount", todayPending.size());
            request.setAttribute("newestCustomersCount", newestCustomers.size());

        } catch (Exception e) {
            e.printStackTrace();
            // Set default values in case of error
            request.setAttribute("appointmentsToday", 0L);
            request.setAttribute("appointmentsThisMonth", 0L);
            request.setAttribute("customersThisMonth", 0L);
            request.setAttribute("todayPending", java.util.Collections.emptyList());
            request.setAttribute("newestCustomers", java.util.Collections.emptyList());
            request.setAttribute("todayPendingCount", 0);
            request.setAttribute("newestCustomersCount", 0);
        }

        request.setAttribute("user", doctor);
        request.setAttribute("role", "DOCTOR");
        request.setAttribute("pageContent", "/WEB-INF/views/doctor/dashboard.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

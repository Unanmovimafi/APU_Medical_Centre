/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.counterstaff.CounterStaff;
import model.customer.Customer;
import model.customer.CustomerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffDashboard", urlPatterns = { "/staff/dashboard" })
public class StaffDashboard extends HttpServlet {

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
        CounterStaff staff = (CounterStaff) session.getAttribute("counterStaffSession");

        if (staff == null) {
            response.sendRedirect(request.getContextPath() + "/staff/login");
            return;
        }

        try {
            // Get current date and time
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
            LocalDateTime startOfToday = currentDate.atStartOfDay();
            LocalDateTime now = LocalDateTime.now();

            // Convert to Date objects for database queries
            Date monthStart = java.sql.Date.valueOf(firstDayOfMonth);
            Date todayStart = java.sql.Timestamp.valueOf(startOfToday);
            Date currentTime = java.sql.Timestamp.valueOf(now);

            // Get statistics
            List<Appointment> allAppointments = appointmentFacade.findAll();
            List<Customer> allCustomers = customerFacade.findAll();

            // Calculate statistics
            long appointmentsThisMonth = allAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(monthStart))
                    .count();

            long newCustomersThisMonth = allCustomers.stream()
                    .filter(c -> c.getCreationDatetime() != null &&
                            !c.getCreationDatetime().before(monthStart))
                    .count();

            long pendingAppointments = allAppointments.stream()
                    .filter(a -> "PENDING".equals(a.getStatus()))
                    .count();

            // Get newest 5 pending appointments
            List<Appointment> newestPending = allAppointments.stream()
                    .filter(a -> "PENDING".equals(a.getStatus()))
                    .sorted((a1, a2) -> a2.getCreationDatetime().compareTo(a1.getCreationDatetime()))
                    .limit(5)
                    .collect(Collectors.toList());

            // Get today's upcoming appointments (after current time)
            List<Appointment> todayUpcoming = allAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            java.sql.Date.valueOf(currentDate)
                                    .equals(new java.sql.Date(a.getAppointmentStartDatetime().getTime())))
                    .filter(a -> {
                        // Check if appointment time is after current time
                        return a.getAppointmentStartDatetime().after(currentTime);
                    })
                    .sorted((a1, a2) -> a1.getAppointmentStartDatetime().compareTo(a2.getAppointmentStartDatetime()))
                    .collect(Collectors.toList());

            // Set attributes for JSP
            request.setAttribute("appointmentsThisMonth", appointmentsThisMonth);
            request.setAttribute("newCustomersThisMonth", newCustomersThisMonth);
            request.setAttribute("pendingAppointments", pendingAppointments);
            request.setAttribute("newestPending", newestPending);
            request.setAttribute("todayUpcoming", todayUpcoming);
            request.setAttribute("todayUpcomingCount", todayUpcoming.size());

        } catch (Exception e) {
            e.printStackTrace();
            // Set default values in case of error
            request.setAttribute("appointmentsThisMonth", 0L);
            request.setAttribute("newCustomersThisMonth", 0L);
            request.setAttribute("pendingAppointments", 0L);
            request.setAttribute("newestPending", java.util.Collections.emptyList());
            request.setAttribute("todayUpcoming", java.util.Collections.emptyList());
            request.setAttribute("todayUpcomingCount", 0);
        }

        request.setAttribute("user", staff);
        request.setAttribute("role", "staff");
        request.setAttribute("pageContent", "/WEB-INF/views/staff/dashboard.jsp");
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

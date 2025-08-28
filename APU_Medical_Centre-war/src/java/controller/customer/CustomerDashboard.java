/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.comment.Comment;
import model.comment.CommentFacade;
import model.customer.Customer;

/**
 *
 * @author khong
 */
@WebServlet(name = "CustomerDashboard", urlPatterns = { "/customer/dashboard" })
public class CustomerDashboard extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private CommentFacade commentFacade;

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
        Customer customer = (Customer) session.getAttribute("customerSession");

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Get current date and time
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfToday = currentDate.atStartOfDay();
            LocalDateTime now = LocalDateTime.now();

            // Convert to Date objects for database queries
            Date todayStart = java.sql.Timestamp.valueOf(startOfToday);
            Date currentTime = java.sql.Timestamp.valueOf(now);

            // Get customer's appointments and comments
            List<Appointment> customerAppointments = appointmentFacade.findAll().stream()
                    .filter(a -> a.getCustomer() != null && a.getCustomer().getId().equals(customer.getId()))
                    .collect(Collectors.toList());

            System.out.println("DEBUG: Customer ID: " + customer.getId());
            System.out.println("DEBUG: Total customer appointments: " + customerAppointments.size());
            customerAppointments.forEach(a -> {
                System.out.println("DEBUG: Appointment - Date: " + a.getAppointmentStartDatetime() +
                        ", Status: " + a.getStatus() +
                        ", Doctor: " + (a.getDoctor() != null ? a.getDoctor().getName() : "null"));
            });

            List<Comment> customerComments = commentFacade.findAll().stream()
                    .filter(c -> c.getCustomer() != null && c.getCustomer().getId().equals(customer.getId()))
                    .collect(Collectors.toList());

            // Calculate statistics
            long totalAppointments = customerAppointments.size();

            long pendingAppointments = customerAppointments.stream()
                    .filter(a -> "PENDING".equals(a.getStatus()))
                    .count();

            long totalComments = customerComments.size();

            // Get latest 5 appointments
            List<Appointment> latestAppointments = customerAppointments.stream()
                    .sorted((a1, a2) -> a2.getCreationDatetime().compareTo(a1.getCreationDatetime()))
                    .limit(5)
                    .collect(Collectors.toList());

            // Get today's appointments
            System.out.println("DEBUG: Current date: " + currentDate);
            System.out.println("DEBUG: Customer appointments count: " + customerAppointments.size());

            List<Appointment> todayAppointments = customerAppointments.stream()
                    .filter(a -> {
                        if (a.getAppointmentStartDatetime() == null) {
                            System.out.println("DEBUG: Skipping appointment with null start time");
                            return false;
                        }

                        // Convert appointment date to LocalDate for comparison
                        LocalDate appointmentDate = a.getAppointmentStartDatetime().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        System.out.println("DEBUG: Appointment date: " + appointmentDate + ", Today: " + currentDate
                                + ", Match: " + appointmentDate.equals(currentDate));
                        return appointmentDate.equals(currentDate);
                    })
                    .sorted((a1, a2) -> a1.getAppointmentStartDatetime().compareTo(a2.getAppointmentStartDatetime()))
                    .collect(Collectors.toList());

            System.out.println("DEBUG: Today's appointments found: " + todayAppointments.size());

            // Set attributes for JSP
            request.setAttribute("totalAppointments", totalAppointments);
            request.setAttribute("pendingAppointments", pendingAppointments);
            request.setAttribute("totalComments", totalComments);
            request.setAttribute("latestAppointments", latestAppointments);
            request.setAttribute("todayAppointments", todayAppointments);
            request.setAttribute("todayAppointmentsCount", todayAppointments.size());

        } catch (Exception e) {
            e.printStackTrace();
            // Set default values in case of error
            request.setAttribute("totalAppointments", 0L);
            request.setAttribute("pendingAppointments", 0L);
            request.setAttribute("totalComments", 0L);
            request.setAttribute("latestAppointments", java.util.Collections.emptyList());
            request.setAttribute("todayAppointments", java.util.Collections.emptyList());
            request.setAttribute("todayAppointmentsCount", 0);
        }

        request.setAttribute("user", customer);
        request.setAttribute("role", "CUSTOMER");
        request.setAttribute("pageContent", "/WEB-INF/views/customer/dashboard.jsp");
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

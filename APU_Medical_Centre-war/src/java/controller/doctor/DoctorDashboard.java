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
import model.doctor.DoctorFacade;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.comment.Comment;
import model.comment.CommentFacade;

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

        @EJB
        private DoctorFacade doctorFacade;

        @EJB
        private CounterStaffFacade counterStaffFacade;

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
                Doctor doctor = (Doctor) session.getAttribute("doctorSession");

                if (doctor == null) {
                        response.sendRedirect(request.getContextPath() + "/doctor/login");
                        return;
                }

                try {
                        // Get current date
                        LocalDate currentDate = LocalDate.now();
                        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
                        Date monthStart = java.sql.Date.valueOf(firstDayOfMonth);
                        Date todayStart = java.sql.Date.valueOf(currentDate);

                        // Get all appointments and customers for this doctor only
                        List<Appointment> doctorAppointments = appointmentFacade.findAll().stream()
                                        .filter(a -> a.getDoctor() != null
                                                        && a.getDoctor().getId().equals(doctor.getId()))
                                        .collect(Collectors.toList());

                        List<Customer> doctorCustomers = appointmentFacade.findCustomersByDoctor(doctor);

                        System.out.println("DEBUG: Doctor ID: " + doctor.getId());
                        System.out.println("DEBUG: Doctor's appointments: " + doctorAppointments.size());
                        System.out.println("DEBUG: Doctor's customers: " + doctorCustomers.size());
                        System.out.println("DEBUG: Current date: " + currentDate);

                        // Calculate appointments for today
                        long appointmentsToday = doctorAppointments.stream()
                                        .filter(a -> a.getAppointmentStartDatetime() != null &&
                                                        java.sql.Date.valueOf(currentDate)
                                                                        .equals(new java.sql.Date(
                                                                                        a.getAppointmentStartDatetime()
                                                                                                        .getTime())))
                                        .count();

                        // Total Appointment This Month
                        long appointmentsThisMonth = doctorAppointments.stream()
                                        .filter(a -> a.getAppointmentStartDatetime() != null &&
                                                        !a.getAppointmentStartDatetime().before(monthStart))
                                        .count();

                        // Customers This Month
                        long customersThisMonth = doctorCustomers.stream()
                                        .filter(c -> c.getCreationDatetime() != null &&
                                                        !c.getCreationDatetime().before(monthStart))
                                        .count();

                        // Today's Pending Appointments
                        List<Appointment> todayPending = doctorAppointments.stream()
                                        .filter(a -> a.getAppointmentStartDatetime() != null &&
                                                        java.sql.Date.valueOf(currentDate)
                                                                        .equals(new java.sql.Date(
                                                                                        a.getAppointmentStartDatetime()
                                                                                                        .getTime()))
                                                        &&
                                                        "PENDING".equals(a.getStatus()))
                                        .sorted((a1, a2) -> a1.getAppointmentStartDatetime()
                                                        .compareTo(a2.getAppointmentStartDatetime()))
                                        .limit(5)
                                        .collect(Collectors.toList());

                        // Newest 5 Customers (sorted by creationDatetime desc)
                        List<Customer> newestCustomers = doctorCustomers.stream()
                                        .sorted((c1, c2) -> c2.getCreationDatetime()
                                                        .compareTo(c1.getCreationDatetime()))
                                        .limit(5)
                                        .collect(Collectors.toList());

                        // Set attributes for JSP
                        System.out.println("DEBUG: Setting attributes:");
                        System.out.println("  - appointmentsToday: " + appointmentsToday);
                        System.out.println("  - appointmentsThisMonth: " + appointmentsThisMonth);
                        System.out.println("  - customersThisMonth: " + customersThisMonth);
                        System.out.println("  - todayPending count: " + todayPending.size());
                        System.out.println("  - newestCustomers count: " + newestCustomers.size());

                        request.setAttribute("appointmentsToday", appointmentsToday);
                        request.setAttribute("appointmentsThisMonth", appointmentsThisMonth);
                        request.setAttribute("customersThisMonth", customersThisMonth);
                        request.setAttribute("todayPending", todayPending);
                        request.setAttribute("todayPendingCount", todayPending.size());
                        request.setAttribute("newestCustomers", newestCustomers);
                        request.setAttribute("newestCustomersCount", newestCustomers.size());

                } catch (Exception e) {
                        e.printStackTrace();
                        // Set default values in case of error
                        request.setAttribute("appointmentsToday", 0L);
                        request.setAttribute("appointmentsThisMonth", 0L);
                        request.setAttribute("customersThisMonth", 0L);
                        request.setAttribute("todayPending", java.util.Collections.emptyList());
                        request.setAttribute("todayPendingCount", 0);
                        request.setAttribute("newestCustomers", java.util.Collections.emptyList());
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

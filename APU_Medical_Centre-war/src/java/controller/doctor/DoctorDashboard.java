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

                        // Total Employee = all doctors + all counter staff
                        int totalDoctors = doctorFacade.findAll().size();
                        int totalStaff = counterStaffFacade.findAll().size();
                        int totalEmployee = totalDoctors + totalStaff;

                        // Total Appointment This Month (all appointments in system this month)
                        List<Appointment> allAppointments = appointmentFacade.findAll();
                        long totalAppointmentThisMonth = allAppointments.stream()
                                        .filter(a -> a.getAppointmentStartDatetime() != null &&
                                                        !a.getAppointmentStartDatetime().before(monthStart))
                                        .count();

                        // Total Comments
                        int totalComments = commentFacade.findAll().size();

                        // Latest 5 Employees (doctors + staff, sorted by creationDatetime desc)
                        List<Object> allEmployees = new java.util.ArrayList<>();
                        allEmployees.addAll(doctorFacade.findAll());
                        allEmployees.addAll(counterStaffFacade.findAll());
                        List<Object> latestEmployees = allEmployees.stream()
                                        .sorted((e1, e2) -> {
                                                Date d1 = null, d2 = null;
                                                try {
                                                        d1 = (Date) e1.getClass().getMethod("getCreationDatetime")
                                                                        .invoke(e1);
                                                        d2 = (Date) e2.getClass().getMethod("getCreationDatetime")
                                                                        .invoke(e2);
                                                } catch (Exception ex) {
                                                }
                                                if (d1 == null && d2 == null)
                                                        return 0;
                                                if (d1 == null)
                                                        return 1;
                                                if (d2 == null)
                                                        return -1;
                                                return d2.compareTo(d1);
                                        })
                                        .limit(5)
                                        .collect(Collectors.toList());

                        // Latest 5 Appointments (sorted by creationDatetime desc)
                        List<Appointment> latestAppointments = allAppointments.stream()
                                        .sorted((a1, a2) -> a2.getCreationDatetime()
                                                        .compareTo(a1.getCreationDatetime()))
                                        .limit(5)
                                        .collect(Collectors.toList());

                        // Set attributes for JSP
                        request.setAttribute("totalEmployee", totalEmployee);
                        request.setAttribute("totalAppointmentThisMonth", totalAppointmentThisMonth);
                        request.setAttribute("totalComments", totalComments);
                        request.setAttribute("latestEmployees", latestEmployees);
                        request.setAttribute("latestAppointments", latestAppointments);

                } catch (Exception e) {
                        e.printStackTrace();
                        // Set default values in case of error
                        request.setAttribute("totalEmployee", 0);
                        request.setAttribute("totalAppointmentThisMonth", 0L);
                        request.setAttribute("totalComments", 0);
                        request.setAttribute("latestEmployees", java.util.Collections.emptyList());
                        request.setAttribute("latestAppointments", java.util.Collections.emptyList());
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

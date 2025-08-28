/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.comment.Comment;
import model.comment.CommentFacade;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import model.manager.Manager;

/**
 *
 * @author khong
 */
@WebServlet(name = "ManagerDashboard", urlPatterns = { "/manager/dashboard" })
public class ManagerDashboard extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private CommentFacade commentFacade;

    @EJB
    private CounterStaffFacade counterStaffFacade;

    @EJB
    private DoctorFacade doctorFacade;

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
        Manager manager = (Manager) session.getAttribute("managerSession");

        if (manager == null) {
            response.sendRedirect(request.getContextPath() + "/manager/login");
            return;
        }

        try {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
            Date monthStart = java.sql.Date.valueOf(firstDayOfMonth);

            // Total Employee = all doctors + all counter staff
            List<Doctor> allDoctors = doctorFacade.findAll();
            List<CounterStaff> allStaff = counterStaffFacade.findAll();
            int totalEmployee = allDoctors.size() + allStaff.size();

            // Total Appointment This Month
            List<Appointment> allAppointments = appointmentFacade.findAll();
            long totalAppointmentThisMonth = allAppointments.stream()
                    .filter(a -> a.getAppointmentStartDatetime() != null &&
                            !a.getAppointmentStartDatetime().before(monthStart))
                    .count();

            // Total Comments
            List<Comment> allComments = commentFacade.findAll();
            int totalComments = allComments.size();

            // Latest 5 Employees (doctors + staff, sorted by creationDatetime desc)
            List<Object> allEmployees = new ArrayList<>();
            allEmployees.addAll(allDoctors);
            allEmployees.addAll(allStaff);
            List<Object> latestEmployees = allEmployees.stream()
                    .sorted((e1, e2) -> {
                        Date d1 = null, d2 = null;
                        try {
                            if (e1 instanceof Doctor) {
                                d1 = ((Doctor) e1).getCreationDatetime();
                            } else if (e1 instanceof CounterStaff) {
                                d1 = ((CounterStaff) e1).getCreationDatetime();
                            }
                            if (e2 instanceof Doctor) {
                                d2 = ((Doctor) e2).getCreationDatetime();
                            } else if (e2 instanceof CounterStaff) {
                                d2 = ((CounterStaff) e2).getCreationDatetime();
                            }
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
                    .sorted((a1, a2) -> a2.getCreationDatetime().compareTo(a1.getCreationDatetime()))
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
            request.setAttribute("latestEmployees", new ArrayList<>());
            request.setAttribute("latestAppointments", new ArrayList<>());
        }

        request.setAttribute("user", manager);
        request.setAttribute("role", "MANAGER");
        request.setAttribute("pageContent", "/WEB-INF/views/manager/dashboard.jsp");
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

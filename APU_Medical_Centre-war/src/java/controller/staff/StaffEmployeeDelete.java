/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffEmployeeDelete", urlPatterns = { "/staff/employee/delete" })
public class StaffEmployeeDelete extends HttpServlet {

    @EJB
    CounterStaffFacade counterStaffFacade;

    @EJB
    DoctorFacade doctorFacade;

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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");

            if ("Doctor".equals(role)) {
                doctorFacade.remove(doctorFacade.find(id));
                request.getSession().setAttribute("employeeDeletionCompleted", true);
            } else if ("Counter Staff".equals(role)) {
                counterStaffFacade.remove(counterStaffFacade.find(id));
                request.getSession().setAttribute("employeeDeletionCompleted", true);
            }

        } catch (Exception e) {
            // Optional logging
        }
        response.sendRedirect(request.getContextPath() + "/staff/employee/list");
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
        // For backward compatibility, redirect GET requests to use POST method
        doPost(request, response);
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
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            String role = request.getParameter("role");

            if ("Doctor".equals(role)) {
                Doctor doctor = doctorFacade.find(id);
                if (doctor != null) {
                    String name = doctor.getName();
                    doctorFacade.remove(doctor);
                    request.getSession().setAttribute("modalMessage",
                            "<strong>" + name + "</strong> has been successfully deleted.");
                } else {
                    request.getSession().setAttribute("modalMessage", "Doctor not found.");
                }
            } else if ("Counter Staff".equals(role)) {
                CounterStaff staff = counterStaffFacade.find(id);
                if (staff != null) {
                    String name = staff.getName();
                    counterStaffFacade.remove(staff);
                    request.getSession().setAttribute("modalMessage",
                            "<strong>" + name + "</strong> has been successfully deleted.");
                } else {
                    request.getSession().setAttribute("modalMessage", "Counter Staff not found.");
                }
            } else {
                request.getSession().setAttribute("modalMessage", "Invalid employee role.");
            }
            response.sendRedirect(request.getContextPath() + "/staff/employee/list");

        } catch (IOException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID or role");
        }
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

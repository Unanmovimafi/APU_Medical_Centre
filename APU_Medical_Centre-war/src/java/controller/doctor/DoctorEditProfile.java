/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import helper.DateTimeHelper;
import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 *
 * @author System
 */
@WebServlet(name = "DoctorEditProfile", urlPatterns = { "/doctor/edit-profile" })
public class DoctorEditProfile extends HttpServlet {

    @EJB
    private DoctorFacade doctorFacade;

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
        
        try {
            HttpSession session = request.getSession(false);
            Doctor loggedDoctor = (Doctor) session.getAttribute("doctorSession");

            if (loggedDoctor == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Refresh from database
            Doctor doctor = doctorFacade.find(loggedDoctor.getId());
            request.setAttribute("doctor", doctor);
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/edit-profile.jsp");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading profile: " + e.getMessage());
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
        
        try {
            HttpSession session = request.getSession(false);
            Doctor loggedDoctor = (Doctor) session.getAttribute("doctorSession");

            if (loggedDoctor == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Doctor doctor = doctorFacade.find(loggedDoctor.getId());
            if (doctor == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get parameters
            String newName = request.getParameter("name");
            String newEmail = request.getParameter("email");
            String newPhone = request.getParameter("phoneNumber");
            String newPassword = request.getParameter("newPassword");
            String oldPassword = request.getParameter("oldPassword");

            boolean isPasswordChanged = false;
            boolean isDetailChanged = false;

            // Check if new password is entered
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (oldPassword == null || !oldPassword.equals(doctor.getPassword())) {
                    request.setAttribute("errorMessage", "Old password is incorrect.");
                    request.setAttribute("doctor", doctor);
                    request.setAttribute("pageContent", "/WEB-INF/views/doctor/edit-profile.jsp");
                    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                    return;
                }
                doctor.setPassword(newPassword.trim());
                isPasswordChanged = true;
            }

            // Check if any personal detail changed
            if (!newName.equals(doctor.getName()) || 
                !newEmail.equals(doctor.getEmail()) || 
                !newPhone.equals(doctor.getPhoneNumber())) {
                isDetailChanged = true;
            }

            // Always set new values
            doctor.setName(newName);
            doctor.setEmail(newEmail);
            doctor.setPhoneNumber(newPhone);
            doctor.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            doctor.setLastUpdateBy(doctor.getUsername());
            doctor.setVersionTime(doctor.getVersionTime() + 1);

            doctorFacade.edit(doctor);
            session.setAttribute("doctorSession", doctor);

            // Set success message
            String successMessage = "";
            if (isPasswordChanged && isDetailChanged) {
                successMessage = "Your details and password have been updated.";
            } else if (isPasswordChanged) {
                successMessage = "Your password has been changed.";
            } else if (isDetailChanged) {
                successMessage = "Your details have been updated.";
            }

            session.setAttribute("successMessage", successMessage);

            response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error saving profile: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/edit-profile.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Doctor Edit Profile Servlet";
    }
}

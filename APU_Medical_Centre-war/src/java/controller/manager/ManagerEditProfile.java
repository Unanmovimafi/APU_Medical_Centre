/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

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
import model.manager.Manager;
import model.manager.ManagerFacade;

/**
 *
 * @author System
 */
@WebServlet(name = "ManagerEditProfile", urlPatterns = { "/manager/edit-profile" })
public class ManagerEditProfile extends HttpServlet {

    @EJB
    private ManagerFacade managerFacade;

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
            Manager loggedManager = (Manager) session.getAttribute("managerSession");

            if (loggedManager == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Refresh from database
            Manager manager = managerFacade.find(loggedManager.getId());
            request.setAttribute("manager", manager);
            request.setAttribute("pageContent", "/WEB-INF/views/manager/edit-profile.jsp");

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
            Manager loggedManager = (Manager) session.getAttribute("managerSession");

            if (loggedManager == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Manager manager = managerFacade.find(loggedManager.getId());
            if (manager == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get parameters
            String newName = request.getParameter("name");
            String newEmail = request.getParameter("email");
            String newPhone = request.getParameter("phoneNumber");
            String newGender = request.getParameter("gender");
            String newPassword = request.getParameter("newPassword");
            String oldPassword = request.getParameter("oldPassword");

            boolean isPasswordChanged = false;
            boolean isDetailChanged = false;

            // Check if new password is entered
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (oldPassword == null || !oldPassword.equals(manager.getPassword())) {
                    request.setAttribute("errorMessage", "Old password is incorrect.");
                    request.setAttribute("manager", manager);
                    request.setAttribute("pageContent", "/WEB-INF/views/manager/edit-profile.jsp");
                    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                    return;
                }
                manager.setPassword(newPassword.trim());
                isPasswordChanged = true;
            }

            // Check if any personal detail changed
            if (!newName.equals(manager.getName()) ||
                    !newEmail.equals(manager.getEmail()) ||
                    !newPhone.equals(manager.getPhoneNumber()) ||
                    !newGender.equals(manager.getGender())) {
                isDetailChanged = true;
            }

            // Always set new values
            manager.setName(newName);
            manager.setEmail(newEmail);
            manager.setPhoneNumber(newPhone);
            manager.setGender(newGender);
            manager.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            manager.setLastUpdateBy(manager.getUsername());
            manager.setVersionTime(manager.getVersionTime() + 1);

            managerFacade.edit(manager);
            session.setAttribute("managerSession", manager);

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

            response.sendRedirect(request.getContextPath() + "/manager/edit-profile");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error saving profile: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/manager/edit-profile.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Manager Edit Profile Servlet";
    }
}

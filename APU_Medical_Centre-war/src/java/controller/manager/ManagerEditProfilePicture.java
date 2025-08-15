/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Date;
import model.manager.Manager;
import model.manager.ManagerFacade;
import helper.DateTimeHelper;

/**
 *
 * @author System
 */
@WebServlet(name = "ManagerEditProfilePicture", urlPatterns = { "/manager/edit-profile-picture" })
@MultipartConfig
public class ManagerEditProfilePicture extends HttpServlet {

    @EJB
    private ManagerFacade managerFacade;

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

            Integer id = Integer.valueOf(request.getParameter("id"));
            Manager manager = managerFacade.find(id);

            if (manager == null || !manager.getId().equals(loggedManager.getId())) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream fileContent = filePart.getInputStream()) {
                    byte[] imageBytes = fileContent.readAllBytes();
                    String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

                    manager.setProfilePicture("data:image/jpeg;base64," + base64Image);
                    manager.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                    manager.setLastUpdateBy(manager.getUsername());
                    manager.setVersionTime(manager.getVersionTime() + 1);

                    managerFacade.edit(manager);
                    session.setAttribute("managerSession", manager);
                    session.setAttribute("successMessage", "Profile picture uploaded successfully.");
                }
            } else {
                session.setAttribute("errorMessage", "No file selected.");
            }

            response.sendRedirect(request.getContextPath() + "/manager/edit-profile");

        } catch (Exception e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("errorMessage", "Error uploading picture: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/manager/edit-profile");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manager Edit Profile Picture Servlet";
    }
}

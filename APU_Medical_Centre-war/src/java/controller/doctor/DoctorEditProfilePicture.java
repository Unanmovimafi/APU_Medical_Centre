/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.Base64;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import helper.DateTimeHelper;

/**
 *
 * @author khong
 */
@WebServlet(name = "DoctorEditProfilePicture", urlPatterns = { "/doctor/edit-profile-picture" })
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB max file size
public class DoctorEditProfilePicture extends HttpServlet {

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
        response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");
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

        HttpSession session = request.getSession(false);
        Doctor doctor = (Doctor) session.getAttribute("doctorSession");

        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        try {
            // Get the uploaded file
            Part filePart = request.getPart("profilePicture");

            if (filePart == null || filePart.getSize() == 0) {
                session.setAttribute("errorMessage", "Please select a file to upload.");
                response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");
                return;
            }

            // Check file size (5MB limit)
            if (filePart.getSize() > 5 * 1024 * 1024) {
                session.setAttribute("errorMessage", "File size must be less than 5MB.");
                response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");
                return;
            }

            // Check file type
            String contentType = filePart.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                session.setAttribute("errorMessage", "Please upload a valid image file.");
                response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");
                return;
            }

            // Convert file to base64
            byte[] fileBytes = filePart.getInputStream().readAllBytes();
            String base64Image = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(fileBytes);

            // Update doctor's profile picture
            doctor.setProfilePicture(base64Image);
            doctor.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());

            doctorFacade.edit(doctor);

            // Update session with the new doctor data
            session.setAttribute("doctorSession", doctor);
            session.setAttribute("successMessage", "Profile picture updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage",
                    "An error occurred while uploading the profile picture. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/doctor/edit-profile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Doctor Profile Picture Upload Servlet";
    }// </editor-fold>

}

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
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Date;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffEditProfilePicture", urlPatterns = {"/staff/edit-profile-picture"})
public class StaffEditProfilePicture extends HttpServlet {

    @EJB
    private CounterStaffFacade counterStaffFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffEditProfilePicture</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffEditProfilePicture at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            CounterStaff staff = counterStaffFacade.find(id);

            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream fileContent = filePart.getInputStream()) {
                    byte[] imageBytes = fileContent.readAllBytes();
                    String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

                    staff.setProfilePicture(base64Image);
                    staff.setLastUpdateDatetime(new Date());
                    staff.setLastUpdateBy("STAFF");

                    counterStaffFacade.edit(staff);
                    request.setAttribute("successMessage", "Profile picture uploaded successfully.");
                }
            } else {
                request.setAttribute("errorMessage", "No file selected.");
            }

            request.setAttribute("staff", staff);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/edit-profile.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error uploading picture: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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

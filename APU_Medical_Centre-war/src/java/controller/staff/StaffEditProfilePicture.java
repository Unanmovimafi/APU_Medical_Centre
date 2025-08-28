package controller.staff;

import helper.DateTimeHelper;
import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffEditProfilePicture", urlPatterns = { "/staff/edit-profile-picture" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
public class StaffEditProfilePicture extends HttpServlet {

    @EJB
    private CounterStaffFacade counterStaffFacade;

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
            CounterStaff loggedStaff = (CounterStaff) session.getAttribute("counterStaffSession");

            if (loggedStaff == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Refresh from database
            CounterStaff staff = counterStaffFacade.find(loggedStaff.getId());
            request.setAttribute("staff", staff);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/edit-profile.jsp");

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
            CounterStaff loggedStaff = (CounterStaff) session.getAttribute("counterStaffSession");

            if (loggedStaff == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            CounterStaff staff = counterStaffFacade.find(loggedStaff.getId());
            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                // Validate file type
                String contentType = filePart.getContentType();
                if (!contentType.startsWith("image/")) {
                    request.setAttribute("errorMessage", "Please upload a valid image file.");
                    request.setAttribute("staff", staff);
                    request.setAttribute("pageContent", "/WEB-INF/views/staff/edit-profile.jsp");
                    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                    return;
                }

                try (InputStream fileContent = filePart.getInputStream()) {
                    byte[] imageBytes = fileContent.readAllBytes();
                    String base64Image = "data:" + contentType + ";base64,"
                            + java.util.Base64.getEncoder().encodeToString(imageBytes);

                    staff.setProfilePicture(base64Image);
                    staff.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                    staff.setLastUpdateBy(staff.getUsername());
                    staff.setVersionTime(staff.getVersionTime() + 1);

                    counterStaffFacade.edit(staff);
                    session.setAttribute("counterStaffSession", staff);

                    session.setAttribute("successMessage", "Profile picture updated successfully.");
                    response.sendRedirect(request.getContextPath() + "/staff/edit-profile");
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "No file selected or file is empty.");
            }

            request.setAttribute("staff", staff);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/edit-profile.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error uploading picture: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/staff/edit-profile.jsp");
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
        return "Counter Staff Edit Profile Picture Servlet";
    }
}

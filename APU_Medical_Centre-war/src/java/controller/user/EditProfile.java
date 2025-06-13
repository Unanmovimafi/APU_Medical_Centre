/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import helper.DateTimeHelper;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.customerdetail.CustomerDetail;
import model.customerdetail.CustomerDetailFacade;
import model.staffdetail.StaffDetail;
import model.user.User;
import model.user.UserFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/edit-profile"})
public class EditProfile extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(EditProfile.class.getName());

    @EJB
    private UserFacade userFacade;

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
            out.println("<title>Servlet EditProfile2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditProfile2 at " + request.getContextPath() + "</h1>");
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

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("userSession");
        String role = user.getRole().getCode();
        request.setAttribute("user", user);
        request.setAttribute("role", role);
        request.setAttribute("pageContent", "/WEB-INF/doctor/edit-profile.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
        dispatcher.forward(request, response);

        LOGGER.info("Exiting doGet method."+ role);
        LOGGER.info("Name"+ user.getStaffDetail().getName());
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
        response.setContentType("text/html;charset=UTF-8");
        try {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String dateOfBirthStr = request.getParameter("dateOfJoining");

            // Convert date string to java.sql.Date
            java.sql.Date dateOfBirth = null;
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                dateOfBirth = java.sql.Date.valueOf(dateOfBirthStr);
            }

            // Assuming user session holds the logged-in user object
            HttpSession session = request.getSession();
            User userSession = (User) session.getAttribute("userSession");

            if (userSession == null) {
                request.setAttribute("errorMessage", "User not logged in.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                int id = userSession.getId();
                User user = userFacade.find(id);
                if (user != null) {
                    user.getStaffDetail().setName(name);
                    user.getStaffDetail().setEmail(email);
                    user.getStaffDetail().setPhoneNumber(phoneNumber);
//                    user.getStaffDetail().setDateOfBirth(dateOfBirth);

                    user.getStaffDetail().setVersionTime(user.getVersionTime() + 1);
                    user.getStaffDetail().setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                    user.getStaffDetail().setLastUpdateBy(userSession.getUsername());

                    userFacade.edit(user);
                    session.setAttribute("userSession", user);
                }
                LOGGER.info("Run do post");
                
            }
            response.sendRedirect(request.getContextPath() + "/edit-profile");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating profile: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/edit-profile.jsp").forward(request, response);
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
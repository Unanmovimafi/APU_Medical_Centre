/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import helper.DateTimeHelper;
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
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffEditProfile", urlPatterns = {"/staff/edit-profile"})
public class StaffEditProfile extends HttpServlet {

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
            out.println("<title>Servlet StaffEditProfile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffEditProfile at " + request.getContextPath() + "</h1>");
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
        try {
            HttpSession session = request.getSession(false);
            CounterStaff staff = (CounterStaff) session.getAttribute("counterStaffSession");

            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Refresh from database
            CounterStaff refreshed = counterStaffFacade.find(staff.getId());
            request.setAttribute("user", refreshed);
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            CounterStaff sessionStaff = (CounterStaff) session.getAttribute("userSession");

            if (sessionStaff == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            CounterStaff staff = counterStaffFacade.find(sessionStaff.getId());
            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            staff.setName(request.getParameter("name"));
            staff.setEmail(request.getParameter("email"));
            staff.setPhoneNumber(request.getParameter("phoneNumber"));
            staff.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            staff.setLastUpdateBy(staff.getUsername());
            staff.setVersionTime(staff.getVersionTime() + 1);

            counterStaffFacade.edit(staff);
            session.setAttribute("userSession", staff); // update session object
            response.sendRedirect(request.getContextPath() + "/staff/edit-profile");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error saving profile: " + e.getMessage());
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
        return "Short description";
    }// </editor-fold>

}

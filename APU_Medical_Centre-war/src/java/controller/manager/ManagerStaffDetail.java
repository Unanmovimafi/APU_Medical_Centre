/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.staff.*;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.comment.Comment;
import model.comment.CommentFacade;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "ManagerStaffDetail", urlPatterns = { "/manager/staff/detail" })
public class ManagerStaffDetail extends HttpServlet {

    @EJB
    private CounterStaffFacade staffFacade;

    @EJB
    private CommentFacade commentFacade;

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffStaffDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffStaffDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            CounterStaff staff = staffFacade.find(id);
            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/manager/employee/list");
                return;
            }

            // Check if there's a success message from session (after redirect from POST)
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                request.getSession().removeAttribute("successMessage"); // Clear after using
            }

            // Fetch comments for this staff member
            List<Comment> commentList = commentFacade.findByCounterStaff(staff);

            request.setAttribute("staff", staff);
            request.setAttribute("commentList", commentList);
            request.setAttribute("pageContent", "/WEB-INF/views/manager/staff-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manager/employee/list");
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
            Integer id = Integer.valueOf(request.getParameter("id"));
            CounterStaff staff = staffFacade.find(id);
            String newUsername = request.getParameter("username");

            // Use your method to check if username belongs to another staff
            CounterStaff existing = staffFacade.findCounterStaffByUsername(newUsername);
            if (existing != null && !existing.getId().equals(id)) {
                request.setAttribute("staff", staff);
                request.setAttribute("usernameError", "Username already exists.");
                request.setAttribute("pageContent", "/WEB-INF/views/manager/staff-detail.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            // Update details
            staff.setName(request.getParameter("name"));
            staff.setEmail(request.getParameter("email"));
            staff.setPhoneNumber(request.getParameter("phoneNumber"));
            staff.setGender(request.getParameter("gender"));
            staff.setUsername(newUsername);
            staff.setStatus(request.getParameter("status"));
            staff.setLastUpdateDatetime(new java.util.Date());
            staff.setIc(request.getParameter("ic"));

            staffFacade.edit(staff);

            // Set success message in session for display after redirect
            request.getSession().setAttribute("successMessage", "Staff details updated successfully!");
            response.sendRedirect(request.getContextPath() + "/manager/staff/detail?id=" + id);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manager/employee/list");
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

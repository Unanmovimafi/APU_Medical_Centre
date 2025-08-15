/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.comment.Comment;
import model.comment.CommentFacade;
import model.manager.Manager;

/**
 *
 * @author khong
 */
@WebServlet(name = "ManagerAppointmentComments", urlPatterns = { "/manager/appointment/comment" })
public class ManagerAppointmentComments extends HttpServlet {

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
            out.println("<title>Servlet DoctorAppointmentComments</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorAppointmentComments at " + request.getContextPath() + "</h1>");
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

        // Get manager from session
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");

        if (manager == null) {
            response.sendRedirect(request.getContextPath() + "/manager/login");
            return;
        }

        try {
            // Get search parameters
            String column = request.getParameter("column");
            String keyword = request.getParameter("keyword");

            List<Comment> commentList;

            // Check if this is a search request
            if (keyword != null && !keyword.trim().isEmpty() && column != null) {
                commentList = commentFacade.searchAllCommentsAndKeyword(column, keyword);
            } else {
                // Get all comments for all doctors (manager overview)
                commentList = commentFacade.findAllComments();
            }

            request.setAttribute("commentList", commentList);
            request.setAttribute("manager", manager);
            request.setAttribute("pageContent", "/WEB-INF/views/manager/appointment-comment-list.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading comments: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-comment-list.jsp");
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
        processRequest(request, response);
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

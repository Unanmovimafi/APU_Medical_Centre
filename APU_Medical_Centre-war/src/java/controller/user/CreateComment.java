/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import helper.DateTimeHelper;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.codevalue.CodeValue;
import model.codevalue.CodeValueFacade;
import model.comment.Comment;
import model.comment.CommentFacade;
import model.counterstaff.CounterStaffFacade;
import model.customer.Customer;
import model.doctor.DoctorFacade;
import model.manager.ManagerFacade;

/**
 *
 * @author zihao
 */
@WebServlet(name = "CreateComment", urlPatterns = {"/CreateComment"})
public class CreateComment extends HttpServlet {

    @EJB
    private CounterStaffFacade counterStaffFacade;

    @EJB
    private ManagerFacade managerFacade;

    @EJB
    private DoctorFacade doctorFacade;

    @EJB
    private CodeValueFacade codeValueFacade;

    @EJB
    private CommentFacade commentFacade;

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
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CreateComment</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CreateComment at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
//        processRequest(request, response);
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
//        processRequest(request, response);
        String targetStaffId = request.getParameter("selectedUserId");
        String targetStaffRole = request.getParameter("selectedStaffRole");
        String rating = request.getParameter("rating");
        String content = request.getParameter("content");

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userSession") != null) {
            Comment comment = new Comment();
            Customer customerSession = (Customer) session.getAttribute("customerSession");
            CodeValue cvCommentStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("COMMENT_STATUS", "ACTIVE");
            
            comment.setVersionTime(1);
            comment.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
            comment.setCreateBy(customerSession.getUsername());
            comment.setLastUpdateBy(customerSession.getUsername());
            comment.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());

            comment.setCustomer(customerSession);
            if ("MANAGER".equals(targetStaffRole)){
                comment.setManager(managerFacade.find(targetStaffId));
                
            }
            else if ("COUNTER_STAFF".equals(targetStaffRole)){
                comment.setCounterStaff(counterStaffFacade.find(targetStaffId));
            }
            else if ("DOCTOR".equals(targetStaffRole)){
                comment.setDoctor(doctorFacade.find(targetStaffId));
            }
            
            comment.setRating(Integer.parseInt(rating));
            comment.setContent(content);
            comment.setStatus(cvCommentStatus);
            
            commentFacade.create(comment);
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

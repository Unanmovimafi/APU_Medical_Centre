/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import helper.DateTimeHelper;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author zihao
 */
public class DeleteUser extends HttpServlet {

//    @EJB
//    private UserFacade userFacade;

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
        
        String[] selectedUserIds = request.getParameterValues("selectedUsers");
        HttpSession session = request.getSession(false);

         //Sofe delete approach
        if (selectedUserIds != null && session != null && session.getAttribute("userSession") != null) {
//            User userSession = (User) session.getAttribute("userSession");
//            for (String userId : selectedUserIds) {
//                User user = userFacade.find(Integer.parseInt(userId));
//                CodeValue deleteStatusCodeValue = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("USER_STATUS", "DELETE");
//                user.setUserStatus(deleteStatusCodeValue);
//
//                user.setVersionTime(user.getVersionTime() + 1);
//                user.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
//                user.setLastUpdateBy(userSession.getUsername());
//
//                userFacade.edit(user);
//            }
        }
        response.sendRedirect("ListUser");

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

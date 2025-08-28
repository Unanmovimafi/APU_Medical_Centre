/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import helper.DateTimeHelper;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static java.lang.Long.parseLong;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.counterstaff.CounterStaffFacade;
import model.doctor.DoctorFacade;
import model.manager.Manager;
import model.manager.ManagerFacade;
//import model.user.User;
//import model.user.UserFacade;

/**
 *
 * @author zihao
 */
public class ManagerEditManager extends HttpServlet {

    @EJB
    ManagerFacade managerFacade;

    @EJB
    DoctorFacade doctorFacade;

    @EJB
    CounterStaffFacade counterStaffFacade;

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

//        String id = request.getParameter("id");
//        User user = userFacade.find(Integer.parseInt(id));
//        request.setAttribute("user", user);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager/edit_user.jsp");
//        dispatcher.forward(request, response);
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
        final String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
            return;
        }

        Long id;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
            return;
        }

        Manager manager = managerFacade.find(id);
        if (manager == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Manager not found");
            return;
        }

        request.setAttribute("manager", manager);
        request.getRequestDispatcher("/WEB-INF/views/edit-manager.jsp").forward(request, response);
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
        // Parse identity + optimistic lock field
        Long id = parseLong(request.getParameter("id"));
        Manager m = managerFacade.find(id);

        // Collect form fields
        String status = request.getParameter("status");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        Date dateOfBirth = null;
        if (dateOfBirthStr != null && !dateOfBirthStr.isBlank()) {
            try {
                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            // Apply updates
            m.setStatus(status);
            m.setUsername(username);
            m.setName(name);
            m.setEmail(email);
            m.setPhoneNumber(phoneNumber);
            m.setDateOfBirth(dateOfBirth);
            m.setIc(request.getParameter("ic"));

            m.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            request.setAttribute("managerSession", m);
            managerFacade.edit(m);
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

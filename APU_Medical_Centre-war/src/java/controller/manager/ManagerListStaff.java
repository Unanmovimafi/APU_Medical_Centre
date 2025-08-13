/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import model.manager.Manager;
import model.manager.ManagerFacade;
//import model.user.User;
//import model.user.UserFacade;

/**
 *
 * @author zihao
 */
@WebServlet(name = "ManagerListStaff", urlPatterns = {"/manager/staff/list"})
public class ManagerListStaff extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet GetStaffList</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet GetStaffList at " + request.getContextPath() + "</h1>");
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

        // Fetch from each facade
        List<Doctor> doctorList = doctorFacade.findAll();
        List<Manager> managerList = managerFacade.findAll();
        List<CounterStaff> counterStaffList = counterStaffFacade.findAll();

        // Filter out records whose String status equals "DELETE" (case-insensitive, null-safe)
        doctorList = doctorList.stream()
                .filter(d -> d.getStatus() == null || !"DELETE".equalsIgnoreCase(d.getStatus()))
                .collect(Collectors.toList());

        managerList = managerList.stream()
                .filter(m -> m.getStatus() == null || !"DELETE".equalsIgnoreCase(m.getStatus()))
                .collect(Collectors.toList());

        counterStaffList = counterStaffList.stream()
                .filter(c -> c.getStatus() == null || !"DELETE".equalsIgnoreCase(c.getStatus()))
                .collect(Collectors.toList());

        // Pass to JSP
        request.setAttribute("doctorList", doctorList);
        request.setAttribute("managerList", managerList);
        request.setAttribute("counterStaffList", counterStaffList);

        // Forward to JSP
//    RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/create_comment.jsp
        request.setAttribute("pageContent", "/WEB-INF/views/manager/list_staff.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
//    dispatcher.forward(request, response);
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

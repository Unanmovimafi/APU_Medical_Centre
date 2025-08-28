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
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import model.manager.Manager;
import model.manager.ManagerFacade;

/**
 *
 * @author zihao
 */
//@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private ManagerFacade managerFacade;

    @EJB
    private CounterStaffFacade counterStaffFacade;

    @EJB
    private DoctorFacade doctorFacade;

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

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            switch (role) {
                case "CUSTOMER":
                    Customer customer = customerFacade.findCustomerByUsername(username);
                    if (customer != null && username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("customerSession", customer);
                        customer.setLastLoginDatetime(DateTimeHelper.getCurrentDateTime());
                        customerFacade.edit(customer);
                        response.sendRedirect("customer/dashboard");
//                        request.getRequestDispatcher("/WEB-INF/views/customer/create_comment.jsp")
//                                .forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Login Unsuccessful: Cannot find this user.");
                        request.getRequestDispatcher("").include(request, response);
                    }
                    break;
                case "MANAGER":
                    Manager manager = managerFacade.findManagerByUsername(username);
                    if (manager != null && username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("managerSession", manager);
                        session.setAttribute("managerSession", manager);
                        manager.setLastLoginDatetime(DateTimeHelper.getCurrentDateTime());
                        managerFacade.edit(manager);
                        response.sendRedirect("manager/dashboard");
//                        request.getRequestDispatcher("/WEB-INF/views/customer/create_comment.jsp")
//                                .forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Login Unsuccessful: Cannot find this user.");
                        request.getRequestDispatcher("").include(request, response);

                    }
                    break;

                case "COUNTER_STAFF":
                    CounterStaff counterStaff = counterStaffFacade.findCounterStaffByUsername(username);
                    if (counterStaff != null && username.equals(counterStaff.getUsername()) && password.equals(counterStaff.getPassword())) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("counterStaffSession", counterStaff);
                        counterStaff.setLastLoginDatetime(DateTimeHelper.getCurrentDateTime());
                        counterStaffFacade.edit(counterStaff);
                        response.sendRedirect("staff/dashboard");
//                        request.getRequestDispatcher("/WEB-INF/views/staff/dashboard.jsp")
//                                .forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Login Unsuccessful: Cannot find this user.");
                    }
                    break;

                case "DOCTOR":
                    Doctor doctor = doctorFacade.findDoctorByUsername(username);
                    if (doctor != null && username.equals(doctor.getUsername()) && password.equals(doctor.getPassword())) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("doctorSession", doctor);
                        doctor.setLastLoginDatetime(DateTimeHelper.getCurrentDateTime());
                        doctorFacade.edit(doctor);
                        response.sendRedirect("doctor/dashboard");
                    } else {
                        request.setAttribute("errorMessage", "Login Unsuccessful: Cannot find this user.");
                        request.getRequestDispatcher("").include(request, response);

                    }
                    break;
            }
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

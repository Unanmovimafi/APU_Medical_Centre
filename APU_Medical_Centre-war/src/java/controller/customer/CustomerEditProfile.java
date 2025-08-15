/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import controller.staff.*;
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
import model.customer.Customer;
import model.customer.CustomerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "CustomerEditProfile", urlPatterns = {"/customer/edit-profile"})
public class CustomerEditProfile extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;
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
            Customer customerSession = (Customer) session.getAttribute("customerSession");

            if (customerSession == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Refresh from database
            Customer customer = customerFacade.find(customerSession.getId());
            request.setAttribute("customer", customer);
            request.setAttribute("pageContent", "/WEB-INF/views/customer/edit-profile.jsp");

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
            Customer customerSession = (Customer) session.getAttribute("customerSession");

            if (customerSession == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            Customer customer = customerFacade.find(customerSession.getId());
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get parameters
            String newName = request.getParameter("name");
            String newEmail = request.getParameter("email");
            String newPhone = request.getParameter("phoneNumber");
            String newPassword = request.getParameter("newPassword");
            String oldPassword = request.getParameter("oldPassword");

            boolean isPasswordChanged = false;
            boolean isDetailChanged = false;

            // Check if new password is entered
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (oldPassword == null || !oldPassword.equals(customer.getPassword())) {
                    request.setAttribute("errorMessage", "Old password is incorrect.");
                    request.setAttribute("staff", customer);
                    request.setAttribute("pageContent", "/WEB-INF/views/customer/edit-profile.jsp");
                    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                    return;
                }
                customer.setPassword(newPassword.trim());
                isPasswordChanged = true;
            }

            // Check if any personal detail changed
            if (!newName.equals(customer.getName()) || 
                !newEmail.equals(customer.getEmail()) || 
                !newPhone.equals(customer.getPhoneNumber())) {
                isDetailChanged = true;
            }

            // Always set new values
            customer.setName(newName);
            customer.setEmail(newEmail);
            customer.setPhoneNumber(newPhone);
            customer.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            customer.setLastUpdateBy(customer.getUsername());
            customer.setVersionTime(customer.getVersionTime() + 1);

            customerFacade.edit(customer);
            session.setAttribute("customerSession", customer);

            // Set success message
            String successMessage = "";
            if (isPasswordChanged && isDetailChanged) {
                successMessage = "Your details and password have been updated.";
            } else if (isPasswordChanged) {
                successMessage = "Your password has been changed.";
            } else if (isDetailChanged) {
                successMessage = "Your details have been updated.";
            }

            session.setAttribute("successMessage", successMessage);

            response.sendRedirect(request.getContextPath() + "/customer/edit-profile");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error saving profile: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/customer/edit-profile.jsp");
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

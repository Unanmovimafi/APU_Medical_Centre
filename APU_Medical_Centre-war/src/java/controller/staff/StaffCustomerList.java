/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.customer.Customer;
import model.customer.CustomerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffCustomerList", urlPatterns = {"/staff/customer/list"})
public class StaffCustomerList extends HttpServlet {

    @EJB
    CustomerFacade customerFacade;
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
            out.println("<title>Servlet StaffCustomerList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffCustomerList at " + request.getContextPath() + "</h1>");
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
        String column = request.getParameter("column");
        String keywordRaw = request.getParameter("keyword");

        List<Customer> customers = customerFacade.findAll();

        if (column != null && keywordRaw != null && !keywordRaw.trim().isEmpty()) {
            final String keyword = keywordRaw.trim().toLowerCase();
            customers = customers.stream().filter(cust -> {
                switch (column) {
                    case "name" -> {
                        return cust.getName() != null && cust.getName().toLowerCase().contains(keyword);
                    }
                    case "username" -> {
                        return cust.getUsername() != null && cust.getUsername().toLowerCase().contains(keyword);
                    }
                    case "email" -> {
                        return cust.getEmail() != null && cust.getEmail().toLowerCase().contains(keyword);
                    }
                    default -> {
                        return true; // ✅ THIS is a valid statement
                    }
                }
            }).collect(Collectors.toList());
        }

        request.setAttribute("customerList", customers);
        request.setAttribute("pageContent", "/WEB-INF/views/staff/customer-list.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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

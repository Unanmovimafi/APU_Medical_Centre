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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.customer.Customer;
import model.customer.CustomerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffCreateCustomer", urlPatterns = { "/staff/customer/new" })
public class StaffCreateCustomer extends HttpServlet {

    @EJB
    CustomerFacade customerFacade;

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
            out.println("<title>Servlet StaffCreateCustomer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffCreateCustomer at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("pageContent", "/WEB-INF/views/staff/create-customer.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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
            String username = request.getParameter("username");

            // 🔍 Validate if username already exists
            if (customerFacade.findCustomerByUsername(username) != null) {
                request.setAttribute("error", "Username already exists.");
                request.setAttribute("pageContent", "/WEB-INF/views/staff/create-customer.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            Customer customer = new Customer();
            customer.setName(request.getParameter("name"));
            customer.setEmail(request.getParameter("email"));
            customer.setPhoneNumber(request.getParameter("phoneNumber"));
            customer.setUsername(username);
            customer.setGender(request.getParameter("gender"));
            customer.setBloodType(request.getParameter("bloodType"));
            customer.setAllergic(request.getParameter("allergic"));
            customer.setStatus(request.getParameter("status"));

            customer.setPassword("default"); // Replace with real hash if needed
            customer.setVersionTime(1);
            customer.setCreateBy("admin");
            customer.setLastUpdateBy("admin");
            customer.setCreationDatetime(new Date());
            customer.setLastUpdateDatetime(new Date());

            String dobStr = request.getParameter("dateOfBirth");
            if (dobStr != null && !dobStr.isEmpty()) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                customer.setDateOfBirth(df.parse(dobStr));
            }

            customerFacade.create(customer);

            request.getSession().setAttribute("modalMessage",
                    "<strong>" + customer.getName() + "</strong> has been successfully added as new customer.");
            response.sendRedirect(request.getContextPath() + "/staff/customer/list");

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create customer: " + e.getMessage());
            request.setAttribute("error", "Failed to create customer.");
            request.setAttribute("pageContent", "/WEB-INF/views/staff/create-customer.jsp");
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

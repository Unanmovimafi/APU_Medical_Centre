/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import model.customer.Customer;
import model.customer.CustomerFacade;
import helper.DateTimeHelper;

/**
 *
 * @author System
 */
@WebServlet(name = "CustomerEditProfilePicture", urlPatterns = { "/customer/edit-profile-picture" })
@MultipartConfig
public class CustomerEditProfilePicture extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

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
            HttpSession session = request.getSession(false);
            Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

            if (loggedCustomer == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Integer id = Integer.valueOf(request.getParameter("id"));
            Customer customer = customerFacade.find(id);

            if (customer == null || !customer.getId().equals(loggedCustomer.getId())) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream fileContent = filePart.getInputStream()) {
                    byte[] imageBytes = fileContent.readAllBytes();
                    String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

                    customer.setProfilePicture("data:image/jpeg;base64," + base64Image);
                    customer.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                    customer.setLastUpdateBy(customer.getUsername());
                    customer.setVersionTime(customer.getVersionTime() + 1);

                    customerFacade.edit(customer);
                    session.setAttribute("customerSession", customer);
                    session.setAttribute("successMessage", "Profile picture uploaded successfully.");
                }
            } else {
                session.setAttribute("errorMessage", "No file selected.");
            }

            response.sendRedirect(request.getContextPath() + "/customer/edit-profile");

        } catch (Exception e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("errorMessage", "Error uploading picture: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/customer/edit-profile");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Customer Edit Profile Picture Servlet";
    }
}

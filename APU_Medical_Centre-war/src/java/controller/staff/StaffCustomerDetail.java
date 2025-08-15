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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;
import model.feedback.Feedback;
import model.feedback.FeedbackFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffCustomerDetail", urlPatterns = { "/staff/customer/detail" })
public class StaffCustomerDetail extends HttpServlet {

    @EJB
    CustomerFacade customerFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private FeedbackFacade feedbackFacade;

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
            out.println("<title>Servlet StaffCustomerDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffCustomerDetail at " + request.getContextPath() + "</h1>");
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
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/staff/customer/list");
            return;
        }

        try {
            Integer id = Integer.parseInt(idParam);
            Customer customer = customerFacade.find(id);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/staff/customer/list");
                return;
            }

            // Check if there's a success message from session (after redirect from POST)
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                request.getSession().removeAttribute("successMessage"); // Clear after using
            }

            // Fetch appointments for medical history
            List<Appointment> appointmentList = appointmentFacade.findByCustomer(customer);

            // Fetch feedback for each appointment
            Map<Integer, Feedback> feedbackMap = new HashMap<>();
            if (appointmentList != null) {
                for (Appointment appointment : appointmentList) {
                    try {
                        Feedback feedback = feedbackFacade.findByAppointment(appointment);
                        if (feedback != null) {
                            feedbackMap.put(appointment.getId(), feedback);
                        }
                    } catch (Exception e) {
                        System.out.println("Error fetching feedback for appointment " + appointment.getId() + ": "
                                + e.getMessage());
                    }
                }
            }

            request.setAttribute("customer", customer);
            request.setAttribute("appointmentList", appointmentList);
            request.setAttribute("feedbackMap", feedbackMap);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/customer-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/staff/customer/list");
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
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));

            // Get values from form
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String username = request.getParameter("username");
            String gender = request.getParameter("gender");
            String bloodType = request.getParameter("bloodType");
            String allergic = request.getParameter("allergic");
            String status = request.getParameter("status");

            // Find existing customer
            Customer customer = customerFacade.find(id);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/staff/customer/list");
                return;
            }

            // Update fields
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setUsername(username);
            customer.setGender(gender);
            customer.setBloodType(bloodType);
            customer.setAllergic(allergic);
            customer.setStatus(status);

            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
                customer.setDateOfBirth(dateOfBirth);
            }

            customer.setLastUpdateDatetime(new Date());
            customer.setLastUpdateBy("System"); // You may replace this with the current user

            customerFacade.edit(customer);

            // Set success message in session for the modal
            request.getSession().setAttribute("successMessage", "Customer details have been successfully updated.");

            // ✅ Redirect to the GET detail page to show the updated data
            response.sendRedirect(request.getContextPath() + "/staff/customer/detail?id=" + id);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating customer: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/staff/customer-detail.jsp");
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

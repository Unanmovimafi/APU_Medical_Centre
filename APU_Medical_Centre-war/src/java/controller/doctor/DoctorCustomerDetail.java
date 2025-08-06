/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "DoctorCustomerDetail", urlPatterns = {"/doctor/customer/detail"})
public class DoctorCustomerDetail extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private AppointmentFacade appointmentFacade;
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
            out.println("<title>Servlet DoctorCustomerDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorCustomerDetail at " + request.getContextPath() + "</h1>");
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
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/doctor/customer/list");
            return;
        }

        try {
            Integer id = Integer.parseInt(idParam);
            Customer customer = customerFacade.find(id);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/doctor/customer/list");
                return;
            }

            // ✅ Fetch appointments from the customer entity
            List<Appointment> appointmentList = appointmentFacade.findByCustomer(customer);

            System.out.println("Appointments found: " + appointmentList.size());
            // Sort appointments by latest first
//            appointmentList.sort((a1, a2) -> a2.getAppointmentStartDatetime().compareTo(a1.getAppointmentStartDatetime()));

            request.setAttribute("customer", customer);
            request.setAttribute("appointmentList", appointmentList);
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/customer-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/doctor/customer/list");
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

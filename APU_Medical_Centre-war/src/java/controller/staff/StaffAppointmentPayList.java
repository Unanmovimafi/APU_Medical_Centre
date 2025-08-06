/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.AppointmentFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffAppointmentPayList", urlPatterns = {"/staff/appointment/payment"})
public class StaffAppointmentPayList extends HttpServlet {
    
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
            out.println("<title>Servlet StaffAppointmentReqList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffAppointmentReqList at " + request.getContextPath() + "</h1>");
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
            String column = request.getParameter("column");
            String keywordRaw = request.getParameter("keyword");

            List<Appointment> pendingAppointments = appointmentFacade.findByStatuses(Arrays.asList("WAITING PAYMENT", "PAID"));

            if (column != null && keywordRaw != null && !keywordRaw.trim().isEmpty()) {
                final String keyword = keywordRaw.trim().toLowerCase(); // ✅ final or effectively final

                pendingAppointments = pendingAppointments.stream().filter(appt -> {
                    switch (column) {
                        case "doctor" -> {
                            return appt.getDoctor() != null &&
                                    appt.getDoctor().getName().toLowerCase().contains(keyword);
                        }
                        case "customer" -> {
                            return appt.getCustomer() != null &&
                                    appt.getCustomer().getName().toLowerCase().contains(keyword);
                        }
                        case "date" -> {
                            if (appt.getAppointmentStartDatetime() != null) {
                                String dateStr = new SimpleDateFormat("yyyy-MM-dd")
                                        .format(appt.getAppointmentStartDatetime());
                                return dateStr.contains(keyword);
                            }
                            return false;
                        }
                        case "status" -> {
                            return appt.getStatus() != null &&
                                appt.getStatus().toLowerCase().contains(keyword);
                        }
                        default -> {
                            return true;
                        }
                    }
                }).collect(Collectors.toList());
            }

            // Sort by appointment date/time
            pendingAppointments.sort(Comparator.comparing(Appointment::getAppointmentStartDatetime));

            request.setAttribute("appointmentList", pendingAppointments);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/appointment-payment-list.jsp");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);

        } catch (ServletException | IOException e) {
            request.setAttribute("errorMessage", "Failed to retrieve appointments: " + e.getMessage());
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
        String action = request.getParameter("action");
        String appointmentIdStr = request.getParameter("appointmentId");

        try {
            Integer appointmentId = Integer.valueOf(appointmentIdStr);
            Appointment appt = appointmentFacade.find(appointmentId);

            if (appt == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
            } else {
                switch (action) {
                    case "pay" -> appt.setStatus("PAID");
                    case "finish" -> appt.setStatus("FINISHED");
                    // You may handle "print" separately
                    // You may handle "print" separately
                }

                appt.setLastUpdateDatetime(new Date());
                appt.setLastUpdateBy("STAFF");
                appointmentFacade.edit(appt);

                request.getSession().setAttribute("successMessage", "Action '" + action + "' applied.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Error processing payment action: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/staff/appointment/payments");
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

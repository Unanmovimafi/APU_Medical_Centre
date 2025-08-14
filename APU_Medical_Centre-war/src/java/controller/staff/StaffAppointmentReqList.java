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
@WebServlet(name = "StaffAppointmentReqList", urlPatterns = { "/staff/appointment/request" })
public class StaffAppointmentReqList extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

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
            out.println("<title>Servlet StaffAppointmentReqList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffAppointmentReqList at " + request.getContextPath() + "</h1>");
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
        try {
            // Handle success/error messages from session
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            String errorMessage = (String) request.getSession().getAttribute("errorMessage");

            if (successMessage != null) {
                request.setAttribute("modalMessage", successMessage);
                request.getSession().removeAttribute("successMessage");
            }
            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
                request.getSession().removeAttribute("errorMessage");
            }

            String column = request.getParameter("column");
            String keywordRaw = request.getParameter("keyword");
            String appointmentDate = request.getParameter("appointmentDate");
            String status = request.getParameter("status");

            // Get appointments with PENDING status only
            List<Appointment> appointments = appointmentFacade
                    .findByStatuses(Arrays.asList("PENDING"));

            if (column != null && keywordRaw != null && !keywordRaw.trim().isEmpty()) {
                final String keyword = keywordRaw.trim().toLowerCase();
                appointments = appointments.stream().filter(appt -> {
                    switch (column) {
                        case "doctor" -> {
                            return appt.getDoctor() != null &&
                                    appt.getDoctor().getName().toLowerCase().contains(keyword);
                        }
                        case "customer" -> {
                            return appt.getCustomer() != null &&
                                    appt.getCustomer().getName().toLowerCase().contains(keyword);
                        }
                        default -> {
                            return true;
                        }
                    }
                }).collect(Collectors.toList());
            }

            if (appointmentDate != null && !appointmentDate.trim().isEmpty()) {
                appointments = appointments.stream().filter(appt -> {
                    if (appt.getAppointmentStartDatetime() != null) {
                        String dateStr = new SimpleDateFormat("yyyy-MM-dd")
                                .format(appt.getAppointmentStartDatetime());
                        return dateStr.equals(appointmentDate);
                    }
                    return false;
                }).collect(Collectors.toList());
            }

            appointments.sort(Comparator.comparing(Appointment::getAppointmentStartDatetime));

            request.setAttribute("appointmentList", appointments);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/appointment-request-list.jsp");
            request.setAttribute("column", column);
            request.setAttribute("keyword", keywordRaw);
            request.setAttribute("appointmentDate", appointmentDate);
            request.setAttribute("status", status);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to retrieve appointments: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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
        String appointmentIdStr = request.getParameter("appointmentId");
        String action = request.getParameter("action");

        try {
            Integer appointmentId = Integer.parseInt(appointmentIdStr); // match your entity's Integer ID
            Appointment appt = appointmentFacade.find(appointmentId);

            if (appt == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
            } else {
                // ✅ Update status based on action
                if ("approve".equalsIgnoreCase(action)) {
                    appt.setStatus("APPROVED");
                } else if ("reject".equalsIgnoreCase(action)) {
                    appt.setStatus("REJECTED");
                } else {
                    request.getSession().setAttribute("errorMessage", "Invalid action.");
                    response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
                    return;
                }

                // Set metadata update values (if needed)
                appt.setLastUpdateDatetime(new Date());
                appt.setLastUpdateBy("STAFF"); // You can change this to the actual logged-in user

                appointmentFacade.edit(appt);
                request.getSession().setAttribute("successMessage", "Appointment " + action + "d successfully.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error updating appointment: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/staff/appointment/request");
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

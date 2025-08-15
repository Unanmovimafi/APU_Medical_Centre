package controller.customer;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;

@WebServlet(name = "CustomerAppointmentList", urlPatterns = { "/customer/appointment/list" })
public class CustomerAppointmentList extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

        if (loggedCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Handle success/error messages from session
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            String errorMessage = (String) request.getSession().getAttribute("errorMessage");

            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                request.getSession().removeAttribute("successMessage");
            }
            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
                request.getSession().removeAttribute("errorMessage");
            }

            // Get search parameters
            String keyword = request.getParameter("keyword");
            String dateParam = request.getParameter("date");
            String statusParam = request.getParameter("status");

            // Get all customer's appointments
            List<Appointment> appointmentList = appointmentFacade.findByCustomer(loggedCustomer);

            // Apply filters
            if (keyword != null && !keyword.trim().isEmpty()) {
                final String searchKeyword = keyword.toLowerCase().trim();
                appointmentList = appointmentList.stream()
                        .filter(appt -> {
                            // Search in doctor name
                            if (appt.getDoctor() != null && appt.getDoctor().getName() != null
                                    && appt.getDoctor().getName().toLowerCase().contains(searchKeyword)) {
                                return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
            }

            // Filter by date
            if (dateParam != null && !dateParam.trim().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date searchDate = sdf.parse(dateParam);

                    appointmentList = appointmentList.stream()
                            .filter(appt -> {
                                if (appt.getAppointmentStartDatetime() != null) {
                                    String apptDateStr = sdf.format(appt.getAppointmentStartDatetime());
                                    String searchDateStr = sdf.format(searchDate);
                                    return apptDateStr.equals(searchDateStr);
                                }
                                return false;
                            })
                            .collect(Collectors.toList());
                } catch (ParseException e) {
                    request.setAttribute("errorMessage", "Invalid date format. Please use YYYY-MM-DD format.");
                }
            }

            // Filter by status
            if (statusParam != null && !statusParam.trim().isEmpty()) {
                final String searchStatus = statusParam.trim();
                appointmentList = appointmentList.stream()
                        .filter(appt -> searchStatus.equalsIgnoreCase(appt.getStatus()))
                        .collect(Collectors.toList());
            }

            // Sort by appointment date (latest first)
            appointmentList.sort((a, b) -> {
                if (a.getAppointmentStartDatetime() == null && b.getAppointmentStartDatetime() == null) {
                    return 0;
                }
                if (a.getAppointmentStartDatetime() == null) {
                    return 1;
                }
                if (b.getAppointmentStartDatetime() == null) {
                    return -1;
                }
                return b.getAppointmentStartDatetime().compareTo(a.getAppointmentStartDatetime());
            });

            request.setAttribute("appointmentList", appointmentList);
            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-list.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load appointments: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-list.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

        if (loggedCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("cancel".equals(action) && idParam != null && !idParam.trim().isEmpty()) {
            try {
                Integer appointmentId = Integer.parseInt(idParam);
                Appointment appointment = appointmentFacade.find(appointmentId);

                if (appointment == null) {
                    request.getSession().setAttribute("errorMessage", "Appointment not found.");
                } else if (!appointment.getCustomer().getId().equals(loggedCustomer.getId())) {
                    request.getSession().setAttribute("errorMessage", "You can only cancel your own appointments.");
                } else if ("PENDING".equals(appointment.getStatus()) || "APPROVED".equals(appointment.getStatus())) {
                    // Cancel the appointment
                    appointment.setStatus("CANCELLED");
                    appointment.setLastUpdateDatetime(new Date());
                    appointment.setLastUpdateBy(loggedCustomer.getName());

                    appointmentFacade.edit(appointment);
                    request.getSession().setAttribute("successMessage", "Appointment cancelled successfully.");
                } else {
                    request.getSession().setAttribute("errorMessage",
                            "Cannot cancel appointment with current status: " + appointment.getStatus());
                }

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("errorMessage", "Failed to cancel appointment: " + e.getMessage());
            }
        }

        // Redirect back to appointment list
        response.sendRedirect(request.getContextPath() + "/customer/appointment/list");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing customer appointments with search functionality";
    }
}

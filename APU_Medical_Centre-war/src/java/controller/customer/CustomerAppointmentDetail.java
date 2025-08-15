package controller.customer;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;

@WebServlet(name = "CustomerAppointmentDetail", urlPatterns = { "/customer/appointment/detail" })
public class CustomerAppointmentDetail extends HttpServlet {

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

        String idParam = request.getParameter("id");
        String fromParam = request.getParameter("from"); // "list" or "calendar"

        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            String redirectUrl = "list".equals(fromParam)
                    ? "/customer/appointment/list"
                    : "/customer/appointment/calendar";
            response.sendRedirect(request.getContextPath() + redirectUrl);
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
                String redirectUrl = "list".equals(fromParam)
                        ? "/customer/appointment/list"
                        : "/customer/appointment/calendar";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            }

            // Check if appointment belongs to logged customer
            if (!appointment.getCustomer().getId().equals(loggedCustomer.getId())) {
                request.getSession().setAttribute("errorMessage", "You can only view your own appointments.");
                String redirectUrl = "list".equals(fromParam)
                        ? "/customer/appointment/list"
                        : "/customer/appointment/calendar";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            }

            request.setAttribute("appointment", appointment);
            request.setAttribute("fromSource", fromParam); // Pass the source to JSP for navigation
            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            String redirectUrl = "list".equals(fromParam)
                    ? "/customer/appointment/list"
                    : "/customer/appointment/calendar";
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load appointment details: " + e.getMessage());
            request.setAttribute("fromSource", fromParam); // Pass the source even on error
            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-detail.jsp");
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

        String idParam = request.getParameter("id");
        String fromParam = request.getParameter("from"); // "list" or "calendar"

        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            String redirectUrl = "list".equals(fromParam)
                    ? "/customer/appointment/list"
                    : "/customer/appointment/calendar";
            response.sendRedirect(request.getContextPath() + redirectUrl);
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
                String redirectUrl = "list".equals(fromParam)
                        ? "/customer/appointment/list"
                        : "/customer/appointment/calendar";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            }

            // Check if appointment belongs to logged customer
            if (!appointment.getCustomer().getId().equals(loggedCustomer.getId())) {
                request.getSession().setAttribute("errorMessage", "You can only manage your own appointments.");
                String redirectUrl = "list".equals(fromParam)
                        ? "/customer/appointment/list"
                        : "/customer/appointment/calendar";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            }

            String action = request.getParameter("action");
            String currentStatus = appointment.getStatus();

            if ("cancelAppointment".equals(action) &&
                    ("PENDING".equals(currentStatus) || "APPROVED".equals(currentStatus))) {

                // Update appointment status to CANCELLED
                appointment.setStatus("CANCELLED");
                appointment.setLastUpdateDatetime(new Date());
                appointment.setLastUpdateBy(loggedCustomer.getName());

                appointmentFacade.edit(appointment);

                request.getSession().setAttribute("successMessage", "Appointment cancelled successfully.");

            } else {
                request.getSession().setAttribute("errorMessage",
                        "Cannot cancel appointment with current status: " + currentStatus);
            }

            // Redirect back with from parameter
            String redirectUrl = "detail?id=" + appointmentId + (fromParam != null ? "&from=" + fromParam : "");
            response.sendRedirect(request.getContextPath() + "/customer/appointment/" + redirectUrl);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            String redirectUrl = "list".equals(fromParam)
                    ? "/customer/appointment/list"
                    : "/customer/appointment/calendar";
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Failed to update appointment: " + e.getMessage());
            String redirectUrl = "detail?id=" + idParam + (fromParam != null ? "&from=" + fromParam : "");
            response.sendRedirect(request.getContextPath() + "/customer/appointment/" + redirectUrl);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for customer appointment details and status management";
    }
}

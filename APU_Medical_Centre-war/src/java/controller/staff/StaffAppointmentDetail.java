package controller.staff;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;
import model.customer.CustomerFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

@WebServlet(name = "StaffAppointmentDetail", urlPatterns = { "/staff/appointment/detail" })
public class StaffAppointmentDetail extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private DoctorFacade doctorFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
                response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
                return;
            }

            request.setAttribute("appointment", appointment);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/appointment-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load appointment details: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/staff/appointment-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
                response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
                return;
            }

            // Handle status-specific actions
            String action = request.getParameter("action");
            String currentStatus = appointment.getStatus();

            if ("approve".equals(action) && "PENDING".equals(currentStatus)) {
                appointment.setStatus("CONFIRMED");
                appointment.setLastUpdateDatetime(new Date());
                appointmentFacade.edit(appointment);
                request.getSession().setAttribute("successMessage", "Appointment approved successfully!");

            } else if ("reject".equals(action) && "PENDING".equals(currentStatus)) {
                appointment.setStatus("CANCELLED");
                appointment.setLastUpdateDatetime(new Date());
                appointmentFacade.edit(appointment);
                request.getSession().setAttribute("successMessage", "Appointment rejected successfully!");

            } else if ("makePayment".equals(action) && "WAITING PAYMENT".equals(currentStatus)) {
                appointment.setStatus("PAID");
                appointment.setLastUpdateDatetime(new Date());
                appointmentFacade.edit(appointment);
                request.getSession().setAttribute("successMessage", "Payment processed successfully!");

            } else if ("printReceipt".equals(action) && "WAITING PAYMENT".equals(currentStatus)) {
                // Redirect to receipt printing page
                response.sendRedirect(request.getContextPath() + "/staff/appointment/receipt?id=" + appointmentId);
                return;

            } else {
                request.getSession().setAttribute("errorMessage", "Invalid action for current appointment status.");
            }

            response.sendRedirect(request.getContextPath() + "/staff/appointment/detail?id=" + appointmentId);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/staff/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Failed to update appointment: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/staff/appointment/detail?id=" + idParam);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for viewing and editing appointment details";
    }
}

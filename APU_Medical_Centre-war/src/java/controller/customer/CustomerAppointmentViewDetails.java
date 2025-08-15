package controller.customer;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.appointmentmedicine.AppointmentMedicine;
import model.appointmentmedicine.AppointmentMedicineFacade;
import model.customer.Customer;
import model.feedback.Feedback;
import model.feedback.FeedbackFacade;

@WebServlet(name = "CustomerAppointmentViewDetails", urlPatterns = { "/customer/appointment/view-details" })
public class CustomerAppointmentViewDetails extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private FeedbackFacade feedbackFacade;

    @EJB
    private AppointmentMedicineFacade appointmentMedicineFacade;

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
                request.getSession().setAttribute("errorMessage",
                        "Access denied. You can only view your own appointments.");
                String redirectUrl = "list".equals(fromParam)
                        ? "/customer/appointment/list"
                        : "/customer/appointment/calendar";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            }

            // Check if appointment has appropriate status for viewing details
            if (!"WAITING PAYMENT".equals(appointment.getStatus()) && !"PAID".equals(appointment.getStatus())
                    && !"COMPLETED".equals(appointment.getStatus())) {
                request.getSession().setAttribute("errorMessage",
                        "Details can only be viewed for appointments with 'Waiting Payment', 'Paid', or 'Completed' status.");
                String detailUrl = "detail?id=" + appointmentId + (fromParam != null ? "&from=" + fromParam : "");
                response.sendRedirect(request.getContextPath() + "/customer/appointment/" + detailUrl);
                return;
            }

            // Get the feedback for this appointment
            Feedback feedback = null;
            try {
                List<Feedback> feedbacks = feedbackFacade.findByAppointmentId(appointmentId);
                if (feedbacks != null && !feedbacks.isEmpty()) {
                    feedback = feedbacks.get(0); // Get the first (should be only one)
                }
            } catch (Exception e) {
                System.out.println("Error fetching feedback: " + e.getMessage());
                e.printStackTrace();
            }

            // Get medicines prescribed for this appointment
            List<AppointmentMedicine> appointmentMedicines = null;
            try {
                appointmentMedicines = appointmentMedicineFacade.findByAppointmentId(appointmentId);
            } catch (Exception e) {
                System.out.println("Error fetching appointment medicines: " + e.getMessage());
                e.printStackTrace();
            }

            // Set attributes for JSP
            request.setAttribute("appointment", appointment);
            request.setAttribute("feedback", feedback);
            request.setAttribute("appointmentMedicines",
                    appointmentMedicines != null ? appointmentMedicines : new java.util.ArrayList<>());
            request.setAttribute("fromSource", fromParam); // Pass the source to JSP for navigation

            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-view-details.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            String redirectUrl = "list".equals(fromParam)
                    ? "/customer/appointment/list"
                    : "/customer/appointment/calendar";
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in view details: " + e.getMessage());
            request.setAttribute("errorMessage", "Failed to load appointment details. Please try again.");
            request.setAttribute("fromSource", fromParam); // Pass the source even on error
            request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-view-details.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for customer viewing appointment details with feedback and medicine information";
    }
}

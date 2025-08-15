package controller.staff;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.appointmentmedicine.AppointmentMedicine;
import model.appointmentmedicine.AppointmentMedicineFacade;
import model.counterstaff.CounterStaff;
import model.feedback.Feedback;
import model.feedback.FeedbackFacade;

@WebServlet(name = "StaffReceiptPreview", urlPatterns = { "/staff/receipt/preview" })
public class StaffReceiptPreview extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private AppointmentMedicineFacade appointmentMedicineFacade;

    @EJB
    private FeedbackFacade feedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        CounterStaff loggedStaff = (CounterStaff) session.getAttribute("counterStaffSession");

        if (loggedStaff == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String appointmentIdParam = request.getParameter("appointmentId");
        if (appointmentIdParam == null || appointmentIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Appointment ID is required");
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(appointmentIdParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
                return;
            }

            // Only allow receipt for paid appointments
            if (!"PAID".equals(appointment.getStatus())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Receipt can only be generated for paid appointments");
                return;
            }

            // Get prescribed medicines for this appointment
            List<AppointmentMedicine> appointmentMedicines = null;
            try {
                appointmentMedicines = appointmentMedicineFacade.findByAppointmentId(appointmentId);
            } catch (Exception e) {
                System.out.println("Error fetching appointment medicines: " + e.getMessage());
                appointmentMedicines = new java.util.ArrayList<>();
            }

            // Get feedback for this appointment (for diagnosis details)
            Feedback feedback = null;
            try {
                List<Feedback> feedbacks = feedbackFacade.findByAppointmentId(appointmentId);
                if (feedbacks != null && !feedbacks.isEmpty()) {
                    feedback = feedbacks.get(0);
                }
            } catch (Exception e) {
                System.out.println("Error fetching feedback: " + e.getMessage());
            }

            // Calculate totals
            Long consultationFee = 50L; // Default consultation fee
            Long medicineTotal = 0L;

            if (appointmentMedicines != null) {
                for (AppointmentMedicine am : appointmentMedicines) {
                    if (am.getMedicine() != null && am.getMedicine().getPrice() != null) {
                        medicineTotal += am.getMedicine().getPrice() * am.getQuantity();
                    }
                }
            }

            // Use appointment charge if available, otherwise calculate total
            Long totalAmount = appointment.getCharge();
            if (totalAmount == null) {
                totalAmount = consultationFee + medicineTotal;
            }

            // Set attributes for JSP
            request.setAttribute("appointment", appointment);
            request.setAttribute("appointmentMedicines", appointmentMedicines);
            request.setAttribute("feedback", feedback);
            request.setAttribute("consultationFee", consultationFee);
            request.setAttribute("medicineTotal", medicineTotal);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("receiptDate", new Date());
            request.setAttribute("receiptNumber", "RCP-" + appointmentId + "-" + System.currentTimeMillis());

            // Forward to receipt JSP (this will be a print-friendly page)
            request.getRequestDispatcher("/WEB-INF/views/staff/receipt-preview.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while generating the receipt");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for generating appointment payment receipts";
    }
}

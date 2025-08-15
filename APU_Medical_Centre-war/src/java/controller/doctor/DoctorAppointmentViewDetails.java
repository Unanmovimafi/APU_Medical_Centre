package controller.doctor;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.appointmentmedicine.AppointmentMedicine;
import model.appointmentmedicine.AppointmentMedicineFacade;
import model.doctor.Doctor;
import model.feedback.Feedback;
import model.feedback.FeedbackFacade;

@WebServlet(name = "DoctorAppointmentViewDetails", urlPatterns = { "/doctor/appointment/view-details" })
public class DoctorAppointmentViewDetails extends HttpServlet {

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
        Doctor loggedDoctor = (Doctor) session.getAttribute("doctorSession");

        if (loggedDoctor == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null) {
                request.getSession().setAttribute("errorMessage", "Appointment not found.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            // Check if appointment belongs to logged doctor
            if (!appointment.getDoctor().getId().equals(loggedDoctor.getId())) {
                request.getSession().setAttribute("errorMessage",
                        "Access denied. This appointment belongs to another doctor.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            // Check if appointment has appropriate status for viewing details
            if (!"WAITING PAYMENT".equals(appointment.getStatus()) && !"PAID".equals(appointment.getStatus())) {
                request.getSession().setAttribute("errorMessage",
                        "Details can only be viewed for appointments with 'Waiting Payment' or 'Paid' status.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/detail?id=" + appointmentId);
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

            // Get patient's medical history (previous appointments)
            List<Appointment> patientHistory = null;
            Map<Integer, Feedback> feedbackMap = new HashMap<>();
            Map<Integer, List<AppointmentMedicine>> medicineMap = new HashMap<>();

            try {
                if (appointment.getCustomer() != null) {
                    patientHistory = appointmentFacade.findByCustomerId(appointment.getCustomer().getId());

                    if (patientHistory != null) {
                        // Remove current appointment from history
                        patientHistory.removeIf(apt -> apt.getId().equals(appointmentId));

                        // Get feedback and medicines for each historical appointment
                        for (Appointment historyApt : patientHistory) {
                            try {
                                List<Feedback> historyFeedbacks = feedbackFacade
                                        .findByAppointmentId(historyApt.getId());
                                if (historyFeedbacks != null && !historyFeedbacks.isEmpty()) {
                                    feedbackMap.put(historyApt.getId(), historyFeedbacks.get(0));
                                }

                                List<AppointmentMedicine> historyMedicines = appointmentMedicineFacade
                                        .findByAppointmentId(historyApt.getId());
                                if (historyMedicines != null && !historyMedicines.isEmpty()) {
                                    medicineMap.put(historyApt.getId(), historyMedicines);
                                }
                            } catch (Exception e) {
                                System.out.println("Error fetching history for appointment " + historyApt.getId() + ": "
                                        + e.getMessage());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error fetching patient history: " + e.getMessage());
                e.printStackTrace();
                patientHistory = new java.util.ArrayList<>();
            }

            // Set attributes for JSP
            request.setAttribute("appointment", appointment);
            request.setAttribute("feedback", feedback);
            request.setAttribute("appointmentMedicines",
                    appointmentMedicines != null ? appointmentMedicines : new java.util.ArrayList<>());
            request.setAttribute("patientHistory",
                    patientHistory != null ? patientHistory : new java.util.ArrayList<>());
            request.setAttribute("feedbackMap", feedbackMap);
            request.setAttribute("medicineMap", medicineMap);

            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-view-details.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in view details: " + e.getMessage());
            request.setAttribute("errorMessage", "Failed to load appointment details. Please try again.");
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-view-details.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for viewing appointment details with feedback, charge, medicine and medical history";
    }
}

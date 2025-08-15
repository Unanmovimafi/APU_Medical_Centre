package controller.doctor;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
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
import model.medicine.Medicine;
import model.medicine.MedicineFacade;

@WebServlet(name = "DoctorAppointmentDiagnosis", urlPatterns = { "/doctor/appointment/diagnosis" })
public class DoctorAppointmentDiagnosis extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private FeedbackFacade feedbackFacade;

    @EJB
    private MedicineFacade medicineFacade;

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

            // Check if appointment belongs to logged doctor and is approved
            if (!appointment.getDoctor().getId().equals(loggedDoctor.getId())) {
                request.getSession().setAttribute("errorMessage", "You can only manage your own appointments.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            if (!"APPROVED".equals(appointment.getStatus())) {
                request.getSession().setAttribute("errorMessage",
                        "Only approved appointments can have diagnosis added.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/detail?id=" + appointmentId);
                return;
            }

            // Get available medicines first (required for form)
            List<Medicine> availableMedicines = null;
            try {
                availableMedicines = medicineFacade.findAll();
                System.out.println(
                        "DEBUG: Found " + (availableMedicines != null ? availableMedicines.size() : 0) + " medicines");
                if (availableMedicines != null) {
                    for (Medicine med : availableMedicines) {
                        System.out.println("DEBUG: Medicine - ID: " + med.getId() + ", Name: " + med.getName()
                                + ", Price: " + med.getPrice());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error fetching medicines: " + e.getMessage());
                e.printStackTrace();
                availableMedicines = new java.util.ArrayList<>();
            }

            // Get patient's medical history (previous appointments)
            List<Appointment> patientHistory = null;
            Map<Integer, Feedback> feedbackMap = new HashMap<>();
            Map<Integer, List<AppointmentMedicine>> medicineMap = new HashMap<>();

            try {
                if (appointment.getCustomer() != null) {
                    patientHistory = appointmentFacade.findByCustomer(appointment.getCustomer());

                    if (patientHistory != null) {
                        for (Appointment historyAppt : patientHistory) {
                            try {
                                // Get feedback
                                Feedback feedback = feedbackFacade.findByAppointment(historyAppt);
                                if (feedback != null) {
                                    feedbackMap.put(historyAppt.getId(), feedback);
                                }
                            } catch (Exception e) {
                                System.out.println("Error fetching feedback for appointment " + historyAppt.getId()
                                        + ": " + e.getMessage());
                            }

                            try {
                                // Get medicines
                                List<AppointmentMedicine> medicines = appointmentMedicineFacade
                                        .findByAppointmentId(historyAppt.getId());
                                if (medicines != null && !medicines.isEmpty()) {
                                    medicineMap.put(historyAppt.getId(), medicines);
                                }
                            } catch (Exception e) {
                                System.out.println("Error fetching medicines for appointment " + historyAppt.getId()
                                        + ": " + e.getMessage());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error fetching patient history: " + e.getMessage());
                patientHistory = new java.util.ArrayList<>();
            }

            request.setAttribute("appointment", appointment);
            request.setAttribute("patientHistory", patientHistory);
            request.setAttribute("feedbackMap", feedbackMap);
            request.setAttribute("medicineMap", medicineMap);
            request.setAttribute("availableMedicines", availableMedicines);
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-add-diagnosis.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in diagnosis form: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            }
            request.setAttribute("errorMessage", "Failed to load diagnosis form. Please try again.");
            // Set minimal attributes to avoid null reference errors
            request.setAttribute("availableMedicines", new java.util.ArrayList<>());
            request.setAttribute("patientHistory", new java.util.ArrayList<>());
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-add-diagnosis.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Doctor loggedDoctor = (Doctor) session.getAttribute("doctorSession");

        if (loggedDoctor == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("appointmentId");
        if (idParam == null || idParam.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Appointment ID is required.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(idParam);
            Appointment appointment = appointmentFacade.find(appointmentId);

            if (appointment == null || !appointment.getDoctor().getId().equals(loggedDoctor.getId())
                    || !"APPROVED".equals(appointment.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Invalid appointment or insufficient permissions.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            // Get form data
            String feedback = request.getParameter("feedback");
            String chargeParam = request.getParameter("charge");
            String[] selectedMedicines = request.getParameterValues("medicines");

            System.out.println("DEBUG: Feedback received: " + (feedback != null ? "'" + feedback + "'" : "null"));
            System.out.println("DEBUG: Charge received: " + (chargeParam != null ? "'" + chargeParam + "'" : "null"));
            System.out.println("DEBUG: Selected medicines: "
                    + (selectedMedicines != null ? selectedMedicines.length : 0) + " medicines");
            if (selectedMedicines != null) {
                for (String medId : selectedMedicines) {
                    System.out.println("DEBUG: Selected medicine ID: " + medId);
                    System.out.println(
                            "DEBUG: Quantity for medicine " + medId + ": " + request.getParameter("quantity_" + medId));
                }
            }

            // Validate inputs
            if (feedback == null || feedback.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Feedback is required.");
                doGet(request, response);
                return;
            }

            Long charge = null;
            if (chargeParam != null && !chargeParam.trim().isEmpty()) {
                try {
                    charge = Long.parseLong(chargeParam);
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid charge amount.");
                    doGet(request, response);
                    return;
                }
            }

            // Create feedback
            Feedback newFeedback = new Feedback();
            newFeedback.setAppointment(appointment);
            newFeedback.setContext(feedback.trim());
            newFeedback.setStatus("COMPLETED");
            newFeedback.setCreationDatetime(new Date());
            newFeedback.setCreateBy(loggedDoctor.getUsername());
            newFeedback.setLastUpdateDatetime(new Date());
            newFeedback.setLastUpdateBy(loggedDoctor.getUsername());
            newFeedback.setVersionTime(1);
            feedbackFacade.create(newFeedback);

            // Update appointment
            appointment.setStatus("WAITING PAYMENT");
            if (charge != null) {
                appointment.setCharge(charge);
            } else {
                appointment.setCharge(50L); // Default consultation fee
            }
            appointment.setLastUpdateDatetime(new Date());
            appointment.setLastUpdateBy(loggedDoctor.getUsername());
            appointmentFacade.edit(appointment);

            // Handle selected medicines
            if (selectedMedicines != null && selectedMedicines.length > 0) {
                System.out.println("DEBUG: Processing " + selectedMedicines.length + " selected medicines");
                try {
                    for (String medicineIdStr : selectedMedicines) {
                        System.out.println("DEBUG: Processing medicine ID: " + medicineIdStr);
                        Integer medicineId = Integer.parseInt(medicineIdStr);
                        Medicine medicine = medicineFacade.find(medicineId);

                        if (medicine != null) {
                            System.out.println(
                                    "DEBUG: Found medicine: " + medicine.getName() + ", Price: " + medicine.getPrice());
                            // Get quantity for this medicine
                            String quantityParam = request.getParameter("quantity_" + medicineId);
                            Integer quantity = 1; // Default quantity
                            System.out.println("DEBUG: Quantity parameter: " + quantityParam);

                            if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                                try {
                                    quantity = Integer.parseInt(quantityParam);
                                    if (quantity < 1)
                                        quantity = 1; // Minimum quantity is 1
                                    if (quantity > 10)
                                        quantity = 10; // Maximum quantity is 10
                                } catch (NumberFormatException e) {
                                    System.out.println(
                                            "Invalid quantity for medicine " + medicineId + ", using default: 1");
                                    quantity = 1;
                                }
                            }

                            System.out.println("DEBUG: Final quantity: " + quantity);

                            AppointmentMedicine appointmentMedicine = new AppointmentMedicine();
                            appointmentMedicine.setAppointment(appointment);
                            appointmentMedicine.setMedicine(medicine);
                            appointmentMedicine.setQuantity(quantity);
                            appointmentMedicine.setCreationDatetime(new Date());
                            appointmentMedicine.setCreateBy(loggedDoctor.getUsername());
                            appointmentMedicine.setLastUpdateDatetime(new Date());
                            appointmentMedicine.setLastUpdateBy(loggedDoctor.getUsername());
                            appointmentMedicine.setVersionTime(1);

                            System.out.println("DEBUG: About to save AppointmentMedicine...");
                            appointmentMedicineFacade.create(appointmentMedicine);
                            System.out.println("DEBUG: AppointmentMedicine saved successfully");

                            // Add medicine cost to appointment charge (price * quantity)
                            Long currentCharge = appointment.getCharge();
                            if (currentCharge == null)
                                currentCharge = 0L;
                            Long medicineCost = medicine.getPrice() * quantity;
                            appointment.setCharge(currentCharge + medicineCost);
                            System.out.println("DEBUG: Added medicine cost: " + medicineCost + ", New total charge: "
                                    + appointment.getCharge());
                        } else {
                            System.out.println("DEBUG: Medicine not found for ID: " + medicineId);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error handling selected medicines: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("DEBUG: No medicines selected");
            }

            // Update appointment with final charge
            appointmentFacade.edit(appointment);

            request.getSession().setAttribute("successMessage",
                    "Diagnosis completed successfully! Appointment moved to waiting payment.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/detail?id=" + appointmentId);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to save diagnosis: " + e.getMessage());
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding diagnosis to appointments";
    }
}

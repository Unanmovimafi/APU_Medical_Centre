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

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.doctor.Doctor;

@WebServlet(name = "DoctorAppointmentDetail", urlPatterns = { "/doctor/appointment/detail" })
public class DoctorAppointmentDetail extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

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
                request.getSession().setAttribute("errorMessage", "You can only view your own appointments.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            request.setAttribute("appointment", appointment);
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load appointment details: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-detail.jsp");
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
                request.getSession().setAttribute("errorMessage", "You can only manage your own appointments.");
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
                return;
            }

            String action = request.getParameter("action");
            String currentStatus = appointment.getStatus();

            if ("addDiagnosis".equals(action) && "APPROVED".equals(currentStatus)) {
                // Redirect to diagnosis form
                response.sendRedirect(request.getContextPath() + "/doctor/appointment/diagnosis?id=" + appointmentId);
                return;

            } else {
                request.getSession().setAttribute("errorMessage", "Invalid action for current appointment status.");
            }

            response.sendRedirect(request.getContextPath() + "/doctor/appointment/detail?id=" + appointmentId);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid appointment ID format.");
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Failed to update appointment: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/doctor/appointment/detail?id=" + idParam);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for doctor appointment details and diagnosis";
    }
}

package controller.doctor;

import controller.staff.*;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.doctor.Doctor;

@WebServlet(name = "DoctorAppointmentList", urlPatterns = {"/doctor/appointment/list"})
public class DoctorAppointmentList extends HttpServlet {
    
    @EJB
    private AppointmentFacade appointmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            Doctor loggedDoctor = (Doctor) session.getAttribute("doctorSession");
            
            List<Appointment> appointmentList = appointmentFacade.findByDoctor(loggedDoctor);

            String column = request.getParameter("column");
            String keyword = request.getParameter("keyword");
            String date = request.getParameter("date");
            String status = request.getParameter("status");

            if (column != null && keyword != null && !keyword.trim().isEmpty()) {
                String keywordLower = keyword.trim().toLowerCase();
                appointmentList = appointmentList.stream().filter(appt -> {
                    if ("customer".equals(column) && appt.getCustomer() != null) {
                        return appt.getCustomer().getName().toLowerCase().contains(keywordLower);
                    }
                    return false;
                }).collect(Collectors.toList());
            }

            if (date != null && !date.isEmpty()) {
                appointmentList = appointmentList.stream().filter(appt -> {
                    if (appt.getAppointmentStartDatetime() != null) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(appt.getAppointmentStartDatetime()).equals(date);
                    }
                    return false;
                }).collect(Collectors.toList());
            }

            if (status != null && !status.isEmpty()) {
                appointmentList = appointmentList.stream().filter(appt ->
                    status.equalsIgnoreCase(appt.getStatus())
                ).collect(Collectors.toList());
            }

            request.setAttribute("appointmentList", appointmentList);
            request.setAttribute("pageContent", "/WEB-INF/views/doctor/appointment-list.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to retrieve appointments: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing appointments of the logged-in doctor";
    }
}

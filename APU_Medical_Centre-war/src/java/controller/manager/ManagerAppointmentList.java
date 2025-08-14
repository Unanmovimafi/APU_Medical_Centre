package controller.manager;

import controller.staff.*;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;

@WebServlet(name = "ManagerAppointmentList", urlPatterns = { "/manager/appointment/list" })
public class ManagerAppointmentList extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

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

            // Check if this is a list view request
            String view = request.getParameter("view");
            if ("list".equals(view)) {
                // Handle original list view logic
                handleListView(request, response);
                return;
            }

            // Calendar view logic
            handleCalendarView(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to retrieve appointments: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    private void handleCalendarView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get month and year parameters, default to current month
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based

        int targetYear = yearParam != null ? Integer.parseInt(yearParam) : currentYear;
        int targetMonth = monthParam != null ? Integer.parseInt(monthParam) : currentMonth;

        // Set calendar to first day of target month
        cal.set(Calendar.YEAR, targetYear);
        cal.set(Calendar.MONTH, targetMonth - 1); // Calendar.MONTH is 0-based
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDate = cal.getTime();

        // Set to last day of target month
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date endDate = cal.getTime();

        // Get appointments for the month
        List<Appointment> appointmentList = appointmentFacade.findAll().stream()
                .filter(appt -> {
                    if (appt.getAppointmentStartDatetime() != null) {
                        Date apptDate = appt.getAppointmentStartDatetime();
                        return apptDate.compareTo(startDate) >= 0 && apptDate.compareTo(endDate) <= 0;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        // Set current date for JSP
        cal.set(Calendar.YEAR, targetYear);
        cal.set(Calendar.MONTH, targetMonth - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        request.setAttribute("currentDate", cal.getTime());
        request.setAttribute("targetYear", targetYear);
        request.setAttribute("targetMonth", targetMonth);
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("pageContent", "/WEB-INF/views/manager/appointment-list.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    private void handleListView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Appointment> appointmentList = appointmentFacade.findAll();

        String column = request.getParameter("column");
        String keyword = request.getParameter("keyword");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        if (column != null && keyword != null && !keyword.trim().isEmpty()) {
            String keywordLower = keyword.trim().toLowerCase();
            appointmentList = appointmentList.stream().filter(appt -> {
                if ("doctor".equals(column) && appt.getDoctor() != null) {
                    return appt.getDoctor().getName().toLowerCase().contains(keywordLower);
                } else if ("customer".equals(column) && appt.getCustomer() != null) {
                    return appt.getCustomer().getName().toLowerCase().contains(keywordLower);
                }
                return false;
            }).collect(Collectors.toList());
        }

        if (date != null && !date.isEmpty()) {
            appointmentList = appointmentList.stream().filter(appt -> {
                if (appt.getAppointmentStartDatetime() != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(appt.getAppointmentStartDatetime())
                            .equals(date);
                }
                return false;
            }).collect(Collectors.toList());
        }

        if (status != null && !status.isEmpty()) {
            appointmentList = appointmentList.stream().filter(appt -> status.equalsIgnoreCase(appt.getStatus()))
                    .collect(Collectors.toList());
        }

        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("pageContent", "/WEB-INF/views/manager/appointment-list-table.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing all appointments";
    }
}

package controller.customer;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.appointment.Appointment;
import model.appointment.AppointmentFacade;
import model.customer.Customer;

@WebServlet(name = "CustomerAppointmentCalendar", urlPatterns = { "/customer/appointment/calendar" })
public class CustomerAppointmentCalendar extends HttpServlet {

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

            // Calendar view logic
            handleCalendarView(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to retrieve appointments: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
    }

    private void handleCalendarView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer loggedCustomer = (Customer) session.getAttribute("customerSession");

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

        // Get customer's appointments for the month
        List<Appointment> appointmentList = appointmentFacade.findByCustomer(loggedCustomer).stream()
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
        request.setAttribute("pageContent", "/WEB-INF/views/customer/appointment-calendar.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for customer appointment calendar";
    }
}

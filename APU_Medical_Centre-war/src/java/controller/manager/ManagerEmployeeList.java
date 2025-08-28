/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import model.manager.Manager;
import model.manager.ManagerFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "ManagerEmployeeList", urlPatterns = { "/manager/employee/list" })
public class ManagerEmployeeList extends HttpServlet {

    @EJB
    CounterStaffFacade counterStaffFacade;

    @EJB
    DoctorFacade doctorFacade;
    
    @EJB
    ManagerFacade managerFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffEmployeeList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffEmployeeList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String column = request.getParameter("column");
        String statusFilter = request.getParameter("status");
        String roleFilter = request.getParameter("role");

        List<Map<String, Object>> combined = new ArrayList<>();

        for (Doctor doctor : doctorFacade.findAll()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", doctor.getId());
            map.put("role", "Doctor");
            map.put("name", doctor.getName());
            map.put("username", doctor.getUsername());
            map.put("email", doctor.getEmail());
            map.put("gender", doctor.getGender());
            map.put("phoneNumber", doctor.getPhoneNumber());
            map.put("status", doctor.getStatus());
            map.put("lastUpdateDatetime", doctor.getLastUpdateDatetime());
            combined.add(map);
        }

        for (CounterStaff staff : counterStaffFacade.findAll()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", staff.getId());
            map.put("role", "Counter Staff");
            map.put("name", staff.getName());
            map.put("username", staff.getUsername());
            map.put("email", staff.getEmail());
            map.put("gender", staff.getGender());
            map.put("phoneNumber", staff.getPhoneNumber());
            map.put("status", staff.getStatus());
            map.put("lastUpdateDatetime", staff.getLastUpdateDatetime());
            combined.add(map);
        }
        
//        for (Manager manager : managerFacade.findAll()) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", manager.getId());
//            map.put("role", "Manager");
//            map.put("name", manager.getName());
//            map.put("username", manager.getUsername());
//            map.put("email", manager.getEmail());
//            map.put("gender", manager.getGender());
//            map.put("phoneNumber", manager.getPhoneNumber());
//            map.put("status", manager.getStatus());
//            map.put("lastUpdateDatetime", manager.getLastUpdateDatetime());
//            combined.add(map);
//        }

        // Filtering
        if (keyword != null && column != null && !keyword.isEmpty()) {
            combined = combined.stream()
                    .filter(emp -> {
                        String value = emp.get(column) != null ? emp.get(column).toString().toLowerCase() : "";
                        return value.contains(keyword.toLowerCase());
                    }).collect(Collectors.toList());
        }

        if (statusFilter != null && !statusFilter.isEmpty()) {
            combined = combined.stream()
                    .filter(emp -> statusFilter.equalsIgnoreCase((String) emp.get("status")))
                    .collect(Collectors.toList());
        }

        if (roleFilter != null && !roleFilter.isEmpty()) {
            combined = combined.stream()
                    .filter(emp -> roleFilter.equalsIgnoreCase((String) emp.get("role")))
                    .collect(Collectors.toList());
        }

        // Sort by latest updated at (descending)
        combined.sort((e1, e2) -> {
            Date d1 = (Date) e1.get("lastUpdateDatetime");
            Date d2 = (Date) e2.get("lastUpdateDatetime");
            return d2.compareTo(d1);
        });

        Object modalMessage = request.getSession().getAttribute("modalMessage");
        if (modalMessage != null) {
            request.setAttribute("modalMessage", modalMessage);
            request.getSession().removeAttribute("modalMessage"); // Clear flag after using it
        }

        request.setAttribute("employeeList", combined);
        request.setAttribute("pageContent", "/WEB-INF/views/manager/employee-list.jsp");
        request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffDoctorDetail", urlPatterns = {"/staff/doctor/detail"})
public class StaffDoctorDetail extends HttpServlet {

    @EJB
    private DoctorFacade doctorFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffDoctorDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffDoctorDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Doctor doctor = doctorFacade.find(id);
            if (doctor == null) {
                response.sendRedirect(request.getContextPath() + "/staff/employee/list");
                return;
            }

            request.setAttribute("doctor", doctor);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/doctor-detail.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/staff/employee/list");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Doctor doctor = doctorFacade.find(id);
            String newUsername = request.getParameter("username");

            Doctor existing = doctorFacade.findDoctorByUsername(newUsername);
            if (existing != null && !existing.getId().equals(id)) {
                request.setAttribute("doctor", doctor);
                request.setAttribute("usernameError", "Username already exists.");
                request.setAttribute("pageContent", "/WEB-INF/views/staff/doctor-detail.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            doctor.setName(request.getParameter("name"));
            doctor.setEmail(request.getParameter("email"));
            doctor.setPhoneNumber(request.getParameter("phoneNumber"));
            doctor.setUsername(newUsername);
            doctor.setStatus(request.getParameter("status"));
            doctor.setLastUpdateDatetime(new Date());

            doctorFacade.edit(doctor);
            response.sendRedirect(request.getContextPath() + "/staff/employee/list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/staff/employee/list");
        }
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

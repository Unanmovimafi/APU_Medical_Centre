/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.staff.*;
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
@WebServlet(name = "ManagerCreateDoctor", urlPatterns = { "/manager/doctor/new" })
public class ManagerCreateDoctor extends HttpServlet {

    @EJB
    private DoctorFacade doctorFacade;

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
            out.println("<title>Servlet StaffCreateDoctor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffCreateDoctor at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("pageContent", "/WEB-INF/views/manager/create-doctor.jsp");
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
        try {
            String username = request.getParameter("username");

            if (doctorFacade.findDoctorByUsername(username) != null) {
                request.setAttribute("error", "Username already exists.");
                request.setAttribute("pageContent", "/WEB-INF/views/manager/create-doctor.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                return;
            }

            Doctor doctor = new Doctor();
            doctor.setName(request.getParameter("name"));
            doctor.setEmail(request.getParameter("email"));
            doctor.setPhoneNumber(request.getParameter("phoneNumber"));
            doctor.setGender(request.getParameter("gender"));
            doctor.setUsername(username);
            doctor.setStatus(request.getParameter("status"));
            doctor.setPassword("default"); // replace with real hash
            doctor.setCreateBy("admin");
            doctor.setLastUpdateBy("admin");
            doctor.setCreationDatetime(new Date());
            doctor.setLastUpdateDatetime(new Date());
            doctor.setVersionTime(1);
            doctor.setIc(request.getParameter("ic"));

            doctorFacade.create(doctor);

            // Set success message with doctor name
            request.getSession().setAttribute("modalMessage",
                    "<strong>" + doctor.getName() + "</strong> has been successfully added as new doctor.");
            response.sendRedirect(request.getContextPath() + "/manager/employee/list");

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create doctor: " + e.getMessage());
            request.setAttribute("error", "Failed to create doctor.");
            request.setAttribute("pageContent", "/WEB-INF/views/manager/create-doctor.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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

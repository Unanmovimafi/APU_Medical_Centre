/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import model.medicine.Medicine;
import model.medicine.MedicineFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffCreateMedicine", urlPatterns = { "/staff/medicine/new" })
public class StaffCreateMedicine extends HttpServlet {

    @EJB
    MedicineFacade medicineFacade;

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
            out.println("<title>Servlet StaffCreateMedicine</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffCreateMedicine at " + request.getContextPath() + "</h1>");
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
        String path = request.getServletPath();

        if ("/staff/medicine/new".equals(path)) {
            request.setAttribute("pageContent", "/WEB-INF/views/staff/create-medicine.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
        String action = request.getServletPath();

        if ("/staff/medicine/new".equals(action)) {
            try {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String priceStr = request.getParameter("price");

                // Basic validation
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "Medicine name is required.");
                    request.setAttribute("pageContent", "/WEB-INF/views/staff/create-medicine.jsp");
                    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
                    return;
                }

                Medicine med = new Medicine();
                med.setName(name.trim());
                med.setDescription(description != null ? description.trim() : "");
                med.setPrice(Long.valueOf(priceStr));

                med.setVersionTime(1);
                Date now = new Date();
                med.setCreationDatetime(now);
                med.setCreateBy("admin"); // Replace with session user if available
                med.setLastUpdateDatetime(now);
                med.setLastUpdateBy("admin");

                medicineFacade.create(med); // Persist via facade

                // Redirect with URL-encoded success message
                String successMessage = URLEncoder.encode("Medicine created successfully!", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/staff/medicine/list?success=" + successMessage);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid price format. Please enter a valid number.");
                request.setAttribute("pageContent", "/WEB-INF/views/staff/create-medicine.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "An error occurred while creating the medicine: " + e.getMessage());
                request.setAttribute("pageContent", "/WEB-INF/views/staff/create-medicine.jsp");
                request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
            }
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

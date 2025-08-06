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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.medicine.Medicine;
import model.medicine.MedicineFacade;

/**
 *
 * @author khong
 */
@WebServlet(name = "StaffMedicineList", urlPatterns = {"/staff/medicine/list"})
public class StaffMedicineList extends HttpServlet {

    @EJB
    MedicineFacade medicineFacade;
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
            out.println("<title>Servlet StaffMedicineList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffMedicineList at " + request.getContextPath() + "</h1>");
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
            String column = request.getParameter("column");
            String keywordRaw = request.getParameter("keyword");

            List<Medicine> medicineList = medicineFacade.findAll(); // Assume this gets all medicines

            if (column != null && keywordRaw != null && !keywordRaw.trim().isEmpty()) {
                final String keyword = keywordRaw.trim().toLowerCase();

                medicineList = medicineList.stream().filter(med -> {
                    switch (column) {
                        case "name" -> {
                            return med.getName() != null && med.getName().toLowerCase().contains(keyword);
                        }
                        case "createBy" -> {
                            return med.getCreateBy() != null && med.getCreateBy().toLowerCase().contains(keyword);
                        }
                        case "lastUpdateBy" -> {
                            return med.getLastUpdateBy() != null && med.getLastUpdateBy().toLowerCase().contains(keyword);
                        }
                        default -> {
                            return true;
                        }
                    }
                }).collect(Collectors.toList());
            }

            // ✅ Sort by lastUpdateDatetime (descending)
            medicineList.sort(Comparator.comparing(Medicine::getLastUpdateDatetime).reversed());

            request.setAttribute("medicineList", medicineList);
            request.setAttribute("pageContent", "/WEB-INF/views/staff/medicine-list.jsp");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/layout/layout.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to retrieve medicine list: " + e.getMessage());
            request.setAttribute("pageContent", "/WEB-INF/views/staff/medicine-list.jsp");
            request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
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

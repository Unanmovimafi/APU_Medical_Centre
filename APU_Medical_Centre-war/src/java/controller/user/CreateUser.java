/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import helper.DateTimeHelper;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.codevalue.CodeValue;
import model.codevalue.CodeValueFacade;
import model.counterstaff.CounterStaff;
import model.counterstaff.CounterStaffFacade;
import model.doctor.Doctor;
import model.doctor.DoctorFacade;
import model.manager.Manager;
import model.manager.ManagerFacade;

/**
 *
 * @author zihao
 */
public class CreateUser extends HttpServlet {

    @EJB
    private ManagerFacade managerFacade;

    @EJB
    private CounterStaffFacade counterStaffFacade;

    @EJB
    private DoctorFacade doctorFacade;

    @EJB
    private CodeValueFacade codeValueFacade;

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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String phoneNumber = request.getParameter("phoneNumber");
//        String gender = request.getParameter("gender");

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userSession") != null) {
            if ("MANAGER".equals(role)) {
                Manager newManager = new Manager();

                Manager managerSession = (Manager) session.getAttribute("managerSession");
                newManager.setVersionTime(1);
                newManager.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
                newManager.setCreateBy(managerSession.getUsername());
                newManager.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                newManager.setLastUpdateBy(managerSession.getUsername());

                newManager.setUsername(username);
                newManager.setPassword(password);

                CodeValue cvUserStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("MANAGER_STATUS", "ACTIVE");
                newManager.setStatus(cvUserStatus);
                newManager.setName(name);
                newManager.setEmail(email);
                try {
                    newManager.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                } catch (ParseException ex) {
                    System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                newManager.setPhoneNumber(phoneNumber);
                managerFacade.create(newManager);

            } 
            else if ("COUNTER_STAFF".equals(role)) {
                CounterStaff newCounterStaff = new CounterStaff();

                Manager managerSession = (Manager) session.getAttribute("managerSession");
                newCounterStaff.setVersionTime(1);
                newCounterStaff.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
                newCounterStaff.setCreateBy(managerSession.getUsername());
                newCounterStaff.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                newCounterStaff.setLastUpdateBy(managerSession.getUsername());

                newCounterStaff.setUsername(username);
                newCounterStaff.setPassword(password);

                CodeValue cvUserStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("MANAGER_STATUS", "ACTIVE");
                newCounterStaff.setStatus(cvUserStatus);
                newCounterStaff.setName(name);
                newCounterStaff.setEmail(email);
                try {
                    newCounterStaff.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                } catch (ParseException ex) {
                    System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                newCounterStaff.setPhoneNumber(phoneNumber);
                counterStaffFacade.create(newCounterStaff);

            }
            else if ("DOCTOR".equals(role)) {
                Doctor newDoctor = new Doctor();

                Manager managerSession = (Manager) session.getAttribute("managerSession");
                newDoctor.setVersionTime(1);
                newDoctor.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
                newDoctor.setCreateBy(managerSession.getUsername());
                newDoctor.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                newDoctor.setLastUpdateBy(managerSession.getUsername());

                newDoctor.setUsername(username);
                newDoctor.setPassword(password);

                CodeValue cvUserStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("DOCTOR_STATUS", "ACTIVE");
                newDoctor.setStatus(cvUserStatus);
                newDoctor.setName(name);
                newDoctor.setEmail(email);
                try {
                    newDoctor.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                } catch (ParseException ex) {
                    System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                newDoctor.setPhoneNumber(phoneNumber);
                doctorFacade.create(newDoctor);
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

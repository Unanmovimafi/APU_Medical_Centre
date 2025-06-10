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
import model.customerdetail.CustomerDetail;
import model.staffdetail.StaffDetail;
import model.user.User;
import model.user.UserFacade;

/**
 *
 * @author zihao
 */
public class CreateUser extends HttpServlet {

    @EJB
    private UserFacade userFacade;

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
        
        String allergy = request.getParameter("allergy");
        String bloodType = request.getParameter("bloodType");
//        String gender = request.getParameter("gender");

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userSession") != null) {
            User newUser = new User();
            User userSession = (User) session.getAttribute("userSession");
            newUser.setVersionTime(1);
            newUser.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
            newUser.setCreateBy(userSession.getUsername());
            newUser.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
            newUser.setLastUpdateBy(userSession.getUsername());

            newUser.setUsername(username);
            newUser.setPassword(password);

            CodeValue cvRole = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("USER_ROLE", role);
            CodeValue cvUserStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("USER_STATUS", "ACTIVE");
            newUser.setRole(cvRole);
            newUser.setUserStatus(cvUserStatus);

            if ("MANAGER".equals(role) || "COUNTER_STAFF".equals(role) || "DOCTOR".equals(role)) {
                StaffDetail newStaffDetail = new StaffDetail();

                newStaffDetail.setUser(newUser);

                newStaffDetail.setVersionTime(1);
                newStaffDetail.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
                newStaffDetail.setCreateBy(userSession.getUsername());
                newStaffDetail.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                newStaffDetail.setLastUpdateBy(userSession.getUsername());

                newStaffDetail.setName(name);
                newStaffDetail.setEmail(email);
                try {
                    newStaffDetail.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                } catch (ParseException ex) {
                    System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                newStaffDetail.setPhoneNumber(phoneNumber);

                newUser.setStaffDetail(newStaffDetail);

            } else if ("CUSTOMER".equals(role)) {
                CustomerDetail newCustomerDetail = new CustomerDetail();

                newCustomerDetail.setUser(newUser);

                newCustomerDetail.setVersionTime(1);
                newCustomerDetail.setCreationDatetime(DateTimeHelper.getCurrentDateTime());
                newCustomerDetail.setCreateBy(userSession.getUsername());
                newCustomerDetail.setLastUpdateDatetime(DateTimeHelper.getCurrentDateTime());
                newCustomerDetail.setLastUpdateBy(userSession.getUsername());

                newCustomerDetail.setName(name);
                newCustomerDetail.setEmail(email);

                try {
                    newCustomerDetail.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                } catch (ParseException ex) {
                    System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                newCustomerDetail.setPhoneNumber(phoneNumber);

                newCustomerDetail.setAllergic(allergy);
                newCustomerDetail.setBloodType(bloodType);

                newUser.setCustomerDetail(newCustomerDetail);
            }
            userFacade.create(newUser);
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

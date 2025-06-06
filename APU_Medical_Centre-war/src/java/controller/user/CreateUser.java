/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import facade.codevalue.CodeValueFacade;
import facade.user.UserFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import model.codevalue.CodeValue;
import model.customerdetail.CustomerDetail;
import model.staffdetail.StaffDetail;
import model.user.User;

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

        User newUser = new User();

        LocalDateTime localDateTime = LocalDateTime.now();  // Get the current date and time
        Instant instantLocalDateTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date nowDate = Date.from(instantLocalDateTime);

        newUser.setVersionTime(1);
        newUser.setCreationDatetime(nowDate);
        newUser.setCreateBy("TEST");
        newUser.setLastUpdateDatetime(nowDate);
        newUser.setLastUpdateBy("TEST");

        newUser.setUsername(username);
        newUser.setPassword(password);

        CodeValue cvRole = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("USER_ROLE", role);
        CodeValue cvUserStatus = codeValueFacade.findActiveCodeValueByCodeSetAndCodeValue("USER_STATUS", "ACTIVE");
        newUser.setRole(cvRole);
        newUser.setUserStatus(cvUserStatus);
        
        if ("MANAGER".equals(role) || "COUNTER_STAFF".equals(role) || "DOCTOR".equals(role)){
            StaffDetail newStaffDetail = new StaffDetail();
            
            newStaffDetail.setUser(newUser);
            
            newStaffDetail.setVersionTime(1);
            newStaffDetail.setCreationDatetime(nowDate);
            newStaffDetail.setCreateBy("TEST");
            newStaffDetail.setLastUpdateDatetime(nowDate);
            newStaffDetail.setLastUpdateBy("TEST");
            
            newStaffDetail.setName(name);
            newStaffDetail.setEmail(email);
            try {
                newStaffDetail.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
            } catch (ParseException ex) {
                System.getLogger(CreateUser.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            newStaffDetail.setPhoneNumber(phoneNumber);

            newUser.setStaffDetail(newStaffDetail);

        } else if ("CUSTOMER".equals(role)){
            CustomerDetail newCustomerDetail = new CustomerDetail();
            
            newCustomerDetail.setUser(newUser);
            
            newCustomerDetail.setVersionTime(1);
            newCustomerDetail.setCreationDatetime(nowDate);
            newCustomerDetail.setCreateBy("TEST");
            newCustomerDetail.setLastUpdateDatetime(nowDate);
            newCustomerDetail.setLastUpdateBy("TEST");

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

//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CreateUser</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CreateUser at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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

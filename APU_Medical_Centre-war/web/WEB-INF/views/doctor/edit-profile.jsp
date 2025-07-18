<%-- 
    Document   : edit-profile
    Created on : 11 Jun 2025, 1:09:55 am
    Author     : khong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ include file="/WEB-INF/layout/layout.jsp" %>--%>

<!--<c:set var="pageCss" value="../assets/css/dashboard.css" />-->

<content>
    <h2>Edit Profile</h2>
    <p>${role}</p>

    <!-- Display error message if customer details not found -->
    <c:if test="${not empty errorMessage}">
        <div class="error-message" style="color: red; padding: 10px; border: 1px solid red; border-radius: 5px;">
            ${errorMessage}
        </div>
    </c:if>

    <!-- Form for editing customer details -->
    <c:if test="${role == 'CUSTOMER' && user.getCustomerDetail() != null}">
        <form action="/save-profile" method="post">
            <input type="hidden" name="role" value="${role}" />
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${user.getCustomerDetail().getName}" required />
            </div>

            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.getCustomerDetail().getEmail}" required />
            </div>

            <div>
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber" value="${user.getCustomerDetail().getPhoneNumber}" required />
            </div>

            <div>
                <label for="bloodType">Blood Type:</label>
                <input type="text" id="bloodType" name="bloodType" value="${user.getCustomerDetail().getBloodType}" required />
            </div>

            <div>
                <label for="dateOfBirth">Date of Birth:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.getCustomerDetail().getDateOfBirth}" required />
            </div>

            <!-- Profile Picture Upload -->
            <div>
                <label for="profilePicture">Profile Picture:</label>
                <input type="file" id="profilePicture" name="profilePicture" />
            </div>

            <!-- Allergies -->
            <div>
                <label for="allergic">Allergies:</label>
                <textarea id="allergic" name="allergic" rows="4" cols="50">${user.getCustomerDetail().allergic}</textarea>
            </div>

            <div>
                <button type="submit">Save Changes</button>
            </div>
        </form>
    </c:if>
    <c:if test="${(role == 'MANAGER' || role == 'COUNTER_STAFF' || role == 'DOCTOR') && user.getStaffDetail() != null}"> 
       <form action="edit-profile" method="post">
           <input type="hidden" name="role" value="${role}" />
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${user.getStaffDetail().getName()}" required />
            </div>

            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.getStaffDetail().getEmail()}" required />
            </div>

            <div>
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber" value="${user.getStaffDetail().getPhoneNumber()}" required />
            </div>

<!--            <div>
                <label for="dateOfJoining">Date of Birth:</label>
                <input type="date" id="dateOfJoining" name="dateOfJoining" value="${user.getStaffDetail().getDateOfBirth()}" required />
            </div>-->


            <!-- Profile Picture Upload -->
            <div>
                <label for="profilePicture">Profile Picture:</label>
                <input type="file" id="profilePicture" name="profilePicture" />
            </div>

            <div>
                <button type="submit">Save Changes</button>
            </div>
        </form>
    </c:if>
</content>

<!--<script>
    // Dynamically inject a page-specific stylesheet
    let link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = '../assets/css/dashboard.css';  // Path to page-specific CSS
    document.head.appendChild(link);

    // Dynamically inject a page-specific script
    let script = document.createElement('script');
    script.src = '../assets/js/dashboard.js';  // Path to page-specific JS
    document.head.appendChild(script);
</script>-->
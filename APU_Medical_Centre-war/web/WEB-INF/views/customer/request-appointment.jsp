<%-- 
    Document   : request-appointment
    Created on : Customer Appointment Request
    Author     : System
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<content>
<style>
    body {
        background-color: #E0F7FA;
        font-family: Arial, sans-serif;
    }

    h2 {
        color: #00BFFF;
        padding-bottom: 25px;
        justify-self: center;
    }

    .mainbody {
        padding: 40px;
    }

    .form-section {
        width: 100%;
        max-width: 800px;
        margin: 0 auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .form-table {
        width: 100%;
    }

    .form-table td {
        padding: 10px 8px;
        vertical-align: middle;
    }

    .form-table td:first-child {
        width: 150px;
        white-space: nowrap;
        font-weight: bold;
    }

    input[type="date"], select, textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
    }

    textarea {
        min-height: 80px;
        resize: vertical;
    }

    input:focus, select:focus, textarea:focus {
        border-color: #00BFFF;
        background-color: #F0FFFF;
        outline: none;
    }

    .button-footer {
        display: flex;
        justify-content: space-between;
        gap: 15px;
        width: 100%;
        margin-top: 30px;
    }

    .button-footer button,
    .button-footer .btn {
        padding: 12px 24px;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        text-decoration: none;
        text-align: center;
        min-width: 120px;
    }

    .request-btn {
        background-color: #28A745;
        color: white;
    }

    .request-btn:hover {
        background-color: #218838;
    }

    .cancel-btn {
        background-color: #6C757D;
        color: white;
    }

    .cancel-btn:hover {
        background-color: #5A6268;
    }

    .success-message {
        color: #155724;
        background-color: #D4EDDA;
        border: 1px solid #C3E6CB;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 20px;
    }

    .error-message {
        color: #DC3545;
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 20px;
    }

    input:required:invalid, select:required:invalid {
        border-color: #DC3545;
        background-color: #fff5f5;
    }
</style>

<div class="mainbody">
    <h2>Request New Appointment</h2>
    
    <div class="form-section">
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="success-message">
                ${sessionScope.successMessage}
                <c:remove var="successMessage" scope="session"/>
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/customer/appointment/request" method="post">
            <table class="form-table">
                <tr>
                    <td><strong>Patient Name :</strong></td>
                    <td><strong>${customer.name}</strong></td>
                </tr>
                <tr>
                    <td><strong>Appointment Date :</strong></td>
                    <td><input type="date" id="appointmentDate" name="appointmentDate" value="${param.appointmentDate}" required /></td>
                </tr>
                <tr>
                    <td><strong>Select Doctor :</strong></td>
                    <td>
                        <select id="doctorId" name="doctorId" required>
                            <option value="">-- Select Doctor --</option>
                            <c:forEach var="doctor" items="${doctors}">
                                <option value="${doctor.id}" ${param.doctorId == doctor.id ? 'selected' : ''}>${doctor.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Preferred Time :</strong></td>
                    <td>
                        <select id="appointmentTime" name="appointmentTime" required>
                            <option value="">-- Select Time --</option>
                            <option value="09:00" ${param.appointmentTime == '09:00' ? 'selected' : ''}>09:00 AM</option>
                            <option value="09:30" ${param.appointmentTime == '09:30' ? 'selected' : ''}>09:30 AM</option>
                            <option value="10:00" ${param.appointmentTime == '10:00' ? 'selected' : ''}>10:00 AM</option>
                            <option value="10:30" ${param.appointmentTime == '10:30' ? 'selected' : ''}>10:30 AM</option>
                            <option value="11:00" ${param.appointmentTime == '11:00' ? 'selected' : ''}>11:00 AM</option>
                            <option value="11:30" ${param.appointmentTime == '11:30' ? 'selected' : ''}>11:30 AM</option>
                            <option value="14:00" ${param.appointmentTime == '14:00' ? 'selected' : ''}>02:00 PM</option>
                            <option value="14:30" ${param.appointmentTime == '14:30' ? 'selected' : ''}>02:30 PM</option>
                            <option value="15:00" ${param.appointmentTime == '15:00' ? 'selected' : ''}>03:00 PM</option>
                            <option value="15:30" ${param.appointmentTime == '15:30' ? 'selected' : ''}>03:30 PM</option>
                            <option value="16:00" ${param.appointmentTime == '16:00' ? 'selected' : ''}>04:00 PM</option>
                            <option value="16:30" ${param.appointmentTime == '16:30' ? 'selected' : ''}>04:30 PM</option>
                            <option value="17:00" ${param.appointmentTime == '17:00' ? 'selected' : ''}>05:00 PM</option>
                        </select>
                    </td>
                </tr>
            </table>

            <div class="button-footer">
                <button type="submit" class="request-btn">Submit Request</button>
                <a href="${pageContext.request.contextPath}/customer/dashboard" class="btn cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
</div>

<script>
    // Set minimum date to today
    document.addEventListener("DOMContentLoaded", function () {
        var dateInput = document.getElementById("appointmentDate");
        var today = new Date().toISOString().split('T')[0];
        dateInput.min = today;
        
        // Set default date to tomorrow if no date selected
        if (!dateInput.value) {
            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            dateInput.min = today;
        }
    });
</script>
</content>

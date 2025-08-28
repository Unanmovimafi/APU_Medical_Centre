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
                    <td><input type="date" id="appointmentDate" name="appointmentDate" 
                               value="${selectedDate != null ? selectedDate : param.appointmentDate}" required /></td>
                </tr>
                <tr>
                    <td><strong>Select Doctor :</strong></td>
                    <td>
                        <select id="doctorId" name="doctorId" required>
                            <option value="">-- Select Doctor --</option>
                            <c:forEach var="doctor" items="${doctors}">
                                <option value="${doctor.id}" 
                                        ${(selectedDoctorId != null && selectedDoctorId == doctor.id.toString()) || 
                                          (selectedDoctorId == null && param.doctorId == doctor.id) ? 'selected' : ''}>
                                    ${doctor.name} (${doctor.username})
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Time Slot :</strong></td>
                    <td>
                        <select id="appointmentTime" name="appointmentTime" required>
                            <option value="">-- Select Time --</option>
                            <c:if test="${selectedTimeSlot != null}">
                                <option value="${selectedTimeSlot}" selected>${selectedTimeSlot}</option>
                            </c:if>
                            <!-- options will be filled by JS -->
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

<!-- AJAX Script for Time Slot Update -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var dateInput = document.getElementById("appointmentDate");
        var doctorSelect = document.getElementById("doctorId");
        var timeSlotSelect = document.getElementById("appointmentTime");

        // Set minimum date to today
        var today = new Date().toISOString().split('T')[0];
        dateInput.setAttribute('min', today);

        function fetchTimeSlots() {
            var date = dateInput.value;
            var doctorId = doctorSelect.value;

            console.log("Selected Date: ", date);
            console.log("Selected Doctor: ", doctorId);

            if (date && doctorId) {
                var contextPath = "${pageContext.request.contextPath}";
                fetch(contextPath + "/customer/get-available-slots?appointmentDate=" + date + "&doctorId=" + doctorId)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        // Check if there was a previously selected time slot
                        var selectedTimeSlot = "${selectedTimeSlot}";
                        
                        timeSlotSelect.innerHTML = '<option value="">-- Select Time --</option>';
                        data.forEach(slot => {
                            const option = document.createElement("option");
                            option.value = slot;
                            option.textContent = slot;
                            // Re-select the previously selected slot if it's still available
                            if (selectedTimeSlot && slot === selectedTimeSlot) {
                                option.selected = true;
                            }
                            timeSlotSelect.appendChild(option);
                        });
                    })
                    .catch(err => {
                        console.error("Failed to fetch time slots", err);
                        timeSlotSelect.innerHTML = '<option value="">-- Error loading slots --</option>';
                    });
            } else {
                timeSlotSelect.innerHTML = '<option value="">-- Select Time --</option>';
            }
        }

        doctorSelect.addEventListener("change", fetchTimeSlots);
        dateInput.addEventListener("change", fetchTimeSlots);
        
        // If form data is pre-filled (after error), fetch time slots automatically
        if (dateInput.value && doctorSelect.value) {
            fetchTimeSlots();
        }
    });
</script>
</content>

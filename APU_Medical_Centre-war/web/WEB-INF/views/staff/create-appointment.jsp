<%-- 
    Document   : create-appointment
    Created on : 16 Jun 2025, 2:12:25 pm
    Author     : khong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<content>
    <h2>Create Appointment</h2>

    <form action="new" method="post">
        <!-- Select Date -->
        <div>
            <label for="appointmentDate">Select Date:</label>
            <input type="date" id="appointmentDate" name="appointmentDate" required />
        </div>

        <!-- Select Doctor -->
        <div>
            <label for="doctorId">Select Doctor:</label>
            <select id="doctorId" name="doctorId" required>
                <option value="">-- Select Doctor --</option>
                <c:forEach var="doctor" items="${doctors}">
                    <option value="${doctor.id}">${doctor.username}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Dynamic Time Slot Dropdown -->
        <div>
            <label for="appointmentTime">Select Time Slot (30 min):</label>
            <select id="appointmentTime" name="appointmentTime" required>
                <option value="">-- Select Time --</option>
                <!-- options will be filled by JS -->
            </select>
        </div>

        <!-- Select Customer -->
        <div>
            <label for="customerId">Select Customer:</label>
            <select id="customerId" name="customerId" required>
                <option value="">-- Select Customer --</option>
                <c:forEach var="customer" items="${customers}">
                    <option value="${customer.id}">${customer.username}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Submit Button -->
        <div>
            <button type="submit">Create Appointment</button>
        </div>
    </form>

    <!-- Display Error (if any) -->
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <!-- AJAX Script for Time Slot Update -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var dateInput = document.getElementById("appointmentDate");
            var doctorSelect = document.getElementById("doctorId");
            var timeSlotSelect = document.getElementById("appointmentTime");

            function fetchTimeSlots() {
                var date = dateInput.value;
                var doctorId = doctorSelect.value;

                console.log("Selected Date: ", date);
                console.log("Selected Doctor: ", doctorId);

                if (date && doctorId) {
                    fetch(`/APU_Medical_Centre-war/staff/get-available-slots?appointmentDate=2025-07-21&doctorId=1`)
                        .then(response => response.json())
                        .then(data => {
                            timeSlotSelect.innerHTML = '<option value="">-- Select Time --</option>';
                            data.forEach(slot => {
                                const option = document.createElement("option");
                                option.value = slot;
                                option.textContent = slot;
                                timeSlotSelect.appendChild(option);
                            });
                        })
                        .catch(err => {
                            console.error("Failed to fetch time slots", err);
                        });
                } else {
                    timeSlotSelect.innerHTML = '<option value="">-- Select Time --</option>';
                }
            }

            doctorSelect.addEventListener("change", fetchTimeSlots);
            dateInput.addEventListener("change", fetchTimeSlots);
        });
    </script>
</content>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    body {
        background-color: #E0F7FA;
        font-family: Arial, sans-serif;
    }

    h2 {
        color: #00BFFF;
        padding-bottom: 25px;
        text-align: center;
    }

    .mainbody {
        padding: 40px;
    }

    /* Main container for side-by-side layout */
    .diagnosis-container {
        display: flex;
        gap: 30px;
        max-width: 1400px;
        margin: 0 auto;
    }

    .form-section {
        flex: 1;
        max-width: 600px;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        height: fit-content;
    }

    .history-section {
        flex: 1;
        max-width: 700px;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        height: fit-content;
    }

    .form-table {
        width: 100%;
        margin-bottom: 20px;
    }

    .form-table td {
        padding: 12px 8px;
        vertical-align: top;
    }

    .form-table td:first-child {
        width: 150px;
        white-space: nowrap;
        font-weight: bold;
        color: #495057;
    }

    input[type="text"], input[type="number"], select, textarea {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
        font-family: Arial, sans-serif;
    }

    textarea {
        resize: vertical;
        min-height: 120px;
    }

    input[readonly] {
        background-color: #f8f9fa;
        border-color: #e9ecef;
        color: #6c757d;
    }

    .medicine-selection {
        max-height: 200px;
        overflow-y: auto;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 10px;
        background-color: #f8f9fa;
    }

    .medicine-item {
        display: flex;
        align-items: center;
        padding: 5px 0;
        border-bottom: 1px solid #eee;
    }

    .medicine-item:last-child {
        border-bottom: none;
    }

    .medicine-item input[type="checkbox"] {
        width: auto;
        margin-right: 10px;
    }

    .medicine-info {
        flex: 1;
    }

    .medicine-name {
        font-weight: bold;
        color: #495057;
    }

    .medicine-price {
        color: #6c757d;
        font-size: 13px;
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

    .save-btn {
        background-color: #28A745;
        color: white;
    }

    .save-btn:hover {
        background-color: #218838;
    }

    .cancel-btn {
        background-color: #6C757D;
        color: white;
    }

    .cancel-btn:hover {
        background-color: #5A6268;
    }

    .error-message, .success-message {
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 20px;
    }

    .error-message {
        color: #DC3545;
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
    }

    .success-message {
        color: #155724;
        background-color: #D4EDDA;
        border: 1px solid #C3E6CB;
    }

    /* Medical History Section */
    .history-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        font-size: 13px;
    }

    .history-table th {
        background-color: #e8fafd;
        color: #1c1c1c;
        padding: 12px 8px;
        text-align: left;
        border-bottom: 2px solid #00bfff;
        font-weight: bold;
        font-size: 12px;
    }

    .history-table td {
        padding: 10px 8px;
        border-bottom: 1px solid #eee;
        vertical-align: top;
        font-size: 13px;
    }

    .history-table tr:hover {
        background-color: #f0f8ff;
    }

    .history-date {
        white-space: nowrap;
        font-weight: bold;
        color: #495057;
    }

    .history-status {
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 11px;
        font-weight: bold;
        text-transform: uppercase;
    }

    .status-completed {
        background-color: #d4edda;
        color: #155724;
    }

    .status-finished {
        background-color: #d4edda;
        color: #155724;
    }

    .status-pending {
        background-color: #fff3cd;
        color: #856404;
    }

    .status-cancelled {
        background-color: #f8d7da;
        color: #721c24;
    }

    .status-paid {
        background-color: #d1ecf1;
        color: #0c5460;
    }

    .feedback-cell {
        max-width: 250px;
        word-wrap: break-word;
        line-height: 1.3;
        font-size: 12px;
    }

    .medicine-cell {
        max-width: 150px;
        word-wrap: break-word;
        font-size: 12px;
    }

    .no-history {
        text-align: center;
        color: #6c757d;
        font-style: italic;
        padding: 30px;
    }

    /* Responsive design */
    @media (max-width: 1200px) {
        .diagnosis-container {
            flex-direction: column;
        }
        
        .form-section,
        .history-section {
            max-width: 100%;
        }
    }
</style>

<div class="mainbody">
    <h2>Add Diagnosis</h2>
    
    <!-- Success/Error Messages -->
    <c:if test="${not empty errorMessage}">
        <div class="error-message" style="max-width: 1400px; margin: 0 auto 20px auto;">${errorMessage}</div>
    </c:if>
    
    <div class="diagnosis-container">
        <!-- Left Side: Diagnosis Form -->
        <div class="form-section">
            <h3 style="color: #00BFFF; margin-bottom: 20px; margin-top: 0;">Diagnosis Form</h3>
            
            <c:choose>
                <c:when test="${not empty appointment}">
                    <form action="${pageContext.request.contextPath}/doctor/appointment/diagnosis" method="post">
                        <input type="hidden" name="appointmentId" value="${appointment.id}" />
                        
                        <table class="form-table">
                            <tr>
                                <td><strong>Patient :</strong></td>
                                <td><input type="text" value="${appointment.customer.name}" readonly /></td>
                            </tr>
                            <tr>
                                <td><strong>Date :</strong></td>
                                <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='EEEE, dd MMMM yyyy'/>" readonly /></td>
                            </tr>
                            <tr>
                                <td><strong>Time :</strong></td>
                                <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='HH:mm'/> - <fmt:formatDate value='${appointment.appointmentEndDatetime}' pattern='HH:mm'/>" readonly /></td>
                            </tr>
                            <tr>
                                <td><strong>Feedback :</strong></td>
                                <td>
                                    <textarea name="feedback" placeholder="Enter your medical diagnosis and recommendations..." required></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Charge (RM) :</strong></td>
                                <td>
                                    <input type="number" name="charge" min="0" step="1" placeholder="50" />
                                    <small style="color: #6c757d;">Leave empty for default consultation fee (RM 50)</small>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Prescribe Medicine :</strong></td>
                                <td>
                                    <div class="medicine-selection">
                                        <c:choose>
                                            <c:when test="${not empty availableMedicines}">
                                                <c:forEach var="medicine" items="${availableMedicines}">
                                                    <div class="medicine-item">
                                                        <input type="checkbox" name="medicines" value="${medicine.id}" id="med_${medicine.id}" onchange="toggleQuantityInput('${medicine.id}')" />
                                                        <label for="med_${medicine.id}" class="medicine-info">
                                                            <div class="medicine-name">${medicine.name}</div>
                                                            <div class="medicine-price">RM <fmt:formatNumber value="${medicine.price}" pattern="0.00"/></div>
                                                        </label>
                                                        <div class="quantity-input" id="qty_${medicine.id}" style="display: none; margin-left: 25px; margin-top: 5px;">
                                                            <label for="quantity_${medicine.id}" style="font-size: 12px; color: #666;">Quantity:</label>
                                                            <input type="number" name="quantity_${medicine.id}" id="quantity_${medicine.id}" min="1" max="10" value="1" style="width: 60px; padding: 4px; margin-left: 5px;" />
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="color: #6c757d; font-style: italic;">No medicines available</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                            </tr>
                        </table>

                        <div class="button-footer">
                            <a href="${pageContext.request.contextPath}/doctor/appointment/detail?id=${appointment.id}" class="btn cancel-btn">Cancel</a>
                            <button type="submit" class="save-btn">Complete Diagnosis</button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 40px; color: #6c757d;">
                        <p>Unable to load appointment information.</p>
                        <a href="${pageContext.request.contextPath}/doctor/appointment/list" class="btn cancel-btn">Back to Appointments</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Right Side: Medical History -->
        <div class="history-section">
            <h3 style="color: #00BFFF; margin-bottom: 20px; margin-top: 0;">
                Medical History - 
                <c:choose>
                    <c:when test="${not empty appointment}">${appointment.customer.name}</c:when>
                    <c:otherwise>Patient</c:otherwise>
                </c:choose>
            </h3>
            
            <c:choose>
                <c:when test="${not empty patientHistory}">
                    <table class="history-table">
                        <thead>
                            <tr>
                                <th style="width: 100px;">Date & Time<br><small>(Status)</small></th>
                                <th style="width: 45%;">Feedback</th>
                                <th style="width: 35%;">Medicine</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="historyAppt" items="${patientHistory}">
                                <tr>
                                    <td class="history-date">
                                        <div><fmt:formatDate value="${historyAppt.appointmentStartDatetime}" pattern="dd/MM/yyyy"/></div>
                                        <div><fmt:formatDate value="${historyAppt.appointmentStartDatetime}" pattern="HH:mm"/></div>
                                        <div style="margin-top: 5px;">
                                            <span class="history-status 
                                                <c:choose>
                                                    <c:when test="${historyAppt.status == 'FINISHED'}">status-completed</c:when>
                                                    <c:when test="${historyAppt.status == 'PAID'}">status-paid</c:when>
                                                    <c:when test="${historyAppt.status == 'PENDING'}">status-pending</c:when>
                                                    <c:when test="${historyAppt.status == 'CANCELLED'}">status-cancelled</c:when>
                                                    <c:otherwise>status-pending</c:otherwise>
                                                </c:choose>">
                                                ${historyAppt.status}
                                            </span>
                                        </div>
                                    </td>
                                    <td class="feedback-cell">
                                        <c:choose>
                                            <c:when test="${not empty feedbackMap[historyAppt.id]}">
                                                ${feedbackMap[historyAppt.id].context}
                                            </c:when>
                                            <c:otherwise>
                                                <em style="color: #adb5bd;">No feedback available</em>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="medicine-cell">
                                        <c:choose>
                                            <c:when test="${not empty medicineMap[historyAppt.id]}">
                                                <c:forEach var="appointmentMedicine" items="${medicineMap[historyAppt.id]}" varStatus="status">
                                                    ${appointmentMedicine.medicine.name} (x${appointmentMedicine.quantity})<c:if test="${!status.last}">, </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <em style="color: #adb5bd;">-</em>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-history">
                        <p>No previous medical history found for this patient.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    function toggleQuantityInput(medicineId) {
        const checkbox = document.getElementById('med_' + medicineId);
        const quantityDiv = document.getElementById('qty_' + medicineId);
        const quantityInput = document.getElementById('quantity_' + medicineId);
        
        console.log('toggleQuantityInput called for medicine:', medicineId, 'checked:', checkbox.checked);
        
        if (checkbox.checked) {
            quantityDiv.style.display = 'block';
            quantityInput.required = true;
            console.log('Medicine', medicineId, 'selected, quantity input shown');
        } else {
            quantityDiv.style.display = 'none';
            quantityInput.required = false;
            quantityInput.value = 1; // Reset to default
            console.log('Medicine', medicineId, 'deselected, quantity input hidden');
        }
    }

    // Add form submission debugging
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.querySelector('form[action*="/doctor/appointment/diagnosis"]');
        if (form) {
            form.addEventListener('submit', function(e) {
                console.log('Form submission started...');
                
                // Log selected medicines
                const selectedMedicines = document.querySelectorAll('input[name="medicines"]:checked');
                console.log('Selected medicines count:', selectedMedicines.length);
                
                selectedMedicines.forEach(function(checkbox) {
                    const medicineId = checkbox.value;
                    const quantityInput = document.getElementById('quantity_' + medicineId);
                    console.log('Medicine ID:', medicineId, 'Quantity:', quantityInput ? quantityInput.value : 'N/A');
                });
                
                // Log charge
                const chargeInput = document.querySelector('input[name="charge"]');
                console.log('Charge:', chargeInput ? chargeInput.value : 'empty');
                
                // Log feedback
                const feedbackInput = document.querySelector('textarea[name="feedback"]');
                console.log('Feedback length:', feedbackInput ? feedbackInput.value.length : 0);
            });
        }
    });
</script>

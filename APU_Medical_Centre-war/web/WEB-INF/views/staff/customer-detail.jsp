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

    input[type="text"], input[type="email"], input[type="date"], input[type="tel"], select, textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
    }

    input:focus, select:focus, textarea:focus {
        border-color: #00BFFF;
        background-color: #F0FFFF;
        outline: none;
    }

    input[readonly], textarea[readonly], select[disabled] {
        background-color: #f8f9fa;
        border-color: #e9ecef;
        color: #6c757d;
    }

    textarea {
        min-height: 80px;
        resize: vertical;
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

    .modify-btn, .save-btn {
        background-color: #007BFF;
        color: white;
    }

    .modify-btn:hover, .save-btn:hover {
        background-color: #0056B3;
    }

    .back-btn, .cancel-btn {
        background-color: #6C757D;
        color: white;
    }

    .back-btn:hover, .cancel-btn:hover {
        background-color: #5A6268;
    }

    .error-message {
        color: #DC3545;
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 20px;
    }

    .modal {
        display: none;
        position: fixed;
        z-index: 10000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.6);
    }

    .modal-content {
        background-color: #F2FCFD;
        color: #1C1C1C;
        margin: 15% auto;
        padding: 30px;
        width: 400px;
        border-radius: 12px;
        text-align: left;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        line-height: 1.5;
        font-family: Arial, sans-serif;
    }

    .modal-content h3 {
        margin-top: 0;
        margin-bottom: 10px;
        color: #1C1C1C;
        font-size: 20px;
    }

    .modal-content p {
        margin-top: 5px;
        padding-bottom: 25px;
        font-size: 15px;
    }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        margin-top: 25px;
        width: 100%;
    }

    .modal-footer button {
        padding: 12px 24px;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        background-color: #00BFFF;
        color: white;
    }

    .modal-footer button:hover {
        background-color: #00ACC1;
    }

    .medical-history-section {
        width: 100%;
        max-width: 800px;
        margin: 40px auto 0 auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .medical-history-title {
        color: #00BFFF;
        font-size: 20px;
        margin-bottom: 20px;
        border-bottom: 2px solid #e9ecef;
        padding-bottom: 10px;
    }

    .history-item {
        display: flex;
        margin-bottom: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #e9ecef;
    }

    .history-item:last-child {
        border-bottom: none;
        margin-bottom: 0;
        padding-bottom: 0;
    }

    .history-datetime {
        flex-shrink: 0;
        width: 200px;
        padding-right: 20px;
    }

    .history-date {
        font-weight: bold;
        color: #495057;
        font-size: 14px;
        margin-bottom: 4px;
    }

    .history-time {
        color: #6c757d;
        font-size: 13px;
        margin-bottom: 4px;
    }

    .history-status {
        padding: 3px 8px;
        border-radius: 4px;
        font-size: 11px;
        font-weight: bold;
        text-transform: uppercase;
    }

    .status-completed {
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

    .history-feedback {
        flex: 1;
        padding: 15px;
        background-color: #f8f9fa;
        border-radius: 8px;
        border-left: 4px solid #00BFFF;
    }

    .feedback-text {
        color: #495057;
        line-height: 1.5;
        margin: 0;
        font-size: 14px;
    }

    .no-feedback {
        color: #adb5bd;
        font-style: italic;
    }

    .no-history {
        text-align: center;
        color: #6c757d;
        font-style: italic;
        padding: 40px 20px;
        background-color: #f8f9fa;
        border-radius: 8px;
        border: 2px dashed #dee2e6;
    }

    .profile-picture {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        border: 3px solid #00BFFF;
        object-fit: cover;
        margin-bottom: 20px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        display: block;
        margin-left: auto;
        margin-right: auto;
    }

    .profile-picture-header {
        text-align: center;
        padding: 20px 0;
        border-bottom: 2px solid #e9ecef;
        margin-bottom: 20px;
    }
</style>

<div class="mainbody">
    <h2>Customer Details</h2>
    
    <div class="form-section">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <!-- Profile Picture Header -->
        <div class="profile-picture-header">
            <c:choose>
                <c:when test="${not empty customer.profilePicture}">
                    <img src="${pageContext.request.contextPath}/assets/profile/${customer.profilePicture}" 
                         alt="Profile Picture" class="profile-picture" />
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/assets/images/default-avatar.png" 
                         alt="Default Profile" class="profile-picture" />
                </c:otherwise>
            </c:choose>
        </div>
        
        <form method="post" action="${pageContext.request.contextPath}/staff/customer/detail" id="customerForm">
            <input type="hidden" name="id" value="${customer.id}" />

            <table class="form-table">
                <tr>
                    <td><strong>Name :</strong></td>
                    <td><input type="text" name="name" value="${customer.name}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Email :</strong></td>
                    <td><input type="email" name="email" value="${customer.email}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>IC :</strong></td>
                    <td><input type="tel" name="ic" value="${customer.ic}" pattern="^\d{12}$" title="Please enter a 12-digit number" oninput="this.value = this.value.replace(/[^0-9]/g, '')" maxlength="12" readonly/></td>
                </tr>
                <tr>
                    <td><strong>Phone Number :</strong></td>
                    <td><input type="tel" name="phoneNumber" value="${customer.phoneNumber}" pattern="[0-9]+" title="Please enter numbers only" oninput="this.value = this.value.replace(/[^0-9]/g, '')" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Gender :</strong></td>
                    <td>
                        <select name="gender" disabled>
                            <option value="">-- Select Gender --</option>
                            <option value="Male" ${customer.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${customer.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${customer.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Date of Birth :</strong></td>
                    <td><input type="date" name="dateOfBirth" value="<fmt:formatDate value='${customer.dateOfBirth}' pattern='yyyy-MM-dd' />" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Username :</strong></td>
                    <td><input type="text" name="username" value="${customer.username}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Blood Type :</strong></td>
                    <td>
                        <select name="bloodType" disabled>
                            <option value="">-- Select Blood Type --</option>
                            <option value="A" ${customer.bloodType == 'A' ? 'selected' : ''}>A</option>
                            <option value="B" ${customer.bloodType == 'B' ? 'selected' : ''}>B</option>
                            <option value="AB" ${customer.bloodType == 'AB' ? 'selected' : ''}>AB</option>
                            <option value="O" ${customer.bloodType == 'O' ? 'selected' : ''}>O</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Allergic Info :</strong></td>
                    <td><textarea name="allergic" placeholder="Any known allergies..." readonly>${customer.allergic}</textarea></td>
                </tr>
                <tr>
                    <td><strong>Status :</strong></td>
                    <td>
                        <select name="status" disabled>
                            <option value="ACTIVE" ${customer.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                            <option value="INACTIVE" ${customer.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                        </select>
                    </td>
                </tr>
            </table>

            <div class="button-footer">
                <button type="button" id="modifyBtn" class="modify-btn" onclick="enableEdit()">Modify</button>
                <button type="submit" id="saveBtn" class="save-btn" style="display: none;">Save</button>
                <a href="${pageContext.request.contextPath}/staff/customer/list" class="btn back-btn" id="backBtn">Back</a>
                <button type="button" id="cancelBtn" class="cancel-btn" style="display: none;" onclick="disableEdit()">Cancel</button>
            </div>
        </form>
    </div>

    <!-- Medical History Section -->
    <div class="medical-history-section">
        <h3 class="medical-history-title">Medical History</h3>
        
        <c:choose>
            <c:when test="${not empty appointmentList}">
                <c:forEach var="appointment" items="${appointmentList}">
                    <div class="history-item">
                        <div class="history-datetime">
                            <div class="history-date">
                                <fmt:formatDate value="${appointment.appointmentStartDatetime}" pattern="dd/MM/yyyy"/>
                            </div>
                            <div class="history-time">
                                <fmt:formatDate value="${appointment.appointmentStartDatetime}" pattern="HH:mm" /> - 
                                <fmt:formatDate value="${appointment.appointmentEndDatetime}" pattern="HH:mm" />
                            </div>
                            <span class="history-status 
                                <c:choose>
                                    <c:when test="${appointment.status == 'COMPLETED'}">status-completed</c:when>
                                    <c:when test="${appointment.status == 'PAID'}">status-paid</c:when>
                                    <c:when test="${appointment.status == 'PENDING'}">status-pending</c:when>
                                    <c:when test="${appointment.status == 'CANCELLED'}">status-cancelled</c:when>
                                    <c:otherwise>status-pending</c:otherwise>
                                </c:choose>">
                                ${appointment.status}
                            </span>
                        </div>
                        <div class="history-feedback">
                            <c:set var="feedback" value="${feedbackMap[appointment.id]}" />
                            <c:choose>
                                <c:when test="${not empty feedback}">
                                    <p class="feedback-text">
                                        <strong>Doctor:</strong> Dr. ${appointment.doctor.name}<br/>
                                        <strong>Diagnosis:</strong> ${feedback.diagnosis}<br/>
                                        <strong>Treatment:</strong> ${feedback.treatment}<br/>
                                        <c:if test="${not empty feedback.doctorNotes}">
                                            <strong>Notes:</strong> ${feedback.doctorNotes}<br/>
                                        </c:if>
                                        <c:if test="${not empty feedback.appointmentMedicineCollection}">
                                            <strong>Medicine:</strong> 
                                            <c:forEach var="medicine" items="${feedback.appointmentMedicineCollection}" varStatus="status">
                                                ${medicine.medicine.name} (Qty: ${medicine.quantity})<c:if test="${!status.last}">, </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <p class="feedback-text">
                                        <strong>Doctor:</strong> Dr. ${appointment.doctor.name}<br/>
                                        <span class="no-feedback">No diagnosis recorded</span>
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-history">
                    <p>No medical history available for this customer.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div id="successModal" class="modal">
    <div class="modal-content">
        <h3>Success</h3>
        <p>Customer details have been successfully updated.</p>
        <div class="modal-footer">
            <button onclick="closeSuccessModal()">OK</button>
        </div>
    </div>
</div>

<c:if test="${not empty successMessage}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            document.getElementById('successModal').style.display = 'block';
        });
    </script>
</c:if>

<script>
    // Store original values when page loads
    let originalValues = {};
    
    window.addEventListener("DOMContentLoaded", function () {
        storeOriginalValues();
    });

    function storeOriginalValues() {
        originalValues = {
            name: document.querySelector('input[name="name"]').value,
            email: document.querySelector('input[name="email"]').value,
            phoneNumber: document.querySelector('input[name="phoneNumber"]').value,
            dateOfBirth: document.querySelector('input[name="dateOfBirth"]').value,
            username: document.querySelector('input[name="username"]').value,
            gender: document.querySelector('select[name="gender"]').value,
            bloodType: document.querySelector('select[name="bloodType"]').value,
            allergic: document.querySelector('textarea[name="allergic"]').value,
            status: document.querySelector('select[name="status"]').value
        };
    }

    function enableEdit() {
        document.querySelectorAll('#customerForm input, #customerForm textarea, #customerForm select').forEach(el => {
            if (el.name !== "id") el.removeAttribute('readonly');
            if (el.tagName === 'SELECT') el.removeAttribute('disabled');
        });

        document.getElementById('modifyBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline';
        document.getElementById('backBtn').style.display = 'none';
        document.getElementById('cancelBtn').style.display = 'inline';
    }

    function disableEdit() {
        // Restore original values
        document.querySelector('input[name="name"]').value = originalValues.name;
        document.querySelector('input[name="email"]').value = originalValues.email;
        document.querySelector('input[name="phoneNumber"]').value = originalValues.phoneNumber;
        document.querySelector('input[name="dateOfBirth"]').value = originalValues.dateOfBirth;
        document.querySelector('input[name="username"]').value = originalValues.username;
        document.querySelector('select[name="gender"]').value = originalValues.gender;
        document.querySelector('select[name="bloodType"]').value = originalValues.bloodType;
        document.querySelector('textarea[name="allergic"]').value = originalValues.allergic;
        document.querySelector('select[name="status"]').value = originalValues.status;

        // Set fields back to readonly/disabled
        document.querySelectorAll('#customerForm input, #customerForm textarea, #customerForm select').forEach(el => {
            el.setAttribute('readonly', true);
            if (el.tagName === 'SELECT') el.setAttribute('disabled', true);
        });

        document.getElementById('modifyBtn').style.display = 'inline';
        document.getElementById('saveBtn').style.display = 'none';
        document.getElementById('backBtn').style.display = 'inline';
        document.getElementById('cancelBtn').style.display = 'none';
    }

    function closeSuccessModal() {
        document.getElementById('successModal').style.display = 'none';
        // Store new values after successful save
        storeOriginalValues();
    }
</script>

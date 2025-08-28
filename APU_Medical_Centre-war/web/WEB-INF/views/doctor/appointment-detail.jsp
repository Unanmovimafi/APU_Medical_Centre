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

    input[readonly], textarea[readonly], select[disabled] {
        background-color: #f8f9fa;
        border-color: #e9ecef;
        color: #6c757d;
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

    .diagnosis-btn {
        background-color: #28A745;
        color: white;
    }

    .diagnosis-btn:hover {
        background-color: #218838;
    }

    .back-btn {
        background-color: #6C757D;
        color: white;
    }

    .back-btn:hover {
        background-color: #5A6268;
    }

    .badge {
        padding: 5px 10px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
    }

    .badge-warning {
        background-color: #FFC107;
        color: #212529;
    }

    .badge-info {
        background-color: #17A2B8;
        color: white;
    }

    .badge-primary {
        background-color: #007BFF;
        color: white;
    }

    .badge-success {
        background-color: #28A745;
        color: white;
    }

    .badge-danger {
        background-color: #DC3545;
        color: white;
    }

    .badge-secondary {
        background-color: #6C757D;
        color: white;
    }

    .error-message, .success-message {
        border-radius: 8px;
        padding: 10px;
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
</style>

<div class="mainbody">
    <h2>Appointment Details</h2>
    
    <div class="form-section">
        <!-- Success/Error Messages -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="success-message">${sessionScope.successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="error-message">${sessionScope.errorMessage}</div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty appointment}">
            <table class="form-table">
                <tr>
                    <td><strong>Customer :</strong></td>
                    <td><input type="text" value="${appointment.customer.name}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Customer Email :</strong></td>
                    <td><input type="text" value="${appointment.customer.email}" readonly /></td>
                </tr>
                <c:if test="${not empty appointment.customer.phoneNumber}">
                <tr>
                    <td><strong>Customer Phone :</strong></td>
                    <td><input type="text" value="${appointment.customer.phoneNumber}" readonly /></td>
                </tr>
                </c:if>
                <tr>
                    <td><strong>Date :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='EEEE, dd MMMM yyyy'/>" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Start Time :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='HH:mm'/>" readonly /></td>
                </tr>
                <tr>
                    <td><strong>End Time :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentEndDatetime}' pattern='HH:mm'/>" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Status :</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${appointment.status == 'PENDING'}">
                                <span class="badge badge-warning">Pending</span>
                            </c:when>
                            <c:when test="${appointment.status == 'APPROVED'}">
                                <span class="badge badge-info">Approved</span>
                            </c:when>
                            <c:when test="${appointment.status == 'WAITING PAYMENT'}">
                                <span class="badge badge-primary">Waiting Payment</span>
                            </c:when>
                            <c:when test="${appointment.status == 'PAID'}">
                                <span class="badge badge-success">Paid</span>
                            </c:when>
                            <c:when test="${appointment.status == 'FINISHED'}">
                                <span class="badge badge-success">Finished</span>
                            </c:when>
                            <c:when test="${appointment.status == 'CANCELLED'}">
                                <span class="badge badge-danger">Cancelled</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-secondary">${appointment.status}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td><strong>Charge :</strong></td>
                    <td>
                        <input type="text" value="<c:choose><c:when test='${appointment.charge != null}'>RM <fmt:formatNumber value='${appointment.charge}' pattern='0.00'/></c:when><c:otherwise>-</c:otherwise></c:choose>" readonly />
                    </td>
                </tr>
                <tr>
                    <td><strong>Created :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.creationDatetime}' pattern='dd/MM/yyyy HH:mm'/> by ${appointment.createBy}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Last Updated :</strong></td>
                    <td>
                        <input type="text" value="<c:choose><c:when test='${appointment.lastUpdateDatetime != null}'><fmt:formatDate value='${appointment.lastUpdateDatetime}' pattern='dd/MM/yyyy HH:mm'/> by ${appointment.lastUpdateBy}</c:when><c:otherwise>Not updated yet</c:otherwise></c:choose>" readonly />
                    </td>
                </tr>
            </table>

            <div class="button-footer">
                <c:choose>
                    <c:when test="${appointment.status == 'APPROVED'}">
                        <form action="${pageContext.request.contextPath}/doctor/appointment/detail" method="post" style="display: inline;">
                            <input type="hidden" name="id" value="${appointment.id}">
                            <input type="hidden" name="action" value="addDiagnosis">
                            <button type="submit" class="diagnosis-btn">Add Diagnosis</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/doctor/appointment/list" class="btn back-btn">Back to Calendar</a>
                    </c:when>
                    <c:when test="${appointment.status == 'WAITING PAYMENT' || appointment.status == 'PAID'}">
                        <a href="${pageContext.request.contextPath}/doctor/appointment/view-details?id=${appointment.id}" class="btn diagnosis-btn">View Details</a>
                        <a href="${pageContext.request.contextPath}/doctor/appointment/list" class="btn back-btn">Back to Calendar</a>
                    </c:when>
                    <c:otherwise>
                        <div style="flex: 1;"></div>
                        <a href="${pageContext.request.contextPath}/doctor/appointment/list" class="btn back-btn">Back to Calendar</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
        
        <c:if test="${empty appointment}">
            <div class="error-message">
                <h4>Appointment Not Found</h4>
                <p>The requested appointment could not be found. Please check the appointment ID and try again.</p>
            </div>
            <div class="button-footer">
                <div style="flex: 1;"></div>
                <a href="${pageContext.request.contextPath}/doctor/appointment/list" class="btn back-btn">Back to Calendar</a>
            </div>
        </c:if>
    </div>
</div>

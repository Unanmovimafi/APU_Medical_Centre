<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    body {
        background-color: #E0F7FA;
        font-family: Arial, sans-serif;
    }

    h2, h3 {
        color: #00BFFF;
        padding-bottom: 15px;
        margin-bottom: 20px;
    }

    .mainbody {
        padding: 40px;
    }

    .form-section {
        width: 100%;
        max-width: 900px;
        margin: 0 auto 30px auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .form-table {
        width: 100%;
        margin-bottom: 0;
    }

    .form-table td {
        padding: 12px 8px;
        vertical-align: middle;
        border-bottom: 1px solid #f0f0f0;
    }

    .form-table td:first-child {
        width: 150px;
        white-space: nowrap;
        font-weight: bold;
        color: #495057;
    }

    .form-table td:last-child {
        color: #6c757d;
    }

    .medical-history-section {
        width: 100%;
        max-width: 900px;
        margin: 0 auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
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

    .button-footer {
        display: flex;
        justify-content: flex-start;
        width: 100%;
        max-width: 900px;
        margin: 20px auto 0;
    }

    .btn {
        padding: 12px 24px;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        text-decoration: none;
        text-align: center;
        background-color: #6C757D;
        color: white;
        min-width: 120px;
    }

    .btn:hover {
        background-color: #5A6268;
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
</style>

<div class="mainbody">
    <h2>Patient Details</h2>
    
    <div class="form-section">
        <table class="form-table">
            <tr>
                <td><strong>Name :</strong></td>
                <td>${customer.name}</td>
            </tr>
            <tr>
                <td><strong>Email :</strong></td>
                <td>${customer.email}</td>
            </tr>
            <tr>
                <td><strong>Phone :</strong></td>
                <td>${customer.phoneNumber != null ? customer.phoneNumber : '-'}</td>
            </tr>
            <tr>
                <td><strong>Gender :</strong></td>
                <td>${customer.gender != null ? customer.gender : '-'}</td>
            </tr>
            <tr>
                <td><strong>Date of Birth :</strong></td>
                <td>
                    <c:choose>
                        <c:when test="${customer.dateOfBirth != null}">
                            <fmt:formatDate value="${customer.dateOfBirth}" pattern="dd/MM/yyyy"/>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><strong>Blood Type :</strong></td>
                <td>${customer.bloodType != null ? customer.bloodType : '-'}</td>
            </tr>
            <tr>
                <td><strong>Allergic Info :</strong></td>
                <td>${customer.allergic != null ? customer.allergic : '-'}</td>
            </tr>
            <tr>
                <td><strong>Status :</strong></td>
                <td>
                    <span class="history-status ${customer.status == 'ACTIVE' ? 'status-completed' : 'status-cancelled'}">
                        ${customer.status}
                    </span>
                </td>
            </tr>
        </table>
    </div>

    <div class="medical-history-section">
        <h3>Medical History</h3>
        
        <c:choose>
            <c:when test="${not empty appointmentList}">
                <c:forEach var="appt" items="${appointmentList}">
                    <div class="history-item">
                        <div class="history-datetime">
                            <div class="history-date">
                                <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="dd/MM/yyyy"/>
                            </div>
                            <div class="history-time">
                                <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> - 
                                <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                            </div>
                            <span class="history-status 
                                <c:choose>
                                    <c:when test="${appt.status == 'COMPLETED'}">status-completed</c:when>
                                    <c:when test="${appt.status == 'PAID'}">status-paid</c:when>
                                    <c:when test="${appt.status == 'PENDING'}">status-pending</c:when>
                                    <c:when test="${appt.status == 'CANCELLED'}">status-cancelled</c:when>
                                    <c:otherwise>status-pending</c:otherwise>
                                </c:choose>">
                                ${appt.status}
                            </span>
                        </div>
                        <div class="history-feedback">
                            <c:choose>
                                <c:when test="${not empty feedbackMap[appt.id]}">
                                    <p class="feedback-text">${feedbackMap[appt.id].context}</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="feedback-text no-feedback">-</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-history">
                    <p>No medical history available for this patient.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="button-footer">
        <a href="${pageContext.request.contextPath}/doctor/customer/list" class="btn">Back to Patient List</a>
    </div>
</div>

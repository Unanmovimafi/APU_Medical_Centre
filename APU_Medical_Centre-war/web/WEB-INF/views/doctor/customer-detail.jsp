<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Customer Details</h2>

<table class="form-table">
    <tr><td><strong>Name:</strong></td><td>${customer.name}</td></tr>
    <tr><td><strong>Email:</strong></td><td>${customer.email}</td></tr>
    <tr><td><strong>Phone Number:</strong></td><td>${customer.phoneNumber}</td></tr>
    <tr><td><strong>Username:</strong></td><td>${customer.username}</td></tr>
    <tr><td><strong>Blood Type:</strong></td><td>${customer.bloodType}</td></tr>
    <tr><td><strong>Allergic:</strong></td><td>${customer.allergic}</td></tr>
    <tr><td><strong>Status:</strong></td><td>${customer.status}</td></tr>
</table>

<br/>

<h3>Appointment History</h3>

<c:choose>
    <c:when test="${not empty appointmentList}">
        <table border="1" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Doctor</th>
                    <th>Status</th>
                    <th>Charge</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="appt" items="${appointmentList}">
                    <tr>
                        <td><fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> -
                            <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty appt.doctor}">
                                    ${appt.doctor.name}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${appt.status}</td>
                        <td>RM <fmt:formatNumber value="${appt.charge}" type="number" minFractionDigits="2" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>No appointment history available.</p>
    </c:otherwise>
</c:choose>

<br/>
<a href="${pageContext.request.contextPath}/staff/customer/list" class="btn">Back</a>

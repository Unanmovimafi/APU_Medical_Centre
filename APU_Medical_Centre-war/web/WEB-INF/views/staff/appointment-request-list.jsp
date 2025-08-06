<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Pending Appointment Requests</h2>

<table border="1" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
    <thead>
        <tr>
            <th>ID</th>
            <th>Doctor</th>
            <th>Customer</th>
            <th>Date</th>
            <th>Time Slot</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <form method="get" action="${pageContext.request.contextPath}/staff/appointment/request">
            <label for="column">Search by:</label>
            <select name="column" id="column">
                <option value="doctor" ${param.column == 'doctor' ? 'selected' : ''}>Doctor</option>
                <option value="customer" ${param.column == 'customer' ? 'selected' : ''}>Customer</option>
            </select>

            <input type="text" name="keyword" placeholder="Enter keyword..." value="${param.keyword}" />

            <label for="appointmentDate">Date:</label>
            <input type="date" name="appointmentDate" value="${param.appointmentDate}" />
            <label for="status">Status:</label>
            <select name="status">
                <option value="">All</option>
                <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                <option value="APPROVED" ${param.status == 'APPROVED' ? 'selected' : ''}>APPROVED</option>
                <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>REJECTED</option>
            </select>

            <button type="submit">Search</button>
            <a href="${pageContext.request.contextPath}/staff/appointment/request">Reset</a>
        </form>
        <br />
        <c:forEach var="appt" items="${appointmentList}">
            <tr>
                <td>${appt.id}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty appt.doctor}">
                            ${appt.doctor.name}
                        </c:when>
                        <c:otherwise>—</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty appt.customer}">
                            ${appt.customer.name}
                        </c:when>
                        <c:otherwise>—</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="yyyy-MM-dd" />
                </td>
                <td>
                    <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> -
                    <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                </td>
                <td><c:out value="${appt.status}" /></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/staff/appointment/requests">
                        <input type="hidden" name="appointmentId" value="${appt.id}" />
                        <button type="submit" name="action" value="approve">Approve</button>
                        <button type="submit" name="action" value="reject">Reject</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

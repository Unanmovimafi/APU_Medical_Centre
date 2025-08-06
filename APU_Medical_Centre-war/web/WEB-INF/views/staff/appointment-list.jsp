<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Appointment List</h2>

<!-- Show error message if any -->
<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>

<!-- Show success message if any -->
<c:if test="${not empty successMessage}">
    <p style="color:green;">${successMessage}</p>
</c:if>
<form method="get" action="${pageContext.request.contextPath}/staff/appointment/list">
    <label for="column">Search by:</label>
    <select name="column">
        <option value="doctor" ${param.column == 'doctor' ? 'selected' : ''}>Doctor Name</option>
        <option value="customer" ${param.column == 'customer' ? 'selected' : ''}>Customer Name</option>
    </select>

    <input type="text" name="keyword" placeholder="Enter keyword..." value="${fn:escapeXml(param.keyword)}" />

    <label for="date">Date:</label>
    <input type="date" name="date" value="${param.date}" />

    <label for="status">Status:</label>
    <select name="status">
        <option value="" ${empty param.status ? 'selected' : ''}>-- All --</option>
        <option value="ACTIVE" ${param.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
        <option value="INACTIVE" ${param.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
    </select>

    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/staff/appointment/list">Reset</a>
</form>
<br/>
<c:choose>
    <c:when test="${not empty appointmentList}">
        
        <table border="1" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Doctor</th>
                    <th>Customer</th>
                    <th>Date</th>
                    <th>Time Slot</th>
                    <th>Status</th>
                    <th>Charge</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="appt" items="${appointmentList}">
                    <tr>
                        <td>${appt.id}</td>

                        <!-- Doctor Name -->
                        <td>
                            <c:choose>
                                <c:when test="${not empty appt.doctor}">
                                    ${appt.doctor.name}
                                </c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </td>

                        <!-- Customer Name -->
                        <td>
                            <c:choose>
                                <c:when test="${not empty appt.customer}">
                                    ${appt.customer.name}
                                </c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </td>

                        <!-- Appointment Date -->
                        <td>
                            <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="yyyy-MM-dd" />
                        </td>

                        <!-- Time Slot -->
                        <td>
                            <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> -
                            <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                        </td>

                        <!-- Status -->
                        <td>
                            <c:out value="${appt.status}" />
                        </td>
                        
                        <!-- Charge -->
                        <td>
                            <c:out value="${appt.charge}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>

    <c:otherwise>
        <p>No appointments found.</p>
    </c:otherwise>
</c:choose>
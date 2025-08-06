<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Pending Appointment Requests</h2>

<!-- ✅ Search form with dynamic keyword input -->
<form method="get" action="${pageContext.request.contextPath}/staff/appointment/payment" id="searchForm">
    <label for="column">Search by:</label>
    <select name="column" id="column" onchange="updateKeywordInput()">
        <option value="doctor" ${param.column == 'doctor' ? 'selected' : ''}>Doctor</option>
        <option value="customer" ${param.column == 'customer' ? 'selected' : ''}>Customer</option>
        <option value="date" ${param.column == 'date' ? 'selected' : ''}>Date</option>
        <option value="status" ${param.column == 'status' ? 'selected' : ''}>Status</option>
    </select>

    <span id="keywordInputContainer"></span>

    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/staff/appointment/payment">Reset</a>
</form>

<script>
    // Safely pass JSP values into JS variables
    const currentColumn = "<c:out value='${param.column}' />";
    const currentKeyword = "<c:out value='${param.keyword}' />";

    function updateKeywordInput() {
        const column = document.getElementById("column").value;
        const container = document.getElementById("keywordInputContainer");

        if (column === "date") {
            container.innerHTML = '<input type="date" name="keyword" value="' + currentKeyword + '" />';
        } else if (column === "status") {
            container.innerHTML = 
                '<select name="keyword">' +
                    '<option value="WAITING PAYMENT"' + (currentKeyword === "WAITING PAYMENT" ? ' selected' : '') + '>WAITING PAYMENT</option>' +
                    '<option value="PAID"' + (currentKeyword === "PAID" ? ' selected' : '') + '>PAID</option>' +
                '</select>';
        } else {
            container.innerHTML = '<input type="text" name="keyword" value="' + currentKeyword + '" placeholder="Enter keyword..." />';
        }
    }

    document.addEventListener("DOMContentLoaded", updateKeywordInput);
</script>


<br />

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
                    <form method="post" action="${pageContext.request.contextPath}/staff/appointment/payment">
                        <input type="hidden" name="appointmentId" value="${appt.id}" />
                        <c:choose>
                            <c:when test="${appt.status == 'WAITING PAYMENT'}">
                                <button type="submit" name="action" value="pay">Pay</button>
                            </c:when>
                            <c:when test="${appt.status == 'PAID'}">
                                <button type="button" onclick="window.open('${pageContext.request.contextPath}/staff/receipt/preview?appointmentId=${appt.id}', '_blank')">Print Receipt</button>
                                <button type="submit" name="action" value="finish">Finished</button>
                            </c:when>
                        </c:choose>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

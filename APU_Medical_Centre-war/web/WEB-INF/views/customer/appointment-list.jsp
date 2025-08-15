<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
    .mainbody {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
    }

    h2 {
        color: #00BFFF;
        margin-bottom: 25px;
    }

    form {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 10px;
        flex-wrap: wrap;
        margin-bottom: 20px;
    }
    
    .search-controls {
        display: flex;
        align-items: center;
        gap: 10px;
        flex-wrap: wrap;
    }
    
    input[type="text"], input[type="date"], select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
    }

    button, .btn {
        padding: 10px 16px;
        border: none;
        border-radius: 8px;
        font-size: 14px;
        cursor: pointer;
        text-decoration: none;
        background-color: #00BFFF;
        color: white;
        transition: background-color 0.2s ease;
    }

    .btn:hover, button:hover {
        background-color: #00ACC1;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }

    th, td {
        text-align: left;
        padding: 12px;
        border-bottom: 1px solid #ddd;
        color: #1C1C1C;
    }

    th {
        background-color: #E8FAFD;
    }

    td a {
        color: #00BFFF;
        text-decoration: none;
    }

    td a:hover {
        text-decoration: underline;
    }

    .status-badge {
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
    }
    
    .status-pending {
        background-color: #FFF3CD;
        color: #856404;
    }
    
    .status-approved {
        background-color: #D4EDDA;
        color: #155724;
    }
    
    .status-waiting-payment {
        background-color: #CCE5FF;
        color: #004085;
    }
    
    .status-paid {
        background-color: #D1ECF1;
        color: #0C5460;
    }
    
    .status-completed {
        background-color: #D4EDDA;
        color: #155724;
    }
    
    .status-cancelled {
        background-color: #F8D7DA;
        color: #721C24;
    }
    
    .status-rejected {
        background-color: #F8D7DA;
        color: #721C24;
    }
    
    .view-btn, .cancel-btn {
        background-color: #00BFFF;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 6px;
        text-decoration: none;
        font-size: 12px;
        cursor: pointer;
        display: inline-block;
        min-width: 100px;
        text-align: center;
        white-space: nowrap;
    }
    
    .view-btn:hover {
        background-color: #00ACC1;
        color: white;
    }
    
    .cancel-btn {
        background-color: #DC3545;
    }
    
    .cancel-btn:hover {
        background-color: #C82333;
    }
    
    .message {
        padding: 15px;
        border-radius: 8px;
        margin-bottom: 20px;
    }
    
    .success-message {
        background-color: #D4EDDA;
        border: 1px solid #C3E6CB;
        color: #155724;
    }
    
    .error-message {
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
        color: #721C24;
    }
    
    .no-appointments {
        text-align: center;
        padding: 40px;
        color: #666;
        font-style: italic;
    }

    .icon-action {
        color: #33C9E7;
        font-size: 20px;
        vertical-align: middle;
        cursor: pointer;
        transition: color 0.2s ease;
        margin-right: 8px;
    }

    .icon-action:hover {
        color: #00BFFF;
    }
    
    .action-link {
        text-decoration: none;
    }

    .action-link:hover {
        text-decoration: none;
    }
</style>

<div class="mainbody">
    <h2>My Appointments</h2>

    <!-- Success/Error Messages -->
    <c:if test="${not empty successMessage}">
        <div class="message success-message">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="message error-message">${errorMessage}</div>
    </c:if>

    <!-- Search Form -->
    <form method="get" action="${pageContext.request.contextPath}/customer/appointment/list">
        <div class="search-controls">
            <label for="keyword">Doctor:</label>
            <input type="text" name="keyword" id="keyword" placeholder="Enter doctor name..." value="${fn:escapeXml(param.keyword)}" />
            
            <label for="date">Date:</label>
            <input type="date" name="date" id="date" value="${param.date}" />
            
            <label for="status">Status:</label>
            <select name="status" id="status">
                <option value="" ${empty param.status ? 'selected' : ''}>All Statuses</option>
                <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                <option value="APPROVED" ${param.status == 'APPROVED' ? 'selected' : ''}>Approved</option>
                <option value="WAITING PAYMENT" ${param.status == 'WAITING PAYMENT' ? 'selected' : ''}>Waiting Payment</option>
                <option value="PAID" ${param.status == 'PAID' ? 'selected' : ''}>Paid</option>
                <option value="COMPLETED" ${param.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                <option value="CANCELLED" ${param.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
            </select>

            <button type="submit">Search</button>
            <a href="${pageContext.request.contextPath}/customer/appointment/list" class="btn">Reset</a>
        </div>
    </form>

    <!-- Appointments Table -->
    <c:choose>
        <c:when test="${not empty appointmentList}">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Doctor</th>
                        <th>Time Slot</th>
                        <th>Status</th>
                        <th>Charge</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="appt" items="${appointmentList}">
                        <tr>
                            
                            <td>
                                <c:choose>
                                    <c:when test="${not empty appt.appointmentStartDatetime}">
                                        <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="dd/MM/yyyy" />
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty appt.doctor && not empty appt.doctor.name}">
                                        ${appt.doctor.name}
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>


                            <td>
                                <c:choose>
                                    <c:when test="${not empty appt.appointmentStartDatetime && not empty appt.appointmentEndDatetime}">
                                        <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> - 
                                        <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${appt.status == 'PENDING'}">
                                        <span class="status-badge status-pending">Pending</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'APPROVED'}">
                                        <span class="status-badge status-approved">Approved</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'WAITING PAYMENT'}">
                                        <span class="status-badge status-waiting-payment">Waiting Payment</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'PAID'}">
                                        <span class="status-badge status-paid">Paid</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'COMPLETED'}">
                                        <span class="status-badge status-completed">Completed</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'CANCELLED'}">
                                        <span class="status-badge status-cancelled">Cancelled</span>
                                    </c:when>
                                    <c:when test="${appt.status == 'REJECTED'}">
                                        <span class="status-badge status-rejected">Rejected</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge">${appt.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            
                            <td>
                                <c:choose>
                                    <c:when test="${appt.charge != null && appt.charge > 0}">
                                        RM <fmt:formatNumber value="${appt.charge}" pattern="#0.00" minFractionDigits="2"/>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            
                            <td>
                                <c:choose>
                                    <c:when test="${appt.status == 'PENDING' || appt.status == 'APPROVED'}">
                                        <form method="post" action="${pageContext.request.contextPath}/customer/appointment/list" style="display:inline;">
                                            <input type="hidden" name="action" value="cancel">
                                            <input type="hidden" name="id" value="${appt.id}">
                                            <button type="submit" class="cancel-btn" onclick="return confirm('Are you sure you want to cancel this appointment?')">
                                                Cancel
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:when test="${appt.status == 'WAITING PAYMENT' || appt.status == 'PAID' || appt.status == 'COMPLETED'}">
                                        <a href="${pageContext.request.contextPath}/customer/appointment/detail?id=${appt.id}&from=list" class="view-btn">
                                            View Details
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/customer/appointment/detail?id=${appt.id}&from=list" class="view-btn">
                                            View Details
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>

        <c:otherwise>
            <div class="no-appointments">
                <p>No appointments found matching your criteria.</p>
                <c:if test="${not empty param.keyword || not empty param.date || not empty param.status}">
                    <p><a href="${pageContext.request.contextPath}/customer/appointment/list" class="btn">View All Appointments</a></p>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</div>
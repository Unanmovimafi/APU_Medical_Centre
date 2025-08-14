<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    .create-btn {
        margin-left: auto;
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
        background-color: #F2FCFD; /* Soft clean background */
        color: #1C1C1C;            /* Readable dark text */
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
        justify-content: space-between;
        margin-top: 25px;
        width: 100%;
    }

    .modal-footer button {
        flex: 1;
        padding: 12px 0;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        max-width: 48%;
    }
    
    .modal-footer:has(button:only-child) {
        justify-content: flex-end;
    }

    .btn-confirm {
        background-color: #28A745;
        color: white;
    }

    .btn-confirm:hover {
        background-color: #218838;
    }

    .btn-cancel {
        background-color: #DC3545;
        color: white;
    }
    
    .btn-cancel:hover {
        background-color: #C82333;
    }
    
    .full-btn {
        flex: 1;
        text-align: center;
        padding: 12px 0;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        max-width: 48%;
    }

    .icon-action {
        color: #33C9E7;
        font-size: 25px;
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

    .status-badge {
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
    }

    .status-waiting {
        background-color: #FFF3CD;
        color: #856404;
    }

    .status-paid {
        background-color: #D4EDDA;
        color: #155724;
    }
</style>

<div class="mainbody">
    <h2>Appointment Payment Management</h2>

    <form method="get" action="${pageContext.request.contextPath}/staff/appointment/payment" id="searchForm">
        <div class="search-controls">
            <label for="column">Search by:</label>
            <select name="column" id="column" onchange="updateKeywordInput()">
                <option value="doctor" ${param.column == 'doctor' ? 'selected' : ''}>Doctor</option>
                <option value="customer" ${param.column == 'customer' ? 'selected' : ''}>Customer</option>
                <option value="date" ${param.column == 'date' ? 'selected' : ''}>Date</option>
                <option value="status" ${param.column == 'status' ? 'selected' : ''}>Status</option>
            </select>

            <span id="keywordInputContainer"></span>

            <button type="submit">Search</button>
            <a href="${pageContext.request.contextPath}/staff/appointment/payment" class="btn">Reset</a>
        </div>
    </form>

    <c:choose>
        <c:when test="${not empty appointmentList}">
            <table>
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Doctor</th>
                        <th>Date</th>
                        <th>Time Slot</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="appt" items="${appointmentList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty appt.customer}">
                                        ${appt.customer.name}
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty appt.doctor}">
                                        ${appt.doctor.name}
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="dd/MM/yyyy" />
                            </td>
                            <td>
                                <fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" /> -
                                <fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${appt.status == 'WAITING PAYMENT'}">
                                        <span class="status-badge status-waiting">
                                            <c:out value="${appt.status}" />
                                        </span>
                                    </c:when>
                                    <c:when test="${appt.status == 'PAID'}">
                                        <span class="status-badge status-paid">
                                            <c:out value="${appt.status}" />
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge">
                                            <c:out value="${appt.status}" />
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${appt.status == 'WAITING PAYMENT'}">
                                        <a href="javascript:void(0);" 
                                            class="action-link pay-btn" 
                                            data-id="${appt.id}" 
                                            data-action="pay"
                                            data-customer="${not empty appt.customer ? appt.customer.name : 'Unknown Customer'}"
                                            title="Mark as Paid">
                                            <span class="material-icons icon-action">payment</span>
                                        </a>
                                    </c:when>
                                    <c:when test="${appt.status == 'PAID'}">
                                        <a href="javascript:void(0);" 
                                            onclick="window.open('${pageContext.request.contextPath}/staff/receipt/preview?appointmentId=${appt.id}', '_blank')" 
                                            class="action-link" 
                                            title="Print Receipt">
                                            <span class="material-icons icon-action">print</span>
                                        </a>
                                        <a href="javascript:void(0);" 
                                            class="action-link finish-btn" 
                                            data-id="${appt.id}" 
                                            data-action="finish"
                                            data-customer="${not empty appt.customer ? appt.customer.name : 'Unknown Customer'}"
                                            title="Mark as Finished">
                                            <span class="material-icons icon-action">check_circle</span>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No appointments found matching your criteria.</p>
        </c:otherwise>
    </c:choose>
</div>

<div id="actionModal" class="modal">
    <div class="modal-content">
        <h3 id="actionModalTitle">Confirm Action</h3>
        <p id="actionModalMsg">Are you sure you want to perform this action?</p>
        <form id="actionForm" method="post" action="${pageContext.request.contextPath}/staff/appointment/payment">
            <input type="hidden" name="appointmentId" id="actionAppointmentId" value="" />
            <input type="hidden" name="action" id="actionType" value="" />
            <div class="modal-footer">
                <button type="submit" class="btn-confirm full-btn">Confirm</button>
                <button type="button" onclick="closeActionModal()" class="btn-cancel full-btn">Cancel</button>
            </div>
        </form>
    </div>
</div>

<div id="successModal" class="modal">
    <div class="modal-content">
        <h3 id="successModalTitle">Success</h3>
        <p id="successModalMsg">Action completed successfully.</p>
        <div class="modal-footer">
            <button onclick="closeSuccessModal()" style="background-color:#00BFFF; color:white;">OK</button>
        </div>
    </div>
</div>

<c:if test="${not empty modalMessage}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            var msg = '<c:out value="${modalMessage}" escapeXml="false"/>';
            document.getElementById('successModalMsg').innerHTML = msg;
            document.getElementById('successModal').style.display = 'block';
        });
    </script>
</c:if>

<script>
    // Safely pass JSP values into JS variables
    const currentColumn = "<c:out value='${param.column}' />";
    const currentKeyword = "<c:out value='${param.keyword}' />";

    function updateKeywordInput() {
        const column = document.getElementById("column").value;
        const container = document.getElementById("keywordInputContainer");

        if (column === "date") {
            container.innerHTML = '<input type="date" name="keyword" value="' + currentKeyword + '" style="padding: 10px; border: 1px solid #ccc; border-radius: 8px;" />';
        } else if (column === "status") {
            container.innerHTML = 
                '<select name="keyword" style="padding: 10px; border: 1px solid #ccc; border-radius: 8px;">' +
                    '<option value="">All Statuses</option>' +
                    '<option value="WAITING PAYMENT"' + (currentKeyword === "WAITING PAYMENT" ? ' selected' : '') + '>WAITING PAYMENT</option>' +
                    '<option value="PAID"' + (currentKeyword === "PAID" ? ' selected' : '') + '>PAID</option>' +
                '</select>';
        } else {
            container.innerHTML = '<input type="text" name="keyword" value="' + currentKeyword + '" placeholder="Enter keyword..." style="padding: 10px; border: 1px solid #ccc; border-radius: 8px;" />';
        }
    }

    document.addEventListener("DOMContentLoaded", updateKeywordInput);

    document.querySelectorAll('.pay-btn, .finish-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            const action = this.getAttribute('data-action');
            const customer = this.getAttribute('data-customer') || 'Unknown Customer';

            const modal = document.getElementById('actionModal');
            const title = document.getElementById('actionModalTitle');
            const msg = document.getElementById('actionModalMsg');
            const appointmentIdInput = document.getElementById('actionAppointmentId');
            const actionTypeInput = document.getElementById('actionType');
            
            title.innerHTML = action === 'pay' ? 'Confirm Payment' : 'Confirm Completion';
            msg.innerHTML = 'Are you sure you want to mark appointment for <strong>' + customer + '</strong> as ' + (action === 'pay' ? 'paid' : 'finished') + '?';
            appointmentIdInput.value = id;
            actionTypeInput.value = action;
            modal.style.display = 'block';
        });
    });

    function closeActionModal() {
        document.getElementById('actionModal').style.display = 'none';
    }

    function closeSuccessModal() {
        document.getElementById('successModal').style.display = 'none';
    }
</script>

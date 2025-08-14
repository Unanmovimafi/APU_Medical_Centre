<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    
    input[type="text"], select {
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
    
    #deleteForm {
        width: 100%;
        margin-bottom: 0;
    }

    .btn-confirm {
        background-color: #DC3545;
        color: white;
    }

    .btn-confirm:hover {
        background-color: #C82333;
    }

    .btn-cancel {
        background-color: #5EC7E7;
        color: white;
    }
    .btn-cancel:hover {
        background-color: #42B6DC;
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
<h2>Customer List</h2>

<form method="get" action="${pageContext.request.contextPath}/staff/customer/list">
    <div class="search-controls">
        <label for="column">Search by:</label>
        <select name="column" id="column">
            <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
            <option value="username" ${param.column == 'username' ? 'selected' : ''}>Username</option>
            <option value="email" ${param.column == 'email' ? 'selected' : ''}>Email</option>
            <option value="gender" ${param.column == 'gender' ? 'selected' : ''}>Gender</option>
        </select>
        <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
        <button type="submit">Search</button>
        <a href="${pageContext.request.contextPath}/staff/customer/list" class="btn">Reset</a>
    </div>
    <a href="${pageContext.request.contextPath}/staff/customer/new" class="btn create-btn">Create New Customer</a>
</form>

<table>
    <thead>
        <tr>
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
            <th>Gender</th>
            <th>Status</th>
            <th>Phone</th>
            <th>Updated At</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="cust" items="${customerList}">
            <tr>
                <td>${cust.username}</td>
                <td>${cust.name}</td>
                <td>${cust.email}</td>
                <td>${cust.gender != null ? cust.gender : 'N/A'}</td>
                <td>${cust.status}</td>
                <td>${cust.phoneNumber}</td>
                <td><fmt:formatDate value="${cust.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/staff/customer/detail?id=${cust.id}" class="action-link" title="View Details">
                        <span class="material-icons icon-action">visibility</span>
                    </a>
                    <a href="javascript:void(0);" 
                        class="action-link delete-btn" 
                        data-id="${cust.id}" 
                        data-name="${cust.name}" 
                        title="Delete Customer">
                         <span class="material-icons icon-action">delete</span>
                     </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h3 id="deleteModalTitle">Confirm Deletion</h3>
        <p id="deleteModalMsg">Are you sure you want to delete this customer?</p>
        <form id="deleteForm" method="post" action="">
            <div class="modal-footer">
                <button type="submit" class="btn-confirm full-btn">Delete</button>
                <button type="button" onclick="closeDeleteModal()" class="btn-cancel full-btn">Cancel</button>
            </div>
        </form>
    </div>
</div>

<div id="successModal" class="modal">
    <div class="modal-content">
        <h3 id="successModalTitle">Success</h3>
        <p id="successModalMsg">Customer deleted successfully.</p>
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
</div>

<script>
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name') || 'this customer';

            const modal = document.getElementById('deleteModal');
            const msg = document.getElementById('deleteModalMsg');
            const form = document.getElementById('deleteForm');
            
            msg.innerHTML = `Are you sure you want to delete <strong>`+name+`</strong>?`;
            form.action = `${pageContext.request.contextPath}/staff/customer/delete?id=`+ id;
            modal.style.display = 'block';
        });
    });

    function closeDeleteModal() {
        document.getElementById('deleteModal').style.display = 'none';
    }

    function showSuccessModal(message = "Customer deleted successfully.") {
        document.getElementById('successModalMsg').textContent = message;
        document.getElementById('successModal').style.display = 'block';
    }

    function closeSuccessModal() {
        document.getElementById('successModal').style.display = 'none';
        location.reload(); // optional: reload to update table
    }
</script>


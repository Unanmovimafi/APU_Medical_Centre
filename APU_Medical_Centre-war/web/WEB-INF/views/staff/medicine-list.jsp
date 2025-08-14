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

    .create-buttons {
        display: flex;
        gap: 10px;
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

    button:hover, .btn:hover {
        background-color: #00ACC1;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    th, td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #e1e5e9;
        vertical-align: middle;
    }

    th {
        background-color: #f8f9fa;
        font-weight: bold;
        color: #495057;
    }

    tbody tr:hover {
        background-color: #f8f9fa;
    }

    .action-link {
        color: #00BFFF;
        text-decoration: none;
        margin-right: 10px;
        font-size: 18px;
        transition: color 0.2s ease;
    }

    .action-link:hover {
        color: #00ACC1;
    }

    .icon-action {
        vertical-align: middle;
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

    .delete-btn {
        color: #DC3545;
        cursor: pointer;
    }

    .delete-btn:hover {
        color: #C82333;
    }
</style>

<div class="mainbody">
<h2>Medicine List</h2>

<form method="get" action="${pageContext.request.contextPath}/staff/medicine/list">
    <div class="search-controls">
        <label for="column">Search by:</label>
        <select name="column" id="column">
            <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
            <option value="createBy" ${param.column == 'createBy' ? 'selected' : ''}>Created By</option>
            <option value="lastUpdateBy" ${param.column == 'lastUpdateBy' ? 'selected' : ''}>Updated By</option>
        </select>
        <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
        <button type="submit">Search</button>
        <a href="${pageContext.request.contextPath}/staff/medicine/list" class="btn">Reset</a>
    </div>
    <div class="create-buttons">
        <a href="${pageContext.request.contextPath}/staff/medicine/new" class="btn create-btn">Create Medicine</a>
    </div>
</form>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price (RM)</th>
            <th>Created By</th>
            <th>Updated By</th>
            <th>Last Updated</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="med" items="${medicineList}">
            <tr>
                <td>${med.id}</td>
                <td>${med.name}</td>
                <td>${med.description}</td>
                <td>${med.price}</td>
                <td>${med.createBy}</td>
                <td>${med.lastUpdateBy}</td>
                <td><fmt:formatDate value="${med.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/staff/medicine/detail?id=${med.id}" class="action-link" title="View Details">
                        <span class="material-icons icon-action">visibility</span>
                    </a>
                    <a href="javascript:void(0);" 
                        class="action-link delete-btn" 
                        data-id="${med.id}" 
                        data-name="${med.name}" 
                        title="Delete Medicine">
                         <span class="material-icons icon-action">delete</span>
                     </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h3 id="deleteModalTitle">Confirm Deletion</h3>
        <p id="deleteModalMsg">Are you sure you want to delete this medicine?</p>
        <form id="deleteForm" method="post" action="">
            <div class="modal-footer">
                <button type="submit" class="btn-confirm full-btn">Delete</button>
                <button type="button" onclick="closeDeleteModal()" class="btn-cancel full-btn">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Success Modal -->
<div id="successModal" class="modal">
    <div class="modal-content">
        <h3 id="successModalTitle">Success</h3>
        <p id="successModalMsg">Medicine created successfully.</p>
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
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name') || 'this medicine';

            const modal = document.getElementById('deleteModal');
            const msg = document.getElementById('deleteModalMsg');
            const form = document.getElementById('deleteForm');

            msg.innerHTML = `Are you sure you want to delete the medicine "<strong>${name}</strong>"?`;
            form.action = '${pageContext.request.contextPath}/staff/medicine/delete';
            
            // Add hidden input for ID
            let existingInput = form.querySelector('input[name="id"]');
            if (existingInput) {
                existingInput.remove();
            }
            
            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'id';
            hiddenInput.value = id;
            form.appendChild(hiddenInput);
            
            modal.style.display = 'block';
        });
    });

    function closeDeleteModal() {
        document.getElementById('deleteModal').style.display = 'none';
    }

    function closeSuccessModal() {
        document.getElementById('successModal').style.display = 'none';
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        const deleteModal = document.getElementById('deleteModal');
        const successModal = document.getElementById('successModal');
        
        if (event.target == deleteModal) {
            deleteModal.style.display = "none";
        }
        if (event.target == successModal) {
            successModal.style.display = "none";
        }
    }
</script>

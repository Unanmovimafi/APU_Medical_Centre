<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Counter Staff Details</h2>
<form method="post" action="${pageContext.request.contextPath}/staff/staff/detail" id="employeeForm">
    <input type="hidden" name="id" value="${staff.id}" />

    <table class="form-table">
        <tr><td><strong>Name:</strong></td><td><input type="text" name="name" value="${staff.name}" readonly /></td></tr>
        <tr><td><strong>Email:</strong></td><td><input type="email" name="email" value="${staff.email}" readonly /></td></tr>
        <tr><td><strong>Phone Number:</strong></td><td><input type="text" name="phoneNumber" value="${staff.phoneNumber}" readonly /></td></tr>
        <tr><td><strong>Username:</strong></td><td><input type="text" name="username" value="${staff.username}" readonly /></td></tr>
        <tr>
            <td><strong>Status:</strong></td>
            <td>
                <select name="status" disabled>
                    <option value="ACTIVE" ${staff.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                    <option value="INACTIVE" ${staff.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                </select>
            </td>
        </tr>
    </table>

    <br/>
    <button type="button" id="modifyBtn" onclick="enableEdit()">Modify</button>
    <button type="submit" id="saveBtn" style="display: none;">Save</button>
    <a href="${pageContext.request.contextPath}/staff/employee/list" class="btn" id="backBtn">Back</a>
    <button type="button" id="cancelBtn" style="display: none;" onclick="disableEdit()">Cancel</button>
</form>
<c:if test="${not empty usernameError}">
    <div style="color: red;">${usernameError}</div>
</c:if>

<script>
    function enableEdit() {
        document.querySelectorAll('#employeeForm input, #employeeForm select').forEach(el => {
            if (el.name !== "id") el.removeAttribute('readonly');
            if (el.tagName === 'SELECT') el.removeAttribute('disabled');
        });
        document.getElementById('modifyBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline';
        document.getElementById('backBtn').style.display = 'none';
        document.getElementById('cancelBtn').style.display = 'inline';
    }

    function disableEdit() {
        document.querySelectorAll('#employeeForm input, #employeeForm select').forEach(el => {
            el.setAttribute('readonly', true);
            if (el.tagName === 'SELECT') el.setAttribute('disabled', true);
        });
        document.getElementById('modifyBtn').style.display = 'inline';
        document.getElementById('saveBtn').style.display = 'none';
        document.getElementById('backBtn').style.display = 'inline';
        document.getElementById('cancelBtn').style.display = 'none';
    }
</script>

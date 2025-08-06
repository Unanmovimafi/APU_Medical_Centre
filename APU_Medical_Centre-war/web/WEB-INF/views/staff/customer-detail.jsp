<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Customer Details</h2>
<form method="post" action="${pageContext.request.contextPath}/staff/customer/detail" id="customerForm">
    <input type="hidden" name="id" value="${customer.id}" />

    <table class="form-table">
        <tr>
            <td><strong>Name:</strong></td>
            <td><input type="text" name="name" value="${customer.name}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Email:</strong></td>
            <td><input type="email" name="email" value="${customer.email}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Phone Number:</strong></td>
            <td><input type="text" name="phoneNumber" value="${customer.phoneNumber}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Date of Birth:</strong></td>
            <td><input type="date" name="dateOfBirth" value="${customer.dateOfBirth}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Username:</strong></td>
            <td><input type="text" name="username" value="${customer.username}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Blood Type:</strong></td>
            <td><input type="text" name="bloodType" value="${customer.bloodType}" readonly /></td>
        </tr>
        <tr>
            <td><strong>Allergic Info:</strong></td>
            <td><textarea name="allergic" rows="3" readonly>${customer.allergic}</textarea></td>
        </tr>
        <tr>
            <td><strong>Status:</strong></td>
            <td>
                <select name="status" disabled>
                    <option value="ACTIVE" ${customer.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                    <option value="INACTIVE" ${customer.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                </select>
            </td>
        </tr>
    </table>

    <br/>

    <button type="button" id="modifyBtn" onclick="enableEdit()">Modify</button>
    <button type="submit" id="saveBtn" style="display: none;">Save</button>
    <a href="${pageContext.request.contextPath}/staff/customer/list" class="btn" id="backBtn">Back</a>
    <button type="button" id="cancelBtn" style="display: none;" onclick="disableEdit()">Cancel</button>
</form>

<script>
    function enableEdit() {
        document.querySelectorAll('#customerForm input, #customerForm textarea, #customerForm select').forEach(el => {
            if (el.name !== "id") el.removeAttribute('readonly');
            if (el.tagName === 'SELECT') el.removeAttribute('disabled');
        });

        document.getElementById('modifyBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline';
        document.getElementById('backBtn').style.display = 'none';
        document.getElementById('cancelBtn').style.display = 'inline';
    }

    function disableEdit() {
        document.querySelectorAll('#customerForm input, #customerForm textarea, #customerForm select').forEach(el => {
            el.setAttribute('readonly', true);
            if (el.tagName === 'SELECT') el.setAttribute('disabled', true);
        });

        document.getElementById('modifyBtn').style.display = 'inline';
        document.getElementById('saveBtn').style.display = 'none';
        document.getElementById('backBtn').style.display = 'inline';
        document.getElementById('cancelBtn').style.display = 'none';
    }
</script>

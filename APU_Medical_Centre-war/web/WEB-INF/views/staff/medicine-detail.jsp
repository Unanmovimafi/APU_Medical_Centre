<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Medicine Detail</h2>

<form method="post" action="${pageContext.request.contextPath}/staff/medicine/detail" id="medicineForm">
    <input type="hidden" name="id" value="${medicine.id}" />

    <label>Name:</label>
    <input type="text" name="name" value="${medicine.name}" readonly /><br/>

    <label>Description:</label>
    <textarea name="description" readonly>${medicine.description}</textarea><br/>

    <label>Price (RM):</label>
    <input type="number" name="price" value="${medicine.price}" readonly /><br/><br/>

    <button type="button" id="modifyBtn" onclick="enableEdit()">Modify</button>
    <button type="submit" id="saveBtn" style="display:none;">Save</button>
    <a href="${pageContext.request.contextPath}/staff/medicine" id="backBtn">Back</a>
    <button type="button" id="cancelBtn" onclick="disableEdit()" style="display:none;">Cancel</button>
</form>

<script>
    function enableEdit() {
        const form = document.getElementById('medicineForm');
        form.querySelectorAll('input, textarea').forEach(el => {
            if (el.name !== 'id') el.removeAttribute('readonly');
        });

        document.getElementById('modifyBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline';
        document.getElementById('backBtn').style.display = 'none';
        document.getElementById('cancelBtn').style.display = 'inline';
    }

    function disableEdit() {
        const form = document.getElementById('medicineForm');
        form.querySelectorAll('input, textarea').forEach(el => {
            el.setAttribute('readonly', true);
        });

        document.getElementById('modifyBtn').style.display = 'inline';
        document.getElementById('saveBtn').style.display = 'none';
        document.getElementById('backBtn').style.display = 'inline';
        document.getElementById('cancelBtn').style.display = 'none';
    }
</script>

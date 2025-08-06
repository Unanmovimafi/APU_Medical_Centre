<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Add New Medicine</h2>

<form method="post" action="${pageContext.request.contextPath}/staff/medicine/new">
    <table>
        <tr>
            <td><label for="name">Name:</label></td>
            <td><input type="text" name="name" id="name" required></td>
        </tr>
        <tr>
            <td><label for="description">Description:</label></td>
            <td><textarea name="description" id="description" rows="4" cols="40"></textarea></td>
        </tr>
        <tr>
            <td><label for="price">Price (RM):</label></td>
            <td><input type="number" name="price" id="price" step="0.01" min="0" required></td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Add Medicine</button>
                <a href="${pageContext.request.contextPath}/staff/medicine">Cancel</a>
            </td>
        </tr>
    </table>
</form>

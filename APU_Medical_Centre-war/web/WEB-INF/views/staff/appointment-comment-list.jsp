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

    .rating-stars {
        color: #ffc107;
        font-size: 16px;
    }

    .rating-stars .empty-star {
        color: #ddd;
    }

    .comment-content {
        max-width: 300px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .comment-full {
        white-space: normal;
        word-wrap: break-word;
    }

    .status-active {
        color: #28a745;
        font-weight: bold;
    }

    .status-inactive {
        color: #dc3545;
        font-weight: bold;
    }

    .no-comments {
        text-align: center;
        padding: 40px;
        color: #6c757d;
        font-style: italic;
    }

    .expand-btn {
        background: none;
        border: none;
        color: #00BFFF;
        cursor: pointer;
        text-decoration: underline;
        padding: 0;
        font-size: 12px;
    }

    .expand-btn:hover {
        color: #00ACC1;
    }
</style>

<div class="mainbody">
    <h2>My Customer Comments</h2>

    <form method="get" action="${pageContext.request.contextPath}/staff/appointment/comment">
        <div class="search-controls">
            <label for="column">Search by:</label>
            <select name="column" id="column">
                <option value="customer_name" ${param.column == 'customer_name' ? 'selected' : ''}>Customer Name</option>
                <option value="doctor_name" ${param.column == 'doctor_name' ? 'selected' : ''}>Doctor Name</option>
                <option value="staff_name" ${param.column == 'staff_name' ? 'selected' : ''}>Staff Name</option>
                <option value="content" ${param.column == 'content' ? 'selected' : ''}>Comment</option>
                <option value="rating" ${param.column == 'rating' ? 'selected' : ''}>Rating</option>
            </select>
            <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
            <button type="submit">Search</button>
            <a href="${pageContext.request.contextPath}/staff/appointment/comment" class="btn">Reset</a>
        </div>
    </form>

    <c:choose>
        <c:when test="${not empty commentList}">
            <table>
                <thead>
                    <tr>
                        <th>Customer Name</th>
                        <th>Rating</th>
                        <th>Comment</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${commentList}" varStatus="status">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/manager/customer/detail?id=${comment.customer.id}" 
                                   title="View Patient Details">
                                    ${comment.customer.name}
                                </a>
                            </td>
                            
                            <td>
                                <div class="rating-stars">
                                    <c:forEach var="i" begin="1" end="${comment.rating}">
                                        <span>&#9733;</span>
                                    </c:forEach>
                                    <c:forEach var="i" begin="${comment.rating + 1}" end="10">
                                        <span class="empty-star">&#9733;</span>
                                    </c:forEach>
                                    <span style="color: #666; margin-left: 5px;">(${comment.rating}/10)</span>
                                </div>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty comment.content}">
                                        <div class="comment-content" id="comment-${status.index}">
                                            <c:choose>
                                                <c:when test="${comment.content.length() > 50}">
                                                    <span class="short-text">${comment.content.substring(0, 50)}...</span>
                                                    <span class="full-text comment-full" style="display: none;">${comment.content}</span>
                                                    <br>
                                                    <button class="expand-btn" onclick="toggleComment('${status.index}')">Show More</button>
                                                </c:when>
                                                <c:otherwise>
                                                    ${comment.content}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #adb5bd; font-style: italic;">No comment provided</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatDate value="${comment.creationDatetime}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/staff/customer/detail?id=${comment.customer.id}" 
                                   class="action-link" title="View Patient Details">
                                    <span class="material-icons icon-action">person</span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-comments">
                <p>No comments or reviews found in the system.</p>
                <p>Patient feedback will appear here when available.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script>
    function toggleComment(index) {
        const container = document.getElementById('comment-' + index);
        const shortText = container.querySelector('.short-text');
        const fullText = container.querySelector('.full-text');
        const button = container.querySelector('.expand-btn');
        
        if (fullText.style.display === 'none') {
            shortText.style.display = 'none';
            fullText.style.display = 'block';
            button.textContent = 'Show Less';
        } else {
            shortText.style.display = 'inline';
            fullText.style.display = 'none';
            button.textContent = 'Show More';
        }
    }
</script>

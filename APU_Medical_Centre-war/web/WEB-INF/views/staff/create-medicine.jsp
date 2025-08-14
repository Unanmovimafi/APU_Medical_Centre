<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  body {
    background-color: #e0f7fa;
    font-family: Arial, sans-serif;
  }

  h2 {
    color: #00bfff;
    padding-bottom: 25px;
    justify-self: center;
  }

  .mainbody {
    padding: 40px;
  }

  .form-section {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    background: #fff;
    border-radius: 12px;
    padding: 30px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .form-table {
    width: 100%;
  }

  .form-table td {
    padding: 10px 8px;
    vertical-align: middle;
  }

  .form-table td:first-child {
    width: 150px;
    white-space: nowrap;
    font-weight: bold;
  }

  input[type="text"],
  input[type="number"],
  select,
  textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
    font-size: 15px;
  }

  input:focus,
  select:focus,
  textarea:focus {
    border-color: #00bfff;
    background-color: #f0ffff;
    outline: none;
  }

  textarea {
    min-height: 80px;
    resize: vertical;
  }

  .button-footer {
    display: flex;
    justify-content: space-between;
    gap: 15px;
    width: 100%;
    margin-top: 30px;
  }

  .button-footer button,
  .button-footer .btn {
    padding: 12px 24px;
    font-size: 14px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s ease;
    text-decoration: none;
    text-align: center;
    min-width: 120px;
  }

  .create-btn {
    background-color: #28a745;
    color: white;
  }

  .create-btn:hover {
    background-color: #218838;
  }

  .cancel-btn {
    background-color: #6c757d;
    color: white;
  }

  .cancel-btn:hover {
    background-color: #5a6268;
  }

  .error-message {
    color: #dc3545;
    background-color: #f8d7da;
    border: 1px solid #f5c6cb;
    border-radius: 8px;
    padding: 10px;
    margin-bottom: 20px;
  }

  input:required:invalid {
    border-color: #dc3545;
    background-color: #fff5f5;
  }
</style>

<div class="mainbody">
  <h2>Add New Medicine</h2>

  <div class="form-section">
    <c:if test="${not empty error}">
      <div class="error-message">${error}</div>
    </c:if>

    <form
      method="post"
      action="${pageContext.request.contextPath}/staff/medicine/new"
    >
      <table class="form-table">
        <tr>
          <td><strong>Name :</strong></td>
          <td>
            <input type="text" name="name" value="${param.name}" required />
          </td>
        </tr>
        <tr>
          <td><strong>Description :</strong></td>
          <td>
            <textarea
              name="description"
              placeholder="Enter medicine description..."
            >
${param.description}</textarea
            >
          </td>
        </tr>
        <tr>
          <td><strong>Price (RM) :</strong></td>
          <td>
            <input
              type="number"
              name="price"
              value="${param.price}"
              step="0.01"
              min="0"
              required
            />
          </td>
        </tr>
      </table>

      <div class="button-footer">
        <button type="submit" class="create-btn">Add Medicine</button>
        <a
          href="${pageContext.request.contextPath}/staff/medicine/list"
          class="btn cancel-btn"
          >Cancel</a
        >
      </div>
    </form>
  </div>
</div>

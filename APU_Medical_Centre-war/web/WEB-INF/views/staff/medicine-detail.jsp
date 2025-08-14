<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

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

  input[readonly],
  textarea[readonly],
  select[disabled] {
    background-color: #f8f9fa;
    border-color: #e9ecef;
    color: #6c757d;
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
    width: 120px;
    display: inline-block;
  }
  .modify-btn,
  .save-btn {
    background-color: #007bff;
    color: white;
  }

  .modify-btn:hover,
  .save-btn:hover {
    background-color: #0056b3;
  }

  .back-btn,
  .cancel-btn {
    background-color: #6c757d;
    color: white;
  }

  .back-btn:hover,
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

  .modal {
    display: none;
    position: fixed;
    z-index: 10000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
  }

  .modal-content {
    background-color: #f2fcfd;
    color: #1c1c1c;
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
    color: #1c1c1c;
    font-size: 20px;
  }

  .modal-content p {
    margin-top: 5px;
    padding-bottom: 25px;
    font-size: 15px;
  }

  .modal-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 25px;
    width: 100%;
  }

  .modal-footer button {
    padding: 12px 24px;
    font-size: 14px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s ease;
    background-color: #00bfff;
    color: white;
  }

  .modal-footer button:hover {
    background-color: #00acc1;
  }
</style>

<div class="mainbody">
  <h2>Medicine Details</h2>

  <!-- Success Message Modal -->
  <div id="successModal" class="modal">
    <div class="modal-content">
      <h3>Success</h3>
      <p>Medicine details have been updated successfully!</p>
      <div class="modal-footer">
        <button onclick="closeSuccessModal()">OK</button>
      </div>
    </div>
  </div>

  <!-- Error Message -->
  <c:if test="${not empty error}">
    <div class="error-message">${error}</div>
  </c:if>

  <div class="form-section">
    <form
      method="post"
      action="${pageContext.request.contextPath}/staff/medicine/detail"
      id="medicineForm"
    >
      <input type="hidden" name="id" value="${medicine.id}" />

      <table class="form-table">
        <tr>
          <td>Name:</td>
          <td>
            <input type="text" name="name" value="${medicine.name}" readonly />
          </td>
        </tr>
        <tr>
          <td>Description:</td>
          <td>
            <textarea name="description" readonly>
${medicine.description}</textarea
            >
          </td>
        </tr>
        <tr>
          <td>Price (RM):</td>
          <td>
            <input
              type="number"
              name="price"
              value="${medicine.price}"
              step="0.01"
              min="0"
              readonly
            />
          </td>
        </tr>
      </table>

      <div class="button-footer">
        <div>
          <button
            type="button"
            class="modify-btn"
            id="modifyBtn"
            onclick="enableEdit()"
          >
            Modify
          </button>
          <button
            type="submit"
            class="save-btn"
            id="saveBtn"
            style="display: none"
          >
            Save
          </button>
        </div>
        <div>
          <a
            href="${pageContext.request.contextPath}/staff/medicine/list"
            class="btn back-btn"
            id="backBtn"
            >Back</a
          >
          <button
            type="button"
            class="cancel-btn"
            id="cancelBtn"
            onclick="disableEdit()"
            style="display: none"
          >
            Cancel
          </button>
        </div>
      </div>
    </form>
  </div>
</div>

<script>
  let originalData = {};

  document.addEventListener("DOMContentLoaded", function () {
    // Store original form data
    const form = document.getElementById("medicineForm");
    originalData = {
      name: form.name.value,
      description: form.description.value,
      price: form.price.value,
    };

    // Check if there's a success message from the server
    <c:if test="${not empty success}">
      document.getElementById('successModal').style.display = 'block';
    </c:if>;
  });

  function enableEdit() {
    const form = document.getElementById("medicineForm");
    form.querySelectorAll("input, textarea").forEach((el) => {
      if (el.name !== "id") el.removeAttribute("readonly");
    });

    // Left button: Modify -> Save
    document.getElementById("modifyBtn").style.display = "none";
    document.getElementById("saveBtn").style.display = "inline-block";

    // Right button: Back -> Cancel
    document.getElementById("backBtn").style.display = "none";
    document.getElementById("cancelBtn").style.display = "inline-block";
  }

  function disableEdit() {
    const form = document.getElementById("medicineForm");

    // Restore original values
    form.name.value = originalData.name;
    form.description.value = originalData.description;
    form.price.value = originalData.price;

    // Make fields readonly again
    form.querySelectorAll("input, textarea").forEach((el) => {
      el.setAttribute("readonly", true);
    });

    // Left button: Save -> Modify
    document.getElementById("modifyBtn").style.display = "inline-block";
    document.getElementById("saveBtn").style.display = "none";

    // Right button: Cancel -> Back
    document.getElementById("backBtn").style.display = "inline-block";
    document.getElementById("cancelBtn").style.display = "none";
  }

  function closeSuccessModal() {
    document.getElementById("successModal").style.display = "none";
  }

  // Close modal when clicking outside of it
  window.onclick = function (event) {
    const modal = document.getElementById("successModal");
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
</script>

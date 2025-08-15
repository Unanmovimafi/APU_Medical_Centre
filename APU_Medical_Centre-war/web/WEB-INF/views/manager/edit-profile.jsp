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
  }

  .mainbody {
    padding: 40px;
  }

  .container {
    display: flex;
    gap: 30px;
    align-items: flex-start;
  }

  .form-section {
    width: 66.66%;
    background: #fff;
    border-radius: 12px;
    padding: 30px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .picture-section {
    width: 33.33%;
    background: #fff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
  }

  .picture-section img {
    width: 150px;
    height: 150px;
    border-radius: 10px;
    border: 1px solid #ccc;
    object-fit: cover;
  }

  .form-table {
    width: 100%;
  }

  .form-table td {
    padding: 10px 8px;
    vertical-align: central;
  }

  .field-wrapper {
    position: relative;
    width: 100%;
    min-height: 40px;
  }

  .text-display {
    display: block;
    width: 100%;
    padding: 10px;
    background-color: #f8f9fa;
    border-radius: 8px;
    box-sizing: border-box;
    min-height: 40px;
  }

  .editable {
    display: none;
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
    font-size: 15px;
  }

  .editable:focus {
    border-color: #00bfff;
    background-color: #f0ffff;
    outline: none;
  }

  .editable select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
    font-size: 15px;
    background-color: white;
  }

  .editable select:focus {
    border-color: #00bfff;
    background-color: #f0ffff;
    outline: none;
  }

  button,
  .btn {
    padding: 10px 16px;
    margin: 10px 6px 0 0;
    border: none;
    border-radius: 8px;
    font-size: 15px;
    cursor: pointer;
  }

  .button-footer {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    width: 100%;
    margin-top: 10px;
  }

  .button-footer button,
  .button-footer .btn {
    flex: 1;
    padding: 12px 0;
    font-size: 14px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s ease;
    text-align: center;
    max-width: 20%;
  }

  #editBtn {
    background-color: #00bfff;
    color: white;
  }

  #saveBtn {
    background-color: #28a745;
    color: white;
  }

  #cancelBtn {
    background-color: #dc3545;
    color: white;
  }

  #backBtn {
    background-color: #00bfff;
    color: white;
    text-decoration: none;
    display: inline-block;
  }
  .form-table td:first-child {
    width: 120px;
    white-space: nowrap;
  }
  input:required:invalid {
    border-color: #dc3545;
    background-color: #fff5f5;
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
    line-height: 1.25;
  }

  .modal-content h3 {
    margin-top: 0;
    margin-bottom: 10px;
  }

  .modal-content p {
    padding-bottom: 25px;
  }

  .modal-content button {
    margin-top: 20px;
    padding: 10px 24px;
    background-color: #00bfff;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
  }
  .modal-footer button:hover {
    background-color: #00acc1;
  }
  .modal-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
</style>

<div class="mainbody">
  <h2>Edit Profile</h2>
  <div class="container">
    <div class="form-section">
      <form
        action="${pageContext.request.contextPath}/manager/edit-profile"
        method="post"
        id="editProfileForm"
      >
        <input type="hidden" name="id" value="${manager.id}" />

        <table class="form-table">
          <tr>
            <td><strong>Name :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display">${manager.name}</span>
                <input
                  type="text"
                  name="name"
                  value="${manager.name}"
                  class="editable"
                  required
                />
              </div>
            </td>
          </tr>
          <tr>
            <td><strong>Email :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display">${manager.email}</span>
                <input
                  type="email"
                  name="email"
                  value="${manager.email}"
                  class="editable"
                  required
                />
              </div>
            </td>
          </tr>
          <tr>
            <td><strong>Phone Number :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display">${manager.phoneNumber}</span>
                <input
                  type="text"
                  name="phoneNumber"
                  value="${manager.phoneNumber}"
                  class="editable"
                  required
                />
              </div>
            </td>
          </tr>
          <tr>
            <td><strong>Gender :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display">
                  <c:choose>
                    <c:when test="${manager.gender == 'Male'}">Male</c:when>
                    <c:when test="${manager.gender == 'Female'}">Female</c:when>
                    <c:when test="${manager.gender == 'Other'}">Other</c:when>
                    <c:when test="${empty manager.gender}">Not specified</c:when>
                    <c:otherwise>${manager.gender}</c:otherwise>
                  </c:choose>
                </span>
                <select name="gender" class="editable" required>
                  <option value="">Select Gender</option>
                  <option value="Male" ${manager.gender == 'Male' ? 'selected' : ''}>Male</option>
                  <option value="Female" ${manager.gender == 'Female' ? 'selected' : ''}>Female</option>
                  <option value="Other" ${manager.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
              </div>
            </td>
          </tr>
          <tr>
            <td><strong>New Password :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display"></span>
                <input
                  type="password"
                  name="newPassword"
                  id="newPassword"
                  placeholder="Leave blank to keep unchanged"
                  minlength="8"
                  class="editable"
                />
              </div>
            </td>
          </tr>
          <tr>
            <td><strong>Old Password :</strong></td>
            <td>
              <div class="field-wrapper">
                <span class="text-display" id="oldPwdText">********</span>
                <input
                  type="password"
                  name="oldPassword"
                  id="oldPassword"
                  placeholder="Enter current password"
                  class="editable"
                  style="display: none"
                />
              </div>
            </td>
          </tr>
        </table>

        <br />
        <div class="button-footer" id="editBackWrapper">
          <button type="button" id="editBtn" onclick="enableEdit()">
            Edit
          </button>
          <a
            href="${pageContext.request.contextPath}/manager/dashboard"
            class="btn"
            id="backBtn"
            >Back</a
          >
        </div>

        <div class="button-footer" id="saveCancelWrapper" style="display: none">
          <button type="submit" id="saveBtn">Save</button>
          <button type="button" id="cancelBtn" onclick="disableEdit()">
            Cancel
          </button>
        </div>
      </form>
    </div>

    <div class="picture-section">
      <h4>Profile Picture</h4>
      <c:choose>
        <c:when test="${not empty manager.profilePicture}">
          <img src="${manager.profilePicture}" alt="Profile Picture" />
        </c:when>
        <c:otherwise>
          <p>No picture uploaded</p>
        </c:otherwise>
      </c:choose>

      <form
        method="post"
        action="${pageContext.request.contextPath}/manager/edit-profile-picture"
        enctype="multipart/form-data"
        style="margin-top: 15px"
      >
        <input type="hidden" name="id" value="${manager.id}" />
        <input type="file" name="profilePicture" accept="image/*" required />
        <button type="submit" style="background-color: #00bfff; color: white">
          Upload
        </button>
      </form>
    </div>
  </div>
</div>

<div id="errorModal" class="modal">
  <div class="modal-content">
    <h3 id="errorModalTitle">Error</h3>
    <p id="errorModalMessage">An error occurred.</p>
    <div class="modal-footer">
      <button onclick="closeErrorModal()">OK</button>
    </div>
  </div>
</div>

<c:if test="${not empty errorMessage}">
  <script>
    window.addEventListener("DOMContentLoaded", () => {
      showCustomAlert(
        '<c:out value="${errorMessage}" escapeXml="true" />',
        "Password Error"
      );
    });
  </script>
</c:if>

<c:if test="${not empty sessionScope.successMessage}">
  <script>
    window.addEventListener("DOMContentLoaded", () => {
      showCustomAlert(
        '<c:out value="${sessionScope.successMessage}" escapeXml="true" />',
        "Success"
      );
    });
  </script>
  <c:remove var="successMessage" scope="session" />
</c:if>

<script>
  const newPasswordInput = document.getElementById("newPassword");
  const oldPasswordInput = document.getElementById("oldPassword");
  const oldPwdText = document.getElementById("oldPwdText");

  newPasswordInput.addEventListener("input", () => {
    const newPwd = newPasswordInput.value.trim();

    if (newPwd.length >= 8) {
      oldPwdText.style.display = "none";
      oldPasswordInput.style.display = "block";
      oldPasswordInput.removeAttribute("readonly");
    } else {
      oldPwdText.style.display = "block";
      oldPasswordInput.style.display = "none";
      oldPasswordInput.value = "";
      oldPasswordInput.setAttribute("readonly", true);
    }
  });

  document
    .getElementById("editProfileForm")
    .addEventListener("submit", function (e) {
      const newPwd = newPasswordInput.value.trim();
      const oldPwd = oldPasswordInput.value.trim();

      if (newPwd.length >= 8 && !oldPwd) {
        showCustomAlert(
          "Please type in your previous password to change new password.",
          "Password Required"
        );
        oldPasswordInput.focus();
        e.preventDefault();
      }
    });

  function enableEdit() {
    document.querySelectorAll(".text-display").forEach((el) => {
      if (el.id !== "oldPwdText") el.style.display = "none";
    });

    document.querySelectorAll(".editable").forEach((el) => {
      if (el.name !== "oldPassword") el.style.display = "block";
    });

    document.getElementById("editBackWrapper").style.display = "none";
    document.getElementById("saveCancelWrapper").style.display = "flex";
  }

  function disableEdit() {
    document
      .querySelectorAll(".text-display")
      .forEach((el) => (el.style.display = "block"));
    document.querySelectorAll(".editable").forEach((el) => {
      el.style.display = "none";
      if (el.name === "oldPassword") {
        el.setAttribute("readonly", true);
        el.value = "";
      }
    });

    document.getElementById("editBackWrapper").style.display = "flex";
    document.getElementById("saveCancelWrapper").style.display = "none";
  }

  function showCustomAlert(message, title = "Error") {
    document.getElementById("errorModalTitle").textContent = title;
    document.getElementById("errorModalMessage").textContent = message;
    document.getElementById("errorModal").style.display = "block";
  }

  function closeErrorModal() {
    document.getElementById("errorModal").style.display = "none";
  }
</script>

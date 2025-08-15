<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  body {
    background-color: #e0f7fa;
    font-family: Arial, sans-serif;
  }

  h2 {
    color: #00bfff;
    padding-bottom: 25px;
    text-align: center;
  }

  .mainbody {
    padding: 40px;
    max-width: 800px;
    margin: 0 auto;
  }

  .form-section {
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
    vertical-align: top;
  }

  .form-table td:first-child {
    width: 150px;
    white-space: nowrap;
    font-weight: bold;
  }

  input[type="text"], input[type="number"], textarea, select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
    font-size: 15px;
  }

  input[type="text"]:focus, input[type="number"]:focus, textarea:focus {
    border-color: #00bfff;
    background-color: #f0ffff;
    outline: none;
  }

  textarea {
    resize: vertical;
    min-height: 120px;
  }

  .select-btn {
    display: inline-block;
    margin-left: 10px;
    padding: 8px 16px;
    background-color: #00bfff;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s ease;
  }

  .select-btn:hover {
    background-color: #00acc1;
    text-decoration: none;
    color: white;
  }

  .button-footer {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 30px;
  }

  .button-footer button, .button-footer .btn {
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

  #submitBtn {
    background-color: #28a745;
    color: white;
  }

  #submitBtn:hover {
    background-color: #218838;
  }

  #backBtn {
    background-color: #6c757d;
    color: white;
  }

  #backBtn:hover {
    background-color: #5a6268;
  }

  /* Modal styles */
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
    margin: 10% auto;
    padding: 30px;
    width: 70%;
    max-width: 600px;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    line-height: 1.25;
  }

  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 1px solid #ddd;
    padding-bottom: 15px;
  }

  .modal-header h3 {
    margin: 0;
    color: #00bfff;
  }

  .close {
    color: #aaa;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
    line-height: 1;
  }

  .close:hover,
  .close:focus {
    color: #000;
  }

  .staff-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }

  .staff-table th, .staff-table td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd;
  }

  .staff-table th {
    background-color: #e8fafd;
    font-weight: bold;
    color: #1c1c1c;
  }

  .staff-table tr:hover {
    background-color: #f0f8ff;
  }

  .staff-table input[type="radio"] {
    width: auto;
  }

  .modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
  }

  .modal-footer button {
    padding: 10px 20px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.2s ease;
  }

  .save-btn {
    background-color: #28a745;
    color: white;
  }

  .save-btn:hover {
    background-color: #218838;
  }

  .cancel-btn {
    background-color: #6c757d;
    color: white;
  }

  .cancel-btn:hover {
    background-color: #5a6268;
  }

  .input-group {
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .input-group input[type="text"] {
    flex: 1;
  }

  input:required:invalid {
    border-color: #dc3545;
    background-color: #fff5f5;
  }

  .required {
    color: #dc3545;
  }
</style>

<div class="mainbody">
    <h2>Give Your Comment</h2>
    
    <div class="form-section">
        <form action="CustomerCreateComment" method="POST" id="commentForm">
            <input type="hidden" name="selectedUserId" id="selectedUserId" value="">
            
            <table class="form-table">
                <tr>
                    <td><strong>Target Staff <span class="required">*</span>:</strong></td>
                    <td>
                        <div class="input-group">
                            <input type="text" id="target_user" name="target_user" readonly placeholder="Click SELECT to choose a staff member" required>
                            <a href="javascript:viewUser();" class="select-btn">SELECT</a>
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td><strong>Rating <span class="required">*</span>:</strong></td>
                    <td>
                        <input type="number" id="rating" name="rating" min="1" max="10" placeholder="Rate from 1 to 10" required>
                    </td>
                </tr>
                
                <tr>
                    <td><strong>Your Comment <span class="required">*</span>:</strong></td>
                    <td>
                        <textarea id="content" name="content" placeholder="Enter your comment here..." required></textarea>
                    </td>
                </tr>
            </table>

            <div class="button-footer">
                <button type="submit" id="submitBtn">Submit Comment</button>
                <a href="${pageContext.request.contextPath}/customer/dashboard" class="btn" id="backBtn">Back</a>
            </div>
        </form>
    </div>
</div>

<!-- Modal for staff selection -->
<div id="staffList" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Select Staff Member</h3>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>
        
        <table class="staff-table">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>ID</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${counterStaffList}">
                    <tr>
                        <td><input type="radio" name="selectedUser" value="${staff.id}"></td>
                        <td>${staff.id}</td>
                        <td>${staff.name}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="modal-footer">
            <button type="button" class="save-btn" onclick="saveUser()">Save</button>
            <button type="button" class="cancel-btn" onclick="closeModal()">Cancel</button>
        </div>
    </div>
</div>

<script type="text/javascript">
    function viewUser() {
        document.getElementById("staffList").style.display = "block";
    }
    
    function closeModal() {
        var checkboxes = document.querySelectorAll('input[name="selectedUser"]:checked');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = false;
        });
        document.getElementById('staffList').style.display = "none";
    }
    
    // Close modal when clicking outside of it
    window.onclick = function(event) {
        var modal = document.getElementById('staffList');
        if (event.target === modal) {
            closeModal();
        }
    };
    
    function saveUser() {
        var selectedRadio = document.querySelector("input[name='selectedUser']:checked");

        if (!selectedRadio) {
            alert("Please select a staff member to save.");
        } else {
            var userId = selectedRadio.value;
            var row = selectedRadio.closest("tr");
            var userName = row.cells[2].textContent;

            // Store the ID in the hidden field for form submission
            document.getElementById("selectedUserId").value = userId;

            // Close modal
            document.getElementById('staffList').style.display = "none";

            // Show name in the text field
            document.getElementById('target_user').value = userName;
        }
    }

    // Form validation
    document.getElementById('commentForm').addEventListener('submit', function(e) {
        var selectedUserId = document.getElementById('selectedUserId').value;
        var rating = document.getElementById('rating').value;
        var content = document.getElementById('content').value.trim();

        if (!selectedUserId) {
            alert('Please select a staff member.');
            e.preventDefault();
            return false;
        }

        if (!rating || rating < 1 || rating > 10) {
            alert('Please enter a valid rating between 1 and 10.');
            e.preventDefault();
            return false;
        }

        if (!content) {
            alert('Please enter your comment.');
            e.preventDefault();
            return false;
        }
    });
</script>

<%-- 
    Document   : list_staff
    Created on : 7 Jun 2025, 12:06:49 am
    Author     : zihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List Staff</title>
    <!-- For Modal CSS -->
    <style>
        /* Modal styles */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(0, 0, 0, 0.4); /* Black with transparency */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        table { border-collapse: collapse; width: 100%; margin: 16px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background: #f5f5f5; text-align: left; }
        h2 { margin-top: 24px; }
        .section-header { display:flex; align-items:center; gap:12px; }
        .section-header form { margin-left:auto; }
    </style>
</head>
<body>
    <h1>Staff Directory</h1>

    <!-- ========== MANAGERS ========== -->
    <div class="section">
        <div class="section-header">
            <h2>Managers</h2>
            <form action="DeleteStaff" method="POST" onsubmit="return confirmDelete()">
                <input type="hidden" name="role" value="MANAGER"/>
                <button type="submit">Delete Selected Managers</button>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>View</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty managerList}">
                    <tr><td colspan="5"><em>No managers found.</em></td></tr>
                </c:if>
                <c:forEach var="m" items="${managerList}">
                    <tr>
  <td>${m.id}</td>
  <td>${m.name}</td>
  <td>${m.status}</td>
  <td><a href="javascript:viewStaff('MANAGER', ${m.id});">View</a></td>
  <td><input type="checkbox" name="selectedIds" value="${m.id}" /></td>
</tr>

<!-- Modal -->
<div class="modal" data-type="MANAGER" data-id="${m.id}" style="display:none;">
  <div class="modal-content">
    <a class="close" href="javascript:closeModal();">&times;</a>

    <header class="modal-header">
      <h3>Manager Details</h3>
      <a class="edit-link" href="EditManager?id=${m.id}">Edit</a>
    </header>

    <!-- Optional profile picture if stored as Base64 in profilePicture -->
    <div class="profile-picture" style="margin: 8px 0;">
      <img 
        src="${empty m.profilePicture ? '': 'data:image/*;base64,' += m.profilePicture}" 
        alt="Profile picture of ${m.name}" 
        onerror="this.style.display='none';" 
        style="max-width:120px; max-height:120px; border-radius:8px;"
      />
    </div>

    <div class="staffDetails" style="display:grid; grid-template-columns: 1fr 2fr; gap:8px 12px;">
      <label>ID:</label>
      <input type="text" value="${m.id}" disabled />

      <label>Status:</label>
      <input type="text" value="${m.status}" disabled />

      <label>Username:</label>
      <input type="text" value="${m.username}" disabled />

      <label>Name:</label>
      <input type="text" value="${m.name}" disabled />

      <label>Email:</label>
      <input type="text" value="${m.email}" disabled />

      <label>Phone Number:</label>
      <input type="text" value="${m.phoneNumber}" disabled />

      <label>Date of Birth:</label>
      <input type="text" value="${m.dateOfBirth}" disabled />

      <label>Last Login:</label>
      <input type="text" value="${m.lastLoginDatetime}" disabled />

    <!-- If you’d like to surface related comments count -->
    <div class="meta" style="margin-top:12px;">
      <small>
        Comments: ${empty m.commentCollection ? 0 : m.commentCollection.size()}
      </small>
    </div>
  </div>
</div>
                </c:forEach>
            </tbody>
        </table>
            </form>
    </div>

    <!-- ========== COUNTER STAFF ========== -->
    <div class="section">
        <div class="section-header">
            <h2>Counter Staff</h2>
            <form action="DeleteStaff" method="POST" onsubmit="return confirmDelete()">
                <input type="hidden" name="role" value="COUNTER_STAFF"/>
                <button type="submit">Delete Selected Counter Staff</button>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>View</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty counterStaffList}">
                    <tr><td colspan="5"><em>No counter staff found.</em></td></tr>
                </c:if>
                <c:forEach var="cst" items="${counterStaffList}">
                    <tr>
  <td>${cst.id}</td>
  <td>${cst.name}</td>
  <td>${cst.status}</td>
  <td><a href="javascript:viewStaff('COUNTER_STAFF', ${cst.id});">View</a></td>
  <td><input type="checkbox" name="selectedIds" value="${cst.id}" /></td>
</tr>

<!-- Modal -->
<div class="modal" data-type="COUNTER_STAFF" data-id="${cst.id}" style="display:none;">
  <div class="modal-content">
    <a class="close" href="javascript:closeModal();">&times;</a>

    <header class="modal-header">
      <h3>Counter Staff Details</h3>
      <a class="edit-link" href="EditCounterStaff?id=${cst.id}">Edit</a>
    </header>

    <!-- Optional profile picture -->
    <div class="profile-picture" style="margin: 8px 0;">
      <img 
        src="${empty cst.profilePicture ? '' : 'data:image/*;base64,' += cst.profilePicture}" 
        alt="Profile picture of ${cst.name}" 
        onerror="this.style.display='none';" 
        style="max-width:120px; max-height:120px; border-radius:8px;"
      />
    </div>

    <div class="staffDetails" style="display:grid; grid-template-columns: 1fr 2fr; gap:8px 12px;">
      <label>ID:</label>
      <input type="text" value="${cst.id}" disabled />

      <label>Status:</label>
      <input type="text" value="${cst.status}" disabled />

      <label>Username:</label>
      <input type="text" value="${cst.username}" disabled />

      <label>Name:</label>
      <input type="text" value="${cst.name}" disabled />

      <label>Email:</label>
      <input type="text" value="${cst.email}" disabled />

      <label>Phone Number:</label>
      <input type="text" value="${cst.phoneNumber}" disabled />

      <label>Date of Birth:</label>
      <input type="text" value="${cst.dateOfBirth}" disabled />

      <label>Last Login:</label>
      <input type="text" value="${cst.lastLoginDatetime}" disabled />
    </div>

    <!-- Related comments count -->
    <div class="meta" style="margin-top:12px;">
      <small>
        Comments: ${empty cst.commentCollection ? 0 : cst.commentCollection.size()}
      </small>
    </div>
  </div>
</div>

                </c:forEach>
            </tbody>
        </table>
            </form>
    </div>

    <!-- ========== DOCTORS ========== -->
    <div class="section">
        <div class="section-header">
            <h2>Doctors</h2>
            <form action="DeleteStaff" method="POST" onsubmit="return confirmDelete()">
                <input type="hidden" name="role" value="DOCTOR"/>
                <button type="submit">Delete Selected Doctors</button>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>View</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty doctorList}">
                    <tr><td colspan="5"><em>No doctors found.</em></td></tr>
                </c:if>
                <c:forEach var="d" items="${doctorList}">
                   <tr>
  <td>${d.id}</td>
  <td>${d.name}</td>
  <td>${d.status}</td>
  <td><a href="javascript:viewStaff('DOCTOR', ${d.id});">View</a></td>
  <td><input type="checkbox" name="selectedIds" value="${d.id}" /></td>
</tr>

<!-- Modal -->
<div class="modal" data-type="DOCTOR" data-id="${d.id}" style="display:none;">
  <div class="modal-content">
    <a class="close" href="javascript:closeModal();">&times;</a>

    <header class="modal-header">
      <h3>Doctor Details</h3>
      <a class="edit-link" href="EditDoctor?id=${d.id}">Edit</a>
    </header>

    <!-- Optional profile picture (Base64) -->
    <div class="profile-picture" style="margin: 8px 0;">
      <img
        src="${empty d.profilePicture ? '' : 'data:image/*;base64,' += d.profilePicture}"
        alt="Profile picture of ${d.name}"
        onerror="this.style.display='none';"
        style="max-width:120px; max-height:120px; border-radius:8px;"
      />
    </div>

    <div class="staffDetails" style="display:grid; grid-template-columns: 1fr 2fr; gap:8px 12px;">
      <!-- Core fields -->
      <label>ID:</label>
      <input type="text" value="${d.id}" disabled />

      <label>Status:</label>
      <input type="text" value="${d.status}" disabled />

      <label>Username:</label>
      <input type="text" value="${d.username}" disabled />

      <label>Name:</label>
      <input type="text" value="${d.name}" disabled />

      <label>Email:</label>
      <input type="text" value="${d.email}" disabled />

      <label>Phone Number:</label>
      <input type="text" value="${d.phoneNumber}" disabled />

      <label>Date of Birth:</label>
      <input type="text" value="${d.dateOfBirth}" disabled />

      <label>Last Login:</label>
      <input type="text" value="${d.lastLoginDatetime}" disabled />

    <!-- Related comments count -->
    <div class="meta" style="margin-top:12px;">
      <small>
        Comments: ${empty d.commentCollection ? 0 : d.commentCollection.size()}
      </small>
    </div>
  </div>
</div>

                </c:forEach>
            </tbody>
        </table>
            </form>
    </div>

    <script type="text/javascript">
        function confirmDelete() {
            return confirm("Are you sure you want to delete the selected staff?");
        }

        // Open a modal by role + id
        function viewStaff(role, id) {
            var selector = '.modal[data-type="' + role + '"][data-id="' + id + '"]';
            var el = document.querySelector(selector);
            if (el) el.style.display = "block";
        }

        function closeModal() {
            var modals = document.getElementsByClassName('modal');
            for (let i = 0; i < modals.length; i++) {
                modals[i].style.display = "none";
            }
        }

        // Close when clicking outside
        window.onclick = function(event) {
            var modals = document.getElementsByClassName('modal');
            for (let i = 0; i < modals.length; i++) {
                if (event.target === modals[i]) {
                    closeModal();
                }
            }
        };
    </script>
</body>
</html>

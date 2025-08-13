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
                            <h3>Manager Details</h3>
                            <a href="EditManager?id=${m.id}">Edit</a>
                            <div class="staffDetails">
                                ID: <input type="text" value="${m.id}" disabled />
                                Name: <input type="text" value="${m.name}" disabled />
                                Status: <input type="text" value="${m.status}" disabled />
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
                            <h3>Counter Staff Details</h3>
                            <a href="EditCounterStaff?id=${cst.id}">Edit</a>
                            <div class="staffDetails">
                                ID: <input type="text" value="${cst.id}" disabled />
                                Name: <input type="text" value="${cst.name}" disabled />
                                Status: <input type="text" value="${cst.status}" disabled />
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
                            <h3>Doctor Details</h3>
                            <a href="EditDoctor?id=${d.id}">Edit</a>
                            <div class="staffDetails">
                                ID: <input type="text" value="${d.id}" disabled />
                                Name: <input type="text" value="${d.name}" disabled />
                                Status: <input type="text" value="${d.status}" disabled />
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

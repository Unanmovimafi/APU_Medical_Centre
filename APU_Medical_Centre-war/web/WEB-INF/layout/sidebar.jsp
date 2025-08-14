<%@ page import="model.manager.Manager" %>
<%@ page import="model.counterstaff.CounterStaff" %>
<%@ page import="model.customer.Customer" %>
<%@ page import="model.doctor.Doctor" %>

<%
    Manager managerSession = (Manager) session.getAttribute("managerSession");
    CounterStaff staffSession = (CounterStaff) session.getAttribute("counterStaffSession");
    Customer customerSession = (Customer) session.getAttribute("customerSession");
    Doctor doctorSession = (Doctor) session.getAttribute("doctorSession");

    String role = "GUEST";
    String displayName = "Guest";

    if (managerSession != null) {
        role = "MANAGER";
        displayName = managerSession.getName();
    } else if (staffSession != null) {
        role = "COUNTER_STAFF";
        displayName = staffSession.getName();
    } else if (customerSession != null) {
        role = "CUSTOMER";
        displayName = customerSession.getName();
    } else if (doctorSession != null) {
        role = "DOCTOR";
        displayName = doctorSession.getName();
    }
%>

<aside id="sidebar">
  <button class="toggle-btn" onclick="toggleSidebar()">?</button>
  <img src="../assets/img/default-profile.png" alt="Profile" class="profile-img" />
  <h3><%= displayName %></h3>


  <% if ("MANAGER".equalsIgnoreCase(role)) { %>

    <a href="../manager/dashboard.jsp">Dashboard</a>
    <details>
      <summary>Manager</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/manager/staff/list">Staff List</a>
        <a href="../manager/registerStaff.jsp">Register New Staff</a>
      </div>
    </details>
    <details>
      <summary>Appointments</summary>
      <div style="margin-left: 10px;">
        <a href="../appointments/calendar.jsp">Calendar</a>
        <a href="../appointments/requestList.jsp">Request List</a>
        <a href="../appointments/comments.jsp">Comments</a>
      </div>
    </details>
    <details>
      <summary>Reports</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/manager/customer/report">Customer Report</a>
      </div>
    </details>

  <% } else if ("COUNTER_STAFF".equalsIgnoreCase(role)) { %>

    <a href="${pageContext.request.contextPath}/staff/edit-profile">Edit Profile</a>
    <a href="${pageContext.request.contextPath}/staff/dashboard">Dashboard</a>
    <details>
      <summary>User</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/staff/customer/list">Customer List</a>
        <a href="${pageContext.request.contextPath}/staff/employee/list">Employee List</a>
      </div>
    </details>
    <details>
      <summary>Appointment</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/staff/appointment/new">Create Appointment</a>
        <a href="${pageContext.request.contextPath}/staff/appointment/list">Appointment Calendar</a>
        <a href="${pageContext.request.contextPath}/staff/appointment/request">Appointment Request</a>
        <a href="${pageContext.request.contextPath}/staff/appointment/payment">Appointment Payment</a>
      </div>
    </details>
    <details>
      <summary>Medicine</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/staff/medicine/new">Add Medicine</a>
        <a href="${pageContext.request.contextPath}/staff/medicine/list">Medicine List</a>
      </div>
    </details>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
    
  <% } else if ("DOCTOR".equalsIgnoreCase(role)) { %>

    <a href="${pageContext.request.contextPath}/doctor/dashboard">Dashboard</a>
    <details>
      <summary>Appointment</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/doctor/appointment/list">Appointment List</a>
        <a href="${pageContext.request.contextPath}/doctor/appointment/calendar">Appointment Calendar</a>
        <a href="${pageContext.request.contextPath}/doctor/appointment/list">Current Appointment</a>
        <a href="${pageContext.request.contextPath}/doctor/appointment/list">Feedback</a>
        <a href="${pageContext.request.contextPath}/doctor/appointment/list">Charge</a>
      </div>
    </details>
    <details>
      <summary>Customer</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/doctor/customer/list">Customer List</a>
        <a href="${pageContext.request.contextPath}/doctor/customer/medical-history">Check Payments</a>
      </div>
    </details>
    
    <a href="${pageContext.request.contextPath}/logout">Logout</a>

  <% } else if ("CUSTOMER".equalsIgnoreCase(role)) { %>

    <a href="../customer/dashboard.jsp">Dashboard</a>
    <details>
      <summary>Appointments</summary>
      <div style="margin-left: 10px;">
        <a href="../appointments/request.jsp">Appointment Request</a>
        <a href="../appointments/myAppointments.jsp">My Appointments</a>
        <a href="../appointments/history.jsp">Appointment History</a>
      </div>
    </details>
    <details>
      <summary>Comments</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/customer/create-comment">Comment to Staff/Doctor</a>
      </div>
    </details>
    <details>
      <summary><a href="../logout">Reports</a></summary>
    </details>

  <% } else { %>
    <!-- No session (guest) -->
    <a href="../public/dashboard.jsp">Dashboard</a>
    <a href="../about.jsp">About</a>
    <a href="../login.jsp">Login</a>
  <% } %>
</aside>

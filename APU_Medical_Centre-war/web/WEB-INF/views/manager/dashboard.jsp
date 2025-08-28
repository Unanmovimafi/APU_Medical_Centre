<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<link
  href="https://fonts.googleapis.com/icon?family=Material+Icons"
  rel="stylesheet"
/>

<style>
  .mainbody {
    font-family: Arial, sans-serif;
    margin: 20px;
    background-color: #f8f9fa;
    padding: 25px;
  }
  .dashboard-header {
    color: #00bfff;
    margin-bottom: 30px;
    font-size: 2rem;
    font-weight: bold;
  }
  .stats-container {
    display: flex;
    gap: 20px;
    margin-bottom: 40px;
    flex-wrap: wrap;
  }
  .stat-card {
    background: linear-gradient(135deg, #00bfff 0%, #33c9e7 100%);
    color: white;
    padding: 25px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 191, 255, 0.3);
    flex: 1;
    min-width: 250px;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }
  .stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 20px rgba(0, 191, 255, 0.4);
  }
  .stat-card .icon {
    font-size: 2.5rem;
    margin-bottom: 10px;
    opacity: 0.9;
  }
  .stat-card .number {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 5px;
  }
  .stat-card .label {
    font-size: 1rem;
    opacity: 0.9;
  }
  .content-sections {
    display: flex;
    gap: 30px;
    flex-wrap: wrap;
  }
  .section {
    background: white;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    flex: 1;
    min-width: 400px;
  }
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 2px solid #e9ecef;
    padding-bottom: 15px;
  }
  .section-title {
    color: #00bfff;
    font-size: 1.3rem;
    font-weight: bold;
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .section-count {
    background: #00bfff;
    color: white;
    padding: 5px 12px;
    border-radius: 20px;
    font-size: 0.9rem;
    font-weight: bold;
  }
  .arrow-link {
    background: #00bfff;
    color: white;
    padding: 8px 15px;
    border-radius: 8px;
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 5px;
    transition: background-color 0.2s ease;
  }
  .arrow-link:hover {
    background: #00acc1;
    text-decoration: none;
    color: white;
  }
  .item-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  .item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    margin-bottom: 10px;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    transition: background-color 0.2s ease;
  }
  .item:hover {
    background-color: #f8f9fa;
  }
  .item-info {
    flex: 1;
  }
  .item-title {
    font-weight: bold;
    color: #333;
    margin-bottom: 5px;
  }
  .item-details {
    color: #666;
    font-size: 0.9rem;
  }
  .no-items {
    text-align: center;
    padding: 40px 20px;
    color: #6c757d;
    font-style: italic;
  }
  .no-items .material-icons {
    font-size: 3rem;
    opacity: 0.3;
    margin-bottom: 10px;
  }
  @media (max-width: 768px) {
    .stats-container {
      flex-direction: column;
    }
    .content-sections {
      flex-direction: column;
    }
    .section {
      min-width: auto;
    }
  }
</style>

<div class="mainbody">
  <h1 class="dashboard-header">
    <span class="material-icons" style="font-size: 2rem; vertical-align: middle"
      >dashboard</span
    >
    Manager Dashboard
  </h1>

  <!-- Statistics Cards -->
  <div class="stats-container">
    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">groups</span>
      </div>
      <div class="number">${totalEmployee}</div>
      <div class="label">Total Employee</div>
    </div>
    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">event</span>
      </div>
      <div class="number">${totalAppointmentThisMonth}</div>
      <div class="label">Total Appointment This Month</div>
    </div>
    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">comment</span>
      </div>
      <div class="number">${totalComments}</div>
      <div class="label">Total Comments</div>
    </div>
  </div>

  <!-- Content Sections -->
  <div class="content-sections">
    <!-- Latest 5 Employees -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">badge</span>
          Latest Employees
          <span class="section-count">5</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/manager/employee/list"
          class="arrow-link"
        >
          View All
          <span class="material-icons">arrow_forward</span>
        </a>
      </div>
      <c:choose>
        <c:when test="${not empty latestEmployees}">
          <ul class="item-list">
            <c:forEach
              var="employee"
              items="${latestEmployees}"
              varStatus="status"
            >
              <li class="item">
                <div class="item-info">
                  <div class="item-title">${employee.name}</div>
                  <div class="item-details">
                    Role:
                    <c:choose>
                      <c:when test="${employee.class.simpleName == 'Doctor'}">
                        Doctor
                      </c:when>
                      <c:when
                        test="${employee.class.simpleName == 'CounterStaff'}"
                      >
                        Counter Staff
                      </c:when>
                      <c:otherwise> Employee </c:otherwise>
                    </c:choose>
                  </div>
                  <div class="item-details">
                    Joined:
                    <fmt:formatDate
                      value="${employee.creationDatetime}"
                      pattern="dd/MM/yyyy"
                    />
                  </div>
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div><span class="material-icons">person_off</span></div>
            <p>No employees found</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
    <!-- Latest 5 Appointments -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">event_note</span>
          Latest Appointments
          <span class="section-count">5</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/manager/appointment/list"
          class="arrow-link"
        >
          View All
          <span class="material-icons">arrow_forward</span>
        </a>
      </div>
      <c:choose>
        <c:when test="${not empty latestAppointments}">
          <ul class="item-list">
            <c:forEach
              var="appointment"
              items="${latestAppointments}"
              varStatus="status"
            >
              <li class="item">
                <div class="item-info">
                  <div class="item-title">${appointment.customer.name}</div>
                  <div class="item-details">
                    Doctor: ${appointment.doctor.name}
                  </div>
                  <div class="item-details">
                    Date:
                    <fmt:formatDate
                      value="${appointment.appointmentStartDatetime}"
                      pattern="dd/MM/yyyy HH:mm"
                    />
                  </div>
                  <div class="item-details">Status: ${appointment.status}</div>
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div><span class="material-icons">event_busy</span></div>
            <p>No appointments found</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

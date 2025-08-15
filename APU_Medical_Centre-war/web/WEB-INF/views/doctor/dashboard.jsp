<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
language="java" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

  .appointment-list,
  .customer-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .appointment-item,
  .customer-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    margin-bottom: 10px;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    transition: background-color 0.2s ease;
  }

  .appointment-item:hover,
  .customer-item:hover {
    background-color: #f8f9fa;
  }

  .appointment-info,
  .customer-info {
    flex: 1;
  }

  .customer-name,
  .appointment-customer {
    font-weight: bold;
    color: #333;
    margin-bottom: 5px;
  }

  .appointment-details,
  .customer-details {
    color: #666;
    font-size: 0.9rem;
  }

  .appointment-time {
    color: #00bfff;
    font-weight: bold;
    font-size: 0.9rem;
  }

  .status-badge {
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 0.8rem;
    font-weight: bold;
  }

  .status-pending {
    background: #fff3cd;
    color: #856404;
  }

  .customer-badge {
    background: #d1ecf1;
    color: #0c5460;
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 0.8rem;
    font-weight: bold;
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
      >local_hospital</span
    >
    Doctor Dashboard
  </h1>

  <!-- Statistics Cards -->
  <div class="stats-container">
    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">today</span>
      </div>
      <div class="number">${appointmentsToday}</div>
      <div class="label">Appointments Today</div>
    </div>

    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">event</span>
      </div>
      <div class="number">${appointmentsThisMonth}</div>
      <div class="label">Total Appointments This Month</div>
    </div>

    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">people</span>
      </div>
      <div class="number">${customersThisMonth}</div>
      <div class="label">Patients This Month</div>
    </div>
  </div>

  <!-- Content Sections -->
  <div class="content-sections">
    <!-- Today's Pending Appointments -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">schedule</span>
          Today's Pending Appointments
          <span class="section-count">${todayPendingCount}</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="arrow-link"
        >
          View All
          <span class="material-icons">arrow_forward</span>
        </a>
      </div>

      <c:choose>
        <c:when test="${not empty todayPending}">
          <ul class="appointment-list">
            <c:forEach
              var="appointment"
              items="${todayPending}"
              varStatus="status"
            >
              <li class="appointment-item">
                <div class="appointment-info">
                  <div class="appointment-customer">
                    ${appointment.customer.name}
                  </div>
                  <div class="appointment-details">
                    Time:
                    <fmt:formatDate
                      value="${appointment.appointmentStartDatetime}"
                      pattern="HH:mm"
                    />
                  </div>
                  <div class="appointment-details">
                    Created:
                    <fmt:formatDate
                      value="${appointment.creationDatetime}"
                      pattern="dd/MM/yyyy HH:mm"
                    />
                  </div>
                </div>
                <div>
                  <span class="status-badge status-pending">PENDING</span>
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div>
              <span class="material-icons">event_busy</span>
            </div>
            <p>No pending appointments for today</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Newest Customers -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">person_add</span>
          Newest Patients
          <span class="section-count">${newestCustomersCount}</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/staff/customer/list"
          class="arrow-link"
        >
          View All
          <span class="material-icons">arrow_forward</span>
        </a>
      </div>

      <c:choose>
        <c:when test="${not empty newestCustomers}">
          <ul class="customer-list">
            <c:forEach
              var="customer"
              items="${newestCustomers}"
              varStatus="status"
            >
              <li class="customer-item">
                <div class="customer-info">
                  <div class="customer-name">${customer.name}</div>
                  <div class="customer-details">
                    Username: ${customer.username}
                  </div>
                  <div class="customer-details">
                    Registered:
                    <fmt:formatDate
                      value="${customer.creationDatetime}"
                      pattern="dd/MM/yyyy"
                    />
                  </div>
                </div>
                <div>
                  <span class="customer-badge">PATIENT</span>
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div>
              <span class="material-icons">person_outline</span>
            </div>
            <p>No patients found</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

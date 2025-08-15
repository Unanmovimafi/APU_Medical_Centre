<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    padding: 20px;
    max-width: 1000px;
    margin: 0 auto;
  }

  .form-section {
    background: #fff;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }

  .section-title {
    color: #00bfff;
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 2px solid #e0f7fa;
  }

  .form-table {
    width: 100%;
  }

  .form-table td {
    padding: 8px;
    vertical-align: top;
  }

  .form-table td:first-child {
    width: 150px;
    font-weight: bold;
    white-space: nowrap;
  }

  input[type="text"], textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f8f9fa;
    color: #6c757d;
    box-sizing: border-box;
  }

  .badge {
    padding: 5px 10px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
  }

  .badge-warning {
    background-color: #ffc107;
    color: #212529;
  }
  .badge-info {
    background-color: #17a2b8;
    color: white;
  }
  .badge-primary {
    background-color: #007bff;
    color: white;
  }
  .badge-success {
    background-color: #28a745;
    color: white;
  }
  .badge-danger {
    background-color: #dc3545;
    color: white;
  }
  .badge-secondary {
    background-color: #6c757d;
    color: white;
  }

  .medicine-list {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 8px;
    border: 1px solid #ddd;
  }

  .medicine-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #eee;
  }

  .medicine-item:last-child {
    border-bottom: none;
  }

  .medicine-name {
    font-weight: bold;
    color: #007bff;
    font-size: 14px;
  }

  .medicine-details {
    font-size: 12px;
    color: #666;
    margin-top: 2px;
  }

  .medicine-quantity {
    color: #28a745;
    font-weight: bold;
    font-size: 14px;
  }

  .no-data {
    text-align: center;
    color: #666;
    font-style: italic;
    padding: 20px;
  }

  .button-footer {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 30px;
  }

  .btn {
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

  .back-btn {
    background-color: #6c757d;
    color: white;
  }

  .back-btn:hover {
    background-color: #5a6268;
  }

  .calendar-btn {
    background-color: #00bfff;
    color: white;
  }

  .calendar-btn:hover {
    background-color: #00acc1;
  }

  .error-message {
    color: #dc3545;
    background-color: #f8d7da;
    border: 1px solid #f5c6cb;
    border-radius: 8px;
    padding: 10px;
    margin-bottom: 20px;
  }

  .diagnosis-section {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 8px;
    border-left: 4px solid #00bfff;
    margin-top: 10px;
  }
</style>

<div class="mainbody">
    <h2>Appointment Details & Medical Report</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <c:if test="${not empty appointment}">
        <!-- Appointment Information -->
        <div class="form-section">
            <div class="section-title">Appointment Information</div>
            <table class="form-table">
                <tr>
                    <td><strong>Doctor :</strong></td>
                    <td><input type="text" value="${appointment.doctor.name}" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Date :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='EEEE, dd MMMM yyyy'/>" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Time :</strong></td>
                    <td><input type="text" value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='HH:mm'/> - <fmt:formatDate value='${appointment.appointmentEndDatetime}' pattern='HH:mm'/>" readonly /></td>
                </tr>
                <tr>
                    <td><strong>Status :</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${appointment.status == 'WAITING PAYMENT'}">
                                <span class="badge badge-primary">Waiting Payment</span>
                            </c:when>
                            <c:when test="${appointment.status == 'PAID'}">
                                <span class="badge badge-success">Paid</span>
                            </c:when>
                            <c:when test="${appointment.status == 'COMPLETED'}">
                                <span class="badge badge-success">Completed</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-secondary">${appointment.status}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td><strong>Charge :</strong></td>
                    <td>
                        <input type="text" value="<c:choose><c:when test='${appointment.charge != null}'>RM <fmt:formatNumber value='${appointment.charge}' pattern='0.00'/></c:when><c:otherwise>-</c:otherwise></c:choose>" readonly />
                    </td>
                </tr>
            </table>
        </div>

        <!-- Medical Report & Diagnosis -->
        <c:if test="${not empty feedback}">
        <div class="form-section">
            <div class="section-title">Medical Report & Diagnosis</div>
            <table class="form-table">
                <tr>
                    <td><strong>Feedback :</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty feedback.context}">
                                <textarea rows="6" readonly>${feedback.context}</textarea>
                            </c:when>
                            <c:otherwise>
                                <input type="text" value="No feedback recorded" readonly />
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </div>
        </c:if>

        <!-- Prescribed Medicines -->
        <div class="form-section">
            <div class="section-title">Prescribed Medicines</div>
            <c:choose>
                <c:when test="${not empty appointmentMedicines}">
                    <div class="medicine-list">
                        <c:forEach var="medicine" items="${appointmentMedicines}">
                        <div class="medicine-item">
                            <div>
                                <div class="medicine-name">${medicine.medicine.name}</div>
                                <div class="medicine-details">
                                    <c:if test="${not empty medicine.medicine.description}">
                                        ${medicine.medicine.description}
                                    </c:if>
                                    <c:if test="${not empty medicine.dosageInstructions}">
                                        <br><strong>Instructions:</strong> ${medicine.dosageInstructions}
                                    </c:if>
                                </div>
                            </div>
                            <div class="medicine-quantity">
                                Qty: ${medicine.quantity}
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-data">No medicines prescribed for this appointment</div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- No Medical Report Available -->
        <c:if test="${empty feedback}">
        <div class="form-section">
            <div class="section-title">Medical Report</div>
            <div class="no-data">
                Medical report is not yet available for this appointment.
                <c:if test="${appointment.status == 'WAITING PAYMENT'}">
                    <br>The report will be available after payment is completed.
                </c:if>
            </div>
        </div>
        </c:if>

    </c:if>

    <c:if test="${empty appointment}">
        <div class="form-section">
            <div class="error-message">
                <h4>Appointment Not Found</h4>
                <p>The requested appointment could not be found. Please check the appointment ID and try again.</p>
            </div>
        </div>
    </c:if>

    <!-- Navigation Buttons -->
    <div class="button-footer">
        <!-- Dynamic back to appointment button based on source -->
        <c:choose>
            <c:when test="${fromSource == 'list'}">
                <a href="${pageContext.request.contextPath}/customer/appointment/detail?id=${appointment.id}&from=list" class="btn back-btn">Back to Appointment</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/customer/appointment/detail?id=${appointment.id}" class="btn back-btn">Back to Appointment</a>
            </c:otherwise>
        </c:choose>
        
        <!-- Dynamic back to main page button based on source -->
        <c:choose>
            <c:when test="${fromSource == 'list'}">
                <a href="${pageContext.request.contextPath}/customer/appointment/list" class="btn calendar-btn">Back to Appointment List</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/customer/appointment/calendar" class="btn calendar-btn">Back to Calendar</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

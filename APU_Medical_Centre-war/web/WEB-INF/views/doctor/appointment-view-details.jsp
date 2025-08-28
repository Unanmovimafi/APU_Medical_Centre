<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>

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
    display: flex;
    gap: 30px;
    max-width: 1400px;
    margin: 0 auto;
  }

  .left-panel {
    flex: 1;
    min-width: 450px;
  }

  .right-panel {
    flex: 1;
    min-width: 450px;
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
    border-collapse: collapse;
  }

  .form-table td {
    padding: 10px 8px;
    vertical-align: top;
  }

  .form-table td:first-child {
    width: 140px;
    font-weight: bold;
    color: #333;
  }

  input[type="text"],
  textarea,
  select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f8f9fa;
    font-size: 14px;
  }

  textarea {
    min-height: 80px;
    resize: vertical;
  }

  .badge {
    padding: 5px 10px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
    display: inline-block;
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
    padding: 10px;
    border-radius: 8px;
    border: 1px solid #ddd;
  }

  .medicine-item {
    display: flex;
    justify-content: space-between;
    padding: 5px 0;
    border-bottom: 1px solid #eee;
  }

  .medicine-item:last-child {
    border-bottom: none;
  }

  .medicine-name {
    font-weight: bold;
    color: #007bff;
  }

  .medicine-quantity {
    color: #666;
    font-size: 12px;
  }

  .history-table {
    width: 100%;
    border-collapse: collapse;
    background: white;
  }

  .history-table th,
  .history-table td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: left;
    vertical-align: top;
  }

  .history-table th {
    background-color: #00bfff;
    color: white;
    font-weight: bold;
    font-size: 14px;
  }

  .history-table td {
    font-size: 13px;
  }

  .no-data {
    text-align: center;
    color: #666;
    font-style: italic;
    padding: 20px;
  }

  .button-footer {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 30px;
    padding: 0 25px;
  }

  .btn {
    padding: 12px 24px;
    border-radius: 8px;
    text-decoration: none;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
    border: none;
    font-size: 14px;
    display: inline-block;
  }

  .back-btn {
    background-color: #6c757d;
    color: white;
  }

  .back-btn:hover {
    background-color: #5a6268;
    color: white;
  }

  .error-message {
    color: #dc3545;
    background-color: #f8d7da;
    border: 1px solid #f5c6cb;
    border-radius: 8px;
    padding: 15px;
    margin: 20px;
    text-align: center;
  }

  @media (max-width: 1024px) {
    .mainbody {
      flex-direction: column;
      padding: 15px;
    }

    .left-panel,
    .right-panel {
      min-width: auto;
    }
  }
</style>

<div class="mainbody">
  <c:if test="${not empty appointment}">
    <!-- Left Panel: Appointment Details and Current Diagnosis -->
    <div class="left-panel">
      <!-- Appointment Information -->
      <div class="form-section">
        <div class="section-title">Appointment Information</div>
        <table class="form-table">
          <tr>
            <td><strong>Patient :</strong></td>
            <td>
              <input
                type="text"
                value="${appointment.customer.name}"
                readonly
              />
            </td>
          </tr>
          <tr>
            <td><strong>Email :</strong></td>
            <td>
              <input
                type="text"
                value="${appointment.customer.email}"
                readonly
              />
            </td>
          </tr>
          <tr>
            <td><strong>Phone :</strong></td>
            <td>
              <input
                type="text"
                value="${appointment.customer.phoneNumber}"
                readonly
              />
            </td>
          </tr>
          <tr>
            <td><strong>Date :</strong></td>
            <td>
              <input
                type="text"
                value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='dd/MM/yyyy'/>"
                readonly
              />
            </td>
          </tr>
          <tr>
            <td><strong>Time :</strong></td>
            <td>
              <input
                type="text"
                value="<fmt:formatDate value='${appointment.appointmentStartDatetime}' pattern='HH:mm'/> - <fmt:formatDate value='${appointment.appointmentEndDatetime}' pattern='HH:mm'/>"
                readonly
              />
            </td>
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
                <c:otherwise>
                  <span class="badge badge-secondary"
                    >${appointment.status}</span
                  >
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <td><strong>Charge :</strong></td>
            <td>
              <input
                type="text"
                value="<c:choose><c:when test='${appointment.charge != null}'>RM <fmt:formatNumber value='${appointment.charge}' pattern='0.00'/></c:when><c:otherwise>-</c:otherwise></c:choose>"
                readonly
              />
            </td>
          </tr>
        </table>
      </div>

      <!-- Diagnosis Details -->
      <div class="form-section">
        <div class="section-title">Diagnosis Details</div>
        <table class="form-table">
          <tr>
            <td><strong>Feedback :</strong></td>
            <td>
              <textarea readonly>
<c:choose><c:when test="${not empty feedback}">${feedback.context}</c:when><c:otherwise>No feedback recorded</c:otherwise></c:choose></textarea
              >
            </td>
          </tr>
          <tr>
            <td><strong>Medicines :</strong></td>
            <td>
              <div class="medicine-list">
                <c:choose>
                  <c:when test="${not empty appointmentMedicines}">
                    <c:forEach
                      var="appointmentMedicine"
                      items="${appointmentMedicines}"
                    >
                      <div class="medicine-item">
                        <div class="medicine-name">
                          ${appointmentMedicine.medicine.name}
                        </div>
                        <div class="medicine-quantity">
                          Quantity: ${appointmentMedicine.quantity}
                        </div>
                      </div>
                    </c:forEach>
                  </c:when>
                  <c:otherwise>
                    <div class="no-data">No medicines prescribed</div>
                  </c:otherwise>
                </c:choose>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <!-- Right Panel: Medical History -->
    <div class="right-panel">
      <div class="form-section">
        <div class="section-title">Patient Medical History</div>

        <c:choose>
          <c:when test="${not empty patientHistory}">
            <table class="history-table">
              <thead>
                <tr>
                  <th>Date & Time</th>
                  <th>Status</th>
                  <th>Feedback</th>
                  <th>Medicine</th>
                  <th>Charge</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="historyApt" items="${patientHistory}">
                  <c:if
                    test="${historyApt.status == 'WAITING PAYMENT' || historyApt.status == 'PAID' || historyApt.status == 'FINISHED'}"
                  >
                    <tr>
                      <td>
                        <fmt:formatDate
                          value="${historyApt.appointmentStartDatetime}"
                          pattern="dd/MM/yyyy HH:mm"
                        />
                      </td>
                      <td>
                        <span
                          class="status-badge status-${fn:toLowerCase(historyApt.status)}"
                          >${historyApt.status}</span
                        >
                      </td>
                      <td>
                        <c:choose>
                          <c:when
                            test="${not empty feedbackMap[historyApt.id]}"
                          >
                            ${feedbackMap[historyApt.id].context}
                          </c:when>
                          <c:otherwise>
                            <em style="color: #999">No feedback</em>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when
                            test="${not empty medicineMap[historyApt.id]}"
                          >
                            <c:forEach
                              var="med"
                              items="${medicineMap[historyApt.id]}"
                              varStatus="status"
                            >
                              ${med.medicine.name} (x${med.quantity})<c:if
                                test="${!status.last}"
                                >,
                              </c:if>
                            </c:forEach>
                          </c:when>
                          <c:otherwise>
                            <em style="color: #999">No medicine</em>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${historyApt.charge != null}">
                            RM
                            <fmt:formatNumber
                              value="${historyApt.charge}"
                              pattern="0.00"
                            />
                          </c:when>
                          <c:otherwise>
                            <em style="color: #999">-</em>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:if>
                </c:forEach>
              </tbody>
            </table>
          </c:when>
          <c:otherwise>
            <div class="no-data">No previous medical history available</div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </c:if>

  <c:if test="${empty appointment}">
    <div class="error-message">
      <h4>Appointment Not Found</h4>
      <p>
        The requested appointment could not be found or you don't have
        permission to view it.
      </p>
    </div>
  </c:if>
</div>

<div class="button-footer">
  <a
    href="${pageContext.request.contextPath}/doctor/appointment/detail?id=${appointment.id}"
    class="btn back-btn"
    >Back to Appointment</a
  >
  <a
    href="${pageContext.request.contextPath}/doctor/appointment/list"
    class="btn back-btn"
    >Back to Calendar</a
  >
</div>

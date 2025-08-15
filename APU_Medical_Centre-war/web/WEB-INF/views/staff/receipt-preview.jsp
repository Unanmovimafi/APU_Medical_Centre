<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receipt - APU Medical Centre</title>
    <style>
        @page {
            size: A4;
            margin: 15mm;
        }

        * {
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            font-size: 14px;
            line-height: 1.4;
            color: #333;
            margin: 0;
            padding: 20px;
            background: white;
        }

        .receipt-container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border: 2px solid #00BFFF;
            border-radius: 10px;
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 3px solid #00BFFF;
            padding-bottom: 20px;
        }

        .header h1 {
            color: #00BFFF;
            font-size: 32px;
            margin: 0;
            font-weight: bold;
        }

        .header h2 {
            color: #666;
            font-size: 18px;
            margin: 5px 0 0 0;
            font-weight: normal;
        }

        .receipt-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 25px;
            flex-wrap: wrap;
        }

        .receipt-info div {
            flex: 1;
            min-width: 250px;
        }

        .receipt-info .right {
            text-align: right;
        }

        .patient-details {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid #00BFFF;
        }

        .patient-details h3 {
            color: #00BFFF;
            margin: 0 0 15px 0;
            font-size: 18px;
        }

        .detail-row {
            display: flex;
            margin-bottom: 8px;
            align-items: flex-start;
        }

        .detail-label {
            font-weight: bold;
            width: 140px;
            color: #555;
        }

        .detail-value {
            flex: 1;
            color: #333;
        }

        .charges-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
            border: 1px solid #ddd;
        }

        .charges-table th,
        .charges-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .charges-table th {
            background-color: #00BFFF;
            color: white;
            font-weight: bold;
        }

        .charges-table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        .charges-table tbody tr:hover {
            background-color: #e3f2fd;
        }

        .quantity {
            text-align: center;
            width: 80px;
        }

        .price {
            text-align: right;
            width: 120px;
            font-family: 'Courier New', monospace;
        }

        .total-section {
            background-color: #f0f8ff;
            padding: 20px;
            border-radius: 8px;
            border: 2px solid #00BFFF;
            margin-bottom: 25px;
        }

        .total-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            font-size: 16px;
        }

        .total-row.grand-total {
            font-weight: bold;
            font-size: 20px;
            color: #00BFFF;
            border-top: 2px solid #00BFFF;
            padding-top: 12px;
            margin-top: 12px;
        }

        .footer {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #00BFFF;
            color: #666;
            font-size: 12px;
        }

        .print-button {
            text-align: center;
            margin-bottom: 20px;
        }

        .print-button button {
            background-color: #00BFFF;
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            margin-right: 10px;
        }

        .print-button button:hover {
            background-color: #00ACC1;
        }

        .close-button {
            background-color: #6c757d !important;
        }

        .close-button:hover {
            background-color: #5a6268 !important;
        }

        /* Print styles */
        @media print {
            body {
                background: white;
                padding: 0;
            }
            
            .print-button {
                display: none;
            }
            
            .receipt-container {
                border: none;
                border-radius: 0;
                box-shadow: none;
                max-width: none;
                padding: 0;
            }
        }

        .diagnosis-section {
            background-color: #fff8e1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #ffb300;
        }

        .diagnosis-section h4 {
            color: #ef6c00;
            margin: 0 0 10px 0;
            font-size: 16px;
        }

        .no-medicines {
            font-style: italic;
            color: #666;
            text-align: center;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="print-button">
        <button onclick="window.print()">Print Receipt</button>
        <button class="close-button" onclick="window.close()">Close Window</button>
    </div>

    <div class="receipt-container">
        <!-- Header -->
        <div class="header">
            <h1>APU Medical Centre</h1>
            <h2>Payment Receipt</h2>
        </div>

        <!-- Receipt Information -->
        <div class="receipt-info">
            <div>
                <strong>Receipt No:</strong> ${receiptNumber}<br>
                <strong>Receipt Date:</strong> <fmt:formatDate value="${receiptDate}" pattern="dd/MM/yyyy HH:mm" />
            </div>
            <div class="right">
                <strong>Appointment ID:</strong> ${appointment.id}<br>
                <strong>Appointment Date:</strong> <fmt:formatDate value="${appointment.appointmentStartDatetime}" pattern="dd/MM/yyyy" />
            </div>
        </div>

        <!-- Patient Details -->
        <div class="patient-details">
            <h3>Patient Information</h3>
            <div class="detail-row">
                <div class="detail-label">Patient Name:</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${not empty appointment.customer}">
                            ${appointment.customer.name}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Email:</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${not empty appointment.customer.email}">
                            ${appointment.customer.email}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Phone:</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${not empty appointment.customer.phoneNumber}">
                            ${appointment.customer.phoneNumber}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Doctor:</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${not empty appointment.doctor}">
                            Dr. ${appointment.doctor.name}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Appointment Time:</div>
                <div class="detail-value">
                    <fmt:formatDate value="${appointment.appointmentStartDatetime}" pattern="HH:mm" /> - 
                    <fmt:formatDate value="${appointment.appointmentEndDatetime}" pattern="HH:mm" />
                </div>
            </div>
        </div>

        <!-- Diagnosis Information -->
        <c:if test="${not empty feedback}">
            <div class="diagnosis-section">
                <h4>Diagnosis & Treatment</h4>
                <p>${feedback.context}</p>
            </div>
        </c:if>

        <!-- Charges Breakdown -->
        <table class="charges-table">
            <thead>
                <tr>
                    <th>Description</th>
                    <th class="quantity">Quantity</th>
                    <th class="price">Unit Price (RM)</th>
                    <th class="price">Total (RM)</th>
                </tr>
            </thead>
            <tbody>
                <!-- Consultation Fee -->
                <tr>
                    <td><strong>Medical Consultation</strong></td>
                    <td class="quantity">1</td>
                    <td class="price"><fmt:formatNumber value="${consultationFee}" pattern="0.00"/></td>
                    <td class="price"><fmt:formatNumber value="${consultationFee}" pattern="0.00"/></td>
                </tr>
                
                <!-- Prescribed Medicines -->
                <c:choose>
                    <c:when test="${not empty appointmentMedicines}">
                        <c:forEach var="appointmentMedicine" items="${appointmentMedicines}">
                            <tr>
                                <td>
                                    <strong>${appointmentMedicine.medicine.name}</strong>
                                    <c:if test="${not empty appointmentMedicine.medicine.description}">
                                        <br><small style="color: #666;">${appointmentMedicine.medicine.description}</small>
                                    </c:if>
                                </td>
                                <td class="quantity">${appointmentMedicine.quantity}</td>
                                <td class="price"><fmt:formatNumber value="${appointmentMedicine.medicine.price}" pattern="0.00"/></td>
                                <td class="price"><fmt:formatNumber value="${appointmentMedicine.medicine.price * appointmentMedicine.quantity}" pattern="0.00"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4" class="no-medicines">No medicines prescribed</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <!-- Total Section -->
        <div class="total-section">
            <div class="total-row">
                <span>Consultation Fee:</span>
                <span>RM <fmt:formatNumber value="${consultationFee}" pattern="0.00"/></span>
            </div>
            <div class="total-row">
                <span>Medicine Total:</span>
                <span>RM <fmt:formatNumber value="${medicineTotal}" pattern="0.00"/></span>
            </div>
            <div class="total-row grand-total">
                <span>TOTAL AMOUNT:</span>
                <span>RM <fmt:formatNumber value="${totalAmount}" pattern="0.00"/></span>
            </div>
        </div>

        <!-- Footer -->
        <div class="footer">
            <p><strong>Payment Status: PAID</strong></p>
            <p>Thank you for choosing APU Medical Centre for your healthcare needs.</p>
            <p>For any queries regarding this receipt, please contact us at info@apumedical.com</p>
            <p><em>This is a computer-generated receipt and does not require a signature.</em></p>
        </div>
    </div>

    <script>
        // Auto-focus for better printing experience
        window.addEventListener('load', function() {
            window.focus();
        });
        
        // Optional: Auto-print when page loads (uncomment if desired)
        // window.addEventListener('load', function() {
        //     setTimeout(function() { window.print(); }, 1000);
        // });
    </script>
</body>
</html>

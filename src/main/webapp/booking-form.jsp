<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Flight - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>Book Your Flight</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <div class="booking-container">
                <!-- Flight Summary -->
                <div class="flight-summary">
                    <h3>Flight Details</h3>
                    <div class="summary-item">
                        <span>Flight:</span>
                        <strong>${flight.airline} ${flight.flightNumber}</strong>
                    </div>
                    <div class="summary-item">
                        <span>From:</span>
                        <strong>${flight.source}</strong>
                    </div>
                    <div class="summary-item">
                        <span>To:</span>
                        <strong>${flight.destination}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Departure:</span>
                        <strong>${flight.departureTime}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Arrival:</span>
                        <strong>${flight.arrivalTime}</strong>
                    </div>
                </div>

                <!-- Booking Form -->
                <div class="booking-form-container">
                    <form action="booking" method="post" class="booking-form">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="flightId" value="${flight.flightId}">

                        <h3>Passenger Information</h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="firstName">First Name *</label>
                                <input type="text" id="firstName" name="firstName" required>
                            </div>
                            <div class="form-group">
                                <label for="lastName">Last Name *</label>
                                <input type="text" id="lastName" name="lastName" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="email">Email *</label>
                                <input type="email" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone *</label>
                                <input type="tel" id="phone" name="phone" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="idType">ID Type *</label>
                                <select id="idType" name="idType" required>
                                    <option value="">Select ID Type</option>
                                    <option value="PASSPORT">Passport</option>
                                    <option value="NATIONAL_ID">National ID</option>
                                    <option value="DRIVER_LICENSE">Driver License</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="idNumber">ID Number *</label>
                                <input type="text" id="idNumber" name="idNumber" required>
                            </div>
                        </div>

                        <h3>Flight Preferences</h3>
                        
                        <div class="form-group">
                            <label for="seatClass">Class *</label>
                            <select id="seatClass" name="seatClass" required onchange="updateFare()">
                                <option value="ECONOMY" selected>Economy - $<fmt:formatNumber value="${flight.economyFare}" pattern="#,##0.00"/></option>
                                <option value="BUSINESS">Business - $<fmt:formatNumber value="${flight.businessFare}" pattern="#,##0.00"/></option>
                            </select>
                        </div>

                        <h3>Payment Information</h3>
                        
                        <div class="form-group">
                            <label for="paymentMethod">Payment Method *</label>
                            <select id="paymentMethod" name="paymentMethod" required>
                                <option value="CREDIT_CARD">Credit Card</option>
                                <option value="DEBIT_CARD">Debit Card</option>
                                <option value="NET_BANKING">Net Banking</option>
                                <option value="UPI">UPI</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="cardNumber">Card Number *</label>
                            <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="expiryDate">Expiry Date *</label>
                                <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" required>
                            </div>
                            <div class="form-group">
                                <label for="cvv">CVV *</label>
                                <input type="text" id="cvv" name="cvv" placeholder="123" maxlength="3" required>
                            </div>
                        </div>

                        <div class="fare-summary">
                            <h3>Total Fare</h3>
                            <p class="total-amount">$<span id="totalFare">${flight.economyFare}</span></p>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary btn-block">Confirm Booking</button>
                            <a href="index.jsp" class="btn btn-secondary btn-block">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />

    <script>
        function updateFare() {
            const seatClass = document.getElementById('seatClass').value;
            const economyFareValue = parseFloat('${flight.economyFare}');
            const businessFareValue = parseFloat('${flight.businessFare}');
            const totalFare = seatClass === 'BUSINESS' ? businessFareValue : economyFareValue;
            document.getElementById('totalFare').textContent = totalFare.toFixed(2);
        }
    </script>
</body>
</html>

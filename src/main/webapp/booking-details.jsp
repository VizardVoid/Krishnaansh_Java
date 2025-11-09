<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Details - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="page-header">
                <h2><i class="icon">🎫</i> Booking Details</h2>
                <p>Complete information about your reservation</p>
            </div>

            <div class="premium-card">
                <div class="booking-header">
                    <h3>Booking ID: ${booking.bookingId}</h3>
                    <span class="booking-status status-${booking.status}">${booking.status}</span>
                </div>

                <div class="booking-info" style="margin-top: 2rem;">
                    <div>
                        <h3 style="color: #667eea; margin-bottom: 1rem;">✈️ Flight Information</h3>
                        <p><strong>Flight Number:</strong> ${flight.flightNumber}</p>
                        <p><strong>Airline:</strong> ${flight.airline}</p>
                        <p><strong>Route:</strong> ${flight.source} → ${flight.destination}</p>
                        <p><strong>Departure:</strong> ${flight.departureTime}</p>
                        <p><strong>Arrival:</strong> ${flight.arrivalTime}</p>
                        <p><strong>Status:</strong> <span class="status-badge status-${flight.status}">${flight.status}</span></p>
                    </div>

                    <div>
                        <h3 style="color: #667eea; margin-bottom: 1rem;">👤 Passenger Details</h3>
                        <p><strong>Name:</strong> ${booking.passengerName}</p>
                        <p><strong>Email:</strong> ${booking.passengerEmail}</p>
                        <p><strong>Phone:</strong> ${booking.passengerPhone}</p>
                        <p><strong>Seat Class:</strong> ${booking.seatClass}</p>
                        <p><strong>Seat Number:</strong> ${booking.seatNumber}</p>
                    </div>

                    <div>
                        <h3 style="color: #667eea; margin-bottom: 1rem;">💰 Payment Information</h3>
                        <p><strong>Fare:</strong> $<fmt:formatNumber value="${booking.fare}" pattern="#,##0.00"/></p>
                        <p><strong>Payment ID:</strong> ${booking.paymentId}</p>
                        <p><strong>Booking Date:</strong> ${booking.bookingTime}</p>
                        <p><strong>Status:</strong> ${booking.status}</p>
                    </div>
                </div>

                <div class="form-actions" style="margin-top: 2rem;">
                    <a href="booking?action=history" class="btn btn-secondary btn-large">
                        <i class="icon">←</i> Back to My Bookings
                    </a>
                    <c:if test="${booking.status == 'CONFIRMED'}">
                        <a href="booking?action=cancel&id=${booking.bookingId}" 
                           class="btn btn-danger btn-large" 
                           onclick="return confirm('Are you sure you want to cancel this booking?');">
                            <i class="icon">✖️</i> Cancel Booking
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>

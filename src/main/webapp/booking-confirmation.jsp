<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="confirmation-container">
                <div class="confirmation-icon">✓</div>
                <h2>Booking Confirmed!</h2>
                <p>Your flight has been successfully booked</p>

                <c:set var="booking" value="${sessionScope.latestBooking}" />
                <c:set var="flight" value="${sessionScope.bookedFlight}" />

                <div class="ticket">
                    <div class="ticket-header">
                        <h3>E-Ticket</h3>
                        <p>Booking ID: <strong>${booking.bookingId}</strong></p>
                    </div>

                    <div class="ticket-body">
                        <div class="ticket-section">
                            <h4>Passenger Details</h4>
                            <p>Name: <strong>${booking.passengerName}</strong></p>
                            <p>Email: <strong>${booking.passengerEmail}</strong></p>
                            <p>Phone: <strong>${booking.passengerPhone}</strong></p>
                        </div>

                        <div class="ticket-section">
                            <h4>Flight Details</h4>
                            <p>Flight: <strong>${flight.airline} ${flight.flightNumber}</strong></p>
                            <p>From: <strong>${flight.source}</strong></p>
                            <p>To: <strong>${flight.destination}</strong></p>
                            <p>Departure: <strong>${flight.departureTime}</strong></p>
                            <p>Arrival: <strong>${flight.arrivalTime}</strong></p>
                        </div>

                        <div class="ticket-section">
                            <h4>Booking Details</h4>
                            <p>Class: <strong>${booking.seatClass}</strong></p>
                            <p>Seat: <strong>${booking.seatNumber}</strong></p>
                            <p>Status: <strong class="status-confirmed">${booking.status}</strong></p>
                        </div>

                        <div class="ticket-section">
                            <h4>Payment Details</h4>
                            <p>Total Fare: <strong>$<fmt:formatNumber value="${booking.fare}" pattern="#,##0.00"/></strong></p>
                            <p>Payment ID: <strong>${booking.paymentId}</strong></p>
                        </div>
                    </div>

                    <div class="ticket-footer">
                        <p class="note">Please arrive at the airport at least 2 hours before departure</p>
                        <p class="note">A confirmation email has been sent to ${booking.passengerEmail}</p>
                    </div>
                </div>

                <div class="confirmation-actions">
                    <button onclick="window.print()" class="btn btn-primary">Print Ticket</button>
                    <a href="index.jsp" class="btn btn-secondary">Book Another Flight</a>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>

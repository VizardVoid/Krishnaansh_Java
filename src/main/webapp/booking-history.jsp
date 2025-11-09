<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>My Booking History</h2>

            <c:choose>
                <c:when test="${empty bookings}">
                    <div class="alert alert-info">
                        <p>You don't have any bookings yet.</p>
                        <a href="index.jsp" class="btn btn-primary">Search Flights</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="bookings-list">
                        <c:forEach items="${bookings}" var="booking">
                            <div class="booking-card">
                                <div class="booking-header">
                                    <h3>Booking ID: ${booking.bookingId}</h3>
                                    <span class="booking-status status-${booking.status}">${booking.status}</span>
                                </div>
                                <div class="booking-details">
                                    <div class="booking-info">
                                        <p><strong>Passenger:</strong> ${booking.passengerName}</p>
                                        <p><strong>Email:</strong> ${booking.passengerEmail}</p>
                                        <p><strong>Phone:</strong> ${booking.passengerPhone}</p>
                                        <p><strong>Seat Class:</strong> ${booking.seatClass}</p>
                                        <p><strong>Seat Number:</strong> ${booking.seatNumber}</p>
                                        <p><strong>Fare:</strong> $<fmt:formatNumber value="${booking.fare}" pattern="#,##0.00"/></p>
                                        <p><strong>Booking Date:</strong> ${booking.bookingTime}</p>
                                    </div>
                                </div>
                                <div class="booking-actions">
                                    <a href="booking?action=view&id=${booking.bookingId}" class="btn btn-primary">View Details</a>
                                    <c:if test="${booking.status == 'CONFIRMED'}">
                                        <a href="booking?action=cancel&id=${booking.bookingId}" 
                                           class="btn btn-danger" 
                                           onclick="return confirm('Are you sure you want to cancel this booking?');">Cancel Booking</a>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>

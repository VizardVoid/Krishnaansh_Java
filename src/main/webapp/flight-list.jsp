<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Flights - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="page-header">
                <h2><i class="icon">✈️</i> All Available Flights</h2>
                <p>Browse and book from our complete flight schedule</p>
            </div>

            <c:choose>
                <c:when test="${empty flights}">
                    <div class="alert alert-info">
                        <i class="icon">ℹ️</i>
                        <p>No flights available at the moment. Please check back later.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="flights-list">
                        <c:forEach items="${flights}" var="flight">
                            <div class="flight-card">
                                <div class="flight-header">
                                    <h3>${flight.flightNumber} - ${flight.airline}</h3>
                                    <span class="flight-status status-${flight.status}">${flight.status}</span>
                                </div>
                                <div class="flight-details">
                                    <div class="flight-route">
                                        <div class="route-point">
                                            <div class="city">${flight.source}</div>
                                            <div class="time">${flight.departureTime.hour}:${flight.departureTime.minute < 10 ? '0' : ''}${flight.departureTime.minute}</div>
                                        </div>
                                        <div class="route-line">→</div>
                                        <div class="route-point">
                                            <div class="city">${flight.destination}</div>
                                            <div class="time">${flight.arrivalTime.hour}:${flight.arrivalTime.minute < 10 ? '0' : ''}${flight.arrivalTime.minute}</div>
                                        </div>
                                    </div>
                                    <div class="flight-info">
                                        <p><strong>Date:</strong> ${flight.departureTime.year}-${flight.departureTime.monthValue < 10 ? '0' : ''}${flight.departureTime.monthValue}-${flight.departureTime.dayOfMonth < 10 ? '0' : ''}${flight.departureTime.dayOfMonth}</p>
                                        <p><strong>Available Seats:</strong> ${flight.availableSeats} / ${flight.totalSeats}</p>
                                        <p><strong>Economy Fare:</strong> $<fmt:formatNumber value="${flight.economyFare}" pattern="#,##0.00"/></p>
                                        <p><strong>Business Fare:</strong> $<fmt:formatNumber value="${flight.businessFare}" pattern="#,##0.00"/></p>
                                    </div>
                                </div>
                                <div class="flight-actions">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == 'ADMIN'}">
                                            <a href="admin?action=edit&id=${flight.flightId}" class="btn btn-primary">
                                                <i class="icon">✏️</i> Edit Flight
                                            </a>
                                            <a href="admin?action=delete&id=${flight.flightId}" 
                                               class="btn btn-danger" 
                                               onclick="return confirm('Are you sure you want to delete this flight?');">
                                                <i class="icon">🗑️</i> Delete Flight
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${flight.availableSeats > 0 && flight.status == 'SCHEDULED'}">
                                                    <a href="booking?action=new&flightId=${flight.flightId}" class="btn btn-primary">
                                                        <i class="icon">🎫</i> Book Now
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="btn btn-secondary" style="opacity: 0.5; cursor: not-allowed;">
                                                        <i class="icon">✖️</i> Not Available
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
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

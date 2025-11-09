<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>Search Results</h2>
            
            <c:choose>
                <c:when test="${showingAll}">
                    <p class="search-info">Showing all available flights</p>
                </c:when>
                <c:when test="${noExactMatch}">
                    <div class="alert alert-info">
                        <p>No exact matches found for <strong>${source}</strong> to <strong>${destination}</strong> on <strong>${date}</strong>.</p>
                        <p>Showing all available flights instead.</p>
                    </div>
                </c:when>
                <c:when test="${not empty source and not empty destination and not empty date}">
                    <p class="search-info">
                        Showing flights from <strong>${source}</strong> to <strong>${destination}</strong> on <strong>${date}</strong>
                    </p>
                </c:when>
            </c:choose>

            <c:choose>
                <c:when test="${empty flights}">
                    <div class="alert alert-info">
                        <p>No flights found matching your criteria. Please try different search parameters.</p>
                    </div>
                    <a href="index.jsp" class="btn btn-primary">Back to Search</a>
                </c:when>
                <c:otherwise>
                    <div class="flights-list">
                        <c:forEach items="${flights}" var="flight">
                            <div class="flight-card">
                                <div class="flight-header">
                                    <h3>${flight.airline} - ${flight.flightNumber}</h3>
                                    <span class="flight-status status-${flight.status}">${flight.status}</span>
                                </div>
                                <div class="flight-details">
                                    <div class="flight-route">
                                        <div class="route-point">
                                            <p class="city">${flight.source}</p>
                                            <p class="time">${flight.departureTime.hour}:${flight.departureTime.minute < 10 ? '0' : ''}${flight.departureTime.minute}</p>
                                        </div>
                                        <div class="route-line">→</div>
                                        <div class="route-point">
                                            <p class="city">${flight.destination}</p>
                                            <p class="time">${flight.arrivalTime.hour}:${flight.arrivalTime.minute < 10 ? '0' : ''}${flight.arrivalTime.minute}</p>
                                        </div>
                                    </div>
                                    <div class="flight-info">
                                        <p>Available Seats: <strong>${flight.availableSeats}</strong></p>
                                        <p>Economy: <strong>$<fmt:formatNumber value="${flight.economyFare}" pattern="#,##0.00"/></strong></p>
                                        <p>Business: <strong>$<fmt:formatNumber value="${flight.businessFare}" pattern="#,##0.00"/></strong></p>
                                    </div>
                                </div>
                                <div class="flight-actions">
                                    <a href="booking?action=new&flightId=${flight.flightId}" class="btn btn-primary">Book Now</a>
                                    <a href="flight?action=view&id=${flight.flightId}" class="btn btn-secondary">View Details</a>
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

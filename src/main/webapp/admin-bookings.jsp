<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Bookings - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>All Bookings</h2>

            <c:choose>
                <c:when test="${empty bookings}">
                    <div class="alert-info">
                        <p>No bookings found in the system.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-container">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Booking ID</th>
                                    <th>Passenger</th>
                                    <th>Seat Class</th>
                                    <th>Seat Number</th>
                                    <th>Fare</th>
                                    <th>Status</th>
                                    <th>Booking Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${bookings}" var="booking">
                                    <tr>
                                        <td><strong>${booking.bookingId}</strong></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty booking.passengerName}">
                                                    ${booking.passengerName}<br>
                                                    <small>${booking.passengerEmail}</small>
                                                </c:when>
                                                <c:otherwise>
                                                    ${booking.passengerId}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${booking.seatClass}</td>
                                        <td>${booking.seatNumber}</td>
                                        <td>$<fmt:formatNumber value="${booking.fare}" pattern="#,##0.00"/></td>
                                        <td><span class="status-badge status-${booking.status}">${booking.status}</span></td>
                                        <td>${booking.bookingTime}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

            <div style="margin-top: 2rem;">
                <a href="admin?action=dashboard" class="btn btn-secondary">← Back to Dashboard</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>

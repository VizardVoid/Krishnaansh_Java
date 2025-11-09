<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head><title>Booking History</title></head>
<body>
    <h1>Booking History</h1>
    <!-- Add code to display and cancel bookings -->
    <c:if test="${param.cancelled eq 'true'}">
        <p>Booking Cancelled Successfully.</p>
    </c:if>
</body>
</html>
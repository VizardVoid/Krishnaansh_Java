<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head><title>Search Flights</title></head>
<body>
    <h1>Search Flights</h1>
    <form action="search" method="get">
        From: <input type="text" name="source" required>
        To: <input type="text" name="destination" required>
        Date: <input type="date" name="date" required>
        <button type="submit">Search</button>
    </form>
    <c:if test="${not empty flights}">
        <h2>Available Flights</h2>
        <table border="1">
            <tr>
                <th>Flight No</th><th>Source</th><th>Destination</th><th>Date</th><th>Time</th><th>Fare</th><th>Seats</th><th>Book</th>
            </tr>
            <c:forEach var="flight" items="${flights}">
                <tr>
                    <td>${flight.flightId}</td>
                    <td>${flight.source}</td>
                    <td>${flight.destination}</td>
                    <td>${flight.date}</td>
                    <td>${flight.time}</td>
                    <td>${flight.fare}</td>
                    <td>${flight.availableSeats}</td>
                    <td>
                        <form action="book" method="post">
                            <input type="hidden" name="flightId" value="${flight.flightId}">
                            <input type="text" name="passengerId" placeholder="Passenger ID" required>
                            <input type="text" name="seatNo" placeholder="Seat No" required>
                            <button type="submit">Book</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
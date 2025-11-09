<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header">
    <div class="container">
        <nav class="navbar">
            <div class="logo">
                <c:choose>
                    <c:when test="${sessionScope.role == 'ADMIN'}">
                        <a href="admin-dashboard.jsp">
                            <h1>✈️ Flight Reservation</h1>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="index.jsp">
                            <h1>✈️ Flight Reservation</h1>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <ul class="nav-menu">
                <c:choose>
                    <c:when test="${sessionScope.role == 'ADMIN'}">
                        <li><a href="admin-dashboard.jsp">Home</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="index.jsp">Home</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="flight?action=list">Flights</a></li>
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                            <li><a href="admin?action=dashboard">Admin Dashboard</a></li>
                        </c:if>
                        <c:if test="${sessionScope.role == 'CUSTOMER'}">
                            <li><a href="booking?action=history">My Bookings</a></li>
                        </c:if>
                        <li><a href="#">Welcome, ${sessionScope.username}</a></li>
                        <li><a href="auth?action=logout" class="btn btn-secondary">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="login.jsp" class="btn btn-primary">Login</a></li>
                        <li><a href="register.jsp" class="btn btn-secondary">Register</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</header>

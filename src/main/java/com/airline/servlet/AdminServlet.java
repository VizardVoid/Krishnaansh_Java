package com.airline.servlet;

import com.airline.dao.*;
import com.airline.model.*;
import com.airline.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servlet for admin operations
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private FlightDAO flightDAO;
    private BookingDAO bookingDAO;
    private PassengerDAO passengerDAO;

    @Override
    public void init() throws ServletException {
        String dataPath = getServletContext().getRealPath("/WEB-INF/data");
        flightDAO = new FlightDAO(dataPath);
        bookingDAO = new BookingDAO(dataPath);
        passengerDAO = new PassengerDAO(dataPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        switch (action != null ? action : "dashboard") {
            case "dashboard":
                showDashboard(request, response);
                break;
            case "flights":
                manageFlights(request, response);
                break;
            case "addFlight":
                showAddFlightForm(request, response);
                break;
            case "editFlight":
                showEditFlightForm(request, response);
                break;
            case "deleteFlight":
                deleteFlight(request, response);
                break;
            case "bookings":
                viewAllBookings(request, response);
                break;
            case "passengers":
                viewAllPassengers(request, response);
                break;
            case "reports":
                viewReports(request, response);
                break;
            default:
                showDashboard(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        switch (action != null ? action : "") {
            case "addFlight":
                addFlight(request, response);
                break;
            case "updateFlight":
                updateFlight(request, response);
                break;
            case "updateStatus":
                updateFlightStatus(request, response);
                break;
            default:
                showDashboard(request, response);
                break;
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("role") != null) {
            return "ADMIN".equals(session.getAttribute("role"));
        }
        return false;
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Flight> flights = flightDAO.getAllFlights();
        List<Booking> bookings = bookingDAO.getAllBookings();
        List<Passenger> passengers = passengerDAO.getAllPassengers();

        request.setAttribute("totalFlights", flights.size());
        request.setAttribute("totalBookings", bookings.size());
        request.setAttribute("totalPassengers", passengers.size());
        
        // Calculate revenue
        double totalRevenue = bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .mapToDouble(Booking::getFare)
            .sum();
        request.setAttribute("totalRevenue", totalRevenue);

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }

    private void manageFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Flight> flights = flightDAO.getAllFlights();
        request.setAttribute("flights", flights);
        request.getRequestDispatcher("admin-flights.jsp").forward(request, response);
    }

    private void showAddFlightForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("admin-add-flight.jsp").forward(request, response);
    }

    private void addFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String flightNumber = request.getParameter("flightNumber");
            String airline = request.getParameter("airline");
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");
            String departureTime = request.getParameter("departureTime");
            String arrivalTime = request.getParameter("arrivalTime");
            int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
            double economyFare = Double.parseDouble(request.getParameter("economyFare"));
            double businessFare = Double.parseDouble(request.getParameter("businessFare"));

            String flightId = IDGenerator.generateFlightId();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            
            Flight flight = new Flight(
                flightId, flightNumber, airline, source, destination,
                LocalDateTime.parse(departureTime, formatter),
                LocalDateTime.parse(arrivalTime, formatter),
                totalSeats, totalSeats, economyFare, businessFare
            );

            if (flightDAO.addFlight(flight)) {
                request.setAttribute("message", "Flight added successfully");
            } else {
                request.setAttribute("error", "Failed to add flight");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error adding flight: " + e.getMessage());
        }

        response.sendRedirect("admin?action=flights");
    }

    private void showEditFlightForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("id");
        Flight flight = flightDAO.getFlightById(flightId);
        
        if (flight != null) {
            request.setAttribute("flight", flight);
            request.getRequestDispatcher("admin-edit-flight.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin?action=flights");
        }
    }

    private void updateFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String flightId = request.getParameter("flightId");
            Flight flight = flightDAO.getFlightById(flightId);
            
            if (flight != null) {
                flight.setFlightNumber(request.getParameter("flightNumber"));
                flight.setAirline(request.getParameter("airline"));
                flight.setSource(request.getParameter("source"));
                flight.setDestination(request.getParameter("destination"));
                
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                flight.setDepartureTime(LocalDateTime.parse(request.getParameter("departureTime"), formatter));
                flight.setArrivalTime(LocalDateTime.parse(request.getParameter("arrivalTime"), formatter));
                
                flight.setTotalSeats(Integer.parseInt(request.getParameter("totalSeats")));
                flight.setEconomyFare(Double.parseDouble(request.getParameter("economyFare")));
                flight.setBusinessFare(Double.parseDouble(request.getParameter("businessFare")));

                flightDAO.updateFlight(flight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("admin?action=flights");
    }

    private void deleteFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("id");
        flightDAO.deleteFlight(flightId);
        response.sendRedirect("admin?action=flights");
    }

    private void updateFlightStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("flightId");
        String status = request.getParameter("status");
        String updateInfo = request.getParameter("updateInfo");

        Flight flight = flightDAO.getFlightById(flightId);
        if (flight != null) {
            flight.setStatus(status);
            flightDAO.updateFlight(flight);

            // Notify all passengers with bookings on this flight
            List<Booking> bookings = bookingDAO.getBookingsByFlightId(flightId);
            for (Booking booking : bookings) {
                if ("CONFIRMED".equals(booking.getStatus())) {
                    Passenger passenger = passengerDAO.getPassengerById(booking.getPassengerId());
                    if (passenger != null) {
                        EmailService.sendScheduleUpdate(passenger.getEmail(), 
                            passenger.getFullName(), flight.getFlightNumber(), updateInfo);
                    }
                }
            }
        }

        response.sendRedirect("admin?action=flights");
    }

    private void viewAllBookings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("admin-bookings.jsp").forward(request, response);
    }

    private void viewAllPassengers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Passenger> passengers = passengerDAO.getAllPassengers();
        request.setAttribute("passengers", passengers);
        request.getRequestDispatcher("admin-passengers.jsp").forward(request, response);
    }

    private void viewReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Booking> allBookings = bookingDAO.getAllBookings();
        List<Flight> allFlights = flightDAO.getAllFlights();
        List<Passenger> allPassengers = passengerDAO.getAllPassengers();
        
        // Calculate statistics
        long totalBookings = allBookings.size();
        long confirmedBookings = allBookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count();
        long cancelledBookings = allBookings.stream().filter(b -> "CANCELLED".equals(b.getStatus())).count();
        
        double totalRevenue = allBookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .mapToDouble(Booking::getFare)
            .sum();
        
        // Flight statistics
        long totalFlights = allFlights.size();
        long activeFlights = allFlights.stream()
            .filter(f -> "SCHEDULED".equals(f.getStatus()))
            .count();
        
        int totalSeats = allFlights.stream().mapToInt(Flight::getTotalSeats).sum();
        int availableSeats = allFlights.stream().mapToInt(Flight::getAvailableSeats).sum();
        int occupancyRate = totalSeats > 0 ? ((totalSeats - availableSeats) * 100 / totalSeats) : 0;
        
        // Revenue analysis
        double economyRevenue = allBookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()) && "ECONOMY".equals(b.getSeatClass()))
            .mapToDouble(Booking::getFare)
            .sum();
        
        double businessRevenue = allBookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()) && "BUSINESS".equals(b.getSeatClass()))
            .mapToDouble(Booking::getFare)
            .sum();
        
        double averageFare = confirmedBookings > 0 ? totalRevenue / confirmedBookings : 0;
        
        // Set attributes
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("confirmedBookings", confirmedBookings);
        request.setAttribute("cancelledBookings", cancelledBookings);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalFlights", totalFlights);
        request.setAttribute("activeFlights", activeFlights);
        request.setAttribute("totalPassengers", allPassengers.size());
        request.setAttribute("occupancyRate", occupancyRate);
        request.setAttribute("economyRevenue", economyRevenue);
        request.setAttribute("businessRevenue", businessRevenue);
        request.setAttribute("averageFare", averageFare);

        request.getRequestDispatcher("admin-reports.jsp").forward(request, response);
    }
}

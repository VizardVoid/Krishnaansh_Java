package com.airline.servlet;

import com.airline.dao.*;
import com.airline.model.*;
import com.airline.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet for handling booking operations
 */
@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private BookingDAO bookingDAO;
    private FlightDAO flightDAO;
    private PassengerDAO passengerDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        String dataPath = getServletContext().getRealPath("/WEB-INF/data");
        bookingDAO = new BookingDAO(dataPath);
        flightDAO = new FlightDAO(dataPath);
        passengerDAO = new PassengerDAO(dataPath);
        paymentDAO = new PaymentDAO(dataPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action != null ? action : "list") {
            case "new":
                showBookingForm(request, response);
                break;
            case "view":
                viewBooking(request, response);
                break;
            case "cancel":
                cancelBooking(request, response);
                break;
            case "list":
                listBookings(request, response);
                break;
            case "history":
                viewBookingHistory(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action != null ? action : "") {
            case "create":
                createBooking(request, response);
                break;
            case "confirm":
                confirmBooking(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    private void showBookingForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("flightId");
        
        if (flightId != null) {
            Flight flight = flightDAO.getFlightById(flightId);
            if (flight != null && flight.getAvailableSeats() > 0) {
                request.setAttribute("flight", flight);
                request.getRequestDispatcher("booking-form.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Flight not available for booking");
                response.sendRedirect("search.jsp");
            }
        } else {
            response.sendRedirect("search.jsp");
        }
    }

    private void createBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get form data
            String flightId = request.getParameter("flightId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String idType = request.getParameter("idType");
            String idNumber = request.getParameter("idNumber");
            String seatClass = request.getParameter("seatClass");
            String paymentMethod = request.getParameter("paymentMethod");
            String cardNumber = request.getParameter("cardNumber");
            String cvv = request.getParameter("cvv");
            String expiryDate = request.getParameter("expiryDate");

            // Validate flight availability
            Flight flight = flightDAO.getFlightById(flightId);
            if (flight == null || flight.getAvailableSeats() <= 0) {
                request.setAttribute("error", "Flight not available");
                request.getRequestDispatcher("search.jsp").forward(request, response);
                return;
            }

            // Create or get passenger
            Passenger passenger = passengerDAO.getPassengerByEmail(email);
            if (passenger == null) {
                String passengerId = IDGenerator.generatePassengerId();
                passenger = new Passenger(passengerId, firstName, lastName, email, phone, idType, idNumber);
                passengerDAO.addPassenger(passenger);
            }

            // Calculate fare
            double fare = "BUSINESS".equals(seatClass) ? flight.getBusinessFare() : flight.getEconomyFare();

            // Process payment
            String transactionId = PaymentGateway.processPayment(paymentMethod, cardNumber, cvv, expiryDate, fare);
            
            if (transactionId == null) {
                request.setAttribute("error", "Payment failed. Please try again.");
                request.setAttribute("flight", flight);
                request.getRequestDispatcher("booking-form.jsp").forward(request, response);
                return;
            }

            // Create payment record
            String paymentId = IDGenerator.generatePaymentId();
            Payment payment = new Payment(paymentId, "", fare, paymentMethod);
            payment.setTransactionId(transactionId);
            payment.setPaymentStatus("SUCCESS");
            payment.setCardNumber(PaymentGateway.maskCardNumber(cardNumber));
            paymentDAO.addPayment(payment);

            // Create booking
            String bookingId = IDGenerator.generateBookingId();
            int seatCount = flight.getTotalSeats() - flight.getAvailableSeats();
            String seatNumber = IDGenerator.generateSeatNumber(seatClass, seatCount);
            
            Booking booking = new Booking(bookingId, passenger.getPassengerId(), flightId, seatClass, seatNumber, fare, paymentId);
            booking.setPassengerName(firstName + " " + lastName);
            booking.setPassengerEmail(email);
            booking.setPassengerPhone(phone);
            bookingDAO.addBooking(booking);

            // Update payment with booking ID
            payment.setBookingId(bookingId);
            paymentDAO.updatePayment(payment);

            // Update flight availability
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightDAO.updateFlight(flight);

            // Send confirmation email
            EmailService.sendBookingConfirmation(email, passenger.getFullName(), bookingId, 
                flight.getFlightNumber(), flight.getDepartureTime().toString(), fare);

            // Store booking in session for confirmation page
            request.getSession().setAttribute("latestBooking", booking);
            request.getSession().setAttribute("bookedFlight", flight);
            
            response.sendRedirect("booking-confirmation.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Booking failed: " + e.getMessage());
            request.getRequestDispatcher("booking-form.jsp").forward(request, response);
        }
    }

    private void confirmBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This method can be used for additional confirmation steps if needed
        response.sendRedirect("booking-confirmation.jsp");
    }

    private void viewBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingId = request.getParameter("id");
        
        if (bookingId != null) {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                Flight flight = flightDAO.getFlightById(booking.getFlightId());
                Passenger passenger = passengerDAO.getPassengerById(booking.getPassengerId());
                
                request.setAttribute("booking", booking);
                request.setAttribute("flight", flight);
                request.setAttribute("passenger", passenger);
                request.getRequestDispatcher("booking-details.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Booking not found");
                response.sendRedirect("index.jsp");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void cancelBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingId = request.getParameter("id");
        
        if (bookingId != null) {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking != null && "CONFIRMED".equals(booking.getStatus())) {
                // Update booking status
                booking.setStatus("CANCELLED");
                bookingDAO.updateBooking(booking);

                // Update flight availability
                Flight flight = flightDAO.getFlightById(booking.getFlightId());
                if (flight != null) {
                    flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                    flightDAO.updateFlight(flight);
                }

                // Process refund
                Payment payment = paymentDAO.getPaymentByBookingId(bookingId);
                if (payment != null && payment.getTransactionId() != null) {
                    PaymentGateway.processRefund(payment.getTransactionId(), payment.getAmount());
                }

                // Send cancellation email
                Passenger passenger = passengerDAO.getPassengerById(booking.getPassengerId());
                if (passenger != null) {
                    EmailService.sendCancellationConfirmation(passenger.getEmail(), 
                        passenger.getFullName(), bookingId, flight.getFlightNumber());
                }

                request.setAttribute("message", "Booking cancelled successfully");
            } else {
                request.setAttribute("error", "Booking cannot be cancelled");
            }
            response.sendRedirect("booking?action=history");
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void listBookings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // For customers, show their bookings; for admins, show all
            // This is a simplified version
            request.getRequestDispatcher("booking-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void viewBookingHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String userEmail = user.getEmail();
            
            // Get all bookings
            List<Booking> allBookings = bookingDAO.getAllBookings();
            List<Booking> userBookings = new ArrayList<>();
            
            // Filter bookings for this user
            for (Booking booking : allBookings) {
                if (booking.getPassengerEmail() != null && booking.getPassengerEmail().equals(userEmail)) {
                    userBookings.add(booking);
                }
            }
            
            request.setAttribute("bookings", userBookings);
            request.getRequestDispatcher("booking-history.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}

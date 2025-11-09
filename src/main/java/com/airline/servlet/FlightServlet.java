package com.airline.servlet;

import com.airline.dao.*;
import com.airline.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servlet for handling flight search and booking operations
 */
@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
    private FlightDAO flightDAO;

    @Override
    public void init() throws ServletException {
        String dataPath = getServletContext().getRealPath("/WEB-INF/data");
        flightDAO = new FlightDAO(dataPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "search";
        }

        switch (action) {
            case "search":
                searchFlights(request, response);
                break;
            case "view":
                viewFlight(request, response);
                break;
            case "list":
                listAllFlights(request, response);
                break;
            default:
                response.sendRedirect("search.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("search.jsp");
            return;
        }

        switch (action) {
            case "search":
                searchFlights(request, response);
                break;
            default:
                response.sendRedirect("search.jsp");
                break;
        }
    }

    private void searchFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String dateStr = request.getParameter("date");

        List<Flight> flights;
        
        // If any search parameter is empty or null, show all available flights
        if (source == null || source.trim().isEmpty() || 
            destination == null || destination.trim().isEmpty() || 
            dateStr == null || dateStr.trim().isEmpty()) {
            flights = flightDAO.getAllAvailableFlights();
            request.setAttribute("showingAll", true);
        } else {
            try {
                LocalDateTime date = LocalDateTime.parse(dateStr + "T00:00:00");
                flights = flightDAO.searchFlights(source, destination, date);
                
                // If no exact matches found, show all available flights
                if (flights.isEmpty()) {
                    flights = flightDAO.getAllAvailableFlights();
                    request.setAttribute("noExactMatch", true);
                }
                
                request.setAttribute("source", source);
                request.setAttribute("destination", destination);
                request.setAttribute("date", dateStr);
            } catch (Exception e) {
                e.printStackTrace();
                flights = flightDAO.getAllAvailableFlights();
                request.setAttribute("error", "Invalid date format. Showing all available flights.");
            }
        }
        
        request.setAttribute("flights", flights);
        request.getRequestDispatcher("search-results.jsp").forward(request, response);
    }

    private void viewFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("id");
        
        if (flightId != null) {
            Flight flight = flightDAO.getFlightById(flightId);
            request.setAttribute("flight", flight);
            request.getRequestDispatcher("flight-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("search.jsp");
        }
    }

    private void listAllFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Flight> flights = flightDAO.getAllFlights();
        request.setAttribute("flights", flights);
        request.getRequestDispatcher("flight-list.jsp").forward(request, response);
    }
}

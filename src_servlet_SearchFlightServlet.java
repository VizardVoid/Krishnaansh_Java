package servlet;

import dao.FlightDAO;
import model.Flight;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SearchFlightServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get flights
        String src = req.getParameter("source");
        String dest = req.getParameter("destination");
        String date = req.getParameter("date");
        List<Flight> flights = FlightDAO.getAllFlights();
        // Filter flights as per search (add your filtering logic here)
        req.setAttribute("flights", flights);
        RequestDispatcher rd = req.getRequestDispatcher("jsp/search.jsp");
        rd.forward(req, resp);
    }
}
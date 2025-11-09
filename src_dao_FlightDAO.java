package dao;

import model.Flight;
import util.XMLUtils;
import java.util.List;

public class FlightDAO {
    private static final String FLIGHT_XML = "data/flights.xml";

    public static List<Flight> getAllFlights() {
        return XMLUtils.readFlights(FLIGHT_XML);
    }
    public static void saveAllFlights(List<Flight> flights) {
        XMLUtils.writeFlights(FLIGHT_XML, flights);
    }
    // Add more functions (add, update, delete) as per need
}
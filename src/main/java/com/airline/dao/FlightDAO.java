package com.airline.dao;

import com.airline.model.Flight;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Flight operations using XML storage
 */
public class FlightDAO {
    private static final String XML_FILE = "flights.xml";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String xmlFilePath;

    public FlightDAO(String dataPath) {
        this.xmlFilePath = dataPath + File.separator + XML_FILE;
        initializeXMLFile();
    }

    private void initializeXMLFile() {
        File file = new File(xmlFilePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("flights");
                doc.appendChild(rootElement);
                saveDocument(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Document loadDocument() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(new File(xmlFilePath));
    }

    private void saveDocument(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    public boolean addFlight(Flight flight) {
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();

            Element flightElement = doc.createElement("flight");
            flightElement.setAttribute("id", flight.getFlightId());

            appendChild(doc, flightElement, "flightNumber", flight.getFlightNumber());
            appendChild(doc, flightElement, "airline", flight.getAirline());
            appendChild(doc, flightElement, "source", flight.getSource());
            appendChild(doc, flightElement, "destination", flight.getDestination());
            appendChild(doc, flightElement, "departureTime", flight.getDepartureTime().format(formatter));
            appendChild(doc, flightElement, "arrivalTime", flight.getArrivalTime().format(formatter));
            appendChild(doc, flightElement, "totalSeats", String.valueOf(flight.getTotalSeats()));
            appendChild(doc, flightElement, "availableSeats", String.valueOf(flight.getAvailableSeats()));
            appendChild(doc, flightElement, "economyFare", String.valueOf(flight.getEconomyFare()));
            appendChild(doc, flightElement, "businessFare", String.valueOf(flight.getBusinessFare()));
            appendChild(doc, flightElement, "status", flight.getStatus());

            root.appendChild(flightElement);
            saveDocument(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Flight getFlightById(String flightId) {
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                if (flightElement.getAttribute("id").equals(flightId)) {
                    return parseFlight(flightElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                flights.add(parseFlight(flightElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }

    public List<Flight> searchFlights(String source, String destination, LocalDateTime date) {
        List<Flight> results = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                Flight flight = parseFlight(flightElement);

                if (flight.getSource().equalsIgnoreCase(source) &&
                    flight.getDestination().equalsIgnoreCase(destination) &&
                    flight.getDepartureTime().toLocalDate().equals(date.toLocalDate()) &&
                    flight.getAvailableSeats() > 0 &&
                    "SCHEDULED".equals(flight.getStatus())) {
                    results.add(flight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Flight> getAllAvailableFlights() {
        List<Flight> results = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                Flight flight = parseFlight(flightElement);

                // Only return flights that are scheduled and have available seats
                if (flight.getAvailableSeats() > 0 && "SCHEDULED".equals(flight.getStatus())) {
                    results.add(flight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public boolean updateFlight(Flight flight) {
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                if (flightElement.getAttribute("id").equals(flight.getFlightId())) {
                    updateElement(flightElement, "flightNumber", flight.getFlightNumber());
                    updateElement(flightElement, "airline", flight.getAirline());
                    updateElement(flightElement, "source", flight.getSource());
                    updateElement(flightElement, "destination", flight.getDestination());
                    updateElement(flightElement, "departureTime", flight.getDepartureTime().format(formatter));
                    updateElement(flightElement, "arrivalTime", flight.getArrivalTime().format(formatter));
                    updateElement(flightElement, "totalSeats", String.valueOf(flight.getTotalSeats()));
                    updateElement(flightElement, "availableSeats", String.valueOf(flight.getAvailableSeats()));
                    updateElement(flightElement, "economyFare", String.valueOf(flight.getEconomyFare()));
                    updateElement(flightElement, "businessFare", String.valueOf(flight.getBusinessFare()));
                    updateElement(flightElement, "status", flight.getStatus());

                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFlight(String flightId) {
        try {
            Document doc = loadDocument();
            NodeList flightList = doc.getElementsByTagName("flight");

            for (int i = 0; i < flightList.getLength(); i++) {
                Element flightElement = (Element) flightList.item(i);
                if (flightElement.getAttribute("id").equals(flightId)) {
                    flightElement.getParentNode().removeChild(flightElement);
                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Flight parseFlight(Element element) {
        Flight flight = new Flight();
        flight.setFlightId(element.getAttribute("id"));
        flight.setFlightNumber(getElementValue(element, "flightNumber"));
        flight.setAirline(getElementValue(element, "airline"));
        flight.setSource(getElementValue(element, "source"));
        flight.setDestination(getElementValue(element, "destination"));
        flight.setDepartureTime(LocalDateTime.parse(getElementValue(element, "departureTime"), formatter));
        flight.setArrivalTime(LocalDateTime.parse(getElementValue(element, "arrivalTime"), formatter));
        flight.setTotalSeats(Integer.parseInt(getElementValue(element, "totalSeats")));
        flight.setAvailableSeats(Integer.parseInt(getElementValue(element, "availableSeats")));
        flight.setEconomyFare(Double.parseDouble(getElementValue(element, "economyFare")));
        flight.setBusinessFare(Double.parseDouble(getElementValue(element, "businessFare")));
        flight.setStatus(getElementValue(element, "status"));
        return flight;
    }

    private void appendChild(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    private void updateElement(Element parent, String tagName, String value) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(value);
        }
    }
}

package com.airline.dao;

import com.airline.model.Booking;
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
 * Data Access Object for Booking operations using XML storage
 */
public class BookingDAO {
    private static final String XML_FILE = "bookings.xml";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String xmlFilePath;

    public BookingDAO(String dataPath) {
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
                Element rootElement = doc.createElement("bookings");
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

    public boolean addBooking(Booking booking) {
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();

            Element bookingElement = doc.createElement("booking");
            bookingElement.setAttribute("id", booking.getBookingId());

            appendChild(doc, bookingElement, "passengerId", booking.getPassengerId());
            appendChild(doc, bookingElement, "flightId", booking.getFlightId());
            appendChild(doc, bookingElement, "seatClass", booking.getSeatClass());
            appendChild(doc, bookingElement, "seatNumber", booking.getSeatNumber() != null ? booking.getSeatNumber() : "");
            appendChild(doc, bookingElement, "fare", String.valueOf(booking.getFare()));
            appendChild(doc, bookingElement, "bookingDate", booking.getBookingDate().format(formatter));
            appendChild(doc, bookingElement, "status", booking.getStatus());
            appendChild(doc, bookingElement, "paymentId", booking.getPaymentId() != null ? booking.getPaymentId() : "");
            appendChild(doc, bookingElement, "passengerName", booking.getPassengerName() != null ? booking.getPassengerName() : "");
            appendChild(doc, bookingElement, "passengerEmail", booking.getPassengerEmail() != null ? booking.getPassengerEmail() : "");
            appendChild(doc, bookingElement, "passengerPhone", booking.getPassengerPhone() != null ? booking.getPassengerPhone() : "");

            root.appendChild(bookingElement);
            saveDocument(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Booking getBookingById(String bookingId) {
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                if (bookingElement.getAttribute("id").equals(bookingId)) {
                    return parseBooking(bookingElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Booking> getBookingsByPassengerId(String passengerId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                if (getElementValue(bookingElement, "passengerId").equals(passengerId)) {
                    bookings.add(parseBooking(bookingElement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getBookingsByFlightId(String flightId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                if (getElementValue(bookingElement, "flightId").equals(flightId)) {
                    bookings.add(parseBooking(bookingElement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                bookings.add(parseBooking(bookingElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean updateBooking(Booking booking) {
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                if (bookingElement.getAttribute("id").equals(booking.getBookingId())) {
                    updateElement(bookingElement, "passengerId", booking.getPassengerId());
                    updateElement(bookingElement, "flightId", booking.getFlightId());
                    updateElement(bookingElement, "seatClass", booking.getSeatClass());
                    updateElement(bookingElement, "seatNumber", booking.getSeatNumber() != null ? booking.getSeatNumber() : "");
                    updateElement(bookingElement, "fare", String.valueOf(booking.getFare()));
                    updateElement(bookingElement, "bookingDate", booking.getBookingDate().format(formatter));
                    updateElement(bookingElement, "status", booking.getStatus());
                    updateElement(bookingElement, "paymentId", booking.getPaymentId() != null ? booking.getPaymentId() : "");
                    updateElement(bookingElement, "passengerName", booking.getPassengerName() != null ? booking.getPassengerName() : "");
                    updateElement(bookingElement, "passengerEmail", booking.getPassengerEmail() != null ? booking.getPassengerEmail() : "");
                    updateElement(bookingElement, "passengerPhone", booking.getPassengerPhone() != null ? booking.getPassengerPhone() : "");

                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBooking(String bookingId) {
        try {
            Document doc = loadDocument();
            NodeList bookingList = doc.getElementsByTagName("booking");

            for (int i = 0; i < bookingList.getLength(); i++) {
                Element bookingElement = (Element) bookingList.item(i);
                if (bookingElement.getAttribute("id").equals(bookingId)) {
                    bookingElement.getParentNode().removeChild(bookingElement);
                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Booking parseBooking(Element element) {
        Booking booking = new Booking();
        booking.setBookingId(element.getAttribute("id"));
        booking.setPassengerId(getElementValue(element, "passengerId"));
        booking.setFlightId(getElementValue(element, "flightId"));
        booking.setSeatClass(getElementValue(element, "seatClass"));
        booking.setSeatNumber(getElementValue(element, "seatNumber"));
        booking.setFare(Double.parseDouble(getElementValue(element, "fare")));
        booking.setBookingDate(LocalDateTime.parse(getElementValue(element, "bookingDate"), formatter));
        booking.setStatus(getElementValue(element, "status"));
        booking.setPaymentId(getElementValue(element, "paymentId"));
        booking.setPassengerName(getElementValue(element, "passengerName"));
        booking.setPassengerEmail(getElementValue(element, "passengerEmail"));
        booking.setPassengerPhone(getElementValue(element, "passengerPhone"));
        return booking;
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

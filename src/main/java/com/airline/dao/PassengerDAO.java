package com.airline.dao;

import com.airline.model.Passenger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Passenger operations using XML storage
 */
public class PassengerDAO {
    private static final String XML_FILE = "passengers.xml";
    private String xmlFilePath;

    public PassengerDAO(String dataPath) {
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
                Element rootElement = doc.createElement("passengers");
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

    public boolean addPassenger(Passenger passenger) {
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();

            Element passengerElement = doc.createElement("passenger");
            passengerElement.setAttribute("id", passenger.getPassengerId());

            appendChild(doc, passengerElement, "firstName", passenger.getFirstName());
            appendChild(doc, passengerElement, "lastName", passenger.getLastName());
            appendChild(doc, passengerElement, "email", passenger.getEmail());
            appendChild(doc, passengerElement, "phone", passenger.getPhone());
            appendChild(doc, passengerElement, "idType", passenger.getIdType());
            appendChild(doc, passengerElement, "idNumber", passenger.getIdNumber());
            appendChild(doc, passengerElement, "dateOfBirth", passenger.getDateOfBirth() != null ? passenger.getDateOfBirth() : "");
            appendChild(doc, passengerElement, "gender", passenger.getGender() != null ? passenger.getGender() : "");
            appendChild(doc, passengerElement, "nationality", passenger.getNationality() != null ? passenger.getNationality() : "");
            appendChild(doc, passengerElement, "preferences", passenger.getPreferences() != null ? passenger.getPreferences() : "");

            root.appendChild(passengerElement);
            saveDocument(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Passenger getPassengerById(String passengerId) {
        try {
            Document doc = loadDocument();
            NodeList passengerList = doc.getElementsByTagName("passenger");

            for (int i = 0; i < passengerList.getLength(); i++) {
                Element passengerElement = (Element) passengerList.item(i);
                if (passengerElement.getAttribute("id").equals(passengerId)) {
                    return parsePassenger(passengerElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Passenger getPassengerByEmail(String email) {
        try {
            Document doc = loadDocument();
            NodeList passengerList = doc.getElementsByTagName("passenger");

            for (int i = 0; i < passengerList.getLength(); i++) {
                Element passengerElement = (Element) passengerList.item(i);
                if (getElementValue(passengerElement, "email").equals(email)) {
                    return parsePassenger(passengerElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList passengerList = doc.getElementsByTagName("passenger");

            for (int i = 0; i < passengerList.getLength(); i++) {
                Element passengerElement = (Element) passengerList.item(i);
                passengers.add(parsePassenger(passengerElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passengers;
    }

    public boolean updatePassenger(Passenger passenger) {
        try {
            Document doc = loadDocument();
            NodeList passengerList = doc.getElementsByTagName("passenger");

            for (int i = 0; i < passengerList.getLength(); i++) {
                Element passengerElement = (Element) passengerList.item(i);
                if (passengerElement.getAttribute("id").equals(passenger.getPassengerId())) {
                    updateElement(passengerElement, "firstName", passenger.getFirstName());
                    updateElement(passengerElement, "lastName", passenger.getLastName());
                    updateElement(passengerElement, "email", passenger.getEmail());
                    updateElement(passengerElement, "phone", passenger.getPhone());
                    updateElement(passengerElement, "idType", passenger.getIdType());
                    updateElement(passengerElement, "idNumber", passenger.getIdNumber());
                    updateElement(passengerElement, "dateOfBirth", passenger.getDateOfBirth() != null ? passenger.getDateOfBirth() : "");
                    updateElement(passengerElement, "gender", passenger.getGender() != null ? passenger.getGender() : "");
                    updateElement(passengerElement, "nationality", passenger.getNationality() != null ? passenger.getNationality() : "");
                    updateElement(passengerElement, "preferences", passenger.getPreferences() != null ? passenger.getPreferences() : "");

                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePassenger(String passengerId) {
        try {
            Document doc = loadDocument();
            NodeList passengerList = doc.getElementsByTagName("passenger");

            for (int i = 0; i < passengerList.getLength(); i++) {
                Element passengerElement = (Element) passengerList.item(i);
                if (passengerElement.getAttribute("id").equals(passengerId)) {
                    passengerElement.getParentNode().removeChild(passengerElement);
                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Passenger parsePassenger(Element element) {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(element.getAttribute("id"));
        passenger.setFirstName(getElementValue(element, "firstName"));
        passenger.setLastName(getElementValue(element, "lastName"));
        passenger.setEmail(getElementValue(element, "email"));
        passenger.setPhone(getElementValue(element, "phone"));
        passenger.setIdType(getElementValue(element, "idType"));
        passenger.setIdNumber(getElementValue(element, "idNumber"));
        passenger.setDateOfBirth(getElementValue(element, "dateOfBirth"));
        passenger.setGender(getElementValue(element, "gender"));
        passenger.setNationality(getElementValue(element, "nationality"));
        passenger.setPreferences(getElementValue(element, "preferences"));
        return passenger;
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

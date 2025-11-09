package com.airline.dao;

import com.airline.model.Payment;
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
 * Data Access Object for Payment operations using XML storage
 */
public class PaymentDAO {
    private static final String XML_FILE = "payments.xml";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String xmlFilePath;

    public PaymentDAO(String dataPath) {
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
                Element rootElement = doc.createElement("payments");
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

    public boolean addPayment(Payment payment) {
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();

            Element paymentElement = doc.createElement("payment");
            paymentElement.setAttribute("id", payment.getPaymentId());

            appendChild(doc, paymentElement, "bookingId", payment.getBookingId());
            appendChild(doc, paymentElement, "amount", String.valueOf(payment.getAmount()));
            appendChild(doc, paymentElement, "paymentMethod", payment.getPaymentMethod());
            appendChild(doc, paymentElement, "paymentStatus", payment.getPaymentStatus());
            appendChild(doc, paymentElement, "paymentDate", payment.getPaymentDate().format(formatter));
            appendChild(doc, paymentElement, "transactionId", payment.getTransactionId() != null ? payment.getTransactionId() : "");
            appendChild(doc, paymentElement, "cardNumber", payment.getCardNumber() != null ? payment.getCardNumber() : "");
            appendChild(doc, paymentElement, "cardHolderName", payment.getCardHolderName() != null ? payment.getCardHolderName() : "");

            root.appendChild(paymentElement);
            saveDocument(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment getPaymentById(String paymentId) {
        try {
            Document doc = loadDocument();
            NodeList paymentList = doc.getElementsByTagName("payment");

            for (int i = 0; i < paymentList.getLength(); i++) {
                Element paymentElement = (Element) paymentList.item(i);
                if (paymentElement.getAttribute("id").equals(paymentId)) {
                    return parsePayment(paymentElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment getPaymentByBookingId(String bookingId) {
        try {
            Document doc = loadDocument();
            NodeList paymentList = doc.getElementsByTagName("payment");

            for (int i = 0; i < paymentList.getLength(); i++) {
                Element paymentElement = (Element) paymentList.item(i);
                if (getElementValue(paymentElement, "bookingId").equals(bookingId)) {
                    return parsePayment(paymentElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList paymentList = doc.getElementsByTagName("payment");

            for (int i = 0; i < paymentList.getLength(); i++) {
                Element paymentElement = (Element) paymentList.item(i);
                payments.add(parsePayment(paymentElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean updatePayment(Payment payment) {
        try {
            Document doc = loadDocument();
            NodeList paymentList = doc.getElementsByTagName("payment");

            for (int i = 0; i < paymentList.getLength(); i++) {
                Element paymentElement = (Element) paymentList.item(i);
                if (paymentElement.getAttribute("id").equals(payment.getPaymentId())) {
                    updateElement(paymentElement, "bookingId", payment.getBookingId());
                    updateElement(paymentElement, "amount", String.valueOf(payment.getAmount()));
                    updateElement(paymentElement, "paymentMethod", payment.getPaymentMethod());
                    updateElement(paymentElement, "paymentStatus", payment.getPaymentStatus());
                    updateElement(paymentElement, "paymentDate", payment.getPaymentDate().format(formatter));
                    updateElement(paymentElement, "transactionId", payment.getTransactionId() != null ? payment.getTransactionId() : "");
                    updateElement(paymentElement, "cardNumber", payment.getCardNumber() != null ? payment.getCardNumber() : "");
                    updateElement(paymentElement, "cardHolderName", payment.getCardHolderName() != null ? payment.getCardHolderName() : "");

                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Payment parsePayment(Element element) {
        Payment payment = new Payment();
        payment.setPaymentId(element.getAttribute("id"));
        payment.setBookingId(getElementValue(element, "bookingId"));
        payment.setAmount(Double.parseDouble(getElementValue(element, "amount")));
        payment.setPaymentMethod(getElementValue(element, "paymentMethod"));
        payment.setPaymentStatus(getElementValue(element, "paymentStatus"));
        payment.setPaymentDate(LocalDateTime.parse(getElementValue(element, "paymentDate"), formatter));
        payment.setTransactionId(getElementValue(element, "transactionId"));
        payment.setCardNumber(getElementValue(element, "cardNumber"));
        payment.setCardHolderName(getElementValue(element, "cardHolderName"));
        return payment;
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

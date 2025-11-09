package com.airline.dao;

import com.airline.model.User;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations using XML storage
 */
public class UserDAO {
    private static final String XML_FILE = "users.xml";
    private String xmlFilePath;

    public UserDAO(String dataPath) {
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
                Element rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
                saveDocument(doc);
                
                // Add default admin user
                User admin = new User("USR001", "admin", "admin123", "admin@airline.com", "System Admin", "ADMIN");
                addUser(admin);
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

    public boolean addUser(User user) {
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();

            Element userElement = doc.createElement("user");
            userElement.setAttribute("id", user.getUserId());

            appendChild(doc, userElement, "username", user.getUsername());
            appendChild(doc, userElement, "password", user.getPassword());
            appendChild(doc, userElement, "email", user.getEmail());
            appendChild(doc, userElement, "fullName", user.getFullName());
            appendChild(doc, userElement, "role", user.getRole());
            appendChild(doc, userElement, "phone", user.getPhone() != null ? user.getPhone() : "");
            appendChild(doc, userElement, "active", String.valueOf(user.isActive()));

            root.appendChild(userElement);
            saveDocument(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(String userId) {
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                if (userElement.getAttribute("id").equals(userId)) {
                    return parseUser(userElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsername(String username) {
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                if (getElementValue(userElement, "username").equals(username)) {
                    return parseUser(userElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User validateUser(String username, String password) {
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                if (getElementValue(userElement, "username").equals(username) &&
                    getElementValue(userElement, "password").equals(password) &&
                    getElementValue(userElement, "active").equals("true")) {
                    return parseUser(userElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                users.add(parseUser(userElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(User user) {
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                if (userElement.getAttribute("id").equals(user.getUserId())) {
                    updateElement(userElement, "username", user.getUsername());
                    updateElement(userElement, "password", user.getPassword());
                    updateElement(userElement, "email", user.getEmail());
                    updateElement(userElement, "fullName", user.getFullName());
                    updateElement(userElement, "role", user.getRole());
                    updateElement(userElement, "phone", user.getPhone() != null ? user.getPhone() : "");
                    updateElement(userElement, "active", String.valueOf(user.isActive()));

                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        try {
            Document doc = loadDocument();
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                if (userElement.getAttribute("id").equals(userId)) {
                    userElement.getParentNode().removeChild(userElement);
                    saveDocument(doc);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private User parseUser(Element element) {
        User user = new User();
        user.setUserId(element.getAttribute("id"));
        user.setUsername(getElementValue(element, "username"));
        user.setPassword(getElementValue(element, "password"));
        user.setEmail(getElementValue(element, "email"));
        user.setFullName(getElementValue(element, "fullName"));
        user.setRole(getElementValue(element, "role"));
        user.setPhone(getElementValue(element, "phone"));
        user.setActive(Boolean.parseBoolean(getElementValue(element, "active")));
        return user;
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

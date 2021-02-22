package com.revature.kingkiller.scapers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.HashMap;

public class ModelScraper {

    private static HashMap<String, String> fieldMap = new HashMap<>();
    private static String fileLocation = "src/main/resources/KingKiller.map.xml";

    public static String getTableName(String className) {
        try {
            String tableName = null;

            File inputFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); //doc has the document object
            doc.getDocumentElement().normalize();
            //System.out.println("[INFO] - ModelScraper.getColumnMap - Root element :" + doc.getDocumentElement().getNodeName());

            //should check through here to make sure we don't have the class declared below current one
            NodeList classList = doc.getElementsByTagName("class");
            //for each class
            for (int i = 0; i < classList.getLength(); i++) {


                Node nNode = classList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nodeName = eElement.getElementsByTagName("name").item(0).getTextContent();
                    System.out.println("[INFO] - ModelScraper.getColumnMap - inner element node name: " + nodeName);
                    System.out.println("[INFO] - ModelScraper.getColumnMap - target class node name: " + className);
                    if (nodeName.equalsIgnoreCase(className)) {
                        //found the class name
                        try {
                            if (eElement.getElementsByTagName("tablename").item(0) != null) {
                                tableName = eElement.getElementsByTagName("tablename").item(0).getTextContent();

                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            //catch exception let run hopefully return null
                        }
                    }
                }
            }
            System.out.println("[INFO] - ModelScraper.getColumnMap -Table Name: " + tableName);
            return tableName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, String> getColumnMap(String className) {
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); //doc has the document object
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            //should check through here to make sure we don't have the class declared below current one
            NodeList classList = doc.getElementsByTagName("class");
            //for each class
            for (int i = 0; i < classList.getLength(); i++) {

                String objFieldName = null;
                String fieldColName = null;
                Node nNode = classList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nodeName = eElement.getElementsByTagName("name").item(0).getTextContent();
                    NodeList objFields = eElement.getElementsByTagName("objfieldname");
                    NodeList dbCols = eElement.getElementsByTagName("colname");
//                    System.out.println("[INFO] - ModelScraper.getColumnMap - obj fields len: " + objFields.getLength());
//                    System.out.println("[INFO] - ModelScraper.getColumnMap - inner element node name: " + nodeName);
//                    System.out.println("[INFO] - ModelScraper.getColumnMap - target class node name: " + className);
                    if (nodeName.equalsIgnoreCase(className)) {
                        for (int fieldCount = 0; fieldCount < objFields.getLength(); fieldCount++) {
                            objFieldName = eElement.getElementsByTagName("objfieldname").item(fieldCount).getTextContent();
                            fieldColName = eElement.getElementsByTagName("colname").item(fieldCount).getTextContent();
                            fieldMap.put(objFieldName, fieldColName);
                        }
                    }
                }
            }
            System.out.println("field map: " + fieldMap.toString());
            return fieldMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

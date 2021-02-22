package com.revature.kingkiller.scapers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

/**
 * the model scraper goes through the kingkiller map file and takes in the object-table relationship information
 * needed by the request generator and dbdao classes
 */
public class ModelScraper {

    private static HashMap<String, String> fieldMap = new HashMap<>();
    private static String fileLocation = "src/main/resources/KingKiller.map.xml";

    /**
     * getTableName does at is suggests and returns a String representing the tablename for the given class
     * as mapped in the map.cfg file
     * @param className the classname of the map the user wants to include in the inspection from the cfg file
     * @return String name of the table that the class corresponds to in the sql database
     * */
    public static String getTableName(String className) {
        try {
            String tableName = null;

            File inputFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); //doc has the document object
            doc.getDocumentElement().normalize();
            ////System.out.println("[INFO] - ModelScraper.getColumnMap - Root element :" + doc.getDocumentElement().getNodeName());

            //should check through here to make sure we don't have the class declared below current one
            NodeList classList = doc.getElementsByTagName("class");
            //for each class
            for (int i = 0; i < classList.getLength(); i++) {


                Node nNode = classList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nodeName = eElement.getElementsByTagName("name").item(0).getTextContent();
                    //System.out.println("[INFO] - ModelScraper.getColumnMap - inner element node name: " + nodeName);
                    //System.out.println("[INFO] - ModelScraper.getColumnMap - target class node name: " + className);
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
            //System.out.println("[INFO] - ModelScraper.getColumnMap -Table Name: " + tableName);
            return tableName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * getColumnMap does at is suggests and returns a hashmap of the defined object fields and the
     * user specified table fields they map to from the map.xml file provided by the user
     * @param className the classname of the map the user wants to include in the inspection from the cfg file
     * @return HashMap of strings that defines the field to column mappings for an object/table
     * */
    public static HashMap<String, String> getColumnMap(String className) {
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); //doc has the document object
            doc.getDocumentElement().normalize();
            ////System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

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
//                    //System.out.println("[INFO] - ModelScraper.getColumnMap - obj fields len: " + objFields.getLength());
//                    //System.out.println("[INFO] - ModelScraper.getColumnMap - inner element node name: " + nodeName);
//                    //System.out.println("[INFO] - ModelScraper.getColumnMap - target class node name: " + className);
                    if (nodeName.equalsIgnoreCase(className)) {
                        for (int fieldCount = 0; fieldCount < objFields.getLength(); fieldCount++) {
                            objFieldName = eElement.getElementsByTagName("objfieldname").item(fieldCount).getTextContent();
                            fieldColName = eElement.getElementsByTagName("colname").item(fieldCount).getTextContent();
                            fieldMap.put(objFieldName, fieldColName);
                        }
                        //break out of loop once map found
                        i+=classList.getLength();
                    }
                }
            }
            //System.out.println("field map: " + fieldMap.toString());
            return fieldMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

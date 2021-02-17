package com.revature.scapers;

import com.revature.util.ColumnField;
import com.revature.util.ConfigData;
import com.revature.util.Metamodel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class ModelScraper {

    private static HashMap<String, String> fieldMap = new HashMap<>();
    private static String fileLocation = "src/main/resources/Employee.map.xml";

    public static HashMap<String, String> getColumnMap(String className) {
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); //doc has the document object
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            //should check through here to make sure we don't have the class declared below current one
            NodeList nameList = doc.getElementsByTagName("name");
            String nameString = nameList.item(0).getTextContent();

            if (nameString.equals(className)) {
                System.out.println("Getting map for class " + nameList.item(0).getTextContent());

                NodeList colList = doc.getElementsByTagName("column");
                System.out.println("----------------------------");

                String objFieldName = null;
                String fieldColName = null;
                //this goes through the nodes contained in the root tag name
                for (int temp = 0; temp < colList.getLength(); temp++) {

                    Node nNode = colList.item(temp);
//                System.out.println("\nCurrent Node :" + nNode.getNodeName()+ " at temp " + temp);
//                System.out.println("\nCurrent NodeType :" + nNode.getNodeType() + " at temp " + temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
//                    System.out.println("Element node name: " + eElement.getNodeName()); //element is column at the moment
//                    System.out.println("Object Field Name : "
//                            + eElement
//                            .getElementsByTagName("objfieldname")
//                            .item(0)
//                            .getTextContent());
                        objFieldName = eElement.getElementsByTagName("objfieldname").item(0).getTextContent();
                        fieldColName = eElement.getElementsByTagName("colname").item(0).getTextContent();
                        //tableFields.add(eElement.getElementsByTagName("objfieldname").item(0).getTextContent());
                        fieldMap.put(objFieldName, fieldColName);
                    }
                }
                System.out.println("field map: " + fieldMap.toString());
                return fieldMap;
            } else {
                System.out.println("failed to find class map");
                //no class map found
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

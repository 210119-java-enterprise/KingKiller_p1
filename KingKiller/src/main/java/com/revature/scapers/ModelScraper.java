package com.revature.scapers;

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
import java.util.ArrayList;
import java.util.List;

public class ModelScraper {

    ArrayList<String> tableFields;
    private Metamodel<Class<?>> modelList;
    private String modelFileLoc;

    public ArrayList<String> ScrapeModels(String fileLocation) {
        this.modelFileLoc = fileLocation;

        ArrayList<>
        newModel = new Metamodel<Class<?>>();
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(cfgPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("dbConfig");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("URL : "
                            + eElement
                            .getElementsByTagName("url")
                            .item(0)
                            .getTextContent());
                    configData.setUrl(eElement.getElementsByTagName("url")
                            .item(0)
                            .getTextContent());
                    System.out.println("Username : "
                            + eElement
                            .getElementsByTagName("username")
                            .item(0)
                            .getTextContent());
                    configData.setUsername(eElement.getElementsByTagName("username")
                            .item(0)
                            .getTextContent());
                    System.out.println("Password : "
                            + eElement
                            .getElementsByTagName("password")
                            .item(0)
                            .getTextContent());
                    configData.setPassword(eElement.getElementsByTagName("password")
                            .item(0)
                            .getTextContent());
                }
            }
            return configData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Metamodel<Class<?>> scrapeModel() {

        ConfigData configData = new ConfigData();
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(cfgPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("dbConfig");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("URL : "
                            + eElement
                            .getElementsByTagName("url")
                            .item(0)
                            .getTextContent());
                    configData.setUrl(eElement.getElementsByTagName("url")
                            .item(0)
                            .getTextContent());
                    System.out.println("Username : "
                            + eElement
                            .getElementsByTagName("username")
                            .item(0)
                            .getTextContent());
                    configData.setUsername(eElement.getElementsByTagName("username")
                            .item(0)
                            .getTextContent());
                    System.out.println("Password : "
                            + eElement
                            .getElementsByTagName("password")
                            .item(0)
                            .getTextContent());
                    configData.setPassword(eElement.getElementsByTagName("password")
                            .item(0)
                            .getTextContent());
                }
            }
            return configData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

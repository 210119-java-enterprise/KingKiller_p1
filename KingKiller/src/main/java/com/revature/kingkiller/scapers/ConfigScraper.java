package com.revature.kingkiller.scapers;

import com.revature.kingkiller.util.ConfigData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Gets configuration information from config file in properties for use during DB
 * connection setup.
 */
public class ConfigScraper {

    /**
     * Scrapes configuration data for the database connection from the config file specified by the user.
     * @param cfgPath
     * @return
     */
    public ConfigData ScrapeConfig(String cfgPath) {
        ConfigData configData = new ConfigData();
        try {
            // cfgPath = src/main/resources/KingKiller.cfg.xml
            File inputFile = new File(cfgPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("dbConfig");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    configData.setUrl(eElement.getElementsByTagName("url")
                                                .item(0)
                                                .getTextContent());
                    configData.setUsername(eElement.getElementsByTagName("username")
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
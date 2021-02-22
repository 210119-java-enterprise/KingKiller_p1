package com.revature.kingkiller;


import com.revature.kingkiller.scapers.ConfigScraper;
import com.revature.kingkiller.util.ConfigData;
import com.revature.kingkiller.util.ConnectionFactory;
import com.revature.kingkiller.util.Metamodel;
import com.revature.kingkiller.util.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;


/**
 * Kind of like the overlord object for the mapper utility. It hands out session managers
 * to prospective users and sets up the configuration and initial metamodel mappings for use throughout
 * the mapping process
 */
public class Mapper {

    private static final Logger logger = LogManager.getLogger(Mapper.class);
    private static ConnectionFactory connectionPool;
    private List<Metamodel<Class<?>>> modelList;

    /**
     * Mapper constructor with a configuration path included
     * @param configPath path to the configuration data for mapper
     */
    public Mapper(String configPath) {
        ConfigScraper configScraper = new ConfigScraper();
        ConfigData appConfig = configScraper.ScrapeConfig(configPath);
        connectionPool = new ConnectionFactory(appConfig); //connections avaliable
        modelList = new LinkedList<>();
    }

    /**
     * Mapper constructor with a configuration and mapping path included
     * @param configPath path to the configuration data for mapper
     * @param mapPath path to the map information for the mapper
     */
    public Mapper(String configPath, String mapPath) {
        ConfigScraper configScraper = new ConfigScraper();
        ConfigData appConfig = configScraper.ScrapeConfig(configPath);
        connectionPool = new ConnectionFactory(appConfig); //connections avaliable
        Map(mapPath);
        modelList = new LinkedList<>();
    }

    /**
     * Map method to map a class to our modellist
     * @param clazz the class we wish to make a model of
     * @return true if no exceptions caught
     */
    public boolean Map(Class clazz) {
        try {
            modelList.add(Metamodel.of(clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Map method to map a class as defined at the file location to our modelList
     * @param mapPath file location of the mapping configuration.
     * @return true if no exceptions caught
     */
    public boolean Map(String mapPath) {
        try {
            Class clazz = Class.forName("Employee");
            modelList.add(Metamodel.of(clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * returns the session manager with a connectionPool assigned to it and modelList that can be
     * mapped to a class for use as a crud mapping interface
     * @return SessionManager - object that hands out sessions to our map users :)
     */
    public SessionManager getSessionManager() {
        return new SessionManager(modelList, connectionPool);
    }


}

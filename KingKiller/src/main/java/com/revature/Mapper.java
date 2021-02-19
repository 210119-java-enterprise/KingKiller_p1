package com.revature;


import com.revature.scapers.ClassInspector;
import com.revature.scapers.ConfigScraper;
import com.revature.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

//Kind of like the session object for the KingKiller app
//has db connection and lets user do mappings to from db
public class Mapper {

    private static final Logger logger = LogManager.getLogger(Mapper.class);
    private static ConnectionFactory connectionPool;
    private List<Metamodel<Class<?>>> modelList;

    public Mapper(String configPath) {
        ConfigScraper configScraper = new ConfigScraper();
        ConfigData appConfig = configScraper.ScrapeConfig(configPath);
        connectionPool = new ConnectionFactory(appConfig); //connections avaliable
        modelList = new LinkedList<>();
    }

    public boolean Map(Class clazz) {
        modelList.add(Metamodel.of(clazz));
        return true;
    }

    public SessionManager getSessionManager() {
        return new SessionManager(modelList, connectionPool);
    }

//    public boolean map(Object o) {
//        ClassInspector.inspectClass(o.getClass());
//        ObjectMapper objMapper = new ObjectMapper(o);
//        objMapper.mapObj();
//        return true;
//    }

}

package com.revature.util;

import java.sql.Connection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session
 * Allows us to save 'cookies' about our session like the username and database connection we are using so
 * we dont have to keep instantiating and connecting when we go from screen to screen
 *
 * @author  Eric Newman
 * @version 1.0
 * @since   2021-01-27
 */
public class Session {
    private final Logger logger = LogManager.getLogger(Session.class);
    private Connection connection;
    private LinkedList<String> modelList;
    /**
     * Session constructor that initializes DB connection and provides access to underlying query services
     * Also retrieves list of available models (those containing an XML mapping) for mapping services
     */
    public Session() {
        this.connection = ConnectionFactory.getInstance().getConnection();
        if (connection == null) {
            logger.error("no DB connection for session");
        }
        this.modelList = getModelList();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setModelList(LinkedList<String> modelList) {
        this.modelList = modelList;
    }

    public LinkedList<String> getModelList() {
        return modelList;
    }
}

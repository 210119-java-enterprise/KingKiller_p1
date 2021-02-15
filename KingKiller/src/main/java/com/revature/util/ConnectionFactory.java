package com.revature.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final Logger logger = LogManager.getLogger(Session.class);

    private static BasicDataSource bds = new BasicDataSource();

    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //STARTS a connection to the database
    public ConnectionFactory(ConfigData configData) {
        try {
            bds.setUrl(configData.getUrl());
            bds.setUsername(configData.getUsername());
            bds.setPassword(configData.getPassword());
            bds.setMinIdle(5);
            bds.setMaxIdle(10);
            bds.setMaxOpenPreparedStatements(100);
            //props.load(new FileReader("src/main/resources/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
            return bds.getConnection();
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            logger.info("Test Connection Successful - Connection Schema: " + conn.getSchema());
            return true;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            return false;
        }
    }

}

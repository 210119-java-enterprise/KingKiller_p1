package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final Logger logger = LogManager.getLogger(Session.class);

    private static ConnectionFactory connFactory = new ConnectionFactory();

    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //STARTS a connection to the database
    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an instance of the connFactory static ConnectionFactory object
     * @return
     */
    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    public Connection getConnection() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("admin-usr"),
                    props.getProperty("admin-pw")
            );

        } catch (SQLException e) {
            logger.debug("Failed getting connection -");
            logger.debug("Debug info: " + e.getMessage());
        }
        return conn;
    }

    public boolean testConnection() {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            logger.info("Test Connection Successful - Connection Schema: " + conn.getSchema());
            return true;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            return false;
        }
    }

}

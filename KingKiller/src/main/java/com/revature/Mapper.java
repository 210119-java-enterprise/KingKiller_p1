package com.revature;


import com.revature.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

    private static final Logger logger = LogManager.getLogger(Mapper.class);

    public Mapper() {
        logger.info("Initializing Mapper");
        //get database connection
        //get valid class names
        //map user object to db if matching class name
    }

    /**
     * testConnection
     * Will log connection info for records and
     */
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

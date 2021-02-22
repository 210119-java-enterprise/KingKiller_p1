package com.revature.kingkiller.util;

import java.sql.Connection;
import java.util.*;

/**
 *  The lesser overlord object used in our mapper that is in charge of handing out sessions and overseeing
 *  the connection pool while doing so. This class allows us to create multiple sessions off of one session pool
 *  so that we don't have to keep establishing connections to the databse over and over.
 */
public class SessionManager {

    private List<Metamodel<Class<?>>> modelList;
    private ConnectionFactory connFactory;
    /**
     * Creates a new session manager with the db info
     * @param modelList the list of models
     */
    public SessionManager(List<Metamodel<Class<?>>> modelList, ConnectionFactory connFactory){
        this.modelList = modelList;
        this.connFactory = connFactory;
    }

    /**
     * Gets a new database session
     * @return a new unique database session
     */
    public Session getSession() {
        Session session = null;
        try {
            session = new Session(connFactory.getConnection(), this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return session;
    }

    /**
     * Gets a new database session
     * @return a new unique database session
     */
    public Connection getConnection() {
        Connection newConn;
        try {
            newConn = connFactory.getConnection();
            return newConn;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all of the models in mapper
     * @return the list of metamodels within the entity manager
     */
    List<Metamodel<Class<?>>> getModels() {
        return modelList;
    }
}

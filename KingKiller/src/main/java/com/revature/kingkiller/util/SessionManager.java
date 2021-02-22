package com.revature.kingkiller.util;

import java.util.*;

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
     * Gets all of the models in mapper
     * @return the list of metamodels within the entity manager
     */
    List<Metamodel<Class<?>>> getModels() {
        return modelList;
    }
}

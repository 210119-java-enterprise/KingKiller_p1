package com.revature.kingkiller.util;

import java.sql.Connection;
import java.util.*;

import com.revature.kingkiller.crudao.DbDao;
import com.revature.kingkiller.service.DbQueryService;

/**
 * Session

 * @author  Eric Newman
 * @version 1.0
 * @since   2021-01-27
 */
public class Session {

    private final SessionManager sessionManager;
    private final DbQueryService dbQueryService;
    private Connection connection;
    private DbDao dbDao;

    /**
     * Creates a new session
     * @param connection the connection to the database
     * @param sessionManager the object in charge of sessions
     */
    Session(Connection connection, SessionManager sessionManager){
        this.sessionManager = sessionManager;
        this.connection = connection;
        dbDao = new DbDao(this.connection);
        dbQueryService = new DbQueryService(dbDao);
    }

    /**
     * creates object passed in the db
     * @param object object being created in db
     */
    public void create(Object object){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("No metamodel was found of class " + object.getClass().getName());
        }
        dbQueryService.create(model, object);
    }


    /**
     * creates object passed in the db
     * @param object object being created in db
     */
    public void createNoId(Object object){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("No metamodel was found of class " + object.getClass().getName());
        }
        dbQueryService.createNoId(model, object);
    }

    /**
     * Will try and update an object already in the database with a new obj
     * @param newObject new object
     * @param oldObject object to be replaced for update
     */
    public void update(Object oldObject, Object newObject){
        if(!oldObject.getClass().getName().equals(newObject.getClass().getName())){
            throw new RuntimeException("Classes of the passed objects are not equal");
        }
        Metamodel<?> model = getModel(newObject);
        if (model == null) {
            System.out.println("Could not generate model for update");
        }
        dbQueryService.update(model, newObject, oldObject);
    }

    /**
     * Will try and delete the object passed from the database
     * @param object the object being deleted from the database
     */
    public void delete(Object object){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("Could not generate model for delete");
        }
        dbQueryService.delete(model, object);
    }

    /**
     * reads everthing from obj
     * @param object the object to pull from
     */
    public List<?> readAll(Object object){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        return dbQueryService.read(model, object);
    }

    /**
     * Reads a certain column from an obj
     * @param object the object to pull from
     * @param columnNames target columns
     */
    public List<?> readCols(Object object, ArrayList<String> columnNames){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        return dbQueryService.readFrom(model, object, columnNames);
    }

    /**
     * Checks for model of class in model list
     * @param object the object being checked
     * @return true if there is a metamodel, false if not
     */
    private Metamodel<?> getModel(Object object){
        for(Metamodel<?> model : sessionManager.getModels()){
            if(object.getClass().getName().equals(model.getClassName())){
                return model;
            }
        }
        return null;
    }

    /**
     * Returns connection for session
     * @return connection or null
     */
    public Connection getConnection(){
        try {
            if (!this.connection.isClosed()) {
                //good connection found in session
                return connection;
            }
            else {
                //attempt to set connection properly in session and return it
                this.connection = ConnectionFactory.getConnection();
                return connection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

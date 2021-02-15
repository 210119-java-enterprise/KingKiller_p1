package com.revature.util;

import java.sql.Connection;
import java.util.*;

import com.revature.Mapper;
import com.revature.crudao.DbDao;
import com.revature.service.DbQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session

 * @author  Eric Newman
 * @version 1.0
 * @since   2021-01-27
 */
public class Session {
    private final SessionManager sessionManager;

    private final DbQueryService dml;

    /**
     * Creates a new session
     * @param connection the connection to the database
     * @param sessionManager the object in charge of sessions
     */
    Session(Connection connection, SessionManager sessionManager){
        this.sessionManager = sessionManager;
        final DbDao dao = new DbDao(connection);

        dml = new DbQueryService(dao);
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
        dml.create(model, object);
    }

    /**
     * Will try and update an object already in the database with a new obj
     * @param newObject new object
     * @param oldObject object to be replaced for update
     */
    public void update(Object newObject, Object oldObject){
        if(!newObject.getClass().getName().equals(oldObject.getClass().getName())){
            throw new RuntimeException("Classes of the passed objects are not equal");
        }
        Metamodel<?> model = getModel(oldObject);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        dml.update(model, newObject, oldObject);
    }

    /**
     * Will try and delete the object passed from the database
     * @param object the object being deleted from the database
     */
    public void delete(Object object){
        Metamodel<?> model = getModel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        dml.delete(model, object);
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
        return dml.read(model, object);
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
        return dml.readFrom(model, object, columnNames);
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
}

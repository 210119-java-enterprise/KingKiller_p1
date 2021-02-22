package com.revature.kingkiller.service;

import com.revature.kingkiller.crudao.DbDao;
import com.revature.kingkiller.util.Metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * DBQueryService that acts as a service handler for the dbdao. It takes incoming CRUD requests and cleans them up
 * before forwarding them to the dao.
 */
public class DbQueryService {

    private DbDao queryDao;

    /**
     * Constructor taking in a database access object
     * @param dao database access object that gives us access to jdbc calls
     */
    public DbQueryService(DbDao dao){
        queryDao = dao;
    }

    /**
     * call for create
     * @param model class type model
     * @param object current class instance
     */
    public void create(Metamodel<?> model, Object object){
        if(object == null){
            throw new RuntimeException("obj null");
        }
        queryDao.create(object);
    }

    /**
     * call for create
     * @param model class type model
     * @param object current class instance
     */
    public void createNoId(Metamodel<?> model, Object object){
        if(object == null){
            throw new RuntimeException("obj null");
        }
        queryDao.createNoId(object);
    }

    /**
     * call for delete
     * @param model class type model
     * @param object current class instance
     */
    public void delete(Metamodel<?> model, Object object){
        if(object == null){
            throw new RuntimeException("obj null");
        }
        queryDao.delete(object);
    }

    /**
     * update sql call
     * @param model class type model
     * @param oldObject obj to be changed
     * @param newObject obj to be createed with changes
     */
    public void update(Metamodel<?> model, Object newObject, Object oldObject){
        if(newObject == null | oldObject == null){
            throw new RuntimeException("Invalid object passed");
        }
        queryDao.update(newObject, oldObject);
    }

    /**
     * read sql statement
     * @param model class type model
     * @param object current class instance
     */
    public List<?> read(Metamodel<?> model, Object object){
        if(object == null){
            throw new RuntimeException("Invalid user, user is null");
        }
        return queryDao.read(model, object);
    }

    /**
     * reads certain columns
     * @param object current class instance
     * @param columnNames columns checking
     */
    public List<?> readFrom(Metamodel<?> model, Object object, ArrayList<String> columnNames){
        if(object == null || columnNames == null){
            throw new RuntimeException("null input");
        }
        return queryDao.read(model, object, columnNames);
    }

    /**
     * reads certain columns
     * @param object current class instance
     * @param columnNames columns checking
     */
    public List<?> findByField(Metamodel<?> model, Object object, ArrayList<String> columnNames){
        if(object == null || columnNames == null){
            throw new RuntimeException("null input");
        }
        return queryDao.findByField(model, object, columnNames);
    }

}

package com.revature.kingkiller.service;

import com.revature.kingkiller.crudao.DbDao;
import com.revature.kingkiller.util.Metamodel;

import java.util.ArrayList;
import java.util.List;

public class DbQueryService {

    private DbDao queryDao;

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
        queryDao.create(model, object);
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
        queryDao.createNoId(model, object);
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
        queryDao.delete(model, object);
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
        queryDao.update(model, newObject, oldObject);
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

}

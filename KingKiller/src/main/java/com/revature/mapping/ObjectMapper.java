package com.revature.mapping;

import com.revature.Mapper;
import com.revature.util.ClassInspector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ObjectMapper {
    private static Logger logger = LogManager.getLogger(Mapper.class);

    private Object userObj;

    public ObjectMapper(Object userObj) {
        this.userObj = userObj;
    }

    public ArrayList<String> getObjectValues(){
        ArrayList<String> objectValues = new ArrayList<>();
        Field[] fields = userObj.getClass().getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            try {
                Object value = f.get(userObj);
                objectValues.add(value.toString());
            } catch (Exception e) {
                logger.debug("error getting obj values" + e.getMessage());
                //e.printStackTrace();
            }
        }
        return objectValues;
    }

    //returns user object classname may be deprecated
    public String getClassName(Object o){
        String objClassName = null;
        objClassName = o.getClass().getName();
        return objClassName;
    }

    public ArrayList<String> getMapObj() {
        ArrayList<String> objValues = new ArrayList<>();
        String tableName = userObj.getClass().getName();
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        System.out.println("TableName: " + tableName);
        objValues.add(tableName);
        objValues.addAll(getObjectValues());
        return objValues;
    }

    public void mapObj() {
        ArrayList<String> objList = getMapObj();
        System.out.println("objlist size: " + objList.size());
        String tableName = objList.get(0);
        ArrayList<String> tableColumns = ClassInspector.getFieldNames(userObj.getClass());
        System.out.println("table columns: " + tableColumns);
        //CHECK IF TABLENAME IS IN XML MAPS
        //GET PROPER DATA TYPES FROM XML FILES TO CHANGE STRINGS TOO
        String sqlStatement = null;
        StringBuilder insertInto = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");
        for(int i = 0; i < objList.size() - 2; i++){
            if(i == 0) {
                insertInto.append(tableName).append(") ");
                values.append(" ? ").append(") ");
            }else {
                insertInto.append(tableColumns.get(i)).append(", ");
                values.append(" ? ").append(", ");
            }
        }
        sqlStatement = insertInto.toString() + values.toString();
        System.out.println(sqlStatement);
    }

}

package com.revature.crudao;

import com.revature.query.RequestGenerator;
import com.revature.scapers.ClassInspector;
import com.revature.scapers.ModelScraper;
import com.revature.util.Metamodel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DbDao {

    private Connection conn;

    public DbDao(Connection conn){
        this.conn = conn;
    }

    /**
     * creates some user specified data in db
     * @param model the model of the class being created
     * @param object the data being persisted
     */
    public void create(Metamodel<?> model, Object object){
        RequestGenerator generator = new RequestGenerator(model, object, "Create");
        ArrayList<Field> objectFields = getObjectFields(object);

        System.out.println("Create Request: " + generator.getRequest());
        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            //THERE NEEDS TO BE SOME CHANGING ON THE VALUES GATHERED TO BETTER REPRESENT THEM IN SQL
            for(int i = 0; i < objectFields.size(); i++){
                Object fieldValue = objectFields.get(i).get(object);
                System.out.print("Inserting field name: " + objectFields.get(i).getName());
                System.out.print(" inserting field type: " + objectFields.get(i).getType());
                System.out.print("Inserting field value: " + fieldValue.toString());
                System.out.println("");

                Class<?> classType = objectFields.get(i).getType();
                System.out.println(classType.getSimpleName());
                objectFields.get(i);
                if (objectFields.get(i).getType().getTypeName().equals("int")) {
                    System.out.println("Found integer type during insertion making cast");
                    pstmt.setObject(i + 1, fieldValue, Types.INTEGER);
                } else {
                    pstmt.setObject(i + 1, fieldValue);
                }

            }
            System.out.println("<---------------------------------------->");
            System.out.println("Prepared create statement: " + pstmt.toString());
            System.out.println("<---------------------------------------->");

            pstmt.executeUpdate();
        }catch(SQLException | IllegalAccessException e){
            e.printStackTrace();
        }
    }



    /**
     * Updates some data into a database
     * @param model the model of the class being created
     * @param newObject the data being updated
     * @param oldObject the data being overwritten
     */
    public void update(Metamodel<?> model, Object newObject, Object oldObject){
        RequestGenerator generator = new RequestGenerator(model, oldObject, "Update");
        ArrayList<Field> oldObjectValues = getObjectFields(oldObject);
        ArrayList<Field> newObjectValues = getObjectFields(newObject);

        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());

            for(int i = 0; i < oldObjectValues.size(); i++){
                pstmt.setObject(i+1, newObjectValues.get(i).getName());
                pstmt.setObject(i+5, oldObjectValues.get(i).getName());
            }

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes some user specified data into a database
     * @param model the model of the class being deleted
     * @param object the data being deleted
     */
    public void delete(Metamodel<?> model, Object object){
        RequestGenerator generator = new RequestGenerator(model, object, "Delete");
        ArrayList<Field> objectValues = getObjectFields(object);
        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());

            for(int i = 0; i < objectValues.size(); i++){
                pstmt.setObject(i + 1, objectValues.get(i));
            }

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * reads all the data from a table
     * @param model the model of the class being read
     */
    public List<?> read(Metamodel<?> model, Object object){
        RequestGenerator generator = new RequestGenerator(model, object, "ReadAll");
        List<Object> listOfObjects = new LinkedList<>();
        System.out.println("Read Request " + generator.getRequest());
        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //System.out.println(rs.toString());
            listOfObjects = mapResultSet(rs, rsmd, object, model);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfObjects;
    }

    /**
     * reads from specific columns
     * @param model the table being readed
     * @param object the object being passed by the user
     * @param columnNames the list of column names specified by the user
     */
    public List<?> read(Metamodel<?> model, Object object, ArrayList<String> columnNames){
        RequestGenerator generator = new RequestGenerator(model, object, "ReadCols");
        List<Object> listOfObjects = new LinkedList<>();
        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            listOfObjects = mapResultSet(rs, rsmd, object, model);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfObjects;
    }

    public ArrayList<Field> getObjectFields(Object userObj){
        ArrayList<Field> objectValues = new ArrayList<>();
        Field[] fields = userObj.getClass().getDeclaredFields();
        //TODO get validmapped columns here to make sure the field should be included
        Map<String, String> columnMap = ModelScraper.getColumnMap(userObj.getClass().getSimpleName());
        System.out.println("Column Map in getFields call of dbdao: " + columnMap.toString());
        for(Field field : fields){
            if (columnMap.containsKey(field.getName())) {


                field.setAccessible(true);
                try {
                    objectValues.add(field);
                } catch (Exception e) {
                    //logger.debug("error getting obj values" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return objectValues;
    }

    /**
     * Maps result to object list
     * @param rs the result set returned from the database
     * @param metaData the result set meta data
     * @param object the object composing the list
     * @return the list of objects returned from the database
     */
    private List<Object> mapResultSet(ResultSet rs, ResultSetMetaData metaData, Object object, Metamodel<?> model){
        List<Object> returnObjects = new LinkedList<>();
        try {
            List<String> resultColumns = new LinkedList<>();
            for(int i = 0; i < metaData.getColumnCount(); i++){
                resultColumns.add(metaData.getColumnName(i+1));
            }
            //System.out.println("Result columns: " + resultColumns.toString());
            while(rs.next()){

                Object returnObject = object.getClass().getConstructor().newInstance();
                for(String resultColumn : resultColumns){
                    System.out.println("_______________RESULT INFO HERE_____________");
                    System.out.println("resultcolumn: " + resultColumn);
                    Class<?> type = model.findColumnType(resultColumn);

                    Object objectValue = rs.getObject(resultColumn);

                    String colName = model.findFieldNameOfColumn(resultColumn); // they are getting a field name from a result sql col
                    //Map<String, String> colMap = ModelScraper.getColumnMap()
                    System.out.println("column name: " + colName);
                    String setMethod = colName.substring(0,1).toUpperCase() + colName.substring(1);
                    System.out.println("method name: " + setMethod);

                    Method method = object.getClass().getMethod("set" + setMethod, type);
                    //System.out.println("Method: " + method.toString());


                    //todo HERES THE ISSUE!!!!
                    //System.out.println("Object value: " + objectValue.toString() + " + object type " + objectValue.getClass().getTypeName());



                    //System.out.println("Method type: " + type.getTypeName());
                    method.invoke(returnObject, objectValue);
                }
                returnObjects.add(returnObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObjects;
    }
}

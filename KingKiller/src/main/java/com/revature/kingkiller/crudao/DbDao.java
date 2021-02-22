package com.revature.kingkiller.crudao;

import com.revature.kingkiller.scapers.ModelScraper;
import com.revature.kingkiller.query.RequestGenerator;
import com.revature.kingkiller.util.ConnectionFactory;
import com.revature.kingkiller.util.Metamodel;

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

        try {
            if (conn.isClosed() == true) {
                conn = ConnectionFactory.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            //THERE NEEDS TO BE SOME CHANGING ON THE VALUES GATHERED TO BETTER REPRESENT THEM IN SQL
            for(int i = 0; i < objectFields.size(); i++){
                Object fieldValue = objectFields.get(i).get(object);
                System.out.print("Inserting field name: " + objectFields.get(i).getName());
                System.out.print(" inserting field type: " + objectFields.get(i).getType());
                System.out.print("Inserting field value: " + fieldValue.toString());
                System.out.println("");

                String fieldClassType = objectFields.get(i).getType().getTypeName();
                System.out.println("Field class type in create statement: " + fieldClassType);

                if (fieldClassType.equals("int")) {
                    System.out.println("Found integer type during insertion - making cast");
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
     * creates some user specified data in db
     * @param model the model of the class being created
     * @param object the data being persisted
     */
    public void createNoId(Metamodel<?> model, Object object){
        RequestGenerator generator = new RequestGenerator(model, object, "CreateNoId");
        ArrayList<Field> objectFields = getObjectFields(object);

        System.out.println("Create Request: " + generator.getRequest());

        try {
            if (conn.isClosed() == true) {
                conn = ConnectionFactory.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            //THERE NEEDS TO BE SOME CHANGING ON THE VALUES GATHERED TO BETTER REPRESENT THEM IN SQL
            for(int i = 1; i < objectFields.size(); i++){
                Object fieldValue = objectFields.get(i).get(object);
                System.out.print("Inserting field name: " + objectFields.get(i).getName());
                System.out.print(" inserting field type: " + objectFields.get(i).getType());
                System.out.print("Inserting field value: " + fieldValue.toString());
                System.out.println("");

                String fieldClassType = objectFields.get(i).getType().getTypeName();
                System.out.println("Field class type in create statement: " + fieldClassType);

                if (fieldClassType.equals("int")) {
                    System.out.println("Found integer type during insertion - making cast");
                    pstmt.setObject(i, fieldValue, Types.INTEGER);
                } else {
                    pstmt.setObject(i, fieldValue);
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
        ArrayList<Field> oldObjectFields = getObjectFields(oldObject);
        ArrayList<Field> newObjectFields = getObjectFields(newObject);

        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());
            for(int i = 0; i < oldObjectFields.size(); i++){
                Object oldFieldValue = oldObjectFields.get(i).get(oldObject);
                System.out.print(" Replacing field name: " + oldObjectFields.get(i).getName());
                System.out.print(" Replacing field type: " + oldObjectFields.get(i).getType());
                System.out.print(" Replacing field value: " + oldFieldValue.toString());
                System.out.println("");

                String oldFieldType = oldObjectFields.get(i).getType().getTypeName();
                System.out.println("Old field class type in create statement: " + oldFieldType);

                if (oldFieldType.equals("int")) {
                    System.out.println("Found integer type in old object during update - making cast");
                    pstmt.setObject(i + 1, oldFieldValue, Types.INTEGER);
                } else {
                    pstmt.setObject(i + 1, oldFieldValue);
                }
            }
            for (int i = 0; i < newObjectFields.size(); i++) {
                Object newFieldValue = newObjectFields.get(i).get(newObject);
                System.out.print(" Name to be set in update: " + newObjectFields.get(i).getName());
                System.out.print(" Field type to be set in updatee: " + newObjectFields.get(i).getType());
                System.out.print(" Field value to be set in update: " + newFieldValue.toString());
                System.out.println("");

                String newFieldType = newObjectFields.get(i).getType().getTypeName();
                System.out.println("New field class type in create statement: " + newFieldType);

                if (newFieldType.equals("int")) {
                    System.out.println("Found integer type in new object during update - making cast");
                    pstmt.setObject(i + 5, newFieldValue, Types.INTEGER);
                } else {
                    pstmt.setObject(i + 5, newFieldValue);
                }
            }
            System.out.println("<---------------------------------------->");
            System.out.println("Prepared update statement: " + pstmt.toString());
            System.out.println("<---------------------------------------->");

            pstmt.executeUpdate();

        }catch(SQLException | IllegalAccessException e){
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
        ArrayList<Field> objectFields = getObjectFields(object);
        try{
            PreparedStatement pstmt = conn.prepareStatement(generator.getRequest());

            for(int i = 0; i < objectFields.size(); i++){

                Object fieldValue = objectFields.get(i).get(object);
                String fieldClassType = objectFields.get(i).getType().getTypeName();
                System.out.println("Field class type in delete statement: " + fieldClassType);

                if (fieldClassType.equals("int")) {
                    System.out.println("Found integer type during deletion - making cast");
                    pstmt.setObject(i + 1, fieldValue, Types.INTEGER);
                } else {
                    pstmt.setObject(i + 1, fieldValue);
                }
            }

            System.out.println("<---------------------------------------->");
            System.out.println("Prepared delete statement: " + pstmt.toString());
            System.out.println("<---------------------------------------->");

            pstmt.executeUpdate();

        }catch(SQLException | IllegalAccessException e){
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
        RequestGenerator generator = new RequestGenerator(model, object, columnNames, "ReadCols");
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
        //System.out.println("<------------------QUERY RESULTS---------------------->");
        List<Object> returnObjects = new LinkedList<>();
        try {
            List<String> resultColumns = new LinkedList<>();
            for(int i = 0; i < metaData.getColumnCount(); i++){
                resultColumns.add(metaData.getColumnName(i+1));
            }
            //System.out.println("Result columns: " + resultColumns.toString());
            while(rs.next()){
                //System.out.println("<------------------NEXT RESULT---------------------->");

                Object returnObject = object.getClass().getConstructor().newInstance();
                for(String resultColumn : resultColumns){
                    System.out.println("<---------------------------------------->");
                    System.out.println("MAPPING RESULT (column): " + resultColumn);
                    System.out.println("<---------------------------------------->");

                    String colName = model.findFieldNameOfColumn(resultColumn);
                    System.out.println("Column Name: " + colName);

                    Class<?> type = model.findColumnType(resultColumn);
                    System.out.println("Col Type: " + type.getSimpleName());

                    Object objectValue = rs.getObject(resultColumn);
                    System.out.println("Result value: " + objectValue.toString());
//                      getting a field name from a result sql col
                    //Map<String, String> colMap = ModelScraper.getColumnMap();

                    String setMethod = colName.substring(0,1).toUpperCase() + colName.substring(1);
                    System.out.println("method name: " + setMethod);

                    Method method = object.getClass().getMethod("set" + setMethod, type);
                    System.out.println("Method: " + method.toString());


                    //todo HERES THE ISSUE!!!!
                    //System.out.println("Object value: " + objectValue.toString() + " + object type " + objectValue.getClass().getTypeName());



//                    System.out.println("Method type: " + type.getTypeName());
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

package com.revature.query;

import com.revature.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RequestGenerator {
    private String requestStatement;
    private String requestType;
    private Object userObj;
    private ArrayList<String> readCols;

    public RequestGenerator(Metamodel<?> model, Object object, String requestType){
        requestStatement = "";
        userObj = object;
        this.requestType = requestType;
    }

    public RequestGenerator(Metamodel<?> model, ArrayList<String> columnNames, String requestType){
        requestStatement = "";
        readCols = columnNames;
        this.requestType = requestType;
    }

    public String getRequest() {
        if (requestType == "Create") {
            return getInsertStatement();
        } else if (requestType == "Update") {
            return getUpdateStatement();
        } else if (requestType == "Delete") {
            return getDeleteStatement();
        } else if (requestType == "ReadCols") {
            return getReadColsStatement();
        } else if (requestType == "ReadAll") {
            return getReadAllStatement();
        }
        return null;
    }

    private String getInsertStatement() {
        String tableName = userObj.getClass().getName();
        ArrayList<String> columns = scrapeColumns(userObj);
        insertStatement(tableName, columns);
        return requestStatement;
    }

    private String getUpdateStatement() {
        String tableName = userObj.getClass().getName();
        ArrayList<String> columns = scrapeColumns(userObj);
        updateStatement(tableName, columns);
        return requestStatement;
    }

    private String getDeleteStatement() {
        String tableName = userObj.getClass().getName();
        ArrayList<String> columns = scrapeColumns(userObj);
        deleteStatement(tableName, columns);
        return requestStatement;
    }

    private String getReadAllStatement() {
        String tableName = userObj.getClass().getName();
        readTable(tableName);
        return requestStatement;
    }

    private String getReadColsStatement() {
        String tableName = userObj.getClass().getName();
        readColumns(tableName, readCols);
        return requestStatement;
    }

    /**
     * Scrapes object for col names
     * @param userObj target objectList<User> firstQuery = (List<User>) session1.selectAll(user1);
     *         firstQuery.forEach(System.out::print);
     */
    private ArrayList<String> scrapeColumns(Object userObj){
        ArrayList<String> tableColumns = new ArrayList<>();
        Field[] fields = userObj.getClass().getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            try {
                Object column = f.getName();
                tableColumns.add(column.toString());
            } catch (Exception e) {
                //logger.debug("error getting obj values" + e.getMessage());
                e.printStackTrace();
            }
        }
        return tableColumns;
    }

    /**
     * Builds the SQL insert statement
     * @param tableName table to insert to
     * @param tableColumns columns in table
     */
    private void insertStatement(String tableName, ArrayList<String> tableColumns) {
        //need to clean table name
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        StringBuilder insertInto = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        for(int i = 0; i < tableColumns.size(); i++){
            if(i == (tableColumns.size()-1)) {
                insertInto.append(tableColumns.get(i)).append(") ");
                values.append(" ? ").append(") ");
            }else {
                insertInto.append(tableColumns.get(i)).append(", ");
                values.append(" ? ").append(", ");
            }
        }
        requestStatement = insertInto.toString() + values.toString();
    }

    private void updateStatement(String tableName, ArrayList<String> tableColumns){
        int bound = tableColumns.size();
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        StringBuilder set = new StringBuilder("SET ");
        StringBuilder where = new StringBuilder("WHERE ");

        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                set.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
            }else {
                set.append(tableColumns.get(i)).append(" = ").append(" ? ").append(", ");
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }
        requestStatement = "UPDATE " + tableName + " " + set.toString() + where.toString();
    }

    private void deleteStatement(String tableName, ArrayList<String> tableColumns){
        int bound = tableColumns.size();
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        StringBuilder where = new StringBuilder("WHERE ");
        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
            }else {
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }
        requestStatement = "DELETE FROM " + tableName + " " + where.toString();
    }

    /**
     * Creates a select * call with a specified table name
     * @param tableName the table to grab all rows and columns from
     */
    private void readTable(String tableName){
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        requestStatement = "SELECT " + " * " + "FROM " + " " + tableName;
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the data inserted into
     * @param tableColumns the array of column names within the table
     */
    private void readColumns(String tableName, ArrayList<String> tableColumns){
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        int bound = tableColumns.size();
        StringBuilder select = new StringBuilder("SELECT ");
        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                select.append(tableColumns.get(i)).append(" ");
            }else {
                select.append(tableColumns.get(i)).append(", ");
            }
        }
        requestStatement = select.toString() + "FROM " + " " + tableName;
    }



}

package com.revature.kingkiller.query;

import com.revature.kingkiller.scapers.ModelScraper;
import com.revature.kingkiller.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

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

    public RequestGenerator(Metamodel<?> model, Object object, ArrayList<String> columnNames, String requestType){
        requestStatement = "";
        userObj = object;
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
        } else if (requestType == "CreateNoId") {
            return getInsertStatementNoId();
        } else if (requestType == "FindByField") {
            return getFindByField();
        }
        return null;
    }

    private String getInsertStatement() {
        String tableName = userObj.getClass().getName();
        ArrayList<String> columns = scrapeColumns(userObj);
        insertStatement(tableName, columns);
        return requestStatement;
    }

    private String getInsertStatementNoId() {
        String tableName = userObj.getClass().getName();
        ArrayList<String> columns = scrapeColumns(userObj);
        insertNoId(tableName, columns);
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

    private String getFindByField() {
        String tableName = userObj.getClass().getName();
        findByField(tableName, readCols);
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
        Map<String, String> colMap = ModelScraper.getColumnMap(userObj.getClass().getSimpleName());
        for(Field field : fields){
            if (colMap.containsKey(field.getName())) {
                tableColumns.add(colMap.get(field.getName()));
            }
        }
        System.out.println("Scraped Columns in RequestGenerator: " + tableColumns.toString());
        return tableColumns;
    }

    /**
     * Builds the SQL insert statement
     * @param className table to insert to
     * @param tableColumns columns in table
     */
    private void insertStatement(String className, ArrayList<String> tableColumns) {
        //need to clean table name
        className = className.substring(className.lastIndexOf('.') + 1).trim();
        String declaredTableName = ModelScraper.getTableName(className);
        StringBuilder insertInto;
        if (declaredTableName == null) {
            insertInto = new StringBuilder("INSERT INTO " + className + " (");
        } else {
            insertInto = new StringBuilder("INSERT INTO " + declaredTableName + " (");
        }
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

    /**
     * Builds the SQL insert statement
     * @param className table to insert to
     * @param tableColumns columns in table
     */
    private void insertNoId(String className, ArrayList<String> tableColumns) {
        //need to clean table name
        className = className.substring(className.lastIndexOf('.') + 1).trim();
        String declaredTableName = ModelScraper.getTableName(className);
        StringBuilder insertInto;
        if (declaredTableName == null) {
            insertInto = new StringBuilder("INSERT INTO " + className + " (");
        } else {
            insertInto = new StringBuilder("INSERT INTO " + declaredTableName + " (");
        }
        StringBuilder values = new StringBuilder("VALUES (");

        for(int i = 1; i < tableColumns.size(); i++){
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
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        StringBuilder where = new StringBuilder("WHERE ");
        for(int i = 0; i < tableColumns.size(); i++){
            if(i == (tableColumns.size() -1)){
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
    private void findByField(String tableName, ArrayList<String> tableColumns){
        System.out.println("[INFO] - RequestGenerator.findByField - table columns coming in: " + tableColumns.toString());

        tableName = tableName.substring(tableName.lastIndexOf('.') + 1).trim();
        readTable(tableName);
        String base = requestStatement;
        System.out.println("[INFO] - RequestGenerator.findByField - base statement: " + base);
        StringBuilder where = new StringBuilder(" WHERE ");
        for(int i = 0; i < tableColumns.size(); i++){
            if(i == (tableColumns.size()-1)){
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
            }else {
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }
        System.out.println("[INFO] - RequestGenerator.findByField - base and where statement to be filled: " + base + where.toString());
        requestStatement = base + where.toString();
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

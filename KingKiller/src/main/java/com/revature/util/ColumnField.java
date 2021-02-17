package com.revature.util;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;
    //private String columnName;

    public ColumnField(){

    }

    public ColumnField(Field field) {
        this.field = field;
    }

    public String getFieldName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    //this is where we put the mapped information from the xml file basically
//    public String getColumnName() {
//        return columnName;
//    }
//    public void setColumnName(String colName) {
//        this.columnName = colName;
//    }

    @Override
    public String toString() {
        return "ColumnField{" +
                "field=" + field +
                //", columnName='" + columnName + '\'' +
                '}';
    }
}

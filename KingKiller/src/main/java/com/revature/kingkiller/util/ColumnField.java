package com.revature.kingkiller.util;

import java.lang.reflect.Field;

/**
 * ColumnField class to represent a column field when doing scraping
 */
public class ColumnField {

    private Field field;

    /**
     * Constructor that takes in a field that we wrap for easy access
     * @param field
     */
    public ColumnField(Field field) {
        this.field = field;
    }

    /**
     * Returns the field name of the field being wrapped
     * @return String fieldname for column field
     */
    public String getFieldName() {
        return field.getName();
    }

    /**
     * Gets the type of the field being wrapped
     * @return a class of the type of the field being wrapped
     */
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public String toString() {
        return "ColumnField{" +
                "field=" + field +
                '}';
    }
}

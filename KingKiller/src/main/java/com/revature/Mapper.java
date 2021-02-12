package com.revature;


import com.revature.mapping.ObjectMapper;
import com.revature.models.Employee;
import com.revature.util.ClassInspector;
import com.revature.util.ConnectionFactory;
import com.revature.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mapper {

    private static final Logger logger = LogManager.getLogger(Mapper.class);
    private Session session;
    public Mapper() {
        logger.info("Initializing Mapper");

        this.session = new Session();
        //get database connection
        //get valid class names
        //map user object to db if matching class name
    }

    public boolean Map(Class<?> clazz) {
        ClassInspector.inspectClass(clazz);
        return true;
    }

    public Session getSession() {
        return this.session;
    }

    public boolean map(Object o) {
        ClassInspector.inspectClass(o.getClass());
        ObjectMapper objMapper = new ObjectMapper(o);
        objMapper.mapObj();
        return true;
    }

}

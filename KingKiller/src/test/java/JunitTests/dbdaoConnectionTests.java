package JunitTests;

import com.revature.kingkiller.Mapper;
import com.revature.kingkiller.crudao.DbDao;
import models.Employee;
import com.revature.kingkiller.util.Metamodel;
import com.revature.kingkiller.util.Session;
import com.revature.kingkiller.util.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class dbdaoConnectionTests {

    private Metamodel<?> model;
    private DbDao dbDao;
    private Mapper mapper;
    private SessionManager sessionManager;
    private Session dbSession;
    private Employee eric;


    @BeforeEach
    public void setUp() {

        mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        sessionManager = mapper.getSessionManager();
        dbSession = sessionManager.getSession();
        mapper.Map(Employee.class); //think this sets metamodel?
        this.eric = new Employee();
        eric.setFirstName("test");
        eric.setId(96);
        eric.setLastName("one");
        eric.setSalary(100.01);

        dbSession.create(eric);

    }

    @Test
    @DisplayName("GetConfigurationDataFromFile")
    public void checkConnections() {
        try {
            assertEquals(false, dbSession.getConnection().isClosed(),
                    "Connection not open in dbSession");
            assertEquals(false, sessionManager.getSession().getConnection().isClosed(),
                    "Session managers new sessions connection is not open");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @AfterEach
    public void tearDown() {
        dbSession.delete(eric);
        //just double check to see that it was actually removed by listing results
        List<Employee> queryRead = (List<Employee>) dbSession.readAll(eric);
        System.out.println("<-----------------LISTING READ QUERY RESULTS--------------------->");
        for (Employee employee : queryRead) {
            System.out.println("<-----" + employee.getFirstName() + "------->");
            System.out.println("lastname: " + employee.getLastName());
            System.out.println("id: " + employee.getId());
            System.out.println("salary: " + employee.getSalary());
        }

    }

}

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTest {
    private Metamodel<?> model;
    private DbDao dbDao;
    private Mapper mapper;
    private SessionManager sessionManager;
    private Session dbSession;
    private Employee eric;


    @BeforeEach
    public void setUp() {
        //set up the mapper flow so that we can make our insert statement work
        mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        sessionManager = mapper.getSessionManager();
        dbSession = sessionManager.getSession();
        mapper.Map(Employee.class); //sets our mapper up with a metamodel

        //create the object we are going to be removing
        this.eric = new Employee();
        eric.setFirstName("test2");
        eric.setId(96);
        eric.setLastName("one");
        eric.setSalary(100.01);
        dbSession.create(eric);
    }

    @Test
    @DisplayName("Check create function")
    public void deleteTest() {
        try {
            ArrayList<String> firstName = new ArrayList<String>();
            firstName.add("firstName");

            dbSession.delete(eric);

            //check to see that what was added is no longer in the list :)
            List<Employee> queryRead = (List<Employee>) dbSession.findByField(eric, firstName);
            assertTrue(queryRead.isEmpty(),
                    "failed to delete items still there");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {

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

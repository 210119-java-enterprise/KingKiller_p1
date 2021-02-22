package JunitTests;

import com.revature.kingkiller.Mapper;
import com.revature.kingkiller.crudao.DbDao;
import com.revature.kingkiller.models.Employee;
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

public class ReadTest {
    private Metamodel<?> model;
    private DbDao dbDao;
    private Mapper mapper;
    private SessionManager sessionManager;
    private Session dbSession;
    private Employee eric;
    private Employee newman;


    @BeforeEach
    public void setUp() {
        //set up the mapper flow so that we can make our insert statement work
        mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        sessionManager = mapper.getSessionManager();
        dbSession = sessionManager.getSession();
        mapper.Map(Employee.class); //sets our mapper up with a metamodel

        //create the object we are going to be replicating
        this.eric = new Employee();
        eric.setFirstName("eric");
        eric.setId(96);
        eric.setLastName("one");
        eric.setSalary(100.01);

        this.newman = new Employee();
        newman.setFirstName("newman");
        newman.setId(13);
        newman.setLastName("jeffrey");
        newman.setSalary(1000);
    }

    @Test
    @DisplayName("Check read by field")
    public void readByFieldTest() {
        try {
            ArrayList<String> firstName = new ArrayList<String>();
            firstName.add("firstName");

            //actual thing we are trying to do - add to the list
            dbSession.create(eric);

            //check to see that what was added is actually in the list as we expected by searching for it :)
            List<Employee> queryRead = (List<Employee>) dbSession.findByField(eric, firstName);
            assertEquals("eric", queryRead.get(0).getFirstName(),
                    "FirstName Not Equal");
            assertEquals(queryRead.get(0).getLastName(), "one",
                    "Lastname Not Equal");
            assertEquals(queryRead.get(0).getId(), 96,
                    "Id Not Equal");
            assertEquals(queryRead.get(0).getSalary(), 100.01,
                    "Salary Not Equal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Read everything test")
    public void readAllTest() {
        try {

            Employee metaObj = new Employee();
            //actual thing we are trying to do - add to the list
            dbSession.create(eric);
            dbSession.create(newman);
            //check to see that what was added is actually in the list as we expected by searching for it :)
            List<Employee> queryRead = (List<Employee>) dbSession.readAll(metaObj);
            assertEquals("eric", queryRead.get(0).getFirstName(),
                    "FirstName Not Equal");
            assertEquals(queryRead.get(0).getLastName(), "one",
                    "Lastname Not Equal");
            assertEquals(queryRead.get(0).getId(), 96,
                    "Id Not Equal");
            assertEquals(queryRead.get(0).getSalary(), 100.01,
                    "Salary Not Equal");

            assertEquals("newman", queryRead.get(1).getFirstName(),
                    "FirstName Not Equal");
            assertEquals(queryRead.get(1).getLastName(), "jeffrey",
                    "Lastname Not Equal");
            assertEquals(queryRead.get(1).getId(), 13,
                    "Id Not Equal");
            assertEquals(queryRead.get(1).getSalary(), 1000,
                    "Salary Not Equal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        //delete the object we created so we don't clutter up the db with every test
        dbSession.delete(eric);
        dbSession.delete(newman);
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

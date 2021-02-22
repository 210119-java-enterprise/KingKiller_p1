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

public class CreateTest {
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

        //create the object we are going to be replicating
        this.eric = new Employee();
        eric.setFirstName("test2");
        eric.setId(96);
        eric.setLastName("one");
        eric.setSalary(100.01);
    }

    @Test
    @DisplayName("Check create function")
    public void createTest() {
        try {
            ArrayList<String> firstName = new ArrayList<String>();
            firstName.add("firstName");

            //actual thing we are trying to do - add to the list
            dbSession.create(eric);

            //check to see that what was added is actually in the list as we expected by searching for it :)
            List<Employee> queryRead = (List<Employee>) dbSession.findByField(eric, firstName);
            assertEquals(queryRead.get(0).getFirstName(), "test2",
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

    @AfterEach
    public void tearDown() {
        //delete the object we created so we don't clutter up the db with every test
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

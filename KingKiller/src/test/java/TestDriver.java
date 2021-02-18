import com.revature.Mapper;
import com.revature.models.Employee;
import com.revature.scapers.ModelScraper;
import com.revature.util.ColumnField;
import com.revature.util.Session;
import com.revature.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestDriver {
    public static void main(String[] args) throws InterruptedException {
        Mapper mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        ModelScraper modelScraper = new ModelScraper();

        //TESTING COL Fields
        HashMap<String, String> tableFields = new HashMap<>();
        tableFields = ModelScraper.getColumnMap("Employee");
        System.out.println(tableFields.toString());

        SessionManager allSessions = mapper.getSessionManager();
        Session mapSession = allSessions.getSession();
        mapper.Map(Employee.class);

        Employee eric = new Employee();
        eric.setFirstName("test");
        eric.setId(1);
        eric.setLastName("one");
        eric.setSalary(100);

        Employee eric2 = new Employee();
        eric2.setFirstName("testupdate");
        eric2.setId(4);
        eric2.setLastName("one");
        eric2.setSalary(200);


        System.out.println("<________________>");
        //mapSession.delete(eric);
        //mapSession.create(eric);
        mapSession.update(eric, eric2);
        List<Employee> queryRead = (List<Employee>) mapSession.readAll(eric);
        System.out.println("<-----------------LISTING READ QUERY RESULTS--------------------->");
        for (Employee employee : queryRead) {
            System.out.println("<-----" + employee.getFirstName() + "------->");
            System.out.println("lastname: " + employee.getLastName());
            System.out.println("id: " + employee.getId());
            System.out.println("salary: " + employee.getSalary());
        }
        //firstQuery.forEach(System.out::print);
    }

}

import com.revature.Mapper;
import com.revature.models.Employee;
import com.revature.scapers.ModelScraper;
import com.revature.util.ColumnField;
import com.revature.util.Session;
import com.revature.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDriver {
    public static void main(String[] args) {
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
        System.out.println("<________________>");
        mapSession.create(eric);
        System.out.println("<________________>");
        List<Employee> firstQuery = (List<Employee>) mapSession.readAll(eric);
        firstQuery.forEach(System.out::print);
    }
}

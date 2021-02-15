import com.revature.Mapper;
import com.revature.models.Employee;
import com.revature.util.Session;
import com.revature.util.SessionManager;

public class TestDriver {
    public static void main(String[] args) {
        Mapper mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
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
    }
}

package JunitTests;

import com.revature.kingkiller.Mapper;
import com.revature.kingkiller.crudao.DbDao;
import com.revature.kingkiller.models.Employee;
import com.revature.kingkiller.scapers.ConfigScraper;
import com.revature.kingkiller.util.ConfigData;
import com.revature.kingkiller.util.Metamodel;
import com.revature.kingkiller.util.Session;
import com.revature.kingkiller.util.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class dbdaoConnectionTests {

    private Metamodel<?> model;
    private DbDao dbDao;
    private Mapper mapper;
    private SessionManager sessionManager;
    private Session dbSession;


    @BeforeEach
    public void setUp() {

        mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        sessionManager = mapper.getSessionManager();
        dbSession = sessionManager.getSession();
        mapper.Map(Employee.class); //think this sets metamodel?
        Employee eric = new Employee();
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

}

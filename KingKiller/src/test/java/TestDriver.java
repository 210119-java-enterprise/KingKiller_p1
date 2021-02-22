import com.revature.kingkiller.Mapper;
import com.revature.kingkiller.models.Employee;
import com.revature.kingkiller.scapers.ModelScraper;
import com.revature.kingkiller.util.Session;
import com.revature.kingkiller.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDriver {
    public static void main(String[] args) throws InterruptedException {
        Mapper mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
        SessionManager allSessions = mapper.getSessionManager();
        Session mapSession = allSessions.getSession();
        mapper.Map(Employee.class);


        //TESTING COL Fields
        ModelScraper modelScraper = new ModelScraper();

        //System.out.println("Class name: " + modelScraper.getClassName("src/main/resources/Employee.map.xml"));
        HashMap<String, String> tableFields = new HashMap<>();
        tableFields = ModelScraper.getColumnMap("AppUser");
        System.out.println(tableFields.toString());
        ModelScraper.getTableName("AppUser");


//
        Employee eric = new Employee();
        eric.setFirstName("test");
        //eric.setId(69);
        eric.setLastName("one");
        eric.setSalary(100.07);

//        Employee eric2 = new Employee();
//        eric2.setFirstName("testupdate");
//        eric2.setId(4);
//        eric2.setLastName("one");
//        eric2.setSalary(200);
//
//
//        //System.out.println("<-----------------RUNNING DELETE--------------------->");
//        //mapSession.delete(eric);
//        //System.out.println("<-----------------RUNNING CREATE--------------------->");
        mapSession.create(eric);
//        //System.out.println("<-----------------RUNNING UPDATE--------------------->");
////        mapSession.update(eric, eric2);
////
////
////        List<Employee> queryRead = (List<Employee>) mapSession.readAll(eric);
////        System.out.println("<-----------------LISTING READ QUERY RESULTS--------------------->");
////        for (Employee employee : queryRead) {
////            System.out.println("<-----" + employee.getFirstName() + "------->");
////            System.out.println("lastname: " + employee.getLastName());
////            System.out.println("id: " + employee.getId());
////            System.out.println("salary: " + employee.getSalary());
////        }
//
//        System.out.println("<-----------------getting specific columns--**NOT WORKING USEFULLY------------------->");
//        ArrayList<String> colList = new ArrayList<>();
//        colList.add("firstName");
//        colList.add("lastName");
//        List<Employee> queryRead = (List<Employee>) mapSession.readCols(eric, colList);
//        System.out.println("<-----------------LISTING READ QUERY RESULTS--------------------->");
//        for (Employee employee : queryRead) {
//            System.out.println("<-----" + employee.getFirstName() + "------->");
//            System.out.println("lastname: " + employee.getLastName());
//            System.out.println("id: " + employee.getId());
//            System.out.println("salary: " + employee.getSalary());
//        }
//


    }

}

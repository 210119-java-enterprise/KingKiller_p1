import com.revature.Mapper;
import com.revature.models.Employee;
import com.revature.util.Session;

public class TestDriver {
    public static void main(String[] args) {
        Mapper mapper = new Mapper();
        Employee eric = new Employee();
        eric.setFirstName("eric");
        eric.setId(1);
        eric.setLastName("newman");
        eric.setSalary(100);
        System.out.println(mapper.map(eric));
        System.out.println();
    }
}

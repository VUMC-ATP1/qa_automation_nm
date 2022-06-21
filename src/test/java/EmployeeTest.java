import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeTest {

    @Test
    public void employeeNameTest() {
        Employee emp = new Employee();
        emp.setName("Nikita");
        Assert.assertEquals(emp.getName(), "Nikita");
    }
}

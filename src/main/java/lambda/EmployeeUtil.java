package lambda;

/**
 * Created by XJX on 2017/4/15.
 */
public class EmployeeUtil {
    public static boolean filterEmployee(Employee employee) {
        return employee.getAge() < 35 && employee.getSalary() > 4000;
    }

    public void test() {
        System.out.println("123456");
    }
}

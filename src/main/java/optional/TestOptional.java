package optional;

import lambda.Employee;
import org.junit.Test;
import stream.Status;

import java.util.Optional;

/**
 * Created by XJX on 2017/4/18.
 * 一、Optional 容器类：用于尽量避免空指针异常
 * Optional.of(T t) : 创建一个 Optional 实例
 * Optional.empty() : 创建一个空的 Optional 实例
 * Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * isPresent() : 判断是否包含值
 * orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class TestOptional {

    @Test
    public void test01() {
        Optional<Employee> employeeOptional = Optional.of(new Employee());
        System.out.println(employeeOptional.get());
    }

    @Test
    public void test02() {
        Optional<Employee> employeeOptional = Optional.ofNullable(new Employee());
        if (employeeOptional.isPresent()) {//判断是否有值
            System.out.println(employeeOptional.get());
        } else {
            Employee employee = employeeOptional.orElse(new Employee("道义", 34, 19234.98, Status.FREE));
            System.out.println(employee);
        }
    }

    @Test
    public void test03() {
        Optional<Employee> employeeOptional = Optional.ofNullable(new Employee("天义", 34, 19234.98, Status.FREE));
        Employee employee = employeeOptional.orElseGet(Employee::new);
        System.out.println(employee);
    }

    @Test
    public void test04() {
        Optional<Employee> employeeOptional = Optional.ofNullable(new Employee("天义", 34, 19234.98, Status.FREE));
        Optional<String> stringOptional = employeeOptional.map(Employee::getName);
        System.out.println(stringOptional.get());

        Optional<Employee> optional = Optional.ofNullable(new Employee("水义", 34, 19234.98, Status.FREE));
        Optional<String> optional1 = optional.flatMap(e -> Optional.of(e.getName()));
        System.out.println(optional1.get());
    }
}

package lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.LongBinaryOperator;

/**
 * Created by XJX on 2017/4/15.
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 箭头操作符将 Lambda 表达式拆分成两部分：
 * <p>
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 * <p>
 * 语法格式一：无参数，无返回值
 * () -> System.out.println("Hello Lambda!");
 * <p>
 * 语法格式二：有一个参数，并且无返回值
 * (x) -> System.out.println(x)
 * <p>
 * 语法格式三：若只有一个参数，小括号可以省略不写
 * x -> System.out.println(x)
 * <p>
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
 * Comparator<Integer> com = (x, y) -> {
 * System.out.println("函数式接口");
 * return Integer.compare(x, y);
 * };
 * <p>
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 * Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * <p>
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * (Integer x, Integer y) -> Integer.compare(x, y);
 * <p>
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * <p>
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 * 可以检查是否是函数式接口
 * <p>
 * <p>
 * <p>
 * Java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T> : 消费型接口
 * void accept(T t);
 * <p>
 * Supplier<T> : 供给型接口
 * T get();
 * <p>
 * Function<T, R> : 函数型接口
 * R apply(T t);
 * <p>
 * Predicate<T> : 断言型接口
 * boolean test(T t);
 */
public class TestLambda {

    private List<Employee> employees = Arrays.asList(new Employee("张三", 36, 8823.94),
            new Employee("李四", 25, 4566.87),
            new Employee("王五", 19, 2333.67),
            new Employee("昭示", 66, 6644.89),
            new Employee("田文久", 45, 10007.89));

    //需求：获取公司中年龄小于 35 的员工信息
    private List<Employee> filterEmployees(List<Employee> employees) {
        List<Employee> newEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() < 35) {
                newEmployees.add(employee);
            }
        }
        return newEmployees;
    }

    private List<Employee> filterEmployeesOptimization(List<Employee> employees) {
        List<Employee> newEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.filter(employee, EmployeeUtil::filterEmployee)) {
                newEmployees.add(employee);
            }
        }
        return newEmployees;
    }

    @Test
    public void test01() {
//        List<Employee> employees = this.filterEmployeesOptimization(this.employees);
//        employees.forEach(System.out::println);

        //需求：获取公司中年龄小于 35薪水大于4000 的员工信息
        employees.stream().filter(EmployeeUtil::filterEmployee).forEach(System.out::println);
        employees.stream().map(Employee::getName).forEach(System.out::println);
    }

    @Test
    public void test02() {
        Comparator<Integer> comparator = (x, y) -> {
            return Integer.compare(x, y);
        };
        new Thread(new EmployeeUtil()::test).start();
        Comparator<Integer> comparator1 = Integer::compare;
    }

    //姓名正序，年龄倒叙
    @Test
    public void test03() {
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        employees.forEach(System.out::println);
    }

    //字符串处理运用
    @Test
    public void test04() {
        String str = "\t\t\t心学四训\t\t\t";
        String string = handlerString(str, s -> s.trim());
        System.out.println(string);
        String string2 = handlerString("abcdefg", s -> s.toUpperCase());
        System.out.println(string2);
    }

    //字符串处理
    private String handlerString(String string, MyFunctionStr str) {
        return str.getValue(string);
    }

    //需求：对于两个 Long 型数据进行处理
    @Test
    public void test05() {
        handlerLong(23, 56, Long::sum);
    }

    //需求：对于两个 Long 型数据进行处理
    private void handlerLong(long o1, long o2, LongBinaryOperator operator) {
        System.out.println(operator.applyAsLong(o1, o2));
    }
}

package stream;

import lambda.Employee;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by XJX on 2017/4/16.
 * 一、Stream API 的操作步骤：
 * <p>
 * 1. 创建 Stream
 * <p>
 * 2. 中间操作
 * <p>
 * 3. 终止操作(终端操作)
 */
public class TestStream {

    //1. 创建 Stream
    @Test
    public void test01() {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> strings = new ArrayList<>();
        Stream<String> stream = strings.stream();

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> employeeStream = Arrays.stream(employees);

        //3. 通过 Stream 类中静态方法 of()
        Stream<String> stringStream = Stream.of("aa", "bb", "cc");

        //4. 创建无限流
        //迭代
        Stream<Integer> integerStream = Stream.iterate(1, x -> x + 2).limit(10);
        integerStream.forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random() * 100).limit(10).forEach(System.out::println);
    }

    //2. 中间操作
    List<Employee> employees = Arrays.asList(
            new Employee("李四", 59, 6666.66),
            new Employee("张三", 18, 9999.99),
            new Employee("王五", 28, 3333.33),
            new Employee("赵六", 8, 7777.77),
            new Employee("赵六", 8, 7777.77),
            new Employee("赵六1", 8, 7777.77),
            new Employee("田七", 38, 5555.55)
    );

    /*
      筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    //内部迭代：迭代操作 Stream API 内部完成
    @Test
    public void test02() {
        //所有的中间操作不会做任何的处理
        Stream<Employee> employeeStream = employees.stream().filter(e -> {
            System.out.println("Stream中间操作");
            return e.getAge() > 35;
        }).limit(1);
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        employeeStream.forEach(System.out::println);
    }

    //distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
    @Test
    public void test03() {
        //所有的中间操作不会做任何的处理
        Stream<Employee> employeeStream = employees.stream().filter(e -> {
            System.out.println("Stream中间操作");
            return e.getAge() < 35;
        }).skip(2);
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        employeeStream.forEach(System.out::println);
    }

    @Test
    public void test04() {
        //所有的中间操作不会做任何的处理
        Stream<Employee> employeeStream = employees.stream().filter(e -> {
            System.out.println("Stream中间操作");
            return e.getAge() < 100;
        }).distinct();
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        employeeStream.forEach(System.out::println);
    }

    //2. 中间操作
    /*
        映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    @Test
    public void test06() {
        employees.stream().map(Employee::getName).forEach(System.out::println);
    }

    @Test
    public void test07() {
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        Stream<Character> characterStream = strList.stream().flatMap(TestStream::filterCharacter);
        characterStream.distinct().forEach(System.out::println);
    }

    private static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /*
        sorted()——自然排序(Comparable)
		sorted(Comparator com)——定制排序
	 */
    @Test
    public void test08() {
        employees.stream().sorted((e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return ((Integer) e1.getAge()).compareTo(e2.getAge());
            }
        }).forEach(System.out::println);
    }

    List<Employee> emps = Arrays.asList(
            new Employee("李四", 59, 6666.66, Status.BUSY),
            new Employee("张三", 18, 9999.99, Status.FREE),
            new Employee("王五", 28, 3333.33, Status.VOCATION),
            new Employee("赵六", 8, 7777.77, Status.BUSY),
            new Employee("赵六", 8, 7777.77, Status.FREE),
            new Employee("赵六", 8, 7777.77, Status.FREE),
            new Employee("田七", 38, 5555.55, Status.BUSY)
    );

    //3. 终止操作
    /*
        allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
    @Test
    public void test09() {
        //匹配全部
        boolean match = emps.stream().allMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println(match);
        //至少匹配一个
        boolean match1 = emps.stream().anyMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println(match1);
        //Returns whether no elements of this stream match the provided predicate.
        boolean match2 = emps.stream().noneMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println(match2);

        Optional<Employee> optional = emps.stream().sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(optional.get());

        Optional<Employee> any = emps.parallelStream().filter(e -> e.getStatus().equals(Status.FREE)).findAny();
        System.out.println(any.get());

    }

    //    max——返回流中最大值
    //    min——返回流中最小值
    @Test
    public void test10() {
        long count = emps.stream().count();
        System.out.println(count);
        Optional<Employee> optional = emps.stream().max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(optional.get());
        Optional<Double> min = emps.stream().map(Employee::getSalary).min(Double::compare);
        System.out.println(min.get());
    }

    //3. 终止操作
    /*
        归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
    @Test
    public void test11() {
        List<Integer> list = Arrays.asList(1, 3, 4, 5, 6, 7, 7, 8, 8, 8);
        Integer integer = list.stream().reduce(0, Integer::sum);
        System.out.println(integer);

        Optional<Double> optional = emps.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(optional.get());
    }

    //collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test12() {
        List<String> list = emps.stream().map(Employee::getName).collect(Collectors.toList());
        list.forEach(System.out::println);

        Set<String> set = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        set.forEach(System.out::println);

        //平均值
        Double aDouble = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(aDouble);
    }

    @Test
    public void testMapReduce() {
        Instant start = Instant.now();
        Integer reduce = emps.parallelStream().map(Employee::getName).flatMap(TestStream::filterCharacter).map(ch -> {
            if (ch.equals('六')) {
                return 1;
            } else {
                return 0;
            }
        }).reduce(0, Integer::sum);
        System.out.println(reduce);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    //分组
    @Test
    public void test13() {
        Map<Status, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);

        //多级分组
        Map<Status, Map<String, List<Employee>>> collect = emps.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
            if (e.getAge() >= 60)
                return "老年";
            else if (e.getAge() >= 35)
                return "中年";
            else
                return "成年";
        })));
        System.out.println(collect);
    }

    //分区
    @Test
    public void test14() {
        Map<Boolean, List<Employee>> map = emps.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000));
        System.out.println(map);
    }
}

package time;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by XJX on 2017/4/19.
 */
public class TestSimpleDateFormat {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //list.add(executorService.submit(()->DateFormatThreadLocal.convert("20161106")));
            list.add(executorService.submit(() -> LocalDate.parse("20161123", DateTimeFormatter.ofPattern("yyyyMMdd"))));
        }

        list.stream().forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Test
    public void test0() {

    }

    @Test
    public void test01() {
        System.out.println(LocalDateTime.now());

        LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 23, 2, 34, 45);
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = localDateTime.plusYears(2);
        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = localDateTime.minusYears(2);
        System.out.println(localDateTime2);

        System.out.println(localDateTime.getDayOfWeek());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test02() {
        Instant now = Instant.now();
        System.out.println(now);

        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(now.toEpochMilli());
    }

    //3.
    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    @Test
    public void test03() {
        System.out.println(Duration.between(LocalTime.now(), LocalTime.of(23, 0, 0)).toMillis());//纳秒间隔

        System.out.println(Period.between(LocalDate.of(2015, 1, 1), LocalDate.now()).getYears());//月和日的计算只考虑本单位，不累计高位

    }

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test04() {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);

        LocalDateTime dateTime1 = dateTime.withDayOfMonth(10);
        System.out.println(dateTime1);

        LocalDateTime dateTime2 = dateTime.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(dateTime2);

        //自定义：下一个工作日
        LocalDateTime dateTime3 = dateTime.with((temporal) -> {
            LocalDateTime dateTime4 = (LocalDateTime) temporal;
            DayOfWeek dayOfWeek = dateTime4.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return dateTime4.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return dateTime4.plusDays(2);
            } else {
                return dateTime4.plusDays(1);
            }
        });
        System.out.println("下一个工作日：" + dateTime3);
    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test05() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDateTime dateTime = LocalDateTime.now();

        String format = dateTime.format(timeFormatter);
        System.out.println(format);

        System.out.println("----------------------------------------------");
        DateTimeFormatter timeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String format1 = dateTime.format(timeFormatter1);
        System.out.println(format1);

        LocalDateTime localDateTime = dateTime.parse(format1, timeFormatter1);
        System.out.println(localDateTime);
    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test06() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(localDateTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String format = localDateTime.format(dateTimeFormatter);
        System.out.println(format);
    }


}

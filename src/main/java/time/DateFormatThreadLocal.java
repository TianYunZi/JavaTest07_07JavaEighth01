package time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XJX on 2017/4/19.
 */
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static final Date convert(String source) throws ParseException {
        return dateFormat.get().parse(source);
    }
}

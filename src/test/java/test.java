import com.kmak.utils.DateTimeUtils;

import java.util.Date;

/**
 * Created by Leaf.Ye on 2017/6/13.
 */
public class test {
    public static void main(String [] arg ){
        /*long day = DateTimeUtils.getBetweenDays("2017-02-01","2017-02-02");
        System.out.println(day);*/

        Date date = DateTimeUtils.string2Date("2017-06-24","yyyy-MM-dd");
        date = DateTimeUtils.getWeekFirstDaySun(date);
        System.out.println(DateTimeUtils.date2String(date,"yyyy-MM-dd"));

    }
}

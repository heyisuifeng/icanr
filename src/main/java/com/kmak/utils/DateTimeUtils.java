package com.kmak.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Leaf.Ye on 2017/6/23.
 */
@Slf4j
public class DateTimeUtils {

    //由出生日期获得年龄  n岁
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    //由出生日期获得年龄  n岁n月
    public static String getAgeDesc(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        int month = 0;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                month = 0;
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                    month = 11;
                }
            }else{
                month = 12+monthNow-monthBirth;
                age--;
            }
        }else {
            month = monthNow - monthBirth;
            if (dayOfMonthNow < dayOfMonthBirth) {
                month--;
            }
        }
        return age+"岁"+month+"月";
    }

    /**
     * 将日期对象按照格式转成字符串
     * @param date 日期对象
     * @param format 日期格式
     * @return 字符串
     */
    public static String date2String(Date date, String format){
        if (date == null){
            //如果时间传空，取当前时间
            throw new IllegalArgumentException("参数日期不能为空");
            //date = new Date();
        }
        if (format == null || "".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        String result = null;
        try {
            DateFormat fmt = new SimpleDateFormat(format);
            result = fmt.format(date);
        }catch (Exception e){
            log.error("日期转字符串异常！"+e.getLocalizedMessage());
            throw e;
        }
        return result;
    }

    /**
     * 将字符串转换为时间格式
     * @param dateString
     * @param format
     * @return
     */
    public static Date string2Date(String dateString,String format){
        if (dateString == null && "".equals(dateString)){
            log.error("字符串转日期参数错误！");
            throw new IllegalArgumentException("字符串转日期参数错误！日期不能为空");
        }
        if (format == null || "".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        Date result = null;
        try {
            DateFormat fmt = new SimpleDateFormat(format);
            result = fmt.parse(dateString);
        }catch (Exception e){
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        return result;
    }

    /**
     * 判断某个时间是否在指定的有效期内
     * 如果传入空值则判断当前时间
     * @param  date 需判断的时间
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return true : false
     */
    public static boolean isInPeriod(Date date, Date beginDate, Date endDate){
        if (beginDate == null || endDate == null){
            log.error("参数错误");
            throw new IllegalArgumentException("开始日期和结束日期不能为空");
        }
        if (beginDate.after(endDate)){
            log.error("参数错误");
            throw new IllegalArgumentException("开始日期不能大于结束日期");
        }
        if (date == null){
            date = new Date();
        }
        if (date.after(beginDate) && date.before(endDate)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 两者相差天数
     * @param date1
     * @param date2
     * @return 相差天数
     */
    public static long getBetweenDays(String date1, String date2){
        if (date1 == null || "".equals(date1)){
            log.error("参数错误");
            throw new IllegalArgumentException("比较参数不能为空");
        }
        if (date2 == null || "".equals(date2)){
            log.error("参数错误");
            throw new IllegalArgumentException("比较参数不能为空");
        }
         long day = 0;
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOne = fmt.parse(date1);
            Date dateTwo = fmt.parse(date2);
            day = (dateTwo.getTime()- dateOne.getTime())/(24*60*60*1000);
        }catch (Exception e){
            log.error("时间比较错误");
            throw new IllegalArgumentException("时间比较错误，错误信息"+e.getLocalizedMessage());
        }
        return day;
    }

    /**
     * 获取传入时间对应周的第一天(以星期一为第一天)
     * @param date
     * @return
     */
    public static Date getWeekFirstDayMon(Date date){
        if (date == null){
            log.error("参数为空错误");
            throw new IllegalArgumentException("获取本周第一天失败，参数为空");
        }
        //去掉时分秒
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = fmt.format(date);
        try {
            date = fmt.parse(dateString);
        }catch (Exception e){
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        //用参数初始化日历
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        //cal.get(Calendar.DAY_OF_WEEK)获取到的是参数nowDate在对应周的序号，
        // 默认是以周日为第一天的(即cal.get(Calendar.DAY_OF_WEEK)=1)
        if (cal.get(Calendar.DAY_OF_WEEK) == 1){
            d = -6;
        }else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        return cal.getTime();
    }

    /**
     * 获取传入时间对应周的第一天(以星期日为第一天)
     * @param date
     * @return
     */
    public static Date getWeekFirstDaySun(Date date){
        if (date == null){
            log.error("参数为空错误");
            throw new IllegalArgumentException("获取本周第一天失败，参数为空");
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = fmt.format(date);
        try {
            date = fmt.parse(dateString);
        }catch (Exception e){
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 1 - cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DAY_OF_WEEK,d);
        return cal.getTime();
    }

    /**
     * 获取传入时间对应周的第一天(以星期一为第一天),并返回字符串的形式
     * @param date
     * @return
     */
    public static String getWeekFirstDayMonString(Date date){
        date = getWeekFirstDayMon(date);
        return date2String(date,"yyyy-MM-dd");
    }

    /**
     * 获取传入时间对应周的最后一天(以星期日为最后一天)
     * @param date
     * @return
     */
    public static Date getWeekLastDaySun(Date date){
        if (date == null){
            log.error("参数为空错误");
            throw new IllegalArgumentException("获取本周最后一天失败，参数为空");
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateString = myFormatter.format(date);
        try {
            date = myFormatter.parse(nowDateString);
        } catch (Exception e) {
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int d = 0;
        //calendar以星期日为第一天，所以如果等于1
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        //得到周一
        cal.add(Calendar.DAY_OF_WEEK,d);
        //在周一基础上加6天，得到周日
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    /**
     * 获取传入时间对应周的最后一天(以星期六为最后一天)
     * @param date
     * @return
     */
    public static Date getWeekLastDaySat(Date date){
        if (date == null){
            log.error("参数为空错误");
            throw new IllegalArgumentException("获取本周最后一天失败，参数为空");
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = fmt.format(date);
        try {
            date = fmt.parse(dateString);
        } catch (Exception e) {
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int d = 1-cal.get(Calendar.DAY_OF_WEEK);

        //得到周日
        cal.add(Calendar.DAY_OF_WEEK,d);
        //在周日基础上加6天，得到周六
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    /**
     * 获取传入时间对应周的最后一天(以星期日为最后一天),并以字符串的形式返回
     * @param date
     * @return
     */
    public static String getWeekLastDaySunString(Date date){
        date = getWeekLastDaySun(date);
        return date2String(date,"yyyy-MM-dd");
    }

    /**
     * 判断当前日期是星期几
     * @param date
     * @return
     */
    public static int getWeekOfDate(Date date){
        if (date == null){
            log.error("参数为空");
            throw new IllegalArgumentException("方法调用失败，参数不能为空");
        }
        int [] weeks = {7,1,2,3,4,5,6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weeks[w];
    }

    /**
     * 在给定的时间上增减天数
     * @param date
     * @param num
     * @return
     */
    public static Date getFutureDate(Date date,int num){
        if (date == null){
            log.error("参数为空");
            throw new IllegalArgumentException("方法调用失败，参数不能为空");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,num);
        return cal.getTime();
    }

    /**
     * 传入时间字符串，和增减天数，返回字符串
     * @param date “2017-07-01”
     * @param num “7”
     * @return “2017-07-08”
     */
    public static String getFutureDateString(String date,int num){
        Date dt = string2Date(date,"yyyy-MM-dd");
        dt = getFutureDate(dt,num);
        return date2String(dt,"yyyy-MM-dd");
    }

    /**
     * 获取本月第一天日期
     * @param date
     * @return
     */
    public static Date getMonthFirstDate(Date date){
        if (date == null){
            log.error("参数为空");
            throw new IllegalArgumentException("方法调用失败，参数不能为空");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    /**
     * 获取本月最后一天日期
     * @param date
     * @return
     */
    public static Date getMonthLastDate(Date date){
        if (date == null){
            log.error("参数为空");
            throw new IllegalArgumentException("方法调用失败，参数不能为空");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //加1 变为下个月份
        cal.add(Calendar.MONTH,1);
        //参数为1时获取的本月第一天，为0获取的上个月的第一天
        cal.set(Calendar.DAY_OF_MONTH,0);
        return cal.getTime();
    }

}

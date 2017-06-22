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

    /**
     * 将日期对象按照格式转成字符串
     * @param date 日期对象
     * @param format 日期格式
     * @return 字符串
     */
    public static String date2String(Date date, String format){
        if (date == null){
            //如果时间传空，取当前时间
            //throw new IllegalArgumentException("日期不能为空");
            date = new Date();
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
            day = (dateOne.getTime() - dateTwo.getTime())/(24*60*60*1000);
        }catch (Exception e){
            log.error("时间比较错误");
            throw new IllegalArgumentException("时间比较错误，错误信息"+e.getLocalizedMessage());
        }
        return day;
    }

    /**
     * 得到本周最后一天(以星期日为最后一天)
     * @param nowDate
     * @return
     */
    public static final Date getWeekLastDaySun(Date nowDate){
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateString = myFormatter.format(nowDate);
        try {
            nowDate = myFormatter.parse(nowDateString);
        } catch (Exception e) {
            log.error("字符串转日期异常！ " + e.getMessage());
            throw new IllegalArgumentException("字符串转日期参数错误！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        int d = 0;
        //calendar以星期日为第一天，所以如果等于1
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK,d);
        //所在周结束日期
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    public static final String getWeekLastDaySunString(Date nowDate){
        nowDate = getWeekLastDaySun(nowDate);
        return date2String(nowDate,"yyyy-MM-dd");
    }
    /**
     * 判断当前日期是星期几
     * @param dt
     * @return
     */
    public static int getWeekOfDate(Date dt) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 当前时间加几天
     * @param nowDate
     * @return
     */
    public static final Date getFutureDate(Date nowDate,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DATE,num);
        return cal.getTime();
    }

    /**
     * 传入时间字符串，和增减天数，返回字符串
     * @param date “2017-07-01”
     * @param num “7”
     * @return “2017-07-08”
     */
    public static final String getFutureDateString(String date,int num){
        Date dt = string2Date(date,"yyyy-MM-dd");
        dt = getFutureDate(dt,num);
        return date2String(dt,"yyyy-MM-dd");
    }
}

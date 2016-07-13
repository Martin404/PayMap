package com.hugnew.core.util;

import com.hugnew.core.common.exception.BusinessException;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 时间操作定义类
 * Created by Martin on 2016/7/01.
 */
public class DateUtils extends PropertyEditorSupport {

    public static final DateTimeFormatter standard = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyyMMdd");
    public static final DateTimeFormatter yyyyww = DateTimeFormat.forPattern("yyyyww");
    public static final DateTimeFormatter yyyyMM = DateTimeFormat.forPattern("yyyyMM");
    public static final DateTimeFormatter yy_MM_dd = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter date_sdf_wz = DateTimeFormat.forPattern("yyyy年MM月dd日");
    public static final DateTimeFormatter time_sdf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter yyyymmddhhmmss = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter yyyyMMddHHmmssSSS = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");

    /**
     * 字符串转换成日期
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, DateTimeFormatter sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }
        DateTime date;
        try {
            date = sdf.parseDateTime(str);
            return date.toDate();
        } catch (IllegalArgumentException e) {
            String errorMessage = "yyyy-MM-dd HH:mm:ss";
            if(sdf.equals(yy_MM_dd)){
                errorMessage = "yyyy-MM-dd";
            }
            throw new BusinessException("输入的日期格式有误,应为'"+errorMessage+"'格式.");
        }
    }

    /**
     * 日期转换为字符串
     * @param date     日期
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, DateTimeFormatter date_sdf) {
        if (null == date) {
            return null;
        }
        return date_sdf.print(date.getTime());
    }

    /**
     * 系统时间的毫秒数
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     * @param date 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Date date) {
        return standard.print(date.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：yyyyMMdd
     * @return 指定日期按“yyyyMMdd“格式显示
     */
    public static String formatYYYY() {
        return yyyyMMdd.print(System.currentTimeMillis());
    }

    /**
     * 指定日期的默认显示，具体格式：yyyyMMddHHmmssSSS
     * @return 指定日期按“yyyyMMddHHmmssSSS“格式显示
     */
    public static String formatYYYYMMddHHmmssSSS() {
        return yyyyMMddHHmmssSSS.print(System.currentTimeMillis());
    }

    /**
     * 指定日期的默认显示，具体格式：yyyyMMdd
     * @return 指定日期按“yyyyMMdd“格式显示
     */
    public static String formatYY_YY() {
        return yy_MM_dd.print(System.currentTimeMillis());
    }


    /**
     * 指定日期的默认显示，具体格式：yyyyMMdd
     * @return 指定日期按“yyyyMMdd“格式显示
     */
    public static String formatYYYYWW() {
        return yyyyww.print(System.currentTimeMillis());
    }

    /**
     * 指定日期的默认显示，具体格式：yyyyMMdd
     * @return 指定日期按“yyyyMMdd“格式显示
     */
    public static String formatYYYYMM() {
        return yyyyMM.print(System.currentTimeMillis());
    }

    /**
     * 返回UNIX时间戳-1970年至今的秒数(注意单位-不是毫秒)
     * @return
     */
    public static long getUnixTimestamp() {
        DateTime dateTime = new DateTime();
        return dateTime.getMillis() / 1000;
    }

    /**
     * 返回UNIX时间戳-1970年至今的秒数(注意单位-不是毫秒)
     * @return
     */
    public static long getUnixTimestamp(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getMillis() / 1000;
    }

    /**
     * 返回UNIX时间戳-1970年至今的秒数(注意单位-不是毫秒)
     * @param str    the str
     * @param format the format
     * @return the long
     */
    public static long getUnixTimestamp(String str, DateTimeFormatter format) {
        DateTime dateTime = format.parseDateTime(str);
        return dateTime.getMillis() / 1000;
    }

    /**
     * 将UNIX时间戳转换为DATE类型
     * @param timestamp 秒(注意单位-不是毫秒)
     * @return
     */
    public static DateTime unixTimestampToDate(long timestamp) {
        return new DateTime(timestamp * 1000);
    }

    /**
     * 将UNIX时间戳转换为由format指定的时间类型的字符串
     * @param timestamp 秒(注意单位-不是毫秒)
     * @param format    时间格式
     * @return
     */
    public static String unixTimestampToDate(long timestamp, DateTimeFormatter format) {
        return unixTimestampToDate(timestamp).toString(format);
    }

    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     * @param flag  计算标志，表示按照年/月/日/时/分/秒等计算
     * @param start 开始时间
     * @param ends  结束时间
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Date start, Date ends) {
        DateTime src = new DateTime(start);
        DateTime des = new DateTime(ends);
        if (flag == 'y') {
            return Years.yearsBetween(src, des).getYears();
        }
        if (flag == 'M') {
            return Months.monthsBetween(src, des).getMonths();
        }
        if (flag == 'd') {
            return Days.daysBetween(src, des).getDays();
        }
        if (flag == 'h') {
            return Hours.hoursBetween(src, des).getHours();
        }
        if (flag == 'm') {
            return Minutes.minutesBetween(src, des).getMinutes();
        }
        if (flag == 's') {
            return Seconds.secondsBetween(src, des).getSeconds();
        }
        return 0;
    }

    /**
     * 根据UNIX时间戳（秒）获取UTC标准时间
     * @param unixTimestamp the timestamp 单位是秒，不是毫秒！
     * @return the string
     */
    public static String getUTCTime(long unixTimestamp) {
        DateTime dateTime = new DateTime(unixTimestamp * 1000);
        dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+8")));
        return dateTime.toString(standard);
    }

    /**
     * 根据Date来获取UTC标准时间
     * @param date the date
     * @return the string
     */
    public static String getUTCTime(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+8")));
        return dateTime.toString(standard);
    }

    /**
     * 获取今天零点的时间戳
     * @return the long
     */
    public static long getTodayZeroSeconds() {
        DateTime dt = DateTime.now().withTimeAtStartOfDay();
        return dt.getMillis() / 1000;
    }

    /**
     * 获取Date
     * @param num
     * @param startOrEnd
     * @param scope
     * @return
     */
    public static long getDate(Integer num, String startOrEnd, String scope) {
        DateTime dt = DateTime.now();
        if (scope != null) {
            if ("y".equals(scope)) {
                dt = dt.minusDays(num);
            } else {
                dt = dt.plusDays(num);
            }
        }
        if (startOrEnd != null) {
            if ("s".equals(startOrEnd)) {
                dt = dt.withTimeAtStartOfDay();
            } else {
                dt = dt.millisOfDay().withMaximumValue();
            }
        }
        return dt.getMillis() / 1000;
    }

    /**
     * 获取给定日期之间的日期
     * @param startTime 秒
     * @param endTime   秒
     * @return
     */
    public static List<String> getRangeDates(Long startTime, Long endTime) {
        List<String> dateList = new ArrayList<>();
        DateTime startDt = new DateTime(startTime * 1000);
        DateTime endDt = new DateTime(endTime * 1000);
        endDt = endDt.withTimeAtStartOfDay();
        //因为查询结束时间不包含边界，特殊处理一下
        if (endTime * 1000 == endDt.getMillis()) {
            endDt = endDt.minusDays(1);
        }
        dateList.add(getTime(startTime, date_sdf_wz));
        while (endDt.isAfter(startDt)) {
            startDt = startDt.plusDays(1);
            dateList.add(getTime(startDt.getMillis() / 1000, date_sdf_wz));
        }
        return dateList;
    }

    /**
     * 格式化时间戳，获取Time
     * @param unixTimestamp
     * @param formatter
     * @return
     */
    public static String getTime(long unixTimestamp, DateTimeFormatter formatter) {
        DateTime dateTime = new DateTime(unixTimestamp * 1000);
        return dateTime.toString(formatter);
    }

    /**
     * 获取当前月份第一天0点的时间戳
     * @return 当前月份第一天0点的时间戳
     */
    public static Long getCurrentMonthZeroTimestamp() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withDayOfMonth(1).withTimeAtStartOfDay();
        return dateTime.getMillis() / 1000;
    }

    /**
     * 获取下个月第一天0点时间戳
     * @return 获取下个月第一天0点时间戳
     */
    public static Long getNextMonthZeroTimestamp() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay();
        return dateTime.getMillis() / 1000;
    }

    /**
     * 返回时间间隔天数
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int getDateDiff(Long beginTime, Long endTime) {
        DateTime dateTime1 = new DateTime(beginTime * 1000);
        DateTime dateTime2 = new DateTime(endTime * 1000);
        return Days.daysBetween(dateTime1, dateTime2).getDays();
    }

    /**
     * 当前时间减去day天0点的时间
     * @param day 减去的天数
     * @return the date
     */
    public static Date getBeforeDay(int day){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusDays(day).withTimeAtStartOfDay();
        return dateTime.toDate();
    }

    /**
     * 返回传入时间与当前时间的分钟差
     *
     * @param timestamp 毫秒时间戳
     * @return the int
     */
    public static int diffSecondNow(long timestamp) {

        DateTime dateTime1 = null;
        try {
            dateTime1 = new DateTime(timestamp);

            int year = dateTime1.year().get();
            if(year >= 2026 || year < 2016){
                return Integer.MAX_VALUE;
            }
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }

        return Seconds.secondsBetween(dateTime1, DateTime.now()).getSeconds();
    }
}
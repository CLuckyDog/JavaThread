package com.rh.utils;

/**
 * 
 * @author Li.Wang
 * @version 1.0
 */


import org.apache.log4j.Logger;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Logger log = Logger.getLogger(DateUtil.class);

    public DateUtil() {

    }

    /**
     * dataString: 2002-10-11, 2002/12/12, 2002.12.12
     * 
     * @param dateString
     * @author: Li.Wang
     * @date: 2002-10-20
     */
    public static Date parse(String dateString) {
        java.util.StringTokenizer s = new java.util.StringTokenizer(dateString,
            "-,/,.");

        if (s.countTokens() == 3) {
            int year = Integer.parseInt(s.nextToken());
            int month = Integer.parseInt(s.nextToken()) - 1;
            int day = Integer.parseInt(s.nextToken());

            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0, 0);
            Date d = c.getTime();
            c = null;
            return d;
        }
        return null;
    }

    /**
     * dataString: 2002-10-11 12:12:12, 2002/12/12 12:12:12, 2002.12.12 12:12:12
     *
     * @param dateString
     * @author: Li.Wang
     * @date: 2002-10-20
     */
    public static Date parseTimeStamp(String dateString) {
        java.util.StringTokenizer ss = new java.util.StringTokenizer(
            dateString, " ");
        if (ss.countTokens() == 2) {
            java.util.StringTokenizer s = new java.util.StringTokenizer(
                ss.nextToken(), "-,/,.");
            java.util.StringTokenizer _s = new java.util.StringTokenizer(
                ss.nextToken(), ":");
            if (s.countTokens() == 3 && _s.countTokens() == 3) {
                int year = Integer.parseInt(s.nextToken());
                int month = Integer.parseInt(s.nextToken()) - 1;
                int day = Integer.parseInt(s.nextToken());

                int hour = Integer.parseInt(_s.nextToken());
                int minute = Integer.parseInt(_s.nextToken());
                int second = Integer.parseInt(_s.nextToken());

                Calendar c = Calendar.getInstance();
                c.set(year, month, day, hour, minute, second);
                Date d = c.getTime();
                c = null;
                return d;
            }
        }
        return null;
    }

    /**
     * parseDate(String)
     *
     * @param dataString
     *            MUST be "19990101" formal
     * @return java.util.Date
     */
    public static Date parseDate(String dateString) {
        if (dateString.length() != 8)
            return null;

        int year = Integer.parseInt(dateString.substring(0, 4));
        // Debug.p("year=" + year);
        int month = Integer.parseInt(dateString.substring(4, 6));
        // Debug.p("month=" + month);
        int date = Integer.parseInt(dateString.substring(6, 8));
        // Debug.p("date=" + date);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, date, 0, 0, 0);
        return c.getTime();
    }

    /**
     *
     * @description
     * 将一定时间格式的字符串转换为Date
     * @author sunlei
     * @date 2009-4-8
     * @version 1.0.0
     * @history1:@author;@date;@description
     * @history2:@author;@date;@description
     * @param dateString
     * @return
     */
    public static Date parseStr2Date(String dateString){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.parse(dateString);
        }catch(Exception e){
            return null;
        }
    }

    public static Date parserStr2Date(String dateString, String formatString){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            return sdf.parse(dateString);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ISOSECDateString
     *
     * @param java.util.Date
     *            instance
     * @return String such as: 2002-10-11
     */
    public static String ISOSECDateString(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        StringBuffer dateStringBuffer = new StringBuffer();
        dateStringBuffer.append(c.get(Calendar.YEAR)).append("-").append(
            c.get(Calendar.MONTH) + 1).append("-").append(c.get(Calendar.DATE));
        c.clear();
        c = null;
        return dateStringBuffer.toString();
    }

    /**
     * formatDate
     *
     * @param d
     *            java.util.Date instance
     * @param formatString
     *            such as : yyyy-MM-dd hh:mm:ss, yyyy-MM-dd
     * @return formatedDateString
     */
    public static String formatDate(Date d, String formatString) {
        if (d == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        StringBuffer sb = sdf.format(d, new StringBuffer(),
            new FieldPosition(0));
        return sb.toString();
    }

    public static String formatLongDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }



    public static String format2LongDate(Date date) {
        return formatDate(date, "MM-dd HH:mm:ss");
    }

    public static String formatShortDate(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static String getYear(Date date) {
        return formatShortDate(date).substring(0, 4);
    }

    public static String getMonth(Date date) {
        return formatShortDate(date).substring(4, 6);
    }

    public static String getDay(Date date) {
        return formatShortDate(date).substring(6, 8);
    }

    /**
     * parseDate(String) 将日期和时间合并，
     *
     * @param dataString
     *            MUST be "19990101" formal,"081212"
     * @return java.util.Date 1999-01-01 08:12:12
     */
    public static Date parseDate(String dateString, String timeString) {
        if (dateString.length() != 8 || timeString.length() != 6)
            return null;

        int year = Integer.parseInt(dateString.substring(0, 4));
        // Debug.p("year=" + year);
        int month = Integer.parseInt(dateString.substring(4, 6));
        // Debug.p("month=" + month);
        int date = Integer.parseInt(dateString.substring(6, 8));

        int hour = Integer.parseInt(timeString.substring(0, 2));

        int minute = Integer.parseInt(timeString.substring(2, 4));

        int second = Integer.parseInt(timeString.substring(4, 6));
        // Debug.p("date=" + date);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, date, hour, minute, second);
        return c.getTime();
    }

    /**
     * parseDate(String) 将date类型的日期和String时间合并，
     *
     * @param dataString
     *            MUST be "19990101" formal,"081212"
     * @return java.util.Date 1999-01-01 08:12:12
     */
    public static Date parseAddDate(Date date,
                                    String timeString) {
        if (timeString.length() != 6)
            return null;

        int hour = Integer.parseInt(timeString.substring(0, 2));

        int minute = Integer.parseInt(timeString.substring(2, 4));

        int second = Integer.parseInt(timeString.substring(4, 6));
        // Debug.p("date=" + date);
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    /**
     *
     * <p>
     * 获取时间戳yyyyMMddHHmmss
     * @return
     */
    public static String getNowTimeStamp(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String timeStamp = sdf.format(new Date());
    	return timeStamp;
    }

    public static void main(String[] args) {
        Date date = parserStr2Date("2009-04-30 00:00:00.0", "yyyy-MM-dd HH:mm:ss");
        log.info(format2LongDate(date));
        
        System.out.print(getNowTimeStampLong());
    }
    /**
     * 
     * <p>
     * 获取时间戳yyyyMMddHHmmssfff
     * @return
     */
    public static String getNowTimeStampLong(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	String timeStamp = sdf.format(new Date());
    	return timeStamp;
    }
}

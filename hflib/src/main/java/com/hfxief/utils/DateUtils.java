package com.hfxief.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Title: DateUtils
 * @Description: 日期工具类
 * @date 2017/1/4 13:59
 * @auther xie
 */
public class DateUtils {

    //获取下一天
    public static String formatDateToNextDay(String date) {
        String nextDay = "";
        try {
            Date dates = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates);
            cal.add(Calendar.DATE, 1);
            nextDay = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;
    }


    public static String formatCircleBrowserData(Long millis) {
        if (millis == null || millis.equals("")) {
            return "";
        }
        Date d = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(d);
    }


    public static long getCurrentmillis() {
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    public static long formatDateToMillis(String date) {
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    public static long formatDateToMillisForSimpleDate(String date) {
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            time = sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String friendly_time(long sdate) {
        java.sql.Date date = new java.sql.Date(sdate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = toDate(format.format(date));
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Date cal = new Date();
        long lt = time.getTime() / 86400000;
        long ct = cal.getTime() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTime() - time.getTime()) / 3600000);
            if (hour == 0) {
                if ((cal.getTime() - time.getTime()) / 60000 < 1) {
                    ftime = "刚刚";
                } else {
                    ftime = Math.max((cal.getTime() - time.getTime()) / 60000, 1) + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 3) {
            ftime = days + "天前";
        } else if (days > 3) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };

    @SuppressLint("SimpleDateFormat")
    public static String getShortTime(long mTime) {
        java.sql.Date date = new java.sql.Date(mTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = toDate(format.format(date));
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Date cal = new Date();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);


        long lt = time.getTime() / 86400000;
        long ct = cal.getTime() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            ftime = "今天";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days > 1) {
            ftime = dateFormater3.get().format(time);
        }
        return ftime;
    }

    public static List<String> getFirstDay(Long times) {
        List<String> stringList = new ArrayList<>();
        if (times == null) {
            stringList.add(0, "Unknown");
            stringList.add(1, "Unknown");
            return stringList;
        }
        String odlTime = DateUtils.getShortTime(times);
        String day = "";
        String month = "";
        if (odlTime.contains("-")) {
            int i = odlTime.indexOf("-");
            day = odlTime.substring(i + 1, odlTime.length()) + "/";
            month = odlTime.substring(0, i).replace("0", "") + "月";
        } else {
            day = odlTime.substring(0, 1);
            month = odlTime.substring(1, 2);
        }
        stringList.add(0, day);
        stringList.add(1, month);
        return stringList;
    }

    public static boolean isSameDay(String dateStr1, String dataStr2) {
        long longTime1 = Long.parseLong(dateStr1);
        long longTime2 = Long.parseLong(dataStr2);
        String time1 = null;
        String time2 = null;
        if (dateStr1 == null || dataStr2 == null && dataStr2.equals("") || dateStr1.equals(""))
            return false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            time1 = format.format(longTime1);
            time2 = format.format(longTime2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time1.equals(time2);
    }
}

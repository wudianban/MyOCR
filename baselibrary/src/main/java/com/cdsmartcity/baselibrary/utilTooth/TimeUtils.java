package com.cdsmartcity.baselibrary.utilTooth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author Li
 * 创建日期：2020/3/11 18:55
 * 描述：TimeUtils
 */
public class TimeUtils {

    /*
     *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     * */
    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag = "";
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }

    public static float getMinute(Long second) {
        float s = second / 60;
        return s;
    }

    public static String getTime(Long second) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff = second;

        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天";
        if (hour != 0) return hour + "小时";
        if (min != 0) return min + "分钟";
        return "";
    }

    public static String getTime1(int minutes) {
        if (minutes >= 60) {
            double a = minutes / 60;
            double b = minutes % 60;
            return a + "小时" + b + "分钟";
        } else {
            return minutes + "分钟";
        }
    }

    /**
     * 根据两个秒数 获取两个时间差
     */
    public static String getDatePoor(long endDate, long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // long ns = 1000;
        // 获得两个时间的秒时间差异
        long diff = (endDate * 1000L) - (nowDate * 1000L);
//        // 计算差多少天
//        long day = diff / nd;
//        // 计算差多少小时
//        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String res = "";
//        if(day != 0){
//            res += day + "天";
//        }
//        if(hour != 0){
//            res += "  "+hour + ":"+min + ":"+sec;
//        }
        res = min + ":" + sec;
        return res;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @return
     */
    private static long day = 1000 * 3600 * 24;

    public static int differentDaysByMillisecond(Long start, Long end) {
        Long time = (end - start);
        if (time <= 0) {
            return 0;
        }
        if (time < day) {
            end = end + day;
        }
        int days = (int) ((end - start) / day);
        return days;
    }


    public static String getDateToString(Long milSecond) {
        if (milSecond == 0) {
            return "--";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(milSecond);
        return format.format(date);
    }

    public static String getDateToStringToDay(Long milSecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(milSecond);
        return format.format(date);
    }

    public static String getDateToStringToMdHm(Long milSecond) {
        SimpleDateFormat format = new SimpleDateFormat("MM.dd HH:mm");
        Date date = new Date(milSecond);
        return format.format(date);
    }

    //返回的时间格式 2019年10月4号 11:23-14:34
    public static String TimeConsuming(Long start, Long end) {
        String startTime = getDateToString(start);
        String endTime = getDateToString(end);
        if (endTime.length() > 5) {
            endTime = endTime.substring(endTime.length() - 5);
        }
        return startTime + "至" + endTime;
    }

    public static String getDatePoor(long data) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // long ns = 1000;
        // 获得两个时间的秒时间差异
        long diff = (data);
//        // 计算差多少天
//        long day = diff / nd;
//        // 计算差多少小时
//        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String res = "";
//        if(day != 0){
//            res += day + "天";
//        }
//        if(hour != 0){
//            res += "  "+hour + ":"+min + ":"+sec;
//        }
        res = min + ":" + sec;
        return res;
    }

    public static String test(Long milSecond) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(milSecond);
        return format.format(date);
    }

    //获取临时锁车剩余时长
    private static long thirty = 30 * 60L;

    public static String getSurplusTimeStop(long second) {
        long t = (thirty - second) * 1000 + System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(t);
        return format.format(date);
    }

    public static void main(String[] args) {
//        String time = TimeUtils.differentDaysByMillisecond(1584118446 * 1000L, 1584424446 * 1000L) + "";
//        System.out.println(time);
//        String time1 = TimeUtils.getDatePoor(598);
//        System.out.println(time1);
//        String end = getDateToString(1584424446 * 1000L);
//        end = end.substring(end.length() - 5, end.length());
//        System.out.println(end);
//        String data = TimeConsuming(1584172446 * 1000L, 1584255246 * 1000L);
//        String data = test(1584172446 * 1000L);
        String data = differentDaysByMillisecond(1584753120 * 1000L, 1585098720 * 1000L) + "";
        System.out.println(data);
    }

}

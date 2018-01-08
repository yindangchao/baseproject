package heyhou.com.baseproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 李凯源 on 2016/4/26.
 * 时间的工具类
 */
public class DateUtil {

    /**
     * 获取时区   以 “+8” 的形式
     *
     * @return 时区
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();

        int offsetMinutes = tz.getRawOffset() / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(2);
        builder.append(sign);
        builder.append(Integer.toString(offsetMinutes / 60));
        return builder.toString();
    }

    /**
     * 获取时区   以 8 或 -8 的形式
     *
     * @return 时区
     */
    public static int getCurrentTimeZoneInt() {
        TimeZone tz = TimeZone.getDefault();

        int offsetMinutes = tz.getRawOffset() / 60000 / 60;
        return offsetMinutes;
    }

    public static String getUtcTime() {

        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();


        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);


        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);


        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));


        //之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
        return getFormatStringDate(cal.getTimeInMillis());

    }


    /**
     * 根据传入的时间差获取一个格式化的时间（yyyy-MM-dd）
     *
     * @param timeMillis System.currentTimeMillis()
     * @return
     */
    public static String getMessageStringDate(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    /**
     * 根据传入的时间差获取一个格式化的时间（yyyy-MM-dd）
     *
     * @param timeMillis System.currentTimeMillis()
     * @return
     */
    public static String getFormStringDate(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }


    /**
     * 查询当前日期前(后)x天的日期
     *
     * @param date 当前日期
     * @param day  天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public static String beforNumDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 查询当前日期前(后)x天的日期
     *
     * @param date 当前日期
     * @param day  天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyyMMdd
     */
    public static String beforNumberDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, day);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }


    /**
     * 根据传入的时间差获取一个格式化的时间（yyyy-MM-dd  HH:mm:ss）
     *
     * @param timeMillis System.currentTimeMillis()
     * @return
     */
    public static String getFormatStringDate(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    public static String getFormatStringDate(long timeMillis, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date d2 = new Date(timeMillis);
        String dateStr = simpleDateFormat.format(d2);
        return dateStr;
    }

    /**
     * 返回小时和分钟
     *
     * @return
     */
    public static String getHourAndMinute(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    /**
     * 根据传入的时间差获取一个格式化的时间（yyyy-MM-dd  HH:mm:ss）
     *
     * @param timeMillis System.currentTimeMillis()
     * @return
     */
    public static String getFormatStringDateFromPath(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    public static String getFormatStringOrderDate(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    /**
     * 根据传入的时间差获取一个UTC格式化的时间（yyyy-MM-dd  HH:mm:ss）
     *
     * @param timeMillis System.currentTimeMillis()
     * @return
     */
    public static String getFormatStringUTCDate(long timeMillis) {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance();
        timeMillis -= (tz.getRawOffset() + tz.getDSTSavings());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    public static String getFormatStringLbsActionDate(long timeMillis) {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance();
        timeMillis -= (tz.getRawOffset() + tz.getDSTSavings());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    /**
     * 根据指定日期获取该日期的时间戳
     *
     * @param formatDateStr 指定的日期  需要2016-01-01 00:00:00这样的格式
     * @return 时间戳
     */
    public static long getTimeMillis(String formatDateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(formatDateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 根据指定日期获取该日期的时间戳
     *
     * @param formatDateStr 指定的日期  需要2016-01-01 00:00:00这样的格式
     * @return 时间戳
     */
    public static long getTimeDateMillis(String formatDateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(formatDateStr + " 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentYear(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date d1 = new Date(timeMillis);
        String dateStr = format.format(d1);
        return dateStr;
    }

    /**
     * 根据传入的时间戳获得这个时间是在当前年份的第几天
     *
     * @param timeMillis
     * @return
     */
    public static int getDayFoYear(long timeMillis) {
        Calendar ca = Calendar.getInstance();//创建一个日期实例
        ca.setTime(new Date(timeMillis));//实例化一个日期
        return ca.get(Calendar.DAY_OF_YEAR);
    }


    public static int getYearByTimeStamp(long timeStamp) {
        String date = getFormatStringDate(timeStamp);
        String year = date.substring(0, 4);
        return Integer.parseInt(year);
    }

    public static int getMonthByTimeStamp(long timeStamp) {
        String date = getFormatStringDate(timeStamp);
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    public static int getDayByTimeStamp(long timeStamp) {
        String date = getFormatStringDate(timeStamp);
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    /**
     * 根据时间戳获取时分秒   21:06:30
     *
     * @param timestamp 毫秒值
     * @return
     */
    public static String getHourStringDate(long timestamp) {
        timestamp /= 1000;
        if (timestamp > 86400) {
            return "24:00:00";
        }
        long hour = timestamp / 3600;
        long minute = (timestamp % 3600) / 60;
        long sec = (timestamp % 3600) % 60;
        return "" + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    /**
     * 根据时间戳获取时分秒   21:06:30
     *
     * @param timestamp 毫秒值
     * @return
     */
    public static String getHourStringDateFromShort(long timestamp) {
        timestamp /= 1000;
        if (timestamp > 86400) {
            return "24:00:00";
        }
        long minute = (timestamp % 3600) / 60;
        long sec = (timestamp % 3600) % 60;
        return (minute < 10 ? "0" + minute : minute) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    /**
     * 根据时间戳获取时分秒   21:06:30
     *
     * @param timestamp 毫秒值
     * @return
     */
    public static int getSecDateFromInt(long timestamp) {
        timestamp /= 1000;
        int sec = (int) ((timestamp % 3600) % 60);
        return sec;
    }


    /**
     * @return
     */
    public static String getHourAndSecondFromSecond(int seconds) {
        if (seconds > 6000) {
            return ">99:00";
        }
        long minute = seconds / 60;
        long sec = seconds % 60;
        return (minute < 10 ? "0" + minute : minute) + ":" + (sec < 10 ? "0" + sec : sec);
    }


}

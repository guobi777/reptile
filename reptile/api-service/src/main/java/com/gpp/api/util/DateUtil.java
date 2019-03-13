package com.gpp.api.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述: 日期工具类
 *
 * @author 胡义振
 * @date 2016-03-10
 */
public final class DateUtil {

    public static Date stampToDate(String s) {
        long lt = new Long(s);
        Date date = new Date(lt * 1000L);
        return date;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateStr(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt * 1000L);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 描述: 日期转为字符串
     *
     * @param date
     * @param format
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                result = "";
            }
        }
        return result;
    }

    /**
     * 描述: 字符串转为日期类型
     *
     * @param strDate
     * @param format
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date stringToDate(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(strDate);
        } catch (Exception er) {
            return null;
        }
    }


    /**
     * 描述: 获取当前完整时间
     *
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String getCurrentTime() {
        Date date = new Date();
        date = DateUtils.addHours(date, 8);
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 描述: 获取当前年份
     *
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String getCurrentYear() {
        return dateToString(new Date(), "yyyy");
    }

    /**
     * 描述: 获取当前日期
     *
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String getCurrentDate() {
        return dateToString(new Date(), "yyyy-MM-dd");
    }

    /**
     * 描述: 获取当前时间(时、分、秒)
     *
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String getCurrentSimpleTime() {
        return dateToString(new Date(), "HH:mm:ss");
    }

    /**
     * 描述: 把日期转成完整格式。如：2014-1-1 转化后为 2014-01-01
     *
     * @param strDate
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static String toComplexDate(String strDate) {
        try {
            String tmp_date[] = strDate.split("-");
            String tmp_year = tmp_date[0];
            String tmp_month = tmp_date[1];
            String tmp_day = tmp_date[2];
            if (Integer.parseInt(tmp_month) <= 9) {
                tmp_month = "0" + tmp_month;
            }
            if (Integer.parseInt(tmp_day) <= 9) {
                tmp_day = "0" + tmp_day;
            }
            return tmp_year + "-" + tmp_month + "-" + tmp_day;
        } catch (Exception er) {
            return strDate;
        }
    }

    /**
     * 描述: 获取N年后日期
     *
     * @param date
     * @param years 年数
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date getAfterDateByYears(Date date, int years) {
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.add(Calendar.YEAR, years);
            return now.getTime();
        } catch (Exception er) {
            return date;
        }
    }

    /**
     * 描述: 获取N月后日期
     *
     * @param date
     * @param months 月数
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date getAfterDateByMonths(Date date, int months) {
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.add(Calendar.MONTH, months);
            return now.getTime();
        } catch (Exception er) {
            return date;
        }
    }

    /**
     * 描述: 获取N天后日期
     *
     * @param date
     * @param months 月数
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date getAfterDateByDays(Date date, int days) {
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.add(Calendar.DAY_OF_YEAR, days);
            return now.getTime();
        } catch (Exception er) {
            return date;
        }
    }

    /**
     * 描述: 获取N小时后日期
     *
     * @param date
     * @param hours 小时数
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date getAfterDateByHours(Date date, int hours) {
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.add(Calendar.HOUR, hours);
            return now.getTime();
        } catch (Exception er) {
            return date;
        }
    }

    /**
     * 描述: 获取N分钟后日期
     *
     * @param date
     * @param hours 小时数
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static Date getAfterDateByMinutes(Date date, int minutes) {
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.add(Calendar.MINUTE, minutes);
            return now.getTime();
        } catch (Exception er) {
            return date;
        }
    }

    /**
     * 描述: 计算两日期相差天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     * @auther 胡义振
     * @date 2016-03-10
     */
    public static int getBetweenDays(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            long startTime = startCalendar.getTimeInMillis();
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            long endTime = endCalendar.getTimeInMillis();
            long between_days = (endTime - startTime) / (1000 * 60 * 60 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception er) {
            return 0;
        }
    }

    public static boolean isToday(String date) {
        String currentdate = DateUtil.getCurrentDate();
        if (currentdate.equals(date)) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String str[]) throws Exception {
        boolean bool = isToday("2018-10-11");
        System.out.println(bool);
		/*Date beforeDate = stringToDate("2014-10-20","yyyy-MM-dd");
		Date afterDate = stringToDate("2014-10-18","yyyy-MM-dd");
		
		System.out.println(getBetweenDays(beforeDate,afterDate));*/
		
		/*String date = getCurrentTime();
		date = UUID.getUUID()+date.trim().replace(" ", "").replace("-", "").replace(":", "");
		System.out.println(date);*/

        //String d = date.substring(32, 46);
		/*long time = System.currentTimeMillis();
		long l = 1538096691252L;
		System.out.println(l);
		
		String cut = (time-l)+"";
		System.out.println(cut);
		System.out.println((time-l)/(1000*60)+"分钟");*/
		
		/*long l = 2*60*60*1000;
		System.out.println(l/(1000*60*60)+"小时");
		System.out.println(l);*/

        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-09-13 13:15:52"));
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-09-13 13:15:52"));
        Calendar calendar1 = Calendar.getInstance();
        //calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-12-20 13:15:52"));
        calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-12-20 13:15:52"));
        //活动开始时间毫秒
        long o = calendar.getTimeInMillis();
        System.out.println(o);
        //活动结束时间毫秒
        long d = calendar1.getTimeInMillis();
        System.out.println(d);
        System.out.println(System.currentTimeMillis());
        if (o > System.currentTimeMillis()) {
            //还没开始
            System.out.println("即将开始");
        }
        if (o <= System.currentTimeMillis() && System.currentTimeMillis() <= d) {
            //进行中
            System.out.println("进行中");
        }
        if (d < System.currentTimeMillis()) {
            //结束
            System.out.println("已结束");
        }

    }


}

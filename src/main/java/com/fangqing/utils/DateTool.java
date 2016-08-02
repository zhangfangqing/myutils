package com.fangqing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年7月7日 
 * @time 上午9:50:10
 */
public class DateTool {
    private static Logger       log                = LoggerFactory.getLogger(DateTool.class);

    public final static String  DATEYYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String  DATEYYYYMMDD       = "yyyyMMdd";
    public final static String  DATE_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String  DATE_YYYY_MM_DD       = "yyyy-MM-dd";
    public final static String  DATE_YYYY_MM       = "yyyy-MM";
    
    /**
     * 每月第一天时分秒
     */
    private static final String FIRST_DAY_TIME     = " 00:00:00";

    /**
     * 每月最后一天时分秒
     */
    private static final String LAST_DAY_TIME      = " 23:59:59";

    
    public static void main(String[] args) throws Exception {
//        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
//        System.out.println(ACCURATE_SECONDS.format(forwardMonth(YYYYMMDDHHMMSS.parse("20150601000000"), 1)));
    	
    	System.out.println(FileTool.getPolicyOriginTotalFileName());
    }
    
    
    /**
     * @功能   取上个月
     *
     * @author zhangfangqing 
     * @date 2016年7月19日 
     * @time 下午7:52:11
     */
	public static String getBeforeMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_YYYY_MM);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 取得上一个时间
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
		return sdf.format(calendar.getTime());
	} 
    
    
    /**
     * //根据日期取得星期几
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    /**
     * 将如期转为 yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String formatACCURATE_SECONDS(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf.format(date);
        } catch (Exception e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为日期的类型，yyyyMMddHHmmss；支持多并发
     * 
     * @param date
     * @return
     */
    public static String formatYYYYMMDDHHSS(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat(DATEYYYYMMDDHHMMSS);
        try {
            return sf.format(date);
        } catch (Exception e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为日期的类型，yyyyMMddHHmmss；支持多并发
     * 
     * @param date
     * @return
     */
    public static Date parseYYYYMMDDHHSS(String date) {
        SimpleDateFormat sf = new SimpleDateFormat(DATEYYYYMMDDHHMMSS);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为日期的类型，yyyyMMdd；支持多并发
     * 
     * @param date
     * @return
     */
    public static Date parseYYYYMMDD(String date) {
        SimpleDateFormat sf = new SimpleDateFormat(DATEYYYYMMDD);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为日期的类型，yyyyMM；支持多并发
     * 
     * @param date
     * @return
     */
    public static String formatYYYYMM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        try {
            return sdf.format(date);
        } catch (Exception e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为日期的类型，yyyyMMdd,支持多并发
     */
    public static String formatYYYYMMDD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.format(date);
        } catch (Exception e) {
            log.warn("sf.parse sysetm error;param=" + date + ";message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转日期，精确到秒
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date accurateSecond(String arg) throws ParseException {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ACCURATE_SECONDS.parse(arg);
    }

    /**
     * 字符日期判断，返回最大的日期
     * 
     * @param arg0
     * @param arg1
     * @return Date
     * @throws ParseException
     */
    public static Date MaxDateAccurateSecond(String arg0, String arg1) throws ParseException {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date0 = ACCURATE_SECONDS.parse(arg0);
        Date date1 = ACCURATE_SECONDS.parse(arg1);
        return date0.compareTo(date1) == 1 ? date0 : date1;
    }

    /**
     * 字符串转日期，精确到天 日期格式为yyyy-MM-dd
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date accurateDay(String arg) throws ParseException {
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        return ACCURATE_DAYS.parse(arg);
    }

    /**
     * 字符串转日期，精确到天 日期格式为yyyyMMdd
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date accurateDayByNo(String arg) throws ParseException {
        SimpleDateFormat ACCURATE_DAYS_NULL = new SimpleDateFormat("yyyyMMdd");
        return ACCURATE_DAYS_NULL.parse(arg);
    }

    /**
     * 日期串转字符，精确到天
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String accurateDay(Date arg) throws ParseException {
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        return ACCURATE_DAYS.format(arg);
    }

    /**
     * 字符串转日期，模糊判断， 超过10的则精确到秒，反之精确到天
     * 
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date ignoreDate(String arg) throws ParseException {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        return arg.length() > 10 ? ACCURATE_SECONDS.parse(arg) : ACCURATE_DAYS.parse(arg);
    }

    /**
     * 获得当前系统时间,精确到秒
     * 
     * @return
     */
    public static String getSysAccurateSecond() {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ACCURATE_SECONDS.format(new Date());
    }

    /**
     * 获得当前时间戳(文件名用到),精确到秒
     * 
     * @return
     */
    public static String getSystimes() {
        SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
        return YYYYMMDDHHMMSS.format(new Date());
    }

    /**
     * 获得当前系统时间,精确到天
     * 
     * @return
     */
    public static String getSysAccurateDay() {
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        return ACCURATE_DAYS.format(new Date());
    }

    /**
     * 获得当前系统时间,精确到天
     * 
     * @return yyyyMMdd
     */
    public static String getSysAccurateDayNull() {
        SimpleDateFormat ACCURATE_DAYS_NULL = new SimpleDateFormat("yyyyMMdd");
        return ACCURATE_DAYS_NULL.format(new Date());
    }

    /**
     * 获得相对于当前系统日期的时间,精确到天
     * 
     * @param day
     * @return yyyyMMdd
     */
    public static String getSysAccurateDayNull(int day) {
        SimpleDateFormat ACCURATE_DAYS_NULL = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        return ACCURATE_DAYS_NULL.format(c.getTime());
    }

    /**
     * 方法说明 （创建于 2014-6-17）. 描述：根据day日期进行加法计算
     * 
     * @param date
     * @param day
     * @return
     * @returnType Date
     * @see com.zhongan.core.creditproduct.tools#DateTool
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 方法说明 （创建于 2014-6-17）. 描述：返回格式为yyyy-MM-dd时间
     * 
     * @param date
     * @return
     * @throws ParseException
     * @returnType Date
     * @see com.zhongan.core.creditproduct.tools#DateTool
     */
    public static Date getTime(Date date) throws ParseException {
        return DateTool.accurateDay(DateTool.accurateDay(date));
    }

    /**
     * 处理时间，只保留到天
     * 
     * @param arg
     * @return Calendar
     */
    public static Calendar retainDay(Date arg) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(arg);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 计算arg0相对于arg1相差天数
     * 
     * @param arg0 较小
     * @param arg1
     * @return
     */
    public static int diffDay(Date arg0, Date arg1) {
        Calendar arg2 = retainDay(arg0);
        Calendar arg3 = retainDay(arg1);
        int result = 0;
        if (arg2.getTimeInMillis() > arg3.getTimeInMillis()) {
            throw new RuntimeException("无效的参数：arg0较大");
        }

        while (arg2.compareTo(arg3) != 0) {
            arg2.add(Calendar.DAY_OF_MONTH, 1);
            result++;
        }
        return result;
    }

    /**
     * 计算两个时间相差的天数<br>
     * 包括当天
     * 
     * @param date1 起期
     * @param date2 止期
     * @return
     */
    public static long calcDays(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / (1000 * 24 * 60 * 60) + 1;
    }

    /**
     * 修改日期
     * 
     * @param date 日期
     * @param day 天数
     * @return
     */
    public static Calendar modifyDay(Date date, int day) {
        Calendar celendar = Calendar.getInstance();
        celendar.setTime(date);
        celendar.add(Calendar.DAY_OF_YEAR, day);
        return celendar;
    }

    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, "yyyyMMdd");
    }

    public static String formatDateTime(Date date, String farmat) {
        return DateFormatUtils.format(date, farmat);
    }

    public static Date parseString(String date) {
        try {
            return DateUtils.parseDate(date, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd",
                    "yyyy/MM/dd", "yy-MM-dd HH:mm:ss", "yy/MM/dd hh:mm:ss", "yyyyMMdd" });
        } catch (Exception e) {
            log.warn("parse date exception, dateStr:" + date, e);
        }
        return null;
    }

    /**
     * 方法说明: 描述：（创建于 2014年3月31日 下午12:06:04）.拼装时间 避免在SQL中使用函数 拼装当天开始时间 2014-03-31
     * 00：00：00
     * 
     * @param date
     * @return
     * @returnType Date
     * @see com.zhongan.core.claim.common#ClaimUtils
     */
    public static Date getStartDate(Date strDate) {
        if (strDate == null) {
            return null;
        }
        Calendar start = new GregorianCalendar();
        start.setTime(strDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();

    }

    /**
     * 方法说明: 描述：（创建于 2014年3月31日 下午12:06:38）.拼装时间 避免在SQL中使用函数 拼装当天结束时间 2014-03-31
     * 23：59：59
     * 
     * @param date
     * @return
     * @returnType Date
     * @see com.zhongan.core.claim.common#ClaimUtils
     */
    public static Date getEndDate(Date strDate) {
        if (strDate == null) {
            return null;
        }
        Calendar end = new GregorianCalendar();
        end.setTime(strDate);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 0);
        return end.getTime();
    }

    /**
     * @Description:比较两个时间点是否相等
     * @param firstDate
     * @param secondDate
     * @ReturnType boolean
     * @author:
     */
    public static boolean isEqual(Date firstDate, Date secondDate) {

        return compare(firstDate, secondDate) == 0 ? true : false;
    }

    /**
     * @Description:比较两个时间点 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值； 如果此
     *                      firstDate 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值； 如果此
     *                      firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
     * @param firstDate
     * @param secondDate
     * @ReturnType int
     * @author:
     * @Created 2012 2012-9-20上午08:34:33
     */
    public static int compare(Date firstDate, Date secondDate) {

        Calendar firstCalendar = null;
        /** 使用给定的 Date 设置此 Calendar 的时间。 **/
        if (firstDate != null) {
            firstCalendar = Calendar.getInstance();
            firstCalendar.setTime(firstDate);
        }

        Calendar secondCalendar = null;
        /** 使用给定的 Date 设置此 Calendar 的时间。 **/
        if (firstDate != null) {
            secondCalendar = Calendar.getInstance();
            secondCalendar.setTime(secondDate);
        }

        try {
            /**
             * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。 如果参数表示的时间等于此 Calendar
             * 表示的时间，则返回 0 值； 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值； 如果此
             * Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
             **/
            return firstCalendar.compareTo(secondCalendar);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 计算两个时间相差的天数<br>
     * 仅适用于知道明确时分秒的，比如保险起止期.保险起期加1(因为00:00:00起),保险止期不加1(因为23:59:59). 推荐使用
     * com.zhongan
     * .core.creditproduct.biz.util.DateUtil.getDateDistance.加1的代码在外部程序中.
     * 
     * @param date1 起期
     * @param date2 止期
     * @param day 需要加上的天数
     * @return
     */
    @Deprecated
    public static int calcDays(Date date1, Date date2, int day) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 24 * 60 * 60) + day);
    }

    /**
     * 获取时间，获取num天后的日期 如：num=1则返回 当前的为2014-01-01 则返回 2014-01-02 00:00:00零点的日期
     * 
     * @return
     */
    public static Date getFormatDate(int num) {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, num);
        String d = ACCURATE_DAYS.format(c.getTime());
        try {
            return ACCURATE_SECONDS.parse(d + " 00:00:00");
        } catch (ParseException e) {
            log.error("日期转换错误。。。" + d);
            throw new RuntimeException();
        }
    }

    /**
     * 获得指定月的第一天
     * 
     * @param nowTime
     * @return
     */
    public static Date getMonthFirstDay(Date nowTime) {
        if (nowTime == null) {
            nowTime = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal.getTime();
    }

    /**
     * 获得指下月的某一天
     * 
     * @param nowTime
     * @return
     */
    public static Date getMonthLastDay(Date nowTime, int day) {
        if (nowTime == null) {
            nowTime = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获得翼支付保单过期时间
     * 
     * @param insureDate投保时间
     * @return
     */
    public static Date getBestpayPolicyExpiryDate(Date insureDate, int day) {
        if (insureDate == null) {
            insureDate = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(insureDate);
        cal.add(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 8);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 根据当前日期向前推迟months月数
     * 
     * @param nowTime 需要推迟的日期
     * @param months 推迟的月数
     * @return 推迟后的时间
     */
    public static Date forwardMonth(Date nowTime, int months) {
        if (nowTime == null) {
            nowTime = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - months);
        return cal.getTime();
    }

    /**
     * 拿上一个月第一天或者最后一天
     * 
     * @param date
     * @param firstOrLastFlag true fisrtDay, false lastDay
     * @return
     */
    public static String getFisrtDayOrLastDay(Date date, boolean firstOrLastFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        if (firstOrLastFlag) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            String firstDay = df.format(calendar.getTime());
            return firstDay + FIRST_DAY_TIME;
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String lastDay = df.format(calendar.getTime());
            return lastDay + LAST_DAY_TIME;
        }
    }

    public static Date transformDate(Date date, boolean firstOrLastFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = getFisrtDayOrLastDay(date, firstOrLastFlag);
        Date dateTime = null;
        try {
            dateTime = df.parse(day);
        } catch (ParseException e) {
            log.warn("transform date error ex:" + e, e);
            throw new RuntimeException("transform date error ex:" + e, e);
        }
        return dateTime;
    }

    /**
     * 获取当天的00：00：00时间
     * 
     * @return
     */
    public static Date getDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天的23：59：59时间
     * 
     * @return
     */
    public static Date getDayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }

    /**
     * 根据当前日期向前提前months月数
     * 
     * @param nowTime 需要提前的日期
     * @param months 提前的月数
     * @return 提前后的时间
     */
    public static Date forwardLastMonth(Date nowTime, int months) {
        if (nowTime == null) {
            nowTime = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + months);
        return cal.getTime();
    }

    /**
     * 根据交易日获取创建保单日
     * 
     * @author wangzhifeng
     * @param orderDay 订单日(消费日)
     * @param billDay 账单日
     */
    public static Date getPolicyDate(Date orderDate, Integer billDay) {
        if (orderDate == null) {
            orderDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(orderDate);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, billDay);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        Date billDate = cal.getTime();

        //交易日<当月账单日
        if (orderDate.before(billDate)) {//交易日>当月账单日,交易对应的账单日为下月6日.
            cal.add(Calendar.MONTH, -1);
            billDate = cal.getTime();
        }
        return billDate;
    }

    /**
     * 根据账单日获取保单日,适用于先建立保单,后消费和保单关联场景,如蘑菇街和翼支付.
     * 
     * @author wangzhifeng
     * @param billDay yyyyMMdd
     * @return yyyyMM
     */
    public static String getPolicyCreatedDateByBillDay(String billDay) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            Date billDate = sdf.parse(billDay);

            Calendar policyDay = Calendar.getInstance();

            policyDay.setTime(billDate);

            policyDay.add(Calendar.MONTH, -1);

            String policyTime = DateTool.formatDateTime(policyDay.getTime(), "yyyyMM");

            return policyTime;

        } catch (Exception e) {

            log.warn("getPolicyCreatedDateByBillDay.parse date error;billDay=" + billDay + ";message:{}", e);

            throw new RuntimeException("getPolicyCreatedDateByBillDay.parse date error", e);
        }
    }

    /**
     * 获得指定月的第几天
     * 
     * @param nowTime
     * @return
     */
    public static Date getMonthInsureDay(Date nowTime, int day) {
        if (nowTime == null) {
            nowTime = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal.getTime();
    }

    /**
     * 根据交易日获取账单日
     * 
     * @author wangzhifeng
     * @param orderDay 订单日(消费日)
     * @param billDay 账单日
     */
    public static Date getBillDate(Date orderDate, Integer billDay) {
        if (orderDate == null) {
            orderDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(orderDate);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, billDay);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        Date billDate = cal.getTime();

        //交易日<当月账单日
        if (orderDate.after(billDate)) {//交易日>当月账单日,交易对应的账单日为下月6日.
            cal.add(Calendar.MONTH, 1);
            billDate = cal.getTime();
        }
        return billDate;
    }

    /**
     * 根据账单日获取还款日
     * 
     * @author wangzhifeng
     * @param billDate 账单日
     * @param freeDay 免息期(比如1号出账单,10号最迟还款日,免息期为10;6号出账单,15号最迟还款日,免息期也为10)
     */
    public static Date getRepaymentDay(Date billDate, Integer freeDay) {
        if (billDate == null) {
            billDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(billDate);
        cal.add(Calendar.MONTH, 0);
        cal.add(Calendar.DAY_OF_MONTH, (freeDay - 1));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date repayDate = cal.getTime();
        return repayDate;
    }

    public static String getBestPayLastPaymentDayForReport() {

        java.util.Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_MONTH) > 8) {
            cal.add(Calendar.MONTH, 1);
        }
        cal.set(Calendar.DAY_OF_MONTH, 8);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date billDate = cal.getTime();
        return DateTool.formatYYYYMMDDHHSS(billDate);
    }

    /**
     * bestpay
     */
    public static String getBestpayBillDayForReport(Integer billDay) {

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -1);
        int day = today.get(Calendar.DAY_OF_MONTH);
        if (day >= billDay) {
            today.set(Calendar.DAY_OF_MONTH, billDay);
            return formatYYYYMMDD(today.getTime());
        } else {
            today.add(Calendar.MONTH, -1);
            today.set(Calendar.DAY_OF_MONTH, billDay);

            return formatYYYYMMDD(today.getTime());
        }

    }

    /**
     * 消费
     * 
     * @param tradetime
     * @param aftertime
     * @return
     */
    public static boolean canDoOrderAction(Date tradetime) {
        String tradetime1 = formatYYYYMMDD(tradetime);
        String nowdate = formatYYYYMMDD(new Date());
        if (tradetime1.equals(nowdate)) {
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(tradetime);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 0);
        if (new Date().before(cal.getTime())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 还款补单
     * 
     * @param transactionTime
     * @param tradetime
     * @return
     */
    public static boolean repaymentDoOrderAction(Date repaymentdate, Date tradetime) {
        String repaymentdate1 = formatYYYYMMDD(repaymentdate);
        String nowdate = formatYYYYMMDD(new Date());
        if (repaymentdate1.equals(nowdate)) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(tradetime);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 6);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (repaymentdate.before(cal.getTime())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 退货为传tradeTime,撤单，撤单为传transaction_time
     * 
     * @param tradetime
     * @param aftertime
     * @return
     */
    public static boolean canReturnOrderRedoAction(Date tradetime, int aftertime) {
        String tradetime1 = formatYYYYMMDD(tradetime);
        String nowdate = formatYYYYMMDD(new Date());
        if (tradetime1.equals(nowdate)) {
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(tradetime);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, aftertime);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (new Date().before(cal.getTime())) {
            return true;
        } else {
            return false;
        }

    }
    

    /**
     * @功能     获取当前时间的前一天时间
     *
     * @author zhangfangqing 
     * @date 2016年7月22日 
     * @time 上午11:00:34
     */
	public static Date getBeforeDay() {
		Date dNow = new Date();
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dNow);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dBefore = calendar.getTime();
		return dBefore;
	}  



}
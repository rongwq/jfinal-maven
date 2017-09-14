package com.rong.common.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String MILLISECOND_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	public static Date calExpiredTime(Long startTime, Long expiredTimeWait) {
		if (null == startTime)
			return null;
		Long expiredTime = startTime + expiredTimeWait;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(expiredTime);
		return calendar.getTime();
	}

	public static String formatDateTime(Date date ,String formate) {
		if (null == date)
			return null;
		DateFormat format = new java.text.SimpleDateFormat(formate);
		return format.format(date);
	}
	
	public static String formatDateTime(Date date) {
		if (null == date)
			return null;
		DateFormat format = new java.text.SimpleDateFormat(DEFAULT_FORMAT);
		return format.format(date);
	}
	
	public static String formatMillisecond(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat format = new java.text.SimpleDateFormat(MILLISECOND_FORMAT);
		return format.format(date);
	}

	public static Date parseDateTime(String date ,String formate) {
		if (null == date)
			return null;
		try{
		DateFormat format = new java.text.SimpleDateFormat(formate);
			return format.parse(date);
		}catch(Exception e){
			logger.error("[DateTimeUtil] "+e.getMessage(),e);
		}
		return null;
	}
	
	public static Date parseDateTime(String date) {
		return parseDateTime(date, DEFAULT_FORMAT);
	}
	
	
	public static String formatDistanceTime(Long distanceTime){
		   long between=distanceTime/1000; //除以1000是为了转换成秒
		   long day=between/(24*3600);
		   long hour=between%(24*3600)/3600;
		   long minute=between%3600/60;
		   long second=between%60;
		   StringBuilder formatTime = new StringBuilder();
		   if(day!=0L){
			   formatTime.append(String.valueOf(day)+"天");
		   }
		   if(hour!=0L){
			   formatTime.append(String.valueOf(hour)+"时");
		   }
		   if(minute!=0L){
			   formatTime.append(String.valueOf(minute)+"分");
		   }
		   if(second!=0L){
			   formatTime.append(String.valueOf(second)+"秒");
		   }
		   return formatTime.toString();
	}
	
	public static Date nextMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
	
	public static Date beforeOneHourDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

	public static Date beforeHourDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 0-hour);
        return calendar.getTime();
    }
	
	/**
	 * 获取前一天的开始时间，指定前一天的零时零点
	 * @return
	 */
	public static String lastDay() {
		String now = formatDateTime(new Date(), "yyyy-MM-dd");
		Date today = parseDateTime(now + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date yesterday = new Date(today.getTime() - 24 * 60 * 60 *1000);
		return formatDateTime(yesterday, "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 获取前一天的最后时间，指前一天的23点59分59秒
	 * @return
	 */
	public static String lastSecondInYesterday() {
		String now = formatDateTime(new Date(), "yyyy-MM-dd");
		Date today = parseDateTime(now + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date lastSecond = new Date(today.getTime() - 1);
		return formatDateTime(lastSecond, "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 获取今天的开始时间，指零时零分零秒
	 * @return
	 */
	public static String startDay() {
		String now = formatDateTime(new Date(), "yyyy-MM-dd");
		return now + " 00:00:00";
	}
	/**
	 * 获取当月
	 * @return
	 */
	public static String month() {
		String now = formatDateTime(new Date());
		String month =now.substring(0, 7);
		return month;
	}
	
	/**
	 * 获取今天的结束时间，指23点59分59秒
	 * @return
	 */
	public static String endDay() {
		String now = formatDateTime(new Date(), "yyyy-MM-dd");
		return now + " 23:59:59";
	}
	
	public static Date nextMinute(int minute) {
        return nextMinute(new Date(),minute);
    }
	
	/**
	 * 获取一个日期的几分钟后的日期
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date nextMinute(Date date,int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
	
	public static Date nextDay(int day) {
        return nextDay(new Date(),day);
    }
	
	/**
	 * 获取一个日期的前几天日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date nextDay(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 0-day);
        return calendar.getTime();
    }
	
	/**
	 * 计算两个时间相差多少天
	 * @param startDate 开始时间字段串
	 * @param afterDate 结束时间
	 * @return 相差多少天
	 */
	public static Integer getBetweenDay(String startDate, Date afterDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(parseDateTime(startDate));
		long startTime = start.getTimeInMillis();
		Calendar after = Calendar.getInstance();
		after.setTime(afterDate);
		long afterTime = after.getTimeInMillis();
		long time = (afterTime - startTime) / (1000 * 60 * 60 * 24);
		return Integer.parseInt(String.valueOf(time));
	}
	
	/**
	 * 获取距离限定天数还有多少秒
	 * @param time 限定天数
	 * @param startTime 开始时间
	 * @param middleTime 中间时间
	 * @return
	 */
	public static long getBetweenSecond(Integer time, Date startTime, Date middleTime) {
		long timeSecond = time * 24 * 60 * 60l;
		return timeSecond - getBetweenMillisecond(startTime, middleTime) / 1000;
	}
	
	/**
	 * 获取距离限定天数还有多少秒，扣除中间无效时间
	 * @param time 限定天数
	 * @param startTime 开始时间
	 * @param middleTime 中间时间
	 * @param decrease 要扣除的中间无效时间，单位是秒
	 * @return
	 */
	public static long getBetweenSecond(Integer time, Date startTime, Date middleTime, long decrease) {
		long timeSecond = time * 24 * 60 * 60l;
		return timeSecond + decrease - getBetweenMillisecond(startTime, middleTime) / 1000;
	}
	
	/**
	 * 获取两个时间差之间的毫秒数
	 * @param startTime 开始时间
	 * @param afterTime 结束时间
	 * @return
	 */
	public static long getBetweenMillisecond(Date startTime, Date afterTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		long start = calendar.getTimeInMillis();
		calendar.setTime(afterTime);
		long after = calendar.getTimeInMillis();
		return after - start;
	}
	
	public static String lastMonth() {
		String now = formatDateTime(new Date(), "yyyy-MM-dd");
		Date today = parseDateTime(now + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date lastSecond = new Date(today.getTime() - 1);
		return formatDateTime(lastSecond, "yyyyMM");
	}
	//当前时间的所在月的最后一天最后一个时刻
	public static String getEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		Date end = calendar.getTime();
		String endStr = formatDateTime(end);
		endStr = endStr.substring(0, 11) + "23:59:59";
		return endStr;
	}
	
	public static void main(String[] args) {
		Date date = nextDay(1);
		System.out.println(date);
		String str = "2017-12-11 00:00:00";
		Date now = parseDateTime(str);
		System.out.println(getEnd(now));
	}
}

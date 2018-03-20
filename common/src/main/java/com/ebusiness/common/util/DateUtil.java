package com.ebusiness.common.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description : 日期工具类
 * 
 * Author : junli.zhang
 * 
 * Date	: 2014-08-21
 */
public final class DateUtil
{
    public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT1 = "yyyyMMdd";
    public static final String DATE_WITH_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_WITH_MINUTE_FORMAT1 = "yyyyMMdd HH:mm";
	public static final String HOUR_MINUTE_DATE_FORMAT = "HH:mm";
    
	private DateUtil(){}
	
	/**
	 * 生成日期解析对象
	 * @param 	pattern		转换格式
	 * @return	DateFormat	日期解析对象
	 */
	private static DateFormat doDateFormat(String pattern)
	{
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 日期转换到字符串
	 * @param 	paramDate		要转换的日期
	 * @param 	pattern			转换格式：例：yyyy-MM-dd
	 * @return	String			日期字符串
	 */
	public static String dateToString(Date paramDate, String pattern)
	{
		return doDateFormat(pattern).format(paramDate);
	}
	
	/**
	 * 字符串转换到日期
	 * @param 	dateStr		日期字符串
	 * @param 	pattern		转换格式：例：yyyy-MM-dd
	 * @return	Date		转换后的日期
	 */
	public static Date stringToDate(String dateStr, String pattern)
	{
		try
		{
			return doDateFormat(pattern).parse(dateStr);
		}
		catch(Exception e)
		{
			System.out.println("字符串转换到日期出错！");
			throw new RuntimeException();
		}
	}
	
	/**
	 * 获取当前日期(字符串格式)
	 * @param 	pattern		转换格式：例：yyyy-MM-dd
	 * @return	String		日期字符串
	 */
	public static String getCurrDate(String pattern)
	{
		return dateToString(new Date(), pattern);
	}
	
	/**
	 * 获取当前日期(日期格式)
	 * @param 	pattern		转换格式：例：yyyy-MM-dd
	 * @return	Date		日期
	 */
	public static Date getCurrDateOfDate(String pattern)
	{
		return stringToDate(dateToString(new Date(), pattern), pattern);
	}
	
	/**
     * 获取日期是星期几
     * @param 	paramDate	参数日期
     * @param 	retFormat 	返回格式：0、表示返回数字格式 1、表示返回中文格式
     * @return	String		星期几
     */
    public static String getDayOfWeek(Date paramDate, int retFormat)
    {
    	Calendar c = Calendar.getInstance();
    	c.setTime(paramDate);
    	int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) == 1) ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
    	String dayOfWeekStr = null;
    	switch (dayOfWeek) 
    	{
    		case 1:
    			dayOfWeekStr = (0 == retFormat) ? "1" : "一";
    			break;
    		case 2:
    			dayOfWeekStr = (0 == retFormat) ? "2" : "二";
    			break;
    		case 3:
    			dayOfWeekStr = (0 == retFormat) ? "3" : "三";
    			break;
    		case 4:
    			dayOfWeekStr = (0 == retFormat) ? "4" : "四";
    			break;
    		case 5:
    			dayOfWeekStr = (0 == retFormat) ? "5" : "五";
    			break;
    		case 6:
    			dayOfWeekStr = (0 == retFormat) ? "6" : "六";
    			break;
    		case 7:
    			dayOfWeekStr = (0 == retFormat) ? "7" : "日";
    			break;
		}
    	return dayOfWeekStr;
    }
    
    /**
     * 指定日期几天后或者几天前的日期
     * @param 	paramDate	指定日期
     * @param 	days		天数
     * @return	Date		几天后或者几天前的日期
     */
    public static Date addDate(Date paramDate, int days)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(paramDate);
    	calendar.add(Calendar.DATE, days);
    	return calendar.getTime();
    }
    
    /**
     * 指定日期几月后或者几月前的日期
     * @param 	paramDate	指定日期
     * @param 	months		月数
     * @return	Date		几月后或者几月前的日期	
     */
    public static Date addDateOfMonth(Date paramDate, int months)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(paramDate);
    	calendar.add(Calendar.MONTH, months);
    	return calendar.getTime();
    }
    
    /**
     * 根据指定日期获取指定日期所在周的开始日期和结束日期(星期一、星期天)
     * @param 	paramDate	指定日期
     * @return	String[]	开始日期和结束日期数组
     */
    public static String[] getWeekStartAndEndDate(Date paramDate)
    {
    	String[] retAry = new String[2];

    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(paramDate);
    	//以周一为一周的开始
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		retAry[0] = dateToString(calendar.getTime(), "yyyy-MM-dd");
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		retAry[1] = dateToString(calendar.getTime(), "yyyy-MM-dd");
		
		return retAry;
    }
    
    /**
	 * 根据指定日期获取指定日期所在月的第一天和最后一天
	 * @param	paramDate	指定日期
	 * @return	String[]	第一天和最后一天数组
	 */
	public static String[] getMonthStartAndEndDate(Date paramDate)
	{
		String[] retAry = new String[2];
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(paramDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		retAry[0] = dateToString(calendar.getTime(), "yyyy-MM-dd");
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		retAry[1] = dateToString(calendar.getTime(), "yyyy-MM-dd");
		
		return retAry;
	}
	
	/**
	 * 获取指定两个日期相差的天数
	 * @param 	paramDate1		指定日期1
	 * @param 	paramDate2		指定日期2
	 * @return	int				相差天数
	 */
	public static int getDiffDaysOfTwoDate(String paramDate1, String paramDate2)
	{
		Date date1 = stringToDate(paramDate1, "yyyy-MM-dd");
		Date date2 = stringToDate(paramDate2, "yyyy-MM-dd");
		
		Long diffTimes = date1.getTime() - date2.getTime();
		Long diffDays = diffTimes / (3600 * 1000 * 24);
		
		return Math.abs(diffDays.intValue());
	}
	
	/**
	 * 获取指定日期相差月份数
	 * @param 	paramDate1	指定日期1
	 * @param 	paramDate2	指定日期2
	 * @return 	int			相差月份数
	 * 注：日期所在月都算一月
	 */
	public static int getDiffMonthsOfTwoDate(String paramDate1, String paramDate2)
	{
		//指定日期1的年份、月份
		int tempYear1 = Integer.parseInt(paramDate1.substring(0, 4));
		int tempMonth1 = Integer.parseInt(paramDate1.substring(5, 7));
		
		//指定日期2的年份、月份
		int tempYear2 = Integer.parseInt(paramDate2.substring(0, 4));
		int tempMonth2 = Integer.parseInt(paramDate2.substring(5, 7));
		
		return Math.abs((tempYear1 * 12 + tempMonth1) - (tempYear2 * 12 + tempMonth2)) + 1;
	}
	
	/**
	 * 获取指定日期所在月有多少天
	 * @param 	paramDate	指定日期(yyyy-MM格式)
	 * @return	int			指定日期所在月有多少天
	 */
	public static int getDaysOfMonths(String paramDate)
	{
		int days = 0;
		try
		{
			Date date = doDateFormat("yyyy-MM").parse(paramDate);	
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		catch(Exception e)
		{
			System.out.println("字符串转换到日期出错");
			throw new RuntimeException();
		}
		return days;
	}
	
	/**  
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式   
     * @param date2 被比较的时间  为空(null)则为当前时间  
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年  
     * 
     * @return  
     */ 
	 public static long compareDate(String date1, String date2, int stype){  
		long n = 0;  
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";  
        date2 = date2==null?dateToString(new Date(), "yyyy-MM-dd"):date2;  
        DateFormat df = new SimpleDateFormat(formatStyle);  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        try {  
            c1.setTime(df.parse(date1));  
            c2.setTime(df.parse(date2));  
        } catch (Exception e3) {  
            System.out.println("wrong occured");  
        }  
        while (!c1.after(c2)) {                     
            n++;  
            if(stype==1){  
                c1.add(Calendar.MONTH, 1);          
            }else{  
                c1.add(Calendar.DATE, 1);          
            }  
        }  
        n = n-1;  
        if(stype==2){  
            n = (long)n/365;  
        }     
        return n;  
	 }  
	 
	 public static Date getCurrDateTime() {
	     return Calendar.getInstance().getTime();
	 }
	 
	 public static Date getDateOfYearsAgoOrLater(int year) {
	     Calendar calendar = Calendar.getInstance();
	     return getDateOfYearsAgoOrLater(calendar.getTime(), year);
	 }
	 
	 public static Date getDateOfYearsAgoOrLater(Date date, int year) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.YEAR, year);
         if (year < 0) {
             calendar.add(Calendar.DATE, 1);
         } else if (year > 0) {
             calendar.add(Calendar.DATE, -1);
         }
         calendar.set(Calendar.HOUR_OF_DAY, 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         return calendar.getTime();
     }

     public static Long getDateOfLongValue(Date date) {
	 	if (date == null) {
	 		return null;
		}
		return date.getTime();
	 }

	 public static Long stringDateToLong(String strDate) {
	 	Date date = stringToDate(strDate, DATE_FORMAT);
	 	if (date != null) {
	 		strDate = dateToString(date, DATE_FORMAT1);
	 		return Long.parseLong(strDate);
		}
		return null;
	 }

	 public static Long getMinutes(String startTime, String endTime) {
	 	if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
	 		return 0L;
		}

		try {
	 		SimpleDateFormat hmDateFormat = new SimpleDateFormat(HOUR_MINUTE_DATE_FORMAT);
	 		Long startDateTime = hmDateFormat.parse(startTime).getTime();
	 		Long endDateTime = hmDateFormat.parse(endTime).getTime();
	 		return (endDateTime - startDateTime) / (1000 * 60);
		} catch (Exception e) {
	 		return 0L;
		}
	 }

	/**
	 * 获取当前时间 时：分
	 * @param minutes 比当前时间提前或延迟几分钟
	 * @return
	 */
	 public static String getCurrentMinute(int minutes) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat(HOUR_MINUTE_DATE_FORMAT);
		 Calendar nowTime = Calendar.getInstance();
		 nowTime.add(Calendar.MINUTE, minutes);
		 return dateFormat.format(nowTime.getTime());
	 }
}


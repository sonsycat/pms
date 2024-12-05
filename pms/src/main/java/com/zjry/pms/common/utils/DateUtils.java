/**
 * Copyright &copy; 2008-2019 <a href="http://www.sxzjrj.com">ZhiJie</a> All rights reserved.
 */
package com.zjry.pms.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.zjry.pms.common.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author LiYongqiang
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到年份字符串 格式（yyyy）
	 */
	public static String getYear(Date date) {
		if(ValidUtil.isEmpty(date)){
			return "";
		}
		return formatDate(date, "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}
	/**
	 * 得到月份字符串 格式（MM）
	 */
	public static String getMonth(Date date) {
		if(ValidUtil.isEmpty(date)){
			return "";
		}
		return formatDate(date, "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}
	/**
	 * 得到天字符串 格式（dd）
	 */
	public static String getDay(Date date) {
		if(ValidUtil.isEmpty(date)){
			return "";
		}
		return formatDate(date, "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}



	public static Date parseDate1(String str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	/**
	 * 获得几天后或几天前的日期
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static Date addDays(Date date,int days) {
	     Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(date);  
	     calendar.add(Calendar.DAY_OF_MONTH, days);  
	     return calendar.getTime();
	}

	/**
	 * 获得当月天数
	 * @param yyyy-MM
	 * */
	public static int getDaysOfMonth(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}



	/**
	 * 获得当月工作天数
	 * @param yyyy-MM
	 * */
	public static int getWorkInMonth(String date){
		if(StringUtils.isNotBlank(date)){
		String[]  strings=	date.split("-");
			int month=Integer.parseInt(strings[1]);
			return workDays(getFirstDayOfMonth(month),getLastDayOfMonth(month));
		}else{
			return 0;
		}
	}
	public static int workDays(String strStartDate, String strEndDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cl1 = Calendar.getInstance();
		Calendar cl2 = Calendar.getInstance();

		try {
			cl1.setTime(df.parse(strStartDate));
			cl2.setTime(df.parse(strEndDate));

		} catch (ParseException e) {
			System.out.println("日期格式非法");
			e.printStackTrace();
		}

		int count = 0;
		while (cl1.compareTo(cl2) <= 0) {
			if (cl1.get(Calendar.DAY_OF_WEEK) != 7 && cl1.get(Calendar.DAY_OF_WEEK) != 1){
				count++;
			}
			cl1.add(Calendar.DAY_OF_MONTH, 1);
		}
		return count;

	}

	public  static  String getFirstDayOfMonth( int  month) {
		Calendar calendar  =  Calendar.getInstance();
		//  设置月份
		calendar.set(Calendar.MONTH, month - 1 );
		//  获取某月最小天数
		int firstDay =  calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		//  设置日历中月份的最小天数
		calendar.set(Calendar.DAY_OF_MONTH, firstDay);
		//  格式化日期
		SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM-dd" );
		String first  = sdf.format(calendar.getTime())+" 00:00:00" ;
		return  first;
	}

	public  static  String getLastDayOfMonth(int month) {
		Calendar calendar  =  Calendar.getInstance();
		//  设置月份
		calendar.set(Calendar.MONTH, month - 1 );
		//  获取某月最大天数
		int  lastDay=0 ;
		// 2月的平年瑞年天数
		if (month== Constants.FEBRUARY) {
			lastDay  =  calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
		} else  {
			lastDay  =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		//  设置日历中月份的最大天数
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		//  格式化日期
		SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM-dd" );
		String last  = sdf.format(calendar.getTime())+" 23:59:59" ;
		return  last;
	}
	
	/**
	 * 时间类型装换为十分秒时间类型
	 * @param date
	 * @return "hh:mm:ss" 时间
	 */
	public static Date getDateByDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String dateTemp = df.format(date);
		try {
			return df.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	/**
	 * 字符串类型装换为十分秒时间类型
	 * @param date
	 * @return "hh:mm:ss" 时间
	 */
	public static Date getDateByString(String date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date dateTemp;
		try {
			dateTemp = df.parse(date);
			return dateTemp;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
		
	}



	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		//System.out.println(parseDate1());
//		String t1 = "18:30";
//		String t2 = "17:30";
//		DateFormat formart = new SimpleDateFormat("hh:mm");
//		Date parse = null;
//		Date parse1=null;
//		try {
//		parse = formart.parse(t1);
//		parse1= formart.parse(t2);
//		} catch (ParseException e) {
//		e.printStackTrace();
//		}
//		if(parse.compareTo(parse1)>0)
//		{
//		System.out.println("营业开始时间不能大于营业结束时间");
//		}
//		else {
//		System.out.println("zhdengchneg");
//		}
	}

	/**
	 * 获取当月最后一天的日期
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);

		return calendar.getTime();
	}

	/**
	 * 获取指定日期后一天的日期
	 * @param date
	 * @return
	 */
	public static Date getSpecifiedDayAfter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return c.getTime();
	}

	/**
	 * 获取指定日期后X天的日期
	 * @param date
	 * @return
	 */
	public static Date getSpecifiedDate (Date date,int x) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + x);
		return c.getTime();
	}

	/**判断日期是否早于今天*/
    public static Boolean DateCompare(Date time ){
    	Calendar calendar = Calendar.getInstance();
    	int nowy = calendar.get(Calendar.YEAR);
		int nowm = calendar.get(Calendar.MONTH)+1;
		int nowd = calendar.get(Calendar.DATE);
		calendar.setTime(time);
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DATE);

		if(nowy<y || (nowy==y && nowm<m) || (nowy==y && nowm ==m && nowd<d)){
			return false;
		}else {
			return true;
		}
	}

	/**判断日期是否等于今天*/
	public static Boolean DateCompareSame(Date time){
		Calendar calendar = Calendar.getInstance();
		int nowy = calendar.get(Calendar.YEAR);
		int nowm = calendar.get(Calendar.MONTH)+1;
		int nowd = calendar.get(Calendar.DATE);
		calendar.setTime(time);
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DATE);

		if(nowy==y && nowm ==m && nowd==d){
			return true;
		}else {
			return false;
		}
	}


	/**获取两个日期之间的差值*/
	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}





	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 *
	 * @param newTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author jqlin
	 */
	public static boolean isEffectiveDate(Date newTime, Date startTime, Date endTime) {


		Calendar date = Calendar.getInstance();
		date.setTime(newTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
}

package edu.nefu.GraduationProject.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool
{
	private int year=0;
	private int month=0;
	private int day=0;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public DateTool() 
	{
		String [] nowDate=getNowDate().split("-");
		year=Integer.parseInt(nowDate[0]);
		month=Integer.parseInt(nowDate[1]);
		day=Integer.parseInt(nowDate[2]);
	}

	public DateTool(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public DateTool(String date) {
		super();
		String[] buffer=date.split("-");
		this.year = Integer.parseInt(buffer[0]);
		this.month = Integer.parseInt(buffer[1]);
		this.day = Integer.parseInt(buffer[2]);
	}

	public static String getNowDate()
	{
 		Date now=new Date();
 		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
 		String nowDate=dateFormat.format(now);
 		return nowDate;
	}
	
	public String getStringDate()
	{
		String sDate=year+"-"+month+"-"+day;
		return sDate;
	}
	
	public static String getNowTime()
	{
		Date now=new Date();
 		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
 		String nowTime=dateFormat.format(now); 
 		return nowTime;
	}
	
	public static String getSystemTime()
	{
		Date now=new Date();
 		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String nowTime=dateFormat.format(now); 
 		return nowTime;
	}
	
	public static String getWordTime()
	{
		Date now = new Date();
 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
 		String nowTime = dateFormat.format(now); 
 		return nowTime;
	}
	
	public static String getSqlTime()
	{
		Date now=new Date();
 		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
 		String nowTime=dateFormat.format(now); 
 		return nowTime;
	}
	
	public static String getSqlTime2()
	{
		Date now=new Date();
 		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String nowTime=dateFormat.format(now); 
 		return nowTime;
	}
	
	/**
	 * 得到截止日期xxxx-xx-xx xx:xx相对1969-12-01 00:00的分钟数
	 * @param deadline 截止日期
	 * @return 分钟数
	 */
	public static int getTimeInMinute(String deadline)
	{
		String [] datebuff=deadline.split(" ")[0].split("-");
		String [] timebuff=deadline.split(" ")[1].split(":");
		int year=Integer.parseInt(datebuff[0]);
		int month=Integer.parseInt(datebuff[1]);
		int day=Integer.parseInt(datebuff[2]);
		int hour=Integer.parseInt(timebuff[0]);
		int minute=Integer.parseInt(timebuff[1]);
		Calendar calendar=Calendar.getInstance();
		calendar.set(year, month, day, hour, minute);
		long time=calendar.getTimeInMillis()/1000/60;
		return (int)time;
	}
	
	public static DateTool initDateTool(String date)
	{
		if(date!=null&&!date.equals(""))
			return new DateTool(date);
		else
			return null;
	}
}

package edu.nefu.GraduationProject.bean;

public class DateTime 
{
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
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
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
	public DateTime(int year, int month, int day, int hour, int minute,int second)
	{
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public DateTime(String datetime)
	{
		super();
		String[] buff=datetime.split(" ");
		String [] dateBuff=buff[0].split("-");
		String [] timeBuff=buff[1].split(":");
		year=Integer.parseInt(dateBuff[0]);
		month=Integer.parseInt(dateBuff[1]);
		day=Integer.parseInt(dateBuff[2]);
		hour=Integer.parseInt(timeBuff[0]);
		minute=Integer.parseInt(timeBuff[1]);
		second=Integer.parseInt(timeBuff[2]);
	}
	
	public DateTime(String fileName,String fileType)
	{
		super();
		String[] buff=fileName.split("."+fileType)[0].split(" ");
		String [] dateBuff=buff[0].split("_");
		String [] timeBuff=buff[1].split("_");
		year=Integer.parseInt(dateBuff[0]);
		month=Integer.parseInt(dateBuff[1]);
		day=Integer.parseInt(dateBuff[2]);
		hour=Integer.parseInt(timeBuff[0]);
		minute=Integer.parseInt(timeBuff[1]);
		second=Integer.parseInt(timeBuff[2]);
	}
	
	public String getDBString()
	{
		String month=this.month+"";
		String day=this.day+"";
		String hour=this.hour+"";
		String minute=this.minute+"";
		String second=this.second+"";
		if(this.day<10)
			day="0"+this.day;
		else
			day=""+this.day;
		if(this.month<10)
			month="0"+this.month;
		else
			month=""+this.month;
		if(this.hour<10)
			hour="0"+this.hour;
		else
			hour=""+this.hour;
		if(this.second<10)
			second="0"+this.second;
		else
			second=""+this.second;
		if(this.minute<10)
			minute="0"+this.minute;
		else
			minute=""+this.minute;
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	}
	
	public String getSaveFileName(String type)
	{
		String month,day,hour,minute,second;
		if(this.day<10)
			day="0"+this.day;
		else
			day=""+this.day;
		if(this.month<10)
			month="0"+this.month;
		else
			month=""+this.month;
		if(this.hour<10)
			hour="0"+this.hour;
		else
			hour=""+this.hour;
		if(this.second<10)
			second="0"+this.second;
		else
			second=""+this.second;
		if(this.minute<10)
			minute="0"+this.minute;
		else
			minute=""+this.minute;
		return year+"_"+month+"_"+day+" "+hour+"_"+minute+"_"+second+"."+type;
	}
}

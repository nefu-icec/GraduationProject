package edu.nefu.GraduationProject.bean;

public class Affair 
{
	private int id;
	private int period;
	private String work;
	private DateTime start;
	private DateTime end;
	private int [] columns;
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public DateTime getStart() {
		return start;
	}
	public void setStart(DateTime start) {
		this.start = start;
	}
	public DateTime getEnd() {
		return end;
	}
	public void setEnd(DateTime end) {
		this.end = end;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int[] getColumns() {
		return columns;
	}
	
	public String getColumnsString() 
	{
		String string="";
		for(int i:columns)
			string+=i+",";
		string=string.substring(0, string.length()-1);
		return string;
	}
	
	public void setColumns(int[] columns) {
		this.columns = columns;
	}
	
	public Affair(int period, String work, DateTime start, DateTime end) 
	{
		super();
		this.period = period;
		this.work = work;
		this.start = start;
		this.end = end;
	}
	
	public Affair(String work, DateTime start, DateTime end) 
	{
		super();
		this.work = work;
		this.start = start;
		this.end = end;
	}
	
	public Affair(int id, String work, DateTime start, DateTime end,int[] columns)
	{
		super();
		this.id = id;
		this.work = work;
		this.start = start;
		this.end = end;
		this.columns = columns;
	}
	
	public Affair(String work, DateTime start, DateTime end, String columns) 
	{
		super();
		this.work = work;
		this.start = start;
		this.end = end;
		String [] buff=columns.split(",");
		this.columns=new int[buff.length];//一定要初始化啊！！！！！！！！！
		for(int i=0;i<buff.length;i++)
			this.columns[i]=Integer.parseInt(buff[i]);
	}

}

package edu.nefu.GraduationProject.bean;

import edu.nefu.GraduationProject.util.Constant;

public class Log 
{
	private int lid;
	private int sid;
	private int tid;
	private String columnName;
	private String content;
	private DateTime time;
	private String sname;
	private String tname;
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getContent() {
		return content;
	}
	
	public String getContent(int limit)
	{
		String content=this.content;
		if(content.length()>limit)
			content=content.substring(0, limit)+"...";
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public DateTime getTime() {
		return time;
	}
	public void setTime(DateTime time) {
		this.time = time;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Log(int sid, int tid, String columnName, String content, DateTime time) 
	{
		super();
		this.sid = sid;
		this.tid = tid;
		this.columnName = columnName;
		this.content = content;
		this.time = time;
	}
	
	public Log(int lid, int sid, int tid, String columnName, String content,DateTime time)
	{
		super();
		this.lid = lid;
		this.sid = sid;
		this.tid = tid;
		this.columnName = columnName;
		this.content = content;
		this.time = time;
	}
	
	public Log(int lid, int sid, int tid, String columnName, String content,
			DateTime time, String sname, String tname) 
	{
		super();
		this.lid = lid;
		this.sid = sid;
		this.tid = tid;
		this.columnName = Constant.getColumnName(columnName);
		this.content = content;
		this.time = time;
		this.sname = sname;
		this.tname = tname;
	}
}

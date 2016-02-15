package edu.nefu.GraduationProject.bean;

public class Excel 
{
	private int eid;
	private String ename;
	private DateTime createDate;
	
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public DateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
	
	public Excel(String ename, DateTime createDate) {
		super();
		this.ename = ename;
		this.createDate = createDate;
	}
	
	public Excel(int eid, String ename, DateTime createDate) 
	{
		super();
		this.eid = eid;
		this.ename = ename;
		this.createDate = createDate;
	}
}

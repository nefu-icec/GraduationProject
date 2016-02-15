package edu.nefu.GraduationProject.bean;

import edu.nefu.GraduationProject.util.DateTool;

public class Student
{
	private String college;
	private String major;
	private String className;
	private int sid;
	private String sname;
	private String tel;
	private int grade;
	private String status;
	private DateTool startTime;
	private DateTool secondStartTime;
	private DateTool endTime;
	private DateTool secondEndTime;
	
	public DateTool getStartTime() {
		return startTime;
	}
	public void setStartTime(DateTool startTime) {
		this.startTime = startTime;
	}
	public DateTool getSecondStartTime() {
		return secondStartTime;
	}
	public void setSecondStartTime(DateTool secondStartTime) {
		this.secondStartTime = secondStartTime;
	}
	public DateTool getEndTime() {
		return endTime;
	}
	public void setEndTime(DateTool endTime) {
		this.endTime = endTime;
	}
	public DateTool getSecondEndTime() {
		return secondEndTime;
	}
	public void setSecondEndTime(DateTool secondEndTime) {
		this.secondEndTime = secondEndTime;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Student() 
	{
		super();
	}
	
	public Student(String college, String major, String className, int sid,
			String sname, String tel, int grade, String status,
			DateTool startTime, DateTool secondStartTime, DateTool endTime,
			DateTool secondEndTime) 
	{
		super();
		this.college = college;
		this.major = major;
		this.className = className;
		this.sid = sid;
		this.sname = sname;
		this.tel = tel;
		this.grade = grade;
		this.status = status;
		this.startTime = startTime;
		this.secondStartTime = secondStartTime;
		this.endTime = endTime;
		this.secondEndTime = secondEndTime;
	}
}

package edu.nefu.GraduationProject.bean;

public class Teacher
{
	private int tid;
	private String tname;
	private String profession;
	private String password;
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Teacher(int tid, String tname, String profession, String password) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.profession = profession;
		this.password = password;
	}
	
	public String showMe()
	{
		return tid+","+tname+","+profession+","+password;
	}
}

package edu.nefu.GraduationProject.bean;

import edu.nefu.GraduationProject.util.DateTool;

public class SysDate 
{
	private int period;
	private String name;
	private DateTool date;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTool getDate() {
		return date;
	}

	public void setDate(DateTool date) {
		this.date = date;
	}

	public SysDate(int period, String name, DateTool date) 
	{
		super();
		this.period = period;
		this.name = name;
		this.date = date;
	}
}

package edu.nefu.GraduationProject.bean;

public class Status 
{
	private int peroid;
	private int value;
	
	public int getPeroid() {
		return peroid;
	}
	public void setPeroid(int peroid) {
		this.peroid = peroid;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public Status(int period,int value) 
	{
		super();
		this.peroid=period;
		this.value = value;
	}
}

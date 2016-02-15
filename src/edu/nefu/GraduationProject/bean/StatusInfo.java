package edu.nefu.GraduationProject.bean;

import edu.nefu.GraduationProject.dao.PeriodDao;
import edu.nefu.GraduationProject.dao.StatusDao;
import edu.nefu.GraduationProject.util.Constant;

public class StatusInfo 
{
	private int status;
	private int period;
	private String lastStatus;
	private String nowStatus;
	private String nextStatus;
	private String task;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getNowStatus() {
		return nowStatus;
	}

	public void setNowStatus(String nowStatus) {
		this.nowStatus = nowStatus;
	}

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public StatusInfo() 
	{
		super();
		this.status=StatusDao.get();
		period=PeriodDao.get();
		if(status==0) 
			this.lastStatus="ÎÞ×´Ì¬£¡"; 
		else 
			this.lastStatus=Constant.Status[status-1];
		this.nowStatus=Constant.Status[status];
		if(status==Constant.Status.length-1) 
			this.nextStatus="ÎÞ×´Ì¬£¡"; 
		else 
			this.nextStatus=Constant.Status[status+1];
		this.task=Constant.ToDo[status];
	}
	
	public static void main(String[] args) {
		System.out.println(new StatusInfo().getTask());
	}
}

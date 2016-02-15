package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;

import edu.nefu.GraduationProject.util.ConnectDB;

public class StatusDao
{
	/**
	 * 设置当前届系统状态
	 * @param status 系统状态
	 * @return
	 */
	public static boolean set(int status)
	{
		String update="update status set value="+status
				+" where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(update);
		db.close();
		if(row>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 反复调用set()方法直到设置成功为止
	 * @param status
	 */
	public static boolean SET(int status)
	{
		boolean success=false;
		while(!success)
			success=set(status);
		return success;
	}
	
	/**
	 * 获得当前届届系统状态
	 * @return
	 */
	public static int get()
	{
		String select="select value from status where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			if(rs.next())
			{
				int value=Integer.parseInt(rs.getString("value"));
				db.close();
				return value;
			}
			else 
			{
				db.close();
				return -1;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.close();
			return -1;
		}
	}
	
	public static void check()
	{
		int status=get();
		switch (status) 
		{
		case 0://初始状态
			
			break;
		case 1://已上传开题登记表
			if(!DataDao.hasNull("tid"))
				SET(2);			
			break;
		case 2://已分配教师
			if(!DataDao.hasNull("ctitle")&&!DataDao.hasNull("etitle")&&!DataDao.hasNull("origin")&&!DataDao.hasNull("type"))
				SET(3);
			break;
		case 3://已录入题目信息
			if(SysDateDao.get("firstStart")!=null&&SysDateDao.get("secondStart")!=null)
				SET(4);
			break;
		case 4://管理员已设置开题时间
			if(!DataDao.hasNull2("startTime", "secondStartTime"))
				SET(5);
			break;
		case 5://教师已选择开题时间
			break;
		case 6://已完成一次开题分组
			if(!DataDao.hasNull("startPassed", "where firstStartGroup is not null"))
				SET(7);
			break;
		case 7://一次开题已完成
		
			break;
		case 8://已完成二次开题分组
			if(!DataDao.hasNull("startPassed"))
				SET(9);
			break;
		case 9://二次开题已完成
			if(SysDateDao.get("firstEnd")!=null&&SysDateDao.get("secondEnd")!=null)
			{
				SET(10);
				Group.joinFirstEnd();
			}
			break;
		case 10://管理员已设置答辩时间
			
			break;
		case 11://已完成一次答辩分组
			if(!DataDao.hasNull("endPassed"," where startPassed=1"))
				SET(12);
			break;
		case 12://一次答辩已完成
			if(!DataDao.hasNull("score"," where startPassed=1 and endPassed=1"))
				SET(13);
			break;
		case 13://已录入成绩
			
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		check();
	}
}

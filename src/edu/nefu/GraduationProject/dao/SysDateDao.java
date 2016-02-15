package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.nefu.GraduationProject.servlet.CommonDao;
import edu.nefu.GraduationProject.util.ConnectDB;
import edu.nefu.GraduationProject.util.DateTool;

public class SysDateDao 
{
	private static boolean insertOrUpdate(String sql)
	{
		ConnectDB db=new ConnectDB();
		try
		{
			int row=db.exeUpdate(sql);
			db.close();
			if(row>0)
				return true;
			return false;
		}	
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 清空当前届所有时间信息
	 * @return
	 */
	public static boolean clear()
	{
		String delete="delete from sysdate where period="+PeriodDao.get();
		if(CommonDao.isThisPeriodNull("sysdate"))
			return true;
		return insertOrUpdate(delete);
	}

	
	/**
	 * 设置时间
	 * @param name
	 * @param date
	 * @return
	 */
	public static boolean set(String name,DateTool date)
	{
		String insert="insert into sysdate values("+PeriodDao.get()+",'"+name+"','"+date.getStringDate()+"')";
		String update="update sysdate set date='"+date.getStringDate()+"' where name='"+name+"' and period="+PeriodDao.get();
		if(insertOrUpdate(insert))
		{
			StatusDao.check();
			return true;
		}
		else
			return insertOrUpdate(update);
	}
	
	/**
	 * 读取时间
	 * @param name
	 * @return
	 */
	public static DateTool get(String name)
	{
		String select="select date from sysdate where period="+PeriodDao.get()+" and name='"+name+"'";
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			if(rs.next())
			{
				DateTool dateTool=new DateTool(rs.getString("date"));
				db.close();
				return dateTool;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			db.close();
			return null;
		}
		db.close();
		return null;
	}
	
	/**
	 * 设置一次开题时间
	 * @return设置是否成功
	 */
	
	public static boolean setFirstStart(DateTool firstStart)
	{
		return set("firstStart",firstStart);
	}
	
	/**
	 * 得到一次开题时间
	 * @return一次开题时间
	 */
	public static DateTool getFirstStart()
	{
		return get("firstStart");
	}
	
	/**
	 * 设置二次开题时间
	 * @return设置是否成功
	 */
	public static boolean setSecondStart(DateTool firstStart)
	{
		return set("secondStart",firstStart);
	}
	
	/**
	 * 得到二次开题时间
	 * @return二次开题时间
	 */
	public static DateTool getSecondStart()
	{
		return get("secondStart");
	}
	
	/**
	 * 设置一次答辩时间
	 * @return设置是否成功
	 */
	public static boolean setFirstEnd(DateTool firstStart)
	{
		return set("firstEnd",firstStart);
	}
	
	/**
	 * 得到一次答辩时间
	 * @return一次答辩时间
	 */
	public static DateTool getFirstEnd()
	{
		return get("firstEnd");
	}
	
	/**
	 * 设置二次答辩时间
	 * @return设置是否成功
	 */
	public static boolean setSecondEnd(DateTool firstStart)
	{
		return set("secondEnd",firstStart);
	}
	
	/**
	 * 得到二次答辩时间
	 * @return二次答辩时间
	 */
	public static DateTool getSecondEnd()
	{
		return get("secondEnd");
	}
}

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
	 * ��յ�ǰ������ʱ����Ϣ
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
	 * ����ʱ��
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
	 * ��ȡʱ��
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
	 * ����һ�ο���ʱ��
	 * @return�����Ƿ�ɹ�
	 */
	
	public static boolean setFirstStart(DateTool firstStart)
	{
		return set("firstStart",firstStart);
	}
	
	/**
	 * �õ�һ�ο���ʱ��
	 * @returnһ�ο���ʱ��
	 */
	public static DateTool getFirstStart()
	{
		return get("firstStart");
	}
	
	/**
	 * ���ö��ο���ʱ��
	 * @return�����Ƿ�ɹ�
	 */
	public static boolean setSecondStart(DateTool firstStart)
	{
		return set("secondStart",firstStart);
	}
	
	/**
	 * �õ����ο���ʱ��
	 * @return���ο���ʱ��
	 */
	public static DateTool getSecondStart()
	{
		return get("secondStart");
	}
	
	/**
	 * ����һ�δ��ʱ��
	 * @return�����Ƿ�ɹ�
	 */
	public static boolean setFirstEnd(DateTool firstStart)
	{
		return set("firstEnd",firstStart);
	}
	
	/**
	 * �õ�һ�δ��ʱ��
	 * @returnһ�δ��ʱ��
	 */
	public static DateTool getFirstEnd()
	{
		return get("firstEnd");
	}
	
	/**
	 * ���ö��δ��ʱ��
	 * @return�����Ƿ�ɹ�
	 */
	public static boolean setSecondEnd(DateTool firstStart)
	{
		return set("secondEnd",firstStart);
	}
	
	/**
	 * �õ����δ��ʱ��
	 * @return���δ��ʱ��
	 */
	public static DateTool getSecondEnd()
	{
		return get("secondEnd");
	}
}

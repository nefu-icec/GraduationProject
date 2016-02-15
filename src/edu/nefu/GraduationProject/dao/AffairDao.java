package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.nefu.GraduationProject.bean.Affair;
import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.servlet.CommonDao;
import edu.nefu.GraduationProject.util.ConnectDB;

public class AffairDao
{
	public static boolean clear()
	{
		String delete="delete from affair where period="+PeriodDao.get();
		if(CommonDao.isThisPeriodNull("affair"))
			return true;
		return update(delete);
	}
	
	/**
	 * 插入一条事务记录到affair表
	 * @param Affair
	 * @return
	 */
	public static boolean setWork(Affair Affair)
	{
		String insert="insert into affair(period,work,start,end,columns) values("+PeriodDao.get()+",'"+Affair.getWork()
				+"','"+Affair.getStart().getDBString()+"','"+Affair.getEnd().getDBString()+"','"+Affair.getColumnsString()+"')";
		return update(insert);
	}
	
	/**
	 * 重置事务开始时间
	 * @param id 事务id号
	 * @param start 开始时间
	 * @return
	 */
	public static boolean resetStart(int id,DateTime start)
	{
		String update="update affair set start='"+start.getDBString()+"' where id="+id;
		return update(update);
	}
	
	/**
	 * 重置事务结束时间
	 * @param id 事务id号
	 * @param start 结束时间
	 * @return
	 */
	public static boolean resetEnd(int id,DateTime end)
	{
		String update="update affair set end='"+end.getDBString()+"' where id="+id;
		return update(update);
	}
	
	/**
	 * 向数据库affair表执行update或insert语句
	 * @param sql
	 * @return
	 */
	private static boolean update(String sql)
	{
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(sql);
		db.close();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 得到当前届事务信息
	 * @return
	 */
	public static ArrayList<Affair> getAffair(String where)
	{
		if(where==null)
			where="";
		String select="select * from affair where period="+PeriodDao.get()+" "+where;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		ArrayList<Affair> Affairs=null;
		try 
		{
			Affairs=new ArrayList<Affair>();
			while(rs.next())
			{
				int id=Integer.parseInt(rs.getString("id"));
				String work=rs.getString("work");
				String startString=rs.getString("start");
				String endString=rs.getString("end");
				DateTime start=new DateTime(startString.substring(0, startString.length()-2));
				DateTime end=new DateTime(endString.substring(0, endString.length()-2));
				String columnsString=rs.getString("columns");
				String [] buff=columnsString.split(",");
				int [] columns=new int[buff.length];
				for(int i=0;i<columns.length;i++)
					columns[i]=Integer.parseInt(buff[i]);
				Affairs.add(new Affair(id, work, start, end, columns));
			}
			db.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		return Affairs;
	}
	
	/**
	 * 根据id号得到事务
	 * @param id
	 * @return
	 */
	public static Affair getAffairById(int id)
	{
		String select="select * from affair where id="+id;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);;
		try
		{
			if(rs.next())
			{
				String work=rs.getString("work");
				String startString=rs.getString("start");
				String endString=rs.getString("end");
				DateTime start=new DateTime(startString.substring(0, startString.length()-2));
				DateTime end=new DateTime(endString.substring(0, endString.length()-2));
				String columnsString=rs.getString("columns");
				String [] buff=columnsString.split(",");
				int [] columns=new int[buff.length];
				for(int i=0;i<columns.length;i++)
					columns[i]=Integer.parseInt(buff[i]);
				db.close();
				return new Affair(id, work, start, end, columns);
			}
		} 
		catch (NumberFormatException | SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * 删除事务
	 * @param id 事务id号
	 * @return
	 */
	public static boolean deleteAffair(int id)
	{
		String delete="delete from affair where id="+id;
		return update(delete);
	}
	
	/**
	 * 判断当前时间是否在指定id对应的任务开始和结束时间之间
	 * @param id
	 * @return
	 */
	public static boolean inTime(int id)
	{
		boolean in=false;
		Date now=new Date();
		Affair Affair=AffairDao.getAffairById(id);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try 
		{
			Date start=dateFormat.parse(Affair.getStart().getDBString());
			Date end=dateFormat.parse(Affair.getEnd().getDBString());
			if(start.compareTo(now)<0&&now.compareTo(end)<0)
				in=true; 
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return in;
	}
	
	/**
	 * 获取当前要执行的事务列表
	 * @return
	 */
	public static ArrayList<Affair> getDoingAffair()
	{
		String where=" and (select NOW()) between start and end order by end";
		return getAffair(where);
	}
	
	/**
	 * 有限制数量的获取当前要执行的事务列表
	 * @param limit 限制数量
	 * @return
	 */
	public static ArrayList<Affair> getDoingAffair(int limit)
	{
		String where=" and (select NOW()) between start and end order by end limit "+limit;
		return getAffair(where);
	}
}

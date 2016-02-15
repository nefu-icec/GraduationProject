package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Log;
import edu.nefu.GraduationProject.util.ConnectDB;

public class LogDao 
{
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
	 * 插入一条日志记录
	 * @param log 日志记录
	 * @return
	 */
	public static boolean insert(Log log)
	{
		String insert="insert into log(sid,tid,columnName,content,time) values("+log.getSid()
				+","+log.getTid()+",'"+log.getColumnName()+"','"+log.getContent()+"','"+log.getTime().getDBString()+"')";
		return update(insert);
	}
	
	/**
	 * 删除一条日志记录
	 * @param lid 日志记录id号
	 * @return
	 */
	public static boolean delete(int lid)
	{
		String delete="delete from log where lid="+lid;
		return update(delete);
	}
	
	public static boolean clear()
	{
		String delete="delete from log";
		return update(delete);
	}
	
	public static ArrayList<Log> getLogs(String where)
	{
		if(where==null)
			where="";
		String select="select lid,log.tid,tname,log.sid,sname,columnName,content,time"
			+" from log,teacher,data where log.tid=teacher.tid and log.sid=data.sid "+where;
		ArrayList<Log> logs=null;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			logs=new ArrayList<Log>();
			while(rs.next())
			{
				int lid=Integer.parseInt(rs.getString("lid"));
				int sid=Integer.parseInt(rs.getString("sid"));
				int tid=Integer.parseInt(rs.getString("tid"));
				String columnName=rs.getString("columnName");
				String content=rs.getString("content");
				String timeString=rs.getString("time");
				timeString=timeString.substring(0, timeString.length()-2);
				DateTime time=new DateTime(timeString);
				String sname=rs.getString("sname");
				String tname=rs.getString("tname");
				logs.add(new Log(lid, sid, tid, columnName, content, time, sname, tname));
			}
		} 
		catch (NumberFormatException | SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return logs;
	}
	
	/**
	 * 得到limit条最新的日志
	 * @param limit 日志限制的条数
	 * @return
	 */
	public static ArrayList<Log> getLogs(int limit)
	{
		String where=" order by time desc limit "+limit;
		return getLogs(where);
	}

	/**
	 * 获取最新更新的Log
	 * @return
	 */
	public static Log getNewstLog()
	{
		return getLogs(1).get(0);
	}
	
	/**
	 * 判断lid号日志是否为最新的日志
	 * @param lid 日志id号
	 * @return
	 */
	public static boolean isNewst(int lid)
	{
		if(getNewstLog().getLid()==lid)
			return true;
		return false;
	}
}

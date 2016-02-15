package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Notification;
import edu.nefu.GraduationProject.util.ConnectDB;

public class NotificationDao
{
	/**
	 * 插入一条通知
	 * @param notification 通知
	 * @return 插入是否成功
	 */
	public static boolean insert(Notification notification) 
	{
		String insert="insert into notification(title,content,publish) values('"+notification.getTitle()
				+"','"+notification.getContent()+"','"+notification.getPublish().getDBString()+"')";
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(insert);
		db.close();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 删除一条通知
	 * @param nid 消息id号
	 * @return 删除是否成功
	 */
	public static boolean delete(int nid) 
	{
		String delete="delete from notification where nid="+nid;
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(delete);
		db.close();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 查询通知
	 * @param where 查询条件
	 * @return
	 */
	public static ArrayList<Notification> getNotification(String where)
	{
		if(where==null)
			where="";
		ArrayList<Notification> notifications=null;
		String select="select * from notification "+where;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			notifications=new ArrayList<Notification>();
			while(rs.next())
			{
				int nid=Integer.parseInt(rs.getString("nid"));
				String title=rs.getString("title");
				String content=rs.getString("content");
				String sPublish = rs.getString("publish");
				sPublish = sPublish.substring(0, sPublish.length()-2);
				DateTime publish = new DateTime(sPublish);
				notifications.add(new Notification(nid,title,content,publish));
			}
			db.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			db.close();
			return null;
		}
		db.close();
		return notifications;
	}
	
	/**
	 * 从数据库获取所有通知
	 * @return 所有通知
	 */
	public static ArrayList<Notification> getNotification()
	{
		return getNotification(null);
	}
	
	/**
	 * 获取limit条通知
	 * @param limit
	 * @return
	 */
	public static ArrayList<Notification> getNotification(int limit)
	{
		String where=" order by publish desc limit "+limit;
		return getNotification(where);
	}
	
	/**
	 * 得到指定id号的通知
	 * @param nid 通知id号
	 * @return
	 */
	public static Notification getNotificationByNid(int nid)
	{
		String where=" where nid="+nid;
		return getNotification(where).get(0);
	}
}

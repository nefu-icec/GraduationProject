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
	 * ����һ��֪ͨ
	 * @param notification ֪ͨ
	 * @return �����Ƿ�ɹ�
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
	 * ɾ��һ��֪ͨ
	 * @param nid ��Ϣid��
	 * @return ɾ���Ƿ�ɹ�
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
	 * ��ѯ֪ͨ
	 * @param where ��ѯ����
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
	 * �����ݿ��ȡ����֪ͨ
	 * @return ����֪ͨ
	 */
	public static ArrayList<Notification> getNotification()
	{
		return getNotification(null);
	}
	
	/**
	 * ��ȡlimit��֪ͨ
	 * @param limit
	 * @return
	 */
	public static ArrayList<Notification> getNotification(int limit)
	{
		String where=" order by publish desc limit "+limit;
		return getNotification(where);
	}
	
	/**
	 * �õ�ָ��id�ŵ�֪ͨ
	 * @param nid ֪ͨid��
	 * @return
	 */
	public static Notification getNotificationByNid(int nid)
	{
		String where=" where nid="+nid;
		return getNotification(where).get(0);
	}
}

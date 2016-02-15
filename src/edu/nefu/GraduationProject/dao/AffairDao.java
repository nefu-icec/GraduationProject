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
	 * ����һ�������¼��affair��
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
	 * ��������ʼʱ��
	 * @param id ����id��
	 * @param start ��ʼʱ��
	 * @return
	 */
	public static boolean resetStart(int id,DateTime start)
	{
		String update="update affair set start='"+start.getDBString()+"' where id="+id;
		return update(update);
	}
	
	/**
	 * �����������ʱ��
	 * @param id ����id��
	 * @param start ����ʱ��
	 * @return
	 */
	public static boolean resetEnd(int id,DateTime end)
	{
		String update="update affair set end='"+end.getDBString()+"' where id="+id;
		return update(update);
	}
	
	/**
	 * �����ݿ�affair��ִ��update��insert���
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
	 * �õ���ǰ��������Ϣ
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
	 * ����id�ŵõ�����
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
	 * ɾ������
	 * @param id ����id��
	 * @return
	 */
	public static boolean deleteAffair(int id)
	{
		String delete="delete from affair where id="+id;
		return update(delete);
	}
	
	/**
	 * �жϵ�ǰʱ���Ƿ���ָ��id��Ӧ������ʼ�ͽ���ʱ��֮��
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
	 * ��ȡ��ǰҪִ�е������б�
	 * @return
	 */
	public static ArrayList<Affair> getDoingAffair()
	{
		String where=" and (select NOW()) between start and end order by end";
		return getAffair(where);
	}
	
	/**
	 * �����������Ļ�ȡ��ǰҪִ�е������б�
	 * @param limit ��������
	 * @return
	 */
	public static ArrayList<Affair> getDoingAffair(int limit)
	{
		String where=" and (select NOW()) between start and end order by end limit "+limit;
		return getAffair(where);
	}
}

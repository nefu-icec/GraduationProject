package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Excel;
import edu.nefu.GraduationProject.bean.GroupInfo;
import edu.nefu.GraduationProject.bean.Student;
import edu.nefu.GraduationProject.excel.GroupExcel;
import edu.nefu.GraduationProject.util.ConnectDB;
import edu.nefu.GraduationProject.util.MathTool;

public class Group 
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
	 * һ�ο������
	 * @param groups ������Ϣ����
	 * @return
	 */
	public static int firstStartGroup(int [] groups)
	{
		ArrayList<Student> students=DataDao.getWhoJoinFisrtStart();
		int success=0;
		if(StatusDao.set(6))
		{
			for(int i=0;i<groups.length;i++)
			{
				for(int j=0;j<groups[i];j++)
				{
					int random=MathTool.getRandom(students.size());
					if(DataDao.modifyWithoutCheck(students.get(random).getSid(),"firstStartGroup",i+""))
						success++;
					students.remove(random);
				}
			}			
		}
		return success;
	}
	
	/**
	 * ���ο������
	 * @param groups ������Ϣ����
	 * @return
	 */
	public static int secondStartGroup(int [] groups)
	{
		ArrayList<Student> students=DataDao.getWhoJoinSecondStart();
		int success=0;
		if(StatusDao.set(8))
		{
			for(int i=0;i<groups.length;i++)
			{
				for(int j=0;j<groups[i];j++)
				{
					int random=MathTool.getRandom(students.size());
					if(DataDao.modifyWithoutCheck(students.get(random).getSid(),"secondStartGroup",i+""))
						success++;
					students.remove(random);
				}
			}			
		}
		return success;
	}
	
	/**
	 * ������
	 * @param groups ������Ϣ����
	 * @return
	 */
	public static int endGroup(int [] groups)
	{
		ArrayList<Student> students=DataDao.getWhoJoinFirstEnd();
		int success=0;
		if(StatusDao.set(11))
		{
			for(int i=0;i<groups.length;i++)
			{
				for(int j=0;j<groups[i];j++)
				{
					int random=MathTool.getRandom(students.size());
					if(DataDao.modifyWithoutCheck(students.get(random).getSid(),"endGroup",i+""))
						success++;
					students.remove(random);
				}
			}			
		}
		return success;
	}
	
	public static ArrayList<GroupInfo> getGroup(String where)
	{
		if(where==null)
			where="";
		 ArrayList<GroupInfo> groupInfos=null;
		 String select="select sid,sname,ctitle,tname,origin,startPassed,endPassed,startQuestion,endQuestion,score"
				 +" from data,teacher where data.tid=teacher.tid "+where;
		 ConnectDB db=new ConnectDB();
		 ResultSet rs=db.exeQuery(select);
		 try 
		 {
			groupInfos=new ArrayList<GroupInfo>();
			int number=0;;
			while(rs.next())
			 {
				 number++;
				 int sid=Integer.parseInt(rs.getString("sid"));
				 String sname=rs.getString("sname");
				 String ctitle=rs.getString("ctitle");
				 String tname=rs.getString("tname");
				 String origin=rs.getString("origin");				 
				 int startPassed=(rs.getString("startPassed")==null)?-1:Integer.parseInt(rs.getString("startPassed"));
				 int endPassed=(rs.getString("endPassed")==null)?-1:Integer.parseInt(rs.getString("endPassed"));
				 String startQuestion = rs.getString("startQuestion");
				 String endQuestion = rs.getString("endQuestion");
				 int score=(rs.getString("score")==null)?-1:Integer.parseInt(rs.getString("score"));
				 groupInfos.add(new GroupInfo(number, sid, sname, ctitle, tname, origin,startPassed,endPassed,startQuestion,endQuestion,score));
			 }
		} 
		 catch (NumberFormatException | SQLException e) 
		 {
			e.printStackTrace();
			db.close();
			return null;
		}
		 db.close();
		 return groupInfos;
	}
	
	/**
	 * �õ����вμ�һ�ο����ѧ��
	 * @return
	 */
	public static ArrayList<GroupInfo> getAllFirstGroup()
	{
		String where=" and period="+PeriodDao.get()+" and startTime is not null";
		return getGroup(where);
	}
	
	/**
	 * �õ����вμӶ��ο����ѧ��
	 * @return
	 */
	public static ArrayList<GroupInfo> getAllSecondGroup()
	{
		String where=" and period="+PeriodDao.get()+" and secondStartTime is not null";
		return getGroup(where);
	}
	
	/**
	 * �õ�һ�ο�������е�group�����Ϣ
	 * @param group ���
	 * @return
	 */
	public static ArrayList<GroupInfo> getFirstGroup(int group)
	{
		if((group+1)>getGroupNumber("firstStartGroup"))
			return null;
		String where=" and period="+PeriodDao.get()+" and firstStartGroup="+group;
		return getGroup(where);
	}
	
	/**
	 * �õ����ο�������е�group�����Ϣ
	 * @param group ���
	 * @return
	 */
	public static ArrayList<GroupInfo> getSecondGroup(int group)
	{
		if((group+1)>getGroupNumber("secondStartGroup"))
			return null;
		String where=" and period="+PeriodDao.get()+" and secondStartGroup="+group;
		return getGroup(where);
	}
	
	public static ArrayList<GroupInfo> getAllEndGroup()
	{
		String where=" and period="+PeriodDao.get()+" and endTime is not null";
		return getGroup(where);
	}
	
	/**
	 * �õ����δ����Ϣ
	 * @return
	 */
	public static ArrayList<GroupInfo> getSecondEndGroup()
	{
		String where=" and period="+PeriodDao.get()+" and secondEndTime is not null";
		return getGroup(where);
	}
	
	/**
	 * �õ��������е�group����Ϣ
	 * @param group ���
	 * @return
	 */
	public static ArrayList<GroupInfo> getEndGroup(int group)
	{
		if((group+1)>getGroupNumber("endGroup"))
			return null;
		String where=" and period="+PeriodDao.get()+" and endGroup="+group;
		return getGroup(where);
	}
	
	/**
	 * ��÷�����
	 * @param column ����
	 * @return
	 */
	public static int getGroupNumber(String column)
	{
		String select="select max("+column+") from data where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			if(rs.next())
			{
				if(rs.getString("max("+column+")")==null)
				{
					db.close();
					return -1;
				}		
				else
				{
					int num=1+Integer.parseInt(rs.getString("max("+column+")"));
					db.close();
					return num;
				}		
			}
		} 
		catch (NumberFormatException | SQLException e) 
		{
			e.printStackTrace();
			db.close();
			return -1;
		}
		db.close();
		return -1;
	}
	
	/**
	 * �μ�һ�δ�����
	 * @return
	 */
	public static boolean joinFirstEnd()
	{
		String update="update data set endTime='"+SysDateDao.getFirstEnd().getStringDate()
				+"' where startPassed=1 and period="+PeriodDao.get();
		return update(update);
	}
	
	/**
	 * ָ��ѧ���μ�һ�δ�����
	 * @param sid
	 * @return
	 */
	public static boolean joinSecondStart(int sid)
	{
		String update="update data set secondStartTime='"+SysDateDao.getSecondStart().getStringDate()+"' where sid="+sid;
		return update(update);
	}
	
	/**
	 * ָ��ѧ���˳�һ�δ�����
	 * @param sid
	 * @return
	 */
	public static boolean exitSecondStart(int sid)
	{
		String update="update data set secondStartTime=null where sid="+sid;
		return update(update);
	}
	
	/**
	 * ָ��ѧ���μӶ��δ�����
	 * @param sid ѧ��id��
	 * @return
	 */
	public static boolean joinSecondEnd(int sid)
	{
		String update="update data set secondEndTime='"+SysDateDao.getSecondEnd().getStringDate()
				+"' where sid="+sid;
		return update(update);
	}
	
	/**
	 * ָ��ѧ���˳����δ�����
	 * @param sid ѧ��id��
	 * @return
	 */
	public static boolean exitSecondEnd(int sid)
	{
		String update="update data set secondEndTime=null where sid="+sid;
		return update(update);
	}
	

	
	/**
	 * ���������Excel����
	 * @param filePath ���ɱ�����ļ�·��
	 * @param title ��Ŀ
	 * @param infos ��������
	 * @return
	 */
	public static boolean createExcel(String filePath,String title,ArrayList<GroupInfo> infos)
	{
		boolean create=false;
		while(!create)
		{
			GroupExcel creator=new GroupExcel(title, title,infos);
			String fileName=creator.create(filePath);
			Excel excel=new Excel(title, new DateTime(fileName,"xls"));
			create=ExcelDao.insert(excel);
		}	
		return create;
	}
	
}
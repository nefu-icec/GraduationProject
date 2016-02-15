package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.Student;
import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.util.ConnectDB;

public class TeacherDao 
{
	/**
	 * 取得所有教师信息
	 * @param where 查询条件
	 * @return 教师信息
	 */
	public static ArrayList<Teacher> getTeacher(String where)
	{
		ArrayList<Teacher> teachers=null;
		if(where==null)
			where="";
		String select="select * from teacher "+where;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			teachers=new ArrayList<Teacher>();
			while(rs.next())
			{
				int tid=Integer.parseInt(rs.getString("tid"));
				String tname=rs.getString("tname");
				String profession=rs.getString("profession");
				String password=rs.getString("password");
				teachers.add(new Teacher(tid, tname, profession, password));
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
		return teachers;
	}
	
	public static ArrayList<Teacher> getTeacher()
	{
		return getTeacher("where profession!='管理员'");
	}
	
	/**
	 * 判断教师id是否存在
	 * @param tid 教师id
	 * @return
	 */
	public static boolean isTidExist(int tid)
	{
		String select="select * from teacher where tid="+tid;
		return exsit(select);
	}
	
	/**
	 * 判断密码是否正确
	 * @param tid 教师id
	 * @param password 密码
	 * @return
	 */
	public static boolean isPasswordRight(int tid,String password)
	{
		String select="select * from teacher where password='"+password+"' and tid="+tid;
		return exsit(select);
	}
	
	private static boolean exsit(String select)
	{
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			if(rs.next())
			{
				db.close();
				return true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			db.close();
			return false;
		}
		db.close();
		return false;
	}
	
	/**
	 * 根据教师编号得到指定教师
	 * @param tid 教师编号
	 * @return
	 */
	public static Teacher getTeacherByTid(int tid)
	{
		String where=" where tid="+tid;
		ArrayList<Teacher> teachers=getTeacher(where);
		if(teachers.get(0)!=null)
			return teachers.get(0);
		else
			return null;
	}
	
	/**
	 * 得到指定教师指导学生的学号列表
	 * @param tid 指导教师编号
	 * @return
	 */
	public static ArrayList<Student> getDistributeStudent(int tid)
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get()+" and tid="+tid;
		return DataDao.getStudents(select);
	}
	
	/**
	 * 得到指定教师指导学生的数量
	 * @param tid 指导教师编号
	 * @return
	 */
	public static int getDistributeStudentNum(int tid)
	{
		return getDistributeStudent(tid).size();
	}
	
	/**
	 * 插入一条教师记录
	 * @param teacher
	 * @return
	 */
	public static boolean insert(Teacher teacher)
	{
		String insert="insert into teacher values("+teacher.getTid()+",'"+teacher.getTname()
				+"','"+teacher.getProfession()+"','"+teacher.getPassword()+"')";
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(insert);
		db.close();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 删除一条教师记录
	 * @param tid 教师id号
	 * @return
	 */
	public static boolean delete(int tid)
	{
		String delete="delete from teacher where tid="+tid;
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(delete);
		db.close();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 修改教师的密码
	 * @param tid 教师id号
	 * @param password 要修改的密码
	 * @return
	 */
	public static boolean modifyPassword(int tid,String password)
	{
		String update="update teacher set password='"+password+"' where tid="+tid;
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(update);
		db.close();
		if(row>0)
			return true;
		return false;
	}
}

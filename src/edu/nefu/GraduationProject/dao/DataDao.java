package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.Student;
import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.excel.ExcelReader;
import edu.nefu.GraduationProject.servlet.CommonDao;
import edu.nefu.GraduationProject.util.ConnectDB;
import edu.nefu.GraduationProject.util.Constant;
import edu.nefu.GraduationProject.util.DateTool;

public class DataDao
{	
	private static boolean update(String sql)
	{
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(sql);
		db.close();
		StatusDao.check();
		if(row>0)
			return true;
		return false;
	}
	
	/**
	 * 判断列名是否在要求写入登记表的列中
	 * @param columnName
	 * @return
	 */
	private static boolean inWriteColumnName(String columnName)
	{
		for(String name:Constant.WriteColumnName)
			if(columnName.equals(name))
				return true;
		return false;
	}
	
	/**
	 * 把登记表的相关数据写入数据库data表
	 * @param path 登记表路径
	 * @return 写入是否成功
	 */
	public static boolean writeDataByRegistrationForm(String path)
	{
		ExcelReader reader=new ExcelReader(path);
		String [] firstRow=reader.getRowData(0, 0);
		String [] colleges=null,majors=null,classNames=null,sids=null,snames=null,tel=null,grade=null,status=null;
		int n=0;
		for(String columnName:firstRow)
		{
			if(inWriteColumnName(columnName))
			{
				switch (columnName) 
				{
				case "学院":
					colleges=reader.getColumnData(0, n);
					break;
				case "专业名称":
					majors=reader.getColumnData(0, n);
					break;
				case "行政班":
					classNames=reader.getColumnData(0, n);
					break;
				case "学号":
					sids=reader.getColumnData(0, n);
					break;
				case "姓名":
					snames=reader.getColumnData(0, n);
					break;
				case "联系电话":
					tel=reader.getColumnData(0, n);
					break;
				case "年级":
					grade=reader.getColumnData(0, n);
					break;
				case "学籍状态":
					status=reader.getColumnData(0, n);
					break;
				default:
					break;
				}
			}
			n++;
		}
		String [][] data={colleges,majors,classNames,sids,snames,tel,grade,status};
		String insert="insert into data(period,";
		for(String dbname:Constant.DBWriteColumnName)
			insert+=dbname+",";
		insert=insert.substring(0,insert.length()-1)+") ";
		String values;
		ConnectDB db=new ConnectDB();
		int i;
		int peroid=PeriodDao.get();
		for(i=1;i<data[0].length;i++)
		{
			values="values("+peroid+",";
			for(int j=0;j<data.length;j++)
				values+="'"+data[j][i]+"',";
			values=values.substring(0,values.length()-1)+")";
			db.exeUpdate(insert+values);
		}
		db.close();
		if(i==data.length-1)
		{
			StatusDao.set(1);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 得到data表中指定列的数据
	 * @param columnNumbers 指定的一些列
	 * @return
	 */
	public static ArrayList<String []> getColumnData(int [] columnNumbers,String where)
	{
		if(where==null)
			where="";
		ArrayList<String []> data=new ArrayList<String []>();
		String [] DBColumnNames=new String[columnNumbers.length];
		for(int i=0;i<columnNumbers.length;i++)
			DBColumnNames[i]=Constant.DBColumnName[columnNumbers[i]];
		String select="select ";
		for(String name:DBColumnNames)
			select+=name+",";
		select=select.substring(0,select.length()-1);
		select+=" from data where period="+PeriodDao.get()+where;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			while(rs.next())
			{
				String [] row=new String[columnNumbers.length];
				for(int i=0;i<row.length;i++)
					row[i]=rs.getString(DBColumnNames[i]);
				data.add(row);
			}
			db.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		return data;
	}
	
	/**
	 *清空当前届的所有数据 
	 * @return 是否成功
	 */
	public static boolean clear()
	{
		String delete="delete from data where period="+PeriodDao.get();
		if(CommonDao.isThisPeriodNull("data"))
			return true;
		return update(delete);
	}
	
	/**
	 * 修改指定单元格的信息
	 * @param sid 学号
	 * @param columnName 列名
	 * @param value 值
	 * @return 修改是否成功
	 */
	public static boolean modify(int sid,String columnName,String value)
	{
		String update="update data set "+columnName+"='"+value+"' where sid="+sid;
		StatusDao.check();
		return update(update);
	}
	
	public static boolean modifyWithoutCheck(int sid,String columnName,String value)
	{
		String update="update data set "+columnName+"='"+value+"' where sid="+sid;
		return update(update);
	}
	
	/**
	 * 设置指定单元格为null
	 * @param sid 学号
	 * @param columnName 列名
	 * @return 修改是否成功
	 */
	public static boolean setNull(int sid,String columnName)
	{
		String update="update data set "+columnName+"=null where sid="+sid;
		return update(update);
	}
	
	/**
	 * 得到指定学生的指定列的信息
	 * @param sid 学生学号
	 * @param columnName 列名
	 * @return
	 */
	public static String get(int sid,String columnName)
	{
		String select="select "+columnName+" from data where sid="+sid;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			if(rs.next())
			{
				String value=rs.getString(columnName);
				db.close();
				return value;
			}		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			db.close();
			return null;
		}
		return null;
	}
	
	/**
	 * 得到教师分配情况
	 * @return 已分配的学生数，未分配的学生数
	 */
	public static String getDistributeTeacherInfo()
	{
		int distrubute=0;
		int undistribute=0;
		String select="select tid from data where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			while(rs.next())
			{
				String tid=rs.getString("tid");
				if(tid==null)
					undistribute++;
				else
					distrubute++;
			}
			db.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		db.close();
		return distrubute+","+undistribute;
	}

	/**
	 * 得到项目信息
	 * @return
	 */
	public static ArrayList<String []> getProjectInfo()
	{
		ArrayList<String []> projectInfo=DataDao.getColumnData(Constant.ProjectInfoNum,null);
		for(int i=0;i<projectInfo.size();i++)
		{
			if(projectInfo.get(i)[4]!=null)
			{
				int tid=Integer.parseInt(projectInfo.get(i)[4]);
				Teacher teacher=TeacherDao.getTeacherByTid(tid);
				projectInfo.get(i)[4]=teacher.getTname();
				projectInfo.get(i)[5]=teacher.getProfession();
			}		
		}
		return projectInfo;
	}
	
	/**
	 * 得到未分配教师的学生信息
	 * @return
	 */
	public static ArrayList<String []> getUndistributeInfo()
	{
		ArrayList<String []> studentInfo=DataDao.getColumnData(Constant.DistrubuteStudentInfoNum,null);
		ArrayList<String []> newStudentInfo=new ArrayList<String []>();
		for(int i=0;i<studentInfo.size();i++)
		{
			String [] row=studentInfo.get(i);
			if(row[0]==null)
				newStudentInfo.add(row);
		}
		return newStudentInfo;	
 	}
	
	/**
	 * 得到未分配的学生id号
	 * @return
	 */
	public static ArrayList<Integer> getUndistributeSid()
	{
		String select="select sid,tid from data where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ArrayList<Integer> sids=null;
		ResultSet rs=db.exeQuery(select);
		try 
		{
			sids=new ArrayList<Integer>();
			while(rs.next())
				if(rs.getString("tid")==null)
					sids.add(Integer.parseInt(rs.getString("sid")));
			db.close();
		} 
		catch (NumberFormatException | SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		db.close();
		return sids;
	}
	
	/**
	 * 判断当前届的某一列元素中是否还存在null
	 * @param columnName 列名
	 * @return
	 */
	public static boolean hasNull(String columnName)
	{
		String select="select "+columnName+" from data where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			while(rs.next())
				if(rs.getString(columnName)==null)
				{
					db.close();
					return true;
				}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 判断当前届的某一列元素中是否还存在null
	 * @param columnName 列名
	 * @param where 附加条件
	 * @return
	 */
	public static boolean hasNull(String columnName,String where)
	{
		String select="select "+columnName+" from data "+where+" and period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			while(rs.next())
				if(rs.getString(columnName)==null)
				{
					db.close();
					return true;
				}				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		db.close();
		return false;
	}
	
	/**
	 * 判断当前届的某两列元素中是否还存在两个元素都为null的行
	 * @param columnName1 列名1
	 * @param columnName2 列名2
	 * @return
	 */
	public static boolean hasNull2(String columnName1,String columnName2)
	{
		String select="select "+columnName1+","+columnName2+" from data where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			while(rs.next())
				if(rs.getString(columnName1)==null&&rs.getString(columnName2)==null)
				{
					db.close();
					return true;
				}				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		db.close();
		return false;
	}
	
	public static ArrayList<Student> getStudents(String select)
	{
		ConnectDB db=new ConnectDB();
		ArrayList<Student> sudents=null;
		ResultSet rs=db.exeQuery(select);
		try 
		{
			sudents=new ArrayList<Student>();
			while(rs.next())
			{
				String college=rs.getString("college");
				String major=rs.getString("major");
				String className=rs.getString("className");
				int sid=Integer.parseInt(rs.getString("sid"));
				String sname=rs.getString("sname");
				String tel=rs.getString("tel");
				int grade=Integer.parseInt(rs.getString("grade"));
				String status=rs.getString("status");
				DateTool startTime=DateTool.initDateTool(rs.getString("startTime"));
				DateTool secondStartTime=DateTool.initDateTool(rs.getString("secondStartTime"));
				DateTool endTime=DateTool.initDateTool(rs.getString("endTime"));
				DateTool secondEndTime=DateTool.initDateTool(rs.getString("secondEndTime"));
				sudents.add(new Student
						(college, major, className, sid, sname, tel, grade, status, startTime, secondStartTime, endTime, secondEndTime));
			}
		} 
		catch (NumberFormatException | SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		db.close();
		return sudents;
	}
	
	/**
	 * 得到参加第一次开题的学生信息
	 * @return
	 */
	public static ArrayList<Student> getWhoJoinFisrtStart()
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get()+" and startTime is not null";
		return getStudents(select);
	}
	
	/**
	 * 得到参加第二次开题的学生信息
	 * @return
	 */
	public static ArrayList<Student> getWhoJoinSecondStart()
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get()+" and secondStartTime is not null";
		return getStudents(select);
	}
	
	/**
	 * 得到本届所有学生信息
	 * @return
	 */
	public static ArrayList<Student> getWhole()
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get();
		return getStudents(select);
	}
	
	public static ArrayList<Student> getWhoJoinFirstEnd()
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get()+" and startPassed=1";
		return getStudents(select);
	}
	
	/**
	 * 得到参加第二次答辩的学生信息
	 * @return
	 */
	public static ArrayList<Student> getWhoJoinSecondEnd()
	{
		String select="select college,major,className,sid,sname,tel,grade,status,startTime,secondStartTime,endTime,secondEndTime"
				+" from data where period="+PeriodDao.get()+" and secondEndTime is not null";
		return getStudents(select);
	}
	
	public static ArrayList<String []> getAffairInfo(int [] affairColumns,int tid)
	{
		int [] columns=new int[3+affairColumns.length];
		columns[0]=4;
		columns[1]=5;
		columns[2]=3;
		for(int i=0;i<affairColumns.length;i++)
			columns[i+3]=affairColumns[i];
		return getColumnData(columns, " and tid="+tid);
	}
}
package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Excel;
import edu.nefu.GraduationProject.util.ConnectDB;

public class ExcelDao 
{
	/**
	 * 插入一条项目
	 * @param excel 项目的信息
	 * @return
	 */
	public static boolean insert(Excel excel)
	{
		String insert="insert into excel(ename,createDate) values('"
				+excel.getEname()+"','"+excel.getCreateDate().getDBString()+"')";
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(insert);
		db.close();
		if(row>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 删除指定项目
	 * @param eid
	 * @return
	 */
	public static boolean delete(int eid)
	{
		String delete="delete from excel where eid="+eid;
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(delete);
		db.close();
		if(row>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 删除所有项目
	 * @return
	 */
	public static boolean deleteAll()
	{
		String delete="delete from excel";
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(delete);
		db.close();
		if(row>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 搜索指定项目
	 * @param where sql查询条件
	 * @return
	 */
	public static ArrayList<Excel> getExcel(String where)
	{
		if(where==null)
			where="";
		ArrayList<Excel> excels=null;
		String select="select * from excel "+where;
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try 
		{
			excels=new ArrayList<Excel>();
			while(rs.next())
			{
				int eid=Integer.parseInt(rs.getString("eid"));
				String ename=rs.getString("ename");
				String date=rs.getString("createDate");
				DateTime createDate=new DateTime(date.substring(0,date.length()-2));
				excels.add(new Excel(eid, ename, createDate));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.close();
			return null;
		}
		db.close();
		return excels;
	}
	
	/**
	 * 按时间搜索指定项目
	 * @param start 起始时间
	 * @param end 结束时间
	 * @return
	 */
	public static ArrayList<Excel> searchExcelByTime(DateTime start,DateTime end)
	{
		String where=" where createDate between '"+start.getDBString()+"' and '"+end.getDBString()+"'";
		ArrayList<Excel> excels=getExcel(where);
		return excels;
	}
	
	public static Excel getExcelById(int eid)
	{
		String where=" where eid="+eid;
		ArrayList<Excel> excels=getExcel(where);
		if(excels!=null)
			return excels.get(0);
		return null;
	}
}

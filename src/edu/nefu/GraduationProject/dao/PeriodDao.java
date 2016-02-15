package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.nefu.GraduationProject.util.ConnectDB;

public class PeriodDao
{
	public static int get()
	{
		String select="select year from period";
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		int year=0;
		try
		{
			if(rs.next())
				year=Integer.parseInt(rs.getString("year"));
		} 
		catch (SQLException e)
		{	
			e.printStackTrace();
			return -1;
		}
		db.close();
		return year;
	}
	
	public static boolean set(int period)
	{
		String update="update period set year="+period;
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(update);
		db.close();
		if(row>0)
			return true;
		return false;
	}
}

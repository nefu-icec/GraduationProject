package edu.nefu.GraduationProject.servlet;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.nefu.GraduationProject.dao.PeriodDao;
import edu.nefu.GraduationProject.util.ConnectDB;

public class CommonDao 
{
	
	public static boolean isThisPeriodNull(String tableName)
	{
		String select="select count(*) from "+tableName+" where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			if(rs.next())
				if(Integer.parseInt(rs.getString("count(*)"))==0)
					return true;
		} 
		catch (NumberFormatException | SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(isThisPeriodNull("data"));
	}
}

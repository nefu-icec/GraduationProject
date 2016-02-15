package edu.nefu.GraduationProject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB 
{
	Connection connection=null;
	Statement statement=null;
	
	public ConnectDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/graduationproject?user=root&password=";
			connection=DriverManager.getConnection(url);
			statement=connection.createStatement();
//			Context context=new InitialContext();
//			DataSource ds=(DataSource)context.lookup("java://comp/env/jdbc/graduationproject");
//			connection=ds.getConnection();
//			statement=connection.createStatement();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ResultSet exeQuery(String sql)
	{
		ResultSet rs=null;
		try {
			rs=statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;	
	}
	
	public int exeUpdate(String sql)
	{
		int flag=0;
		try {
			flag=statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public void close()
	{
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

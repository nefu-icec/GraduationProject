package edu.nefu.GraduationProject.util;

import java.net.InetAddress;

public class NetTool 
{
	public static String getIp()
	{
		try 
		{		
			InetAddress addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
			return ip;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getHostName()
	{
		try 
		{		
			InetAddress addr = InetAddress.getLocalHost();
			String name=addr.getHostName().toString();//获得本机名称
			return name;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getIpPort()
	{
		try 
		{		
			InetAddress addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
			return ip+":"+Constant.ServerPort;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}

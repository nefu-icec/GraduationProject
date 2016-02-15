package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.StatusInfo;
import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.AffairDao;
import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.dao.StatusDao;
import edu.nefu.GraduationProject.dao.SysDateDao;
import edu.nefu.GraduationProject.util.DateTool;
import edu.nefu.GraduationProject.websocket.LogMessageInboundPool;

@WebServlet("/StatusServlet")
public class StatusServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public StatusServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task) 
		{
		case "get":
			get(request,response);
			break;
		case "set":
			set(request,response);
			break;
		case "getStatusInfo":
			getAllInfo(request,response);
			break;
		case "getServerTime":
			getServerTime(response);
			break;
		case "checkStatus":
			StatusDao.check();
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task) 
		{
		case "clearData":
			clearData(request,response);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置当前届系统状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void set(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String value=request.getParameter("value");
		boolean success=StatusDao.set(Integer.parseInt(value));
		PrintWriter out=response.getWriter();
		out.print(success);
	}

	/**
	 * 获取当前届系统状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void get(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int value=StatusDao.get();
		PrintWriter out=response.getWriter();
		out.print(value);
	}
	
	/**
	 * 清除当前届所有信息
	 * @param request password 管理员密码
	 * @param response
	 * @throws IOException
	 */
	private void clearData(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out=response.getWriter();
		String password=request.getParameter("password");
		Teacher teacher=SessionServlet.getTeacher(request);
		if(teacher.getPassword().equals(password))
		{
			if(StatusDao.set(0)&&SysDateDao.clear()&&AffairDao.clear()&&DataDao.clear())
			{
				LogMessageInboundPool.sendMessageOther(teacher, "ClearData:=true");
				out.print(true);
			}				
			else
				out.print(false);	
		}
		else
			out.print("passwordError");
	}
	
	/**
	 * 得到服务器当前时间
	 * @param response
	 * @throws IOException 
	 */
	private void getServerTime(HttpServletResponse response) throws IOException 
	{
		String now=DateTool.getSqlTime();
		response.getWriter().print(now);
	}

	private void getAllInfo(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		StatusInfo info=new StatusInfo();
		String print=info.getPeriod()+"$";
		print+=info.getLastStatus()+"$";
		print+=info.getNowStatus()+"$";
		print+=info.getTask()+"$";
		print+=info.getNextStatus()+"$";
		print+=info.getStatus();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(print);
	}
}

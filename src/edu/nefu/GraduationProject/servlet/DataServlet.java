package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Log;
import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.dao.LogDao;
import edu.nefu.GraduationProject.util.DateTool;
import edu.nefu.GraduationProject.websocket.LogMessageInboundPool;

@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public DataServlet() 
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
		case "setNull":
			setNull(request,response);
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
		case "modify":
			modify(request,response);
			break;

		default:
			break;
		}
	}

	/**
	 * 更改指定单元格的内容
	 * @param request
	 * @param response
	 * @throws IOException
	 * @request sid 学生编号
	 * @request columnName 要更改的列名
	 * @request value 要更改的值
	 * @print 更改是否成功
	 */
	private void modify(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		String columnName=request.getParameter("columnName");
		String value=request.getParameter("value");
		value=new String(value.getBytes("UTF-8") , "UTF-8");
		boolean success=DataDao.modify(sid, columnName, value);
		writeLog(request, sid, columnName, value);
		response.getWriter().print(success);
	}
	

	private void setNull(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		String columnName=request.getParameter("columnName");
		boolean success=DataDao.setNull(sid, columnName);
		writeLog(request, sid, columnName, "");
		response.getWriter().print(success);
	}
	

	private void get(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String columnName=request.getParameter("columnName");
		int sid=Integer.parseInt(request.getParameter("sid"));
		String value=DataDao.get(sid, columnName);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(value);
	}
	
	public static void writeLog(HttpServletRequest request, int sid, String columnName, String value)
	{
		Teacher teacher=SessionServlet.getTeacher(request);
		String time=DateTool.getSqlTime2();
		Log log=new Log(sid, teacher.getTid(), columnName, value, new DateTime(time));
		if(LogDao.insert(log))
		{
			Log push=LogDao.getNewstLog();
			String message=push.getLid()+"$&$";//0
			message+=push.getTime().getDBString()+"$&$";//1
			message+=push.getSid()+"$&$";//2
			message+=push.getSname()+"$&$";//3
			message+=push.getTid()+"$&$";//4
			message+=push.getTname()+"$&$";//5
			message+=push.getColumnName()+"$&$";//6
			message+=push.getContent();//7
			LogMessageInboundPool.sendMessageOther(teacher, "Modify:="+message);
		}
	}
}

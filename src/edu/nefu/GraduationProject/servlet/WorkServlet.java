package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.Affair;
import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.dao.AffairDao;

@WebServlet("/WorkServlet")
public class WorkServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public WorkServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "inTime":
			inTime(request,response);
			break;
		case "deleteAffair":
			deleteAffair(request,response);
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
		case "addWork":
			addWork(request,response);
			break;
		case "resetStart":
			resetStart(request, response);
			break;
		case "resetEnd":
			resetEnd(request, response);
			break;
		default:
			break;
		}
	}

	private void addWork(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
		String work=request.getParameter("work");
		String start=request.getParameter("start")+":00";
		String end=request.getParameter("end")+":00";
		String columns=request.getParameter("columns");
		columns=columns.substring(0, columns.length()-1);
		DateTime startTime=new DateTime(start);
		DateTime endTime=new DateTime(end);
		Affair Affair=new Affair(work, startTime, endTime,columns);
		boolean success=AffairDao.setWork(Affair);
		response.getWriter().print(success);
	}
	
	/**
	 * 重设开始时间
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void resetStart(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String startString=request.getParameter("start")+":00";
		int id=Integer.parseInt(request.getParameter("id"));
		DateTime start=new DateTime(startString);
		boolean success=AffairDao.resetStart(id, start);
		response.getWriter().print(success);
	}
	
	/**
	 * 重设截至时间
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void resetEnd(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String endString=request.getParameter("end")+":00";
		int id=Integer.parseInt(request.getParameter("id"));
		DateTime end=new DateTime(endString);
		boolean success=AffairDao.resetEnd(id, end);
		response.getWriter().print(success);
	}

	/**
	 * 判断当前时间是否在指定id对应的任务开始和结束时间之间
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void inTime(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{	
		int id=Integer.parseInt(request.getParameter("id"));
		boolean in=AffairDao.inTime(id);
		response.getWriter().print(in);
	}
	
	/**
	 * 删除指定事务
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void deleteAffair(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int id=Integer.parseInt(request.getParameter("id"));
		boolean success=AffairDao.deleteAffair(id);
		response.getWriter().print(success);
	}
}

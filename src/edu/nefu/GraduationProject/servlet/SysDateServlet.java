package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.SysDateDao;
import edu.nefu.GraduationProject.util.DateTool;

@WebServlet("/SysDateServlet")
public class SysDateServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public SysDateServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		PrintWriter out=response.getWriter();
		Teacher teacher=SessionServlet.getTeacher(request);
		switch (task)
		{
		case "setFirstStart":
//			LogMessageInboundPool.sendMessageOther(teacher, "Set:=设置一次开题时间");
			out.print(SysDateDao.setFirstStart(getStringDate(request)));
			break;
		case "setSecondStart":
//			LogMessageInboundPool.sendMessageOther(teacher, "Set:=设置二次开题时间");
			out.print(SysDateDao.setSecondStart(getStringDate(request)));
			break;
		case "setFirstEnd":
//			LogMessageInboundPool.sendMessageOther(teacher, "Set:=设置一次答辩时间");
			out.print(SysDateDao.setFirstEnd(getStringDate(request)));
			break;
		case "setSecondEnd":
//			LogMessageInboundPool.sendMessageOther(teacher, "Set:=设置二次答辩时间");
			out.print(SysDateDao.setSecondEnd(getStringDate(request)));
			break;
		case "getFirstStart":
			out.print(SysDateDao.getFirstStart().getStringDate());
			break;
		case "getFirstEnd":
			out.print(SysDateDao.getFirstEnd().getStringDate());
			break;
		case "getSecondStart":
			out.print(SysDateDao.getSecondStart().getStringDate());
			break;
		case "getSecondEnd":
			out.print(SysDateDao.getSecondEnd().getStringDate());
			break;	
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	private static DateTool getStringDate(HttpServletRequest request)
	{
		return new DateTool(request.getParameter("date"));
	}

}

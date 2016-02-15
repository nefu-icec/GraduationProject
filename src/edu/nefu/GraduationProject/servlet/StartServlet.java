package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.GroupInfo;
import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.dao.Group;
import edu.nefu.GraduationProject.dao.PeriodDao;
import edu.nefu.GraduationProject.dao.SysDateDao;
import edu.nefu.GraduationProject.util.DateTool;

@WebServlet("/StartServlet")
public class StartServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public StartServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task) 
		{
		case "joinFirstStart":
			joinFirstStart(request,response);
			break;
		case "joinSecondStart":
			joinSecondStart(request,response);
			break;
		case "firstGroup":
			firstGroup(request,response);
			break;
		case "secondGroup":
			secondGroup(request,response);
			break;	
		case "createExcel":
			createExcel(request,response);
			break;
		case "setStartPassed":
			setStartPassed(request,response);
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


		default:
			break;
		}
	}

	private void joinFirstStart(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		DateTool firstStart=SysDateDao.getFirstStart();
		boolean success=DataDao.modify(sid,"startTime", firstStart.getStringDate())
				&&DataDao.setNull(sid, "secondStartTime");
		DataServlet.writeLog(request, sid, "startTime", firstStart.getStringDate());
		response.getWriter().print(success);
	}
	
	private void joinSecondStart(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		DateTool secondStart=SysDateDao.getSecondStart();
		boolean success=DataDao.modify(sid,"secondStartTime", secondStart.getStringDate())
				&&DataDao.setNull(sid, "startTime");
		DataServlet.writeLog(request, sid, "secondStartTime", secondStart.getStringDate());
		response.getWriter().print(success);
	}
	
	private void firstGroup(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int [] groups=transform(request);
		int success=Group.firstStartGroup(groups);
		response.getWriter().print(success);
	}

	private void secondGroup(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int [] groups=transform(request);
		int success=Group.secondStartGroup(groups);
		response.getWriter().print(success);
	}
	
	private int [] transform(HttpServletRequest request)
	{
		String [] groupString=request.getParameter("group").split(",");
		int [] groups=new int[groupString.length];
		for(int i=0;i<groups.length;i++)
			groups[i]=Integer.parseInt(groupString[i]);
		return groups;
	}
	
	private void createExcel(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String type=request.getParameter("type");
		int group=Integer.parseInt(request.getParameter("group"));
		ArrayList<GroupInfo> infos=null;
		String title="";
		switch (type) 
		{
		case "firstGroup":
			infos=Group.getFirstGroup(group);
			title=PeriodDao.get()+"届计算机专业毕业设计第一次开题名单（第"+(group+1)+"组）";
			break;
		case "secondGroup":
			infos=Group.getSecondGroup(group);
			title=PeriodDao.get()+"届计算机专业毕业设计第二次开题名单（第"+(group+1)+"组）";
			break;
		default:
			break;
		}
		if(infos!=null)
		{
			String rootPath=getServletConfig().getServletContext().getRealPath("/");
			String filePath=rootPath+"file\\excel\\";
			boolean create=Group.createExcel(filePath, title, infos)	;
			response.getWriter().print(create);
		}
		else
			response.getWriter().print(false);
	}

	private void setStartPassed(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		int passed=Integer.parseInt(request.getParameter("passed"));
		boolean success=false;
		if(passed==1)
		{
			success=DataDao.modify(sid, "startPassed", String.valueOf(passed))
					&&Group.exitSecondStart(sid);
			DataServlet.writeLog(request, sid, "secondStartTime",null);
		}
		else
		{
			success=DataDao.modifyWithoutCheck(sid, "startPassed", String.valueOf(passed))
					&&Group.joinSecondStart(sid);
			DataServlet.writeLog(request, sid, "secondStartTime",SysDateDao.getSecondStart().getStringDate());
		}
		DataServlet.writeLog(request, sid, "startPassed", String.valueOf(passed));
		response.getWriter().print(success);
	}

}
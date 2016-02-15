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

@WebServlet("/EndServlet")
public class EndServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public EndServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "endGroup":
			endGroup(request,response);
			break;
		case "createExcel":
			createExcel(request,response);
		case "setEndPassed":
			setEndPassed(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
	
	private void endGroup(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int [] groups=transform(request);
		int success=Group.endGroup(groups);
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
		int group=-1;
		String title="";
		if(request.getParameter("group")!=null)
			group=Integer.parseInt(request.getParameter("group"));
		ArrayList<GroupInfo> infos=null;
		switch (type) 
		{
		case "endGroup":
			infos=Group.getEndGroup(group);
			title=PeriodDao.get()+"届计算机专业毕业设计答辩名单（第"+(group+1)+"组）";
			break;
		case "secondEndGroup":
			infos=Group.getSecondEndGroup();
			title=PeriodDao.get()+"届计算机专业毕业设计第二次答辩名单";
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

	private void setEndPassed(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int sid=Integer.parseInt(request.getParameter("sid"));
		int passed=Integer.parseInt(request.getParameter("passed"));
		boolean success=false;
		if(passed==1)
		{
			success=DataDao.modify(sid, "endPassed", String.valueOf(passed))
					&&Group.exitSecondEnd(sid);
			DataServlet.writeLog(request, sid, "secondEndTime",null);
		}
		else
		{
			success=DataDao.modifyWithoutCheck(sid, "endPassed", String.valueOf(passed))
					&&Group.joinSecondEnd(sid);
			DataServlet.writeLog(request, sid, "secondEndTime",SysDateDao.getSecondEnd().getStringDate());
		}
		DataServlet.writeLog(request, sid, "endPassed", String.valueOf(passed));
		response.getWriter().print(success);
	}

}

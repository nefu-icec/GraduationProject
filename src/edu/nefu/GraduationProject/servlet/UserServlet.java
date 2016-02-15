package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.TeacherDao;
import edu.nefu.GraduationProject.util.MathTool;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public UserServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "checkTid":
			checkTid(request,response);
			break;
		case "checkPassword":
			checkPassword(request,response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		task=request.getParameter("task");
		switch (task) {
		case "modifyPassword":
			modifyPassword(request,response);
			break;
		
		default:
			break;
		}
	}

	private void checkPassword(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String sTid=request.getParameter("tid");
		if(MathTool.isNumeric(sTid)&&!sTid.equals(""))
		{
			int tid=Integer.parseInt(sTid);
			String password=request.getParameter("password");
			boolean success=TeacherDao.isPasswordRight(tid, password);
			response.getWriter().print(success);
		}
		else
			response.getWriter().print(false);
	}

	private void checkTid(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{	
		String sTid=request.getParameter("tid");
		if(MathTool.isNumeric(sTid)&&!sTid.equals(""))
		{
			int tid=Integer.parseInt(sTid);
			boolean success=TeacherDao.isTidExist(tid);
			response.getWriter().print(success);
		}
		else
			response.getWriter().print(false);
	}
	
	private void modifyPassword(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		Teacher teacher=SessionServlet.getTeacher(request);
		if(teacher!=null)
		{
			int tid=teacher.getTid();
			String password=request.getParameter("password");
			boolean success=TeacherDao.modifyPassword(tid, password);
			if(success)
			{
				teacher.setPassword(password);
				session.removeAttribute("teacher");
				session.setAttribute("teacher", teacher);
			}
			out.print(success);
		}
		else
			out.print("sessionError");
	}
}

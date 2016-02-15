package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.Teacher;

@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public ApplicationServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "isOnline":
			isOnline(request,response);
			break;
		case "getOnlineTeachers":
			getOnlineTeachers(request,response);
			break;
		case "getOnlineNumber":
			getOnlineNumber(request,response);
		break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	}
	
	private void isOnline(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int tid=Integer.parseInt(request.getParameter("tid"));
		boolean online=isOnline(getServletContext(),tid);
		response.getWriter().print(online);
	}
	
	private void getOnlineTeachers(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		ServletContext application= getServletContext();
		String print="";
		if(application.getAttribute("teachers")!=null)
		{
			ArrayList<Teacher> teachers=getOnlineTeachers(application);
			for(Teacher teacher:teachers)
				print+=teacher.getTid()+","+teacher.getTname()+","+teacher.getProfession()+";";
		}	
		out.print(print);
	}

	private void getOnlineNumber(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.getWriter().print(getOnlineNumber(getServletContext()));
	}

	public static boolean addTeacher(ServletContext application,Teacher teacher)
	{
		if(!isOnline(application, teacher.getTid()))
		{
			ArrayList<Teacher> teachers=new ArrayList<Teacher>();
			if(application.getAttribute("teachers")!=null)
				teachers=getOnlineTeachers(application);
			teachers.add(teacher);
			application.removeAttribute("teachers");
			application.setAttribute("teachers", teachers);
			return true;
		}
		return false;
	}
	
	public static boolean remove(ServletContext application,Teacher teacher)
	{
		if(application.getAttribute("teachers")!=null)
		{
			ArrayList<Teacher> teachers=getOnlineTeachers(application);
			for(int i=0;i<teachers.size();i++)
				if(teacher.getTid()==teachers.get(i).getTid())
					teachers.remove(i);
			application.removeAttribute("teachers");
			application.setAttribute("teachers", teachers);
		}
		return true;
	}
	
	/**
	 * 判断id号为tid的教师用户是否在线
	 * @param application
	 * @param tid tid
	 * @return
	 */
	public static boolean isOnline(ServletContext application,int tid)
	{
		if(application.getAttribute("teachers")!=null)
		{
			ArrayList<Teacher> teachers=getOnlineTeachers(application);
			for(Teacher teacher:teachers)
			{
				if(teacher.getTid()==tid)
					return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Teacher> getOnlineTeachers(ServletContext application)
	{
		if(application.getAttribute("teachers")!=null)
			return (ArrayList<Teacher>)application.getAttribute("teachers");
		return null;
	}
	
	public static int getOnlineNumber(ServletContext application)
	{
		if(getOnlineTeachers(application)!=null)
			return getOnlineTeachers(application).size();
		return 0;
	}
}

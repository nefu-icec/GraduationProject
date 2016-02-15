package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.TeacherDao;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public SessionServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "setTeacherByTid":
			setTeacherByTid(request,response);
			break;
		case "removeTeacher":
			removeTeacher(request,response);
			break;
		case "getTeacher":
			getTeacher(request,response);
			break;
		case "isAdmin":
			isAdmin(request,response);
			break;
		default:
			break;
		}
	}

	private void getTeacher(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		Teacher teacher=getTeacher(request);
		String show="";
		if(teacher!=null)
			show=teacher.showMe();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(show);
	}

	private void removeTeacher(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		HttpSession session=request.getSession();
		Teacher teacher=getTeacher(request);
		if(teacher!=null)
		{
			ApplicationServlet.remove(getServletContext(), teacher);
//			LogMessageInboundPool.sendMessageOther(teacher,"TeacherExit:="+teacher.getTid()+","+teacher.getTname());
		}
		session.removeAttribute("teacher");	
		response.sendRedirect("index.html");
	}

	private void setTeacherByTid(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		HttpSession session=request.getSession();
		int tid=Integer.parseInt(request.getParameter("tid"));
		Teacher teacher=TeacherDao.getTeacherByTid(tid);
		session.removeAttribute("teacher");
		session.setAttribute("teacher", teacher);
		ApplicationServlet.addTeacher(getServletContext(), teacher);
//		LogMessageInboundPool.sendMessageOther(teacher,"NewTeacherLogin:="+teacher.getTid()+","+teacher.getTname());
		response.getWriter().print(true);
	}
	
	private void isAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		boolean isAdmin=isAdmin(request);
		response.getWriter().print(isAdmin);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	public static boolean isAdmin(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		Teacher teacher=(Teacher)session.getAttribute("teacher");
		boolean isAdmin=teacher.getProfession().equals("π‹¿Ì‘±");
		return isAdmin;
	}
	
	public static Teacher getTeacher(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		if(session.getAttribute("teacher")!=null)
			return (Teacher)session.getAttribute("teacher");
		return null;
	}
}

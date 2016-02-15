package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.Student;
import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.dao.TeacherDao;

@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public TeacherServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "delete":
			delete(request,response);
			break;
		case "getDistributeStudent":
			getDistributeStudent(request,response);
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "add":
			add(request,response);
			break;

		default:
			break;
		}
	}

	/**
	 * 增加一名教师
	 * @param request tid 教师编号
	 * @param request tname 教师姓名
	 * @param request profession 教师职称
	 * @param request password 教师密码
	 * @param response
	 * @throws IOException
	 */
	private void add(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
		String stid=request.getParameter("tid");
		String tname=request.getParameter("tname");
		tname=new String(tname.getBytes("UTF-8") , "UTF-8");
		String profession=request.getParameter("profession");
		profession=new String(profession.getBytes("UTF-8") , "UTF-8");
		String password=request.getParameter("password");
		if(stid.equals("")||tname.equals("")||password.equals(""))
			response.getWriter().print("nullParameter");
		else
		{
			int tid=Integer.parseInt(stid);
			boolean success=TeacherDao.insert(new Teacher(tid, tname, profession, password));
			response.getWriter().print(success);			
		}

	}

	/**
	 * 删除一名教师
	 * @param request tid 教师编号
	 * @param response
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int tid=Integer.parseInt(request.getParameter("tid"));
		boolean success=TeacherDao.delete(tid);
		response.getWriter().print(success);
	}
	
	/**
	 * 得到一名教师指导的所有学生的信息
	 * @param request tid 教师编号
	 * @param response
	 * @throws IOException
	 */
	private void getDistributeStudent(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int tid=Integer.parseInt(request.getParameter("tid"));
		ArrayList<Student> students=TeacherDao.getDistributeStudent(tid);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();	
		String print="";
		for(Student student:students)
		{
			print=student.getSid()+","+student.getSname()+","+student.getClassName()+";";
			out.println(print);
		}
	}
}

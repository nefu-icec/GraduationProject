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
	 * ����һ����ʦ
	 * @param request tid ��ʦ���
	 * @param request tname ��ʦ����
	 * @param request profession ��ʦְ��
	 * @param request password ��ʦ����
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
	 * ɾ��һ����ʦ
	 * @param request tid ��ʦ���
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
	 * �õ�һ����ʦָ��������ѧ������Ϣ
	 * @param request tid ��ʦ���
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

package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.dao.LogDao;

@WebServlet("/LogDispServlet")
public class LogDispServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;

    public LogDispServlet()
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
		case "clear":
			clear(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	private void clear(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		boolean success=LogDao.clear();
		response.getWriter().print(success);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int lid=Integer.parseInt(request.getParameter("lid"));
		boolean success=LogDao.delete(lid);
		response.getWriter().print(success);
	}

}

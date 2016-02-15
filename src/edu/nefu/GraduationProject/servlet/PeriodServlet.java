package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.dao.PeriodDao;

@WebServlet("/PeriodServlet")
public class PeriodServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public PeriodServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "setPeriod":
			setPeriod(request,response);
			break;
		case "getPeriod":
			getPeriod(request,response);
			break;
		default:
			break;
		}
	}

	private void getPeriod(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int period=PeriodDao.get();
		response.getWriter().print(period);
	}

	private void setPeriod(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int period=Integer.parseInt(request.getParameter("period"));
		boolean success=PeriodDao.set(period);
		response.getWriter().print(success);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

}

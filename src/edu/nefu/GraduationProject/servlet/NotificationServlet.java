package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Notification;
import edu.nefu.GraduationProject.dao.NotificationDao;
import edu.nefu.GraduationProject.util.DateTool;

@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
	
       
    public NotificationServlet()
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
		case "getNotification":
			getNotification(request,response);
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
		case "add":
			add(request,response);
			break;

		default:
			break;
		}
	}

	/**
	 * ����һ��֪ͨ
	 * @param request title ���ű���
	 * @param request content ��������
	 * @param response �����Ƿ�ɹ�
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		DateTime publish=new DateTime(DateTool.getSqlTime2());
		boolean success=NotificationDao.insert(new Notification(title, content, publish));
		response.getWriter().print(success);
	}

	/**
	 * ɾ��һ��֪ͨ
	 * @param request nid ֪ͨid�� 
	 * @param response
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int nid=Integer.parseInt(request.getParameter("nid"));
		boolean success=NotificationDao.delete(nid);
		response.getWriter().print(success);
	}
	
	/**
	 * �õ�һ��֪ͨ
	 * @param request nid ֪ͨid��
	 * @param response
	 * @throws IOException 
	 */
	private void getNotification(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int nid=Integer.parseInt(request.getParameter("nid"));
		Notification notification=NotificationDao.getNotificationByNid(nid);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(notification.getTitle()+"$&$&$"
				+notification.getContent()+"$&$&$"+notification.getPublish().getDBString());
	}

}

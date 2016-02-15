package edu.nefu.GraduationProject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.util.NetTool;

@WebServlet("/IpServlet")
public class IpServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public IpServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task) {
		case "getServerIP":
			getServerIP(response);
			break;
		case "getServerIpPort":
			getServerIpPort(response);
			break;
		case "sendMessage":
			sendMessage(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	private void getServerIP(HttpServletResponse response) throws IOException
	{
		response.getWriter().print(NetTool.getIp());
	}
	
	private void getServerIpPort(HttpServletResponse response) throws IOException 
	{
		response.getWriter().print(NetTool.getIpPort());
	}
	
	private void sendMessage(HttpServletRequest request,HttpServletResponse response) 
	{
		String message=request.getParameter("message");
//		LogMessageInboundPool.sendMessageAll(message);
	}
}

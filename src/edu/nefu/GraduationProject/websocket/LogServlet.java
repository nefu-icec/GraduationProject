package edu.nefu.GraduationProject.websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import edu.nefu.GraduationProject.bean.Teacher;
import edu.nefu.GraduationProject.servlet.SessionServlet;

@WebServlet("/LogServlet")
public class LogServlet extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
       
    public LogServlet() 
    {
        super();
    }

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,HttpServletRequest request) 
	{
		Teacher thisTeacher=SessionServlet.getTeacher(request);
		return new LogMessageInbound(thisTeacher);
	}
}
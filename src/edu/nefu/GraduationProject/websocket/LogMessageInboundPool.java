package edu.nefu.GraduationProject.websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.nefu.GraduationProject.bean.Teacher;

public class LogMessageInboundPool 
{
	private static final Map<Teacher,LogMessageInbound> connections=
			new HashMap<Teacher,LogMessageInbound>();
	
	public static void addMessageInbound(LogMessageInbound inbound)
	{
		connections.put(inbound.getTeacher(), inbound);
	}
	
	public static Set<Teacher> getOnlineTeacher()
	{
		return connections.keySet();
	}
	
	public static void removeMessageInbound(LogMessageInbound inbound)
	{
		connections.remove(inbound.getTeacher());
	}
	
	/**
	 * ������Ϣ��ָ���û�
	 * @param teacher
	 * @param message
	 */
	public static void sendMessage(Teacher teacher,String message)
	{
		try 
		{
			LogMessageInbound inbound=connections.get(teacher);
			if(inbound!=null)
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * ������Ϣ�������û�
	 * @param message
	 */
	public static void sendMessageAll(String message)
	{
		Set<Teacher> teachers=connections.keySet();
		for(Teacher teacher:teachers)
			sendMessage(teacher, message);
	}
	
	/**
	 * ������Ϣ�������Լ�����������û�
	 * @param me
	 * @param message
	 */
	public static void sendMessageOther(Teacher me,String message)
	{
		Set<Teacher> teachers=connections.keySet();
		for(Teacher teacher:teachers)
			if(teacher!=me)
				sendMessage(teacher, message);
	}
}
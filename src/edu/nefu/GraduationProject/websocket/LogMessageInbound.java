package edu.nefu.GraduationProject.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import edu.nefu.GraduationProject.bean.Teacher;

public class LogMessageInbound extends MessageInbound 
{
	private final Teacher teacher;
	
	public Teacher getTeacher() 
	{
		return this.teacher;
	}
	
	public LogMessageInbound(Teacher teacher) 
	{
		super();
		this.teacher = teacher;
	}
	
	@Override
	protected void onOpen(WsOutbound outbound) 
	{
		super.onOpen(outbound);
		LogMessageInboundPool.addMessageInbound(this);
		LogMessageInboundPool.sendMessage(teacher, "OpenSuccess");
	}
	
	@Override
	protected void onClose(int status) 
	{
		super.onClose(status);
		LogMessageInboundPool.removeMessageInbound(this);
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException 
	{
		throw new UnsupportedOperationException("Binary message not suppoted");
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException
	{
		LogMessageInboundPool.sendMessageAll(message.toString());
	}
}
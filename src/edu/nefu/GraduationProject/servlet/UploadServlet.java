package edu.nefu.GraduationProject.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.dao.StatusDao;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public UploadServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "download":
			download(request,response);
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
		case "uploadRegistrationForm":
			uploadRegistrationForm(request,response);
			break;

		default:
			break;
		}
	}

	private void uploadRegistrationForm(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String filepath=rootPath+"file\\RegistrationForm";
		String fileName=upload(request, filepath);
		String pathName=filepath+"\\"+fileName;
		boolean success=DataDao.writeDataByRegistrationForm(pathName);
		success=StatusDao.set(1);
		File file=new File(pathName);
		if(file.exists())
			file.delete();
		if(success)
			response.sendRedirect("start/uploadRegistrationForm.html?success=true");
		else
			response.sendRedirect("start/uploadRegistrationForm.html?success=false");
	}
	
	private void download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException 
	{
		FileInputStream in=null;
		ServletOutputStream out=null;
		String filePath=request.getParameter("filePath");
		String fileName=request.getParameter("fileName");
		fileName=new String(fileName.getBytes("ISO-8859-1") , "UTF-8");
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-disposition","attachment; filename="+fileName);
		try
		{
			in=new FileInputStream(filePath+fileName);
			out=response.getOutputStream();
			out.flush();
			int aRead=0;
			while((aRead=in.read())!=-1&in!=null)
				out.write(aRead);
			out.flush();
			in.close();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private String upload(HttpServletRequest request,String filepath)
	{
		String fileName=null;
		//Ϊ�ļ����������������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*4); //���û������Ĵ�С���˴�Ϊ4kb
		factory.setRepository(new File(filepath)); //�����ϴ��ļ���Ŀ�ĵ�
		//����servlet�ϴ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4*1024*1024);  //�����ϴ��ļ��Ĵ�С���˴�Ϊ4M
		try 
		{
			List<FileItem> list=upload.parseRequest(request); //ȡ�����е��ϴ��ļ���Ϣ
			Iterator<FileItem> it=list.iterator();
			while(it.hasNext())
			{
			    FileItem item=it.next();
			    if(item.isFormField()==false)
			    { 
				    fileName=item.getName();   //�ļ���
				    //ȡ�ļ���  
				    fileName=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());               
				    if(!fileName.equals("")&&!(fileName==null))
				    {
				    	//���fileNameΪnull����û���ϴ��ļ�  
				    	File uploadedFile=new File(filepath,fileName);  
				        try 
				        {
				        	item.write(uploadedFile);
				        } 
				        catch (Exception e)
				        {
				        	e.printStackTrace();
				        }  
				    }            
			    }
			}
		} 
		catch (FileUploadException e) 
		{
			e.printStackTrace();
		}
		return fileName;
	}
}

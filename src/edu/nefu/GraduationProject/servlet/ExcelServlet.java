package edu.nefu.GraduationProject.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.bean.DateTime;
import edu.nefu.GraduationProject.bean.Excel;
import edu.nefu.GraduationProject.dao.ExcelDao;
import edu.nefu.GraduationProject.excel.ExcelCreator;
import edu.nefu.GraduationProject.util.Constant;

/**
 * 与Excel报表有关的操作
 * @author lm2343635
 *
 */
@WebServlet("/ExcelServlet")
public class ExcelServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public ExcelServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "getDBColumnName":
			getDBColumnName(request,response);
			break;
		case "reDownload":
			reDownload(request,response);
			break;
		case "reDownloadTest":
			reDownloadTest(request,response);
			break;
		case "deleteExcel":
			deleteExcel(request,response);
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
		case "downloadExcel":
			downloadExcel(request,response);
			break;

		default:
			break;
		}
	}

	/**
	 * 瞎子报表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void downloadExcel(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String [] columnNames=request.getParameterValues("ColumnName");
		String title=request.getParameter("title");
		title=new String(title.getBytes("ISO-8859-1") , "UTF-8");
		int [] columnNumbers=new int[columnNames.length];
		for(int i=0;i<columnNames.length;i++)
			columnNumbers[i]=Constant.getDBColumnNumber(columnNames[i]);
		ExcelCreator creator=new ExcelCreator(title, title, columnNumbers);
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String savePath=rootPath+"file\\excel\\";
		String fileName=creator.create(savePath);
		response.setCharacterEncoding("UTF-8");
		Excel excel=new Excel(title, new DateTime(fileName,"xls"));
		boolean success=ExcelDao.insert(excel);
		if(success)
			response.sendRedirect("UploadServlet?task=download&filePath="+savePath+"&fileName="+fileName);
		else
		{
			File file=new File(savePath+fileName);
			if(file.exists())
				file.delete();
		}
	}
	
	/**
	 * 得到列名和相应的列在数据库中的名字
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getDBColumnName(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String[] DBColumnName = Constant.DBColumnName,
				    ColumnName = Constant.ColumnName;
		String names="";
		for(String name:DBColumnName)
			names+=name+",";
		names=names.substring(0,names.length()-1);
		names+=";";	
		for(String name1:ColumnName)
			names+=name1+",";
		names=names.substring(0,names.length()-1);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		out.print(names);
	}

	/**
	 * 重新下载
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void reDownload(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int eid=Integer.parseInt(request.getParameter("eid"));
		Excel excel=ExcelDao.getExcelById(eid);
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String fileName=excel.getCreateDate().getSaveFileName("xls");
		String filePath=rootPath+"file\\excel\\";
		response.sendRedirect("UploadServlet?task=download&filePath="+filePath+"&fileName="+fileName);
	}
	
	/**
	 * 重新下载前测试下载环境
	 * @param request
	 * @param response
	 * @throws IOException
	 * @print itemNotFound 未发现该项
	 * @print fileNotFound 文件不存在
	 * @print fileExist 文件存在，可以正常下载
	 */
	private void reDownloadTest(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int eid=Integer.parseInt(request.getParameter("eid"));
		Excel excel=ExcelDao.getExcelById(eid);
		PrintWriter out=response.getWriter();
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		if(excel==null)
			out.print("itemNotFound");
		else
		{
			String fileName=excel.getCreateDate().getSaveFileName("xls");
			String filePath=rootPath+"file\\excel\\";
			File file=new File(filePath+fileName);
			if(!file.exists())
				out.print("fileNotFound");
			else
				out.print("fileExist");
		}
	}
	
	private void deleteExcel(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int eid=Integer.parseInt(request.getParameter("eid"));
		Excel excel=ExcelDao.getExcelById(eid);
		PrintWriter out=response.getWriter();
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String fileName=excel.getCreateDate().getSaveFileName("xls");
		String filePath=rootPath+"file\\excel\\";
		if(ExcelDao.delete(eid))
		{
			File file=new File(filePath+fileName);
			if(file.exists())
				file.delete();
			out.print(true);
		}
		else
			out.print(false);
	}
}

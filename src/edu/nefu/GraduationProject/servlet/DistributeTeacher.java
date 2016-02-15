package edu.nefu.GraduationProject.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.dao.StatusDao;
import edu.nefu.GraduationProject.dao.TeacherDao;
import edu.nefu.GraduationProject.util.MathTool;

@WebServlet("/DistributeTeacher")
public class DistributeTeacher extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public DistributeTeacher() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "getDistributeInfo":
			getDistributeInfo(response);
			break;
		case "distribute":
			distribute(request,response);
			break;
		case "randomDistribute":
			randomDistribute(request,response);
			break;
		case "getDistributedStuNum":
			getDistributedStuNum(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	/**
	 * 得到分配信息
	 * @param response
	 * @throws IOException
	 */
	private void getDistributeInfo(HttpServletResponse response) throws IOException 
	{
		response.getWriter().print(DataDao.getDistributeTeacherInfo());
	}
	
	/**
	 * 手工分配教师
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void distribute(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String tid=request.getParameter("tid");
		int sid=Integer.parseInt(request.getParameter("sid"));
		boolean success=DataDao.modify(sid, "tid", tid);
		DataServlet.writeLog(request, sid, "tid", tid+"");
		StatusDao.check();
		response.getWriter().print(success);
	}
	
	/**
	 * 随机分配
	 * @param request
	 * @param response
	 * @throws IOException
	 * @print 输出操作成功的学生个数
	 */
	private void randomDistribute(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int tid=Integer.parseInt(request.getParameter("tid"));
		int number=Integer.parseInt(request.getParameter("number"));
		ArrayList<Integer> undistributeSid=DataDao.getUndistributeSid();
		int success=0;
		int [] random=MathTool.getRandom(number, undistributeSid.size());
		for(int i:random)
			if(DataDao.modify(undistributeSid.get(i), "tid", tid+""))
				success++;
		StatusDao.check();
		response.getWriter().print(success);
	}
	
	/**
	 * 得到某个教师指导学生个数
	 * @param request
	 * @param response
	 * @request tid 教师编号
	 * @throws IOException
	 * @print 某个教师指导学生个数
	 */
	private void getDistributedStuNum(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int tid=Integer.parseInt(request.getParameter("tid"));
		int num=TeacherDao.getDistributeStudentNum(tid);
		response.getWriter().print(num);
	}
}

<%@page import="edu.nefu.GraduationProject.dao.DataDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Student"%>
<%@page import="edu.nefu.GraduationProject.util.Constant"%>
<%@page import="edu.nefu.GraduationProject.dao.AffairDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Affair"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	ArrayList<Affair> doingAffairs=AffairDao.getDoingAffair();
	ArrayList<Teacher> teachers=TeacherDao.getTeacher();
	String jsMsg = "";
	String flag = "";
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>完成任务</title>
	<link rel="stylesheet" href="public/css/checkAffair.css">
</head>
<body>
	<p class="title" >
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  
		<span style="color: #bbb;">\</span> 完成任务
	</p>
	<ul class="nav nav-tabs nav-justified">
		<%
			for(int i=0;i<doingAffairs.size(); i++)
			{
				%>
				<li <%if(i==0) out.print("class='active'");%>><a href="#"><%=doingAffairs.get(i).getWork()%></a></li>
				<%
			}
		%>
	</ul>
	<%
	for(Affair affair:doingAffairs)
	{
		%>
		<div class="showInfo">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>学号</th>
						<th>姓名</th>
						<th>班级</th>
						<%
						for(int i:affair.getColumns())
						{
							if(Constant.DBColumnName[i].equals("startPassed") || Constant.DBColumnName[i].equals("endPassed"))
								flag = "passed";
							else
								flag = "";
							%>
								<th><%=Constant.getColumnName(i) %> <span style="display:none;"><%=Constant.DBColumnName[i]%></span></th>
							<%
						}
						%>
					</tr>
				</thead>
				<tbody>
				<%
				for(Teacher teacher:teachers)
				{
					%>
					<tr id="<%=teacher.getTid()%>">
						<td colspan="8" class="teacher" onclick="showStudent(<%=teacher.getTid()%>)" >
							<%=teacher.getTid()+"号教师："+teacher.getTname()+"（"+teacher.getProfession()+"）"%>指导的学生
						</td>
					</tr>
					<%
					boolean finished=true;
					for(String [] row:DataDao.getAffairInfo(affair.getColumns(),teacher.getTid()))
					{
						%>
						<tr class="studentInfo <%=teacher.getTid()%>">
							<%
							int i = 0;
							for(String item:row)
							{
								if(i >= 3)
								{
								%>
									<td>
										<%
											if(item!=null && flag.equals("")){
												out.print(item);
												%> <span class="glyphicon glyphicon-wrench"></span><%
											}else if(flag.equals("passed")){
												if(item == null)
													out.print("<button class='btn btn-danger btn-xs not-pass' style='width: 50px;'>未通过</button>");
												else if(Integer.parseInt(item) == 1)
													out.print("<button class='btn btn-default btn-xs passed' style='width: 50px;'>通过</button>");
												else
													out.print("<button class='btn btn-danger btn-xs not-pass' style='width: 50px;'>未通过</button>");	
											}
											else{
												out.print("<span style='color:blue;'>未填写</span>");
												%> <span class="glyphicon glyphicon-wrench"></span><%
											}
										if(item==null)
											finished=false;
										%> 
										
									</td>
								<% 
								}
								else
								{
								%>
									<td><%if(item!=null) out.print(item); else out.print("<span style='color:blue;'>未填写</span>");%></td>
								<% 
								}
								i++;
							}
							%>
						</tr>
						<%
					}
					if(!finished)
					{
							jsMsg += "$('#"+teacher.getTid()+"').css('background','red')";
					}
				}
				%>
				</tbody>
			</table>
		</div>		
		<% 
	}
	%>
	<script type="text/javascript" src="public/js/checkAffair.js"></script>
</body>
</html>

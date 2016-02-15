<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Student"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	Teacher teacher=(Teacher)session.getAttribute("teacher");
	ArrayList<Student> students=TeacherDao.getDistributeStudent(teacher.getTid());
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>选择开题时间</title>
	<link rel="stylesheet" href="public/css/choose-box.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 选择开题时间
	</p>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>电话</th>
					<th>学院</th>
					<th>专业名称</th>
					<th>年级</th>
					<th>班级</th>
					<th>开题时间</th>
				</tr>
			</thead>
			<tbody>
			<%
			for(Student student:students)
			{
				%>
				<tr>
					<td><%=student.getSid()%></td>
					<td><%=student.getSname()%></td>
					<td><%=student.getTel()%></td>
					<td><%=student.getCollege()%></td>
					<td><%=student.getMajor()%></td>
					<td><%=student.getGrade()%></td>
					<td><%=student.getClassName()%></td>
					<td style="text-align: center;">
					<%
					if(student.getStartTime()==null&&student.getSecondStartTime()==null)
					{
						%>
						<div class="choose-box">
							<span class="no-slt">
								未选择
							</span>
							<span class="opt">
								一次
							</span>
							<span class="opt">
								二次
							</span>
						</div>
						<% 
					}
					else if(student.getStartTime()!=null&&student.getSecondStartTime()==null)
					{
						%>
						<div class="choose-box">
							<span class="opt chosen">
								一次
							</span>
							<span class="opt">
								二次
							</span>
						</div>
						<% 
					}
					else if(student.getStartTime()==null&&student.getSecondStartTime()!=null)
					{
						%>
						<div class="choose-box">
							<span class="opt">
								一次
							</span>
							<span class="opt chosen">
								二次
							</span>
						</div>
						<% 
					}
					%>
					</td>
				</tr>			
				<%
			}
			%>
			</tbody>
		</table>
	</div>
	<script src="public/js/selectStartTime.js"></script>
</body>
</html>
<%@page import="edu.nefu.GraduationProject.bean.Student"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	int select=0;
	int unselect=0;
	ArrayList<Teacher> teachers=TeacherDao.getTeacher();
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>开题时间选择情况</title>
	<link rel="stylesheet" href="public/css/startTimeSelectInfo.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 开题时间选择情况
		<span class="dtT">已选择：<%=select%>人/未选择：<%=unselect%>人</span>
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
			for(Teacher teacher:teachers)
			{
				%>
					<tr>
						<td colspan="8" class="teacher" onclick="showStudent(<%=teacher.getTid()%>)">
							<%=teacher.getTid()+"号教师："+teacher.getTname()+"（"+teacher.getProfession()+"）"%>指导的学生
						</td>
					</tr>
					<%
					for(Student student:TeacherDao.getDistributeStudent(teacher.getTid()))
					{
						%>
						<tr class="studentInfo <%=teacher.getTid()%>">
							<td><%=student.getSid()%></td>
							<td><%=student.getSname()%></td>
							<td><%=student.getTel()%></td>
							<td><%=student.getCollege()%></td>
							<td><%=student.getMajor()%></td>
							<td><%=student.getGrade()%></td>
							<td><%=student.getClassName()%></td>
							<td style="text-align: center;">
							<%
								if(student.getStartTime() != null)
								{
									%>一次开题<%
								}
								else if(student.getSecondStartTime()!=null)
								{
									%>二次开题<%
								}
								else
								{
									%><span style="color: red;">未选择</span><%
								}
							%>
							</td>
						</tr>			
						<%
					}
			}
			%>
			</tbody>
		</table>
	</div>
	<script src="public/js/startTimeSelectInfo.js"></script>
</body>
</html>
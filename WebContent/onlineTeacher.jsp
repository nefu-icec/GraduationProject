<%@page import="edu.nefu.GraduationProject.servlet.ApplicationServlet"%>
<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>在线教师</title>
</head>
<body>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>教师编号</th>
				<th>教师姓名</th>
				<th>教师职称</th>
			</tr>
		</thead>
		<tbody>
		<%
		for(Teacher teacher:ApplicationServlet.getOnlineTeachers(application))
		{
			%>
			<tr>
				<td><%=teacher.getTid() %></td>
				<td><%=teacher.getTname() %></td>
				<td><%=teacher.getProfession() %></td>
			</tr>
			<%
		}
		%>
		</tbody>
	</table>
</body>
</html>

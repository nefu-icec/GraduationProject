<%@page import="edu.nefu.GraduationProject.dao.LogDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Log"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	ArrayList<Log> logs=LogDao.getLogs(" order by time desc");
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>系统日志</title>
	<link rel="stylesheet" href="public/css/log.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 系统日志
		<button id="clearLog">清空</button>
	</p>	
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>日志编号</th>
					<th>修改时间</th>
					<th>教师编号</th>
					<th>教师姓名</th>
					<th>学生学号</th>
					<th>学生姓名</th>
					<th>更改列名</th>
					<th>更改内容</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
			<%
			for(Log log:logs)
			{
				%>
				<tr id="<%=log.getLid() %>">
					<td><%=log.getLid() %></td>
					<td><%=log.getTime().getDBString() %></td>
					<td><%=log.getTid() %></td>
					<td><%=log.getTname() %></td>
					<td><%=log.getSid() %></td>
					<td><%=log.getSname() %></td>
					<td><%=log.getColumnName() %></td>
					<td><%=log.getContent(15) %></td>
					<td><span class="glyphicon glyphicon-trash" onclick="deleteLog(<%=log.getLid()%>)"></span></td>
				</tr>
				<%
			}
			%>
			</tbody>
		</table>
	</div>
	<script src="public/js/log.js"></script>
</body>
</html>

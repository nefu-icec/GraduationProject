<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="edu.nefu.GraduationProject.dao.NotificationDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nefu.GraduationProject.servlet.SessionServlet"%>

<%
ArrayList<Notification> notifications=NotificationDao.getNotification();
boolean isAdmin=SessionServlet.isAdmin(request); 
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>系统通知</title>
	<link rel="stylesheet" href="public/css/notification.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 系统通知
		<%
		if(isAdmin)
		{
			%>
				<span class="dtT">
					<button id="addNewNotification" class="btn btn-default btn-xs" style="float: right;">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</span>	
			<%
		}
		%>
	</p>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>通知编号</th>
					<th>通知标题</th>
					<th>通知内容</th>
					<th>发布时间</th>
					<%
					if(isAdmin)
					{
						%>
							<th>删除</th>
						<%
					}
					%>			
				</tr>
			</thead>
			<tbody>
				<%
				for(Notification notification:notifications)
				{
					%>
					<tr>
						<td><%=notification.getNid() %></td>
						<td><%=notification.getTitle() %></td>
						<td><button onclick="showNotification(<%=notification.getNid()%>)" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"></span></button></td>
						<td><%=notification.getPublish().getDBString() %></td>
						<%
						if(isAdmin)
						{
							%>
								<td><button class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span></button></button></td>
							<%
						}
						%>			
					</tr>
					<%
				}
				%>
			</tbody>
		</table>
		<div id="newNotification" style="display:none;">
			<div class="pop-title">
				<span class="addNotification">添加通知</span>
				<span class="glyphicon glyphicon-remove close"></span>
			</div>
			<form id="notificationInfo">
				<table>
					<tr>
						<td><span>通知标题:</span></td>
						<td><input name="title" type="text" class="form-control" style="width:400px;margin: 5px 0;"></td>
					</tr>
					<tr>
						<td><span>通知内容:</span></td>
			            <td><textarea name="content"  class="form-control" style="width:400px;margin: 5px 0;height:120px;"></textarea></td>
					</tr>
				</table>
				<input type="submit" class="btn btn-default" id="sbt" style="width: 224px;">
				<input type="reset" class="btn btn-warning" style="width: 224px;">
			</form>	
		</div>
	</div>
	
	<script src="public/js/notification.js"></script>
</body>
</html>

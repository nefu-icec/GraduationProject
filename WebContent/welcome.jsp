<%@page import="edu.nefu.GraduationProject.dao.LogDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Log"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nefu.GraduationProject.dao.SysDateDao"%>
<%@page import="edu.nefu.GraduationProject.bean.StatusInfo"%>
<%@page import="edu.nefu.GraduationProject.dao.NotificationDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Notification"%>
<%@page import="edu.nefu.GraduationProject.dao.AffairDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Affair"%>
<%@page import="edu.nefu.GraduationProject.util.Constant"%>
<%@page import="edu.nefu.GraduationProject.dao.StatusDao"%>
<%@page import="edu.nefu.GraduationProject.dao.PeriodDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	StatusInfo statusInfo=new StatusInfo();
	ArrayList<Affair> affairs=AffairDao.getDoingAffair(4);
%>

<html>
	<head>
		<link rel="stylesheet" href="public/css/welcome.css">
		<link rel="stylesheet" href="public/css/pop-box.css">
		<script type="text/javascript" src="public/js/popBox.js"></script>
	</head>
	<body>
		<div class="part1 part">
			<div class="welcome_title">系统状态</div>
			<div class="welcome_content">
				<table class="table table-hover">
					<thead>
						<tr>
							<th colspan="2">当前系统工作在<span id="system-period"><%=statusInfo.getPeriod()%></span>届</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>上一个工作状态：</td>
							<td><span id="system-last-status"><%=statusInfo.getLastStatus()%></span></td>
						</tr>
						<tr>
							<td>当前系统工作状态：</td>
							<td><span id="system-status"><%=statusInfo.getNowStatus() %></span></td>
						</tr>
						<tr>
							<td>当前要完成的任务：</td>
							<td><span id="system-task"><%=statusInfo.getTask()%></span></td>
						</tr>
						<tr>
							<td>下一个工作状态：</td>
							<td><span id="system-next-status"><%=statusInfo.getNextStatus()%></span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="part2 part">
			<div class="welcome_title">当前任务 <button id="checkAffair" class="btn btn-default btn-xs detail" style="float: right;" title="查看任务进度"><span class="glyphicon glyphicon-search"></span></button></div>
			<div class="welcome_content">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>任务名称</th>
							<th>结束时间</th>
						</tr>
					</thead>
					<tbody>
						<%
						if(affairs!=null)
						{
							for(Affair affair:affairs)
							{
								%>
								<tr>
									<td><%=affair.getId() %></td>
									<td><%=affair.getWork() %></td>
									<td><%=affair.getEnd().getDBString().substring(0,10) %></td>
								</tr>
								<%
							}						
						}
						else
							out.print("当前无任务！");
						%>
					</tbody>
				</table>
			</div>
		</div>
		<div class="part3 part">
			<div class="welcome_title">系统通知</div>
			<div class="welcome_content">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>通知内容</th>
							<th>发布时间</th>
						</tr>
					</thead>
					<tbody>
						<%
						for(Notification notification:NotificationDao.getNotification(4))
						{
							%>
							<tr>
								<td><%=notification.getNid() %></td>
								<td><a href="javascript:void(0);" onclick="showNotification(<%=notification.getNid()%>)">
									<%
										if(notification.getTitle().length() > 20)
											out.print(notification.getTitle().substring(0, 20) + "...");
										else
											out.print(notification.getTitle());
									%>
								</a></td>
								<td><%=notification.getPublish().getDBString().substring(0,10) %></td>
							</tr>
							<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<div class="part4 part">
			<div class="welcome_title">系统日志</div>
			<div class="welcome_content">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>时间</th>
							<th>学生</th>
							<th>教师</th>
							<th>列名</th>
							<th>内容</th>
						</tr>
					</thead>
					<tbody>
					<%
					for(Log log:LogDao.getLogs(4))
					{
						%>
						<tr>
							<td><%=log.getTime().getDBString()%></td>
							<td title="<%=log.getSid()%>"><%=log.getSname()%></td>
							<td title="<%=log.getTid()%>"><%=log.getTname()%></td>
							<td><%=log.getColumnName()%></td>
							<td><%=log.getContent(15)%></td>	
						</tr>
						<%
					}
					%>
					</tbody>
				</table>
			</div>
		</div>
		<script src="public/js/welcome.js"></script> 
	</body>
</html>
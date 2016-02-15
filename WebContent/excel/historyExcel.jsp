<%@page import="edu.nefu.GraduationProject.servlet.SessionServlet"%>
<%@page import="edu.nefu.GraduationProject.dao.ExcelDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Excel"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	ArrayList<Excel> excels=ExcelDao.getExcel(null);
	boolean isAdmin=SessionServlet.isAdmin(request);
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>自选学生</title>
	<link rel="stylesheet" href="public/css/match.css">
	<script type="text/javascript" src="public/js/historyExcel.js"></script>
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 报表下载记录
	</p>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th>文件名</th>
					<th>报表生成日期</th>
					<th>下载</th>
					<%
					if(isAdmin)
					{
						%>
							<th>删除记录</th>		
						<%
					}
					%>			
				</tr>
			</thead>
			<tbody>
			<%
			for(Excel excel:excels)
			{
				%>
				<tr>
					<td><%=excel.getEid()%></td>
					<td><%=excel.getEname()%></td>
					<td><%=excel.getCreateDate().getDBString()%></td>
					<td>
						<button onclick="reDownload(<%=excel.getEid()%>)" class="btn btn-default btn-xs">
							<span class="glyphicon glyphicon-download-alt"></span>
						</button>
					</td>
					<%
					if(isAdmin)
					{
						%>
							<td>
								<button onclick="deleteExcel(<%=excel.getEid()%>)" class="btn btn-danger btn-xs">
									<span class="glyphicon glyphicon-remove"></span>
								</button>
							</td>
						<%
					}
					%>	
				</tr>
				<%
			}
			%>
			</tbody>
		</table>
	</div>
</body>
</html>

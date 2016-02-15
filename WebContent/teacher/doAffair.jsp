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
	Teacher teacher=(Teacher)session.getAttribute("teacher");
	String flag = "";
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>完成任务</title>
	<link rel="stylesheet" href="public/css/doAffair.css">
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
				for(String [] row:DataDao.getAffairInfo(affair.getColumns(),teacher.getTid()))
				{
					%>
					<tr>
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
											if(item == null){
												out.print("<button class='btn btn-danger btn-xs not-pass' style='width: 50px;'>未通过</button>");
											}else if(Integer.parseInt(item) == 1){
												out.print("<button class='btn btn-default btn-xs passed' style='width: 50px;'>通过</button>");
											}else{
												out.print("<button class='btn btn-danger btn-xs not-pass' style='width: 50px;'>未通过</button>");
											}
										}
										else{
											out.print("<span style='color:blue;'>未填写</span>");
											%> <span class="glyphicon glyphicon-wrench"></span><%
										}
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
				%>
				</tbody>
			</table>
		</div>		
		<% 
	}
	%>
	<div class="pop-change-box">
		<div class="pop-title">
			<span class="addTask">信息修改</span>
			<span class="glyphicon glyphicon-remove close"></span>
		</div>
		<div class="pop-content">
			<table>
				<tr>
					<td>修改条目:</td>
					<td></td>
				</tr>
				<tr>
					<td>原始信息:</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align:right;">新信息:</td>
					<td>
						<input type="text" class="form-control input-sm" id="new-info">
						<select name="gdp" id="gdp" class="form-control input-sm">
							<option value="论文">论文</option>
							<option value="设计">设计</option>
						</select>
						<select name="sbt-ogn" id="sbt-ogn" class="form-control input-sm">
							<option value="科研">科研</option>
							<option value="生产实践">生产实践</option>
							<option value="自选">自选</option>
						</select>
					</td>
				</tr>
			</table>
			<button class="btn btn-default sbt-new-info" style="width:220px;margin-top:10px;margin-right:5px;">确认修改</button>
			<button class="btn btn-danger" style="width:220px;margin-top:10px;margin-right:5px;">取消</button>
		</div>
	</div>
	<script type="text/javascript" src="public/js/doAffair.js"></script>
</body>
</html>

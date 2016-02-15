<%@page import="edu.nefu.GraduationProject.util.DateTool"%>
<%@page import="edu.nefu.GraduationProject.dao.AffairDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Affair"%>
<%@page import="edu.nefu.GraduationProject.util.Constant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nefu.GraduationProject.dao.PeriodDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	ArrayList<Affair> affairs=AffairDao.getAffair(null);
	String [] ColumnName=Constant.ColumnName;
	String nowTime = DateTool.getSystemTime();
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>任务管理</title>
	<link rel="stylesheet" href="public/css/work.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 任务管理
		<span class="dtT">
			<%=PeriodDao.get()%>届的所有任务
			<button id="addWork" class="btn btn-default btn-xs">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
			<button id="checkAffair" class="btn btn-primary btn-xs detail">查看任务进度</button>
		</span>
	</p>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>任务编号</th>
					<th>任务名称</th>
					<th>开始时间</th>
					<th>截止日期</th>
					<th>任务列</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
			<%
			for(Affair affair:affairs)
			{
				%>
				<tr>
					<td><%=affair.getId() %></td>
					<td><%=affair.getWork() %></td>
					<td><%=affair.getStart().getDBString() %></td>
					<td><%=affair.getEnd().getDBString() %></td>
					<td><%for(int i:affair.getColumns()) out.print(Constant.getColumnName(i)+" ");%></td>
					<td><button class="btn btn-danger btn-xs delet">删除</button></td>
				</tr>	
				<%
			}
			%>
			</tbody>
		</table>
		<div id="newWork" style="display:none;">
			<div class="pop-title">
				<span class="addTask">添加任务:</span>
				<span class="glyphicon glyphicon-remove close"></span>
			</div>
			<div class="pop-content">
				<form id="addNewWork">
					<table>
						<tr>
							<td><span>任务名称:</span></td>
							<td><input type="text" class="form-control" style="width:400px;margin: 5px 0;"></td>
						</tr>
						<tr>
							<td><span>开始时间:</span></td>
							<td>
				                <input size="16" type="text" value="<%=nowTime %>"  readonly class="form_datetime form-control" style="margin: 5px 0;">
							</td>
						</tr>
						<tr>
							<td><span>截止时间:</span></td>
							<td>
								<input size="16" type="text" value="<%=nowTime %>" readonly class="form_datetime form-control" style="margin: 5px 0;">
							</td>
						</tr>
						<tr>
							<td>任务列:</td>
						</tr>
					</table>
					<ul class="task-list">
						<%
						for(int i=0;i<4;i++)
						{
								for(int j=0;j<6;j++)
								{
									boolean bl = i*6+j<23 && ColumnName[i*6+j] 
											!= "届" && ColumnName[i*6+j] 
											!= "学号" && ColumnName[i*6+j] 
											!= "姓名" && ColumnName[i*6+j]
											!= "二次开题时间" && ColumnName[i*6+j] 
											!= "二次答辩时间" && ColumnName[i*6+j] 
											!= "开题时间" && ColumnName[i*6+j] 
											!= "答辩时间";
									if(bl)
									{
										%>
										<li><input type="checkbox" name="columns" value="<%=i*6+j%>"><%=ColumnName[i*6+j] %></li>
										<%										
									}
								}
						}
						%>
					</ul>
					<input type="submit" class="btn btn-default" id="sbt" style="width:240px;margin-top:10px;">
					<input type="reset" class="btn btn-warning" style="width:240px;margin-top:10px;">
				</form>	
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="public/js/jquery.js"></script>
	<script type="text/javascript" src="public/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="public/js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
	<script type="text/javascript" src="public/js/work.js"></script>
</body>
</html>
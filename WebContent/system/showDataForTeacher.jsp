<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nefu.GraduationProject.util.Constant"%>
<%@page import="edu.nefu.GraduationProject.dao.DataDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
ArrayList<String []> studentInfo=DataDao.getColumnData(Constant.StudentInfoNum,null);
ArrayList<String []> startInfo=DataDao.getColumnData(Constant.StartInfoNum,null);
ArrayList<String []> endInfo=DataDao.getColumnData(Constant.EndtInfoNum,null);
ArrayList<String []> projectInfo=DataDao.getProjectInfo();
ArrayList<Teacher> teachers=TeacherDao.getTeacher(null);
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>数据</title>
	<link rel="stylesheet" href="public/css/showData.css">
	<style>
		.showInfo{
			display: none;
		}
		.show{
			display: block;
		}
	</style>
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-calendar" style="margin-right: 5px;"></span>  
		<span style="color: #bbb;">\</span> 所有数据
	</p>
	<ul class="nav nav-tabs nav-justified">
	  <li class="active">
	  	<a href="#">学生信息</a>
	  </li>
	  <li><a href="#">项目信息</a></li>
	  <li><a href="#">开题信息</a></li>
	  <li><a href="#">答辩信息</a></li>
	</ul>
	<br>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>届</th>
					<th>学号<span style="display: none;">sid</span></th>
					<th>姓名<span style="display: none;">sname</span></th>
					<th>电话<span style="display: none;">tel</span></th>
					<th>学院<span style="display: none;">college</span></th>
					<th>专业<span style="display: none;">major</span></th>
					<th>年级<span style="display: none;">grade</span></th>
					<th>行政班<span style="display: none;">className</span></th>		
					<th>学籍状态<span style="display: none;">status</span></th>
				</tr>
			</thead>
			<tbody>
			<%
				for(String [] row:studentInfo)
				{
					%>
					<tr>	
						<%
						for(String item:row)
						{
							%>
							<td><%if(item!=null) out.print(item);%></td>
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
	<div class="showInfo">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>中文题目</th>
					<th>英文题目</th>
					<th>指导教师</th>
					<th>职称</th>
					<th>题目来源</th>
					<th>成绩</th>				
				</tr>
			</thead>
			<tbody>
			<%
				for(String [] row:projectInfo)
				{
					%>
					<tr>	
						<%
						for(String item:row)
						{
							%>
							<td><%if(item!=null) out.print(item);%></td>
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
	<div class="showInfo">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>开题时间</th>
					<th>二次开题时间</th>
					<th>开题问题反馈</th>
					<th>开题通过</th>
				</tr>
			</thead>
			<tbody>
			<%
				for(String [] row:startInfo)
				{
					%>
					<tr>	
						<%
						int i = 0;
						for(String item:row)
						{
							if(i == 5){
								if(item == null){
									%>
									<td></td>
									<%
								}else if(Integer.parseInt(item) == 1){
									%>
									<td><span class="glyphicon glyphicon-ok"></span><span style="display:none">通过</span></td>
									<%
								}else{
									%>
									<td><span class="glyphicon glyphicon-remove"></span><span style="display:none">未通过</span></td>
									<%
								}
								
							}else{
								%>
								<td><%if(item!=null) out.print(item);%></td>
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
	<div class="showInfo">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>答辩时间</th>
					<th>二次答辩时间</th>
					<th>答辩问题反馈</th>
					<th>答辩通过</th>
				</tr>
			</thead>
			<tbody>
			<%
				for(String [] row:endInfo)
				{
					%>
					<tr>	
						<%
						int i = 0;
						for(String item:row)
						{
							if(i == 5){
								if(item == null){
									%>
									<td></td>
									<%
								}else if(Integer.parseInt(item) == 1){
									%>
									<td><span class="glyphicon glyphicon-ok"></span><span style="display:none">通过</span></td>
									<%
								}else{
									%>
									<td><span class="glyphicon glyphicon-remove"></span><span style="display:none">未通过</span></td>
									<%
								}
								
							}else{
								%>
								<td><%if(item!=null) out.print(item);%></td>
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
	<script src="public/js/showDataForTeacher.js"></script>
</body>
</html>
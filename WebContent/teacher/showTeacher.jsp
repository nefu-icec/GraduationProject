<%@page import="edu.nefu.GraduationProject.dao.StatusDao"%>
<%@page import="edu.nefu.GraduationProject.servlet.SessionServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="java.util.ArrayList"%>

<%
ArrayList<Teacher> teachers=TeacherDao.getTeacher(null);
boolean isAdmin=SessionServlet.isAdmin(request);
int status=StatusDao.get();
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>教师信息管理</title>
	<link rel="stylesheet" href="public/css/showTeacher.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 所有教师
		<%
		if(isAdmin)
		{
			%>
				<span class="dtT">
					<button id="addTeacher" class="btn btn-default btn-xs" style="float: right;">
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
					<th>编号</th>
					<th>姓名</th>
					<th>职称</th>		
					<th>指导学生</th>		
					<%
					if(isAdmin)
					{
						%>
							<th>密码</th>
							<%
							if(status==0)
							{
								%>
									<th>删除</th>
								<%
							}
					}
					%>	
				</tr>
			</thead>
			<tbody>
			<%
			for(Teacher teacher:teachers)
			{
				%>
				<tr>
					<td><%=teacher.getTid() %></td>
					<td><%=teacher.getTname() %></td>
					<td><%=teacher.getProfession() %></td>			
					<td>
						<button onclick="studentInfo(<%=teacher.getTid()%>)" class="btn btn-info btn-xs" style="width:50px;">
							<%=TeacherDao.getDistributeStudentNum(teacher.getTid())%>人
						</button>
					</td>
					<%
					if(isAdmin)
					{
						%>
							<td><%=teacher.getPassword() %></td>
							<%
							if(status==0)
							{
								%>
									<td><button class="btn btn-danger btn-xs">删除</button></td>
								<%
							}
					}
					%>			
				</tr>
				<%
			}
			%>
			</tbody>
		</table>
		<div id="newTeacher" style="">
			<div class="pop-title">
				<span class="addTask">添加教师</span>
				<span class="glyphicon glyphicon-remove close"></span>
			</div>
			<form id="addNewTeacher" >
				<table>
					<tr>
						<td><span>教师编号:</span></td>
						<td><input name="tid" type="text" class="form-control" style="width:400px;margin: 5px 0;"></td>
					</tr>
					<tr>
						<td><span>教师姓名:</span></td>
			            <td><input name="tname"  type="text" class="form-control" style="width:400px;margin: 5px 0;"></td>
					</tr>
					<tr>
						<td><span>教师职称:</span></td>
					    <td>
					    	<select name="profession" class="form-control">
					    		<option value="教授">教授</option>
					    		<option value="副教授">副教授</option>
					    		<option value="讲师">讲师</option>
					    		<option value="高级工程师">高级工程师</option>
					    		<option value="工程师">工程师</option>	
					    	</select>
						</td>
					</tr>
					<tr>
						<td><span>登录密码:</span></td>
						<td><input name="password" type="text" class="form-control" style="width:400px;margin: 5px 0;"></td>
					</tr>	
				</table>
				<input type="submit" class="btn btn-default" id="sbt1" style="width: 224px;">
				<input type="reset" class="btn btn-warning" style="width: 224px;">
			</form>	
		</div>
	</div>
	<div class="pop-info">
		<div class="pop-title">
			<span class="addTask">学生信息</span>
			<span class="glyphicon glyphicon-remove close"></span>
		</div>
	</div>
	<script src="public/js/showTeacher.js"></script>
</body>
</html>



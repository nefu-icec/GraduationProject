<%@page import="edu.nefu.GraduationProject.bean.Teacher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nefu.GraduationProject.util.Constant"%>
<%@page import="edu.nefu.GraduationProject.dao.DataDao"%>
<%@page import="edu.nefu.GraduationProject.dao.TeacherDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	ArrayList<String []> DistrubuteStudentInfoNum=DataDao.getUndistributeInfo();
	ArrayList<Teacher> teachers=TeacherDao.getTeacher();
	String [] distributeInfo=DataDao.getDistributeTeacherInfo().split(",");
	int distribute=Integer.parseInt(distributeInfo[0]);
	int undistribute=Integer.parseInt(distributeInfo[1]);
%>

<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>自主选择教师</title>
	<link rel="stylesheet" href="public/css/match.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 自主选择教师
		<span class="dtT">已分配<%=distribute%>人/未分配<%=undistribute%>人</span>
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
					<th>指导教师</th>		
					<th>提交</th>
				</tr>
			</thead>
			<tbody>
			<%
				for(String [] row:DistrubuteStudentInfoNum)
				{
					%>
					<tr>	
						<%
						for(int i=1;i<row.length;i++)
						{
							%>
							<td><%=row[i]%></td>
							<%
						}
						%>
						<td>
							<select name="teachers" class="teachers">
								<option value="default">请选择教师</option>
								<%
								for( Teacher teacher:teachers)
								{
									%>
									<option value="<%=teacher.getTid()%>"><%=teacher.getTname() %></option>								
									<%
								}
								%>
							</select>
						</td>
						<td><button class="btn btn-primary btn-xs">确定</button></td>
					</tr>
					<%			
				}
			%>
			</tbody>
		</table>
	</div>
	<script src="public/js/match.js"></script>
</body>
</html>
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
	<title>随机分配教师</title>
	<link rel="stylesheet" href="public/css/match.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-leaf" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 随机分配教师
		<span class="dtT">已分配<%=distribute%>人/未分配<span class="rest-num"><%=undistribute%></span>人</span>
	</p>
	<div class="showInfo show">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>教师编号</th>
					<th>教师姓名</th>
					<th>职称</th>
					<th>已分配学生数</th>
					<th>还学分配学生数</th>
					<th>确定</th>
				</tr>
			</thead>
			<tbody>
				<%
				for( Teacher teacher:teachers)
				{
					%>
					<tr>
						<td><%=teacher.getTid() %></td>
						<td><%=teacher.getTname() %></td>
						<td><%=teacher.getProfession() %></td>
						<td><%=TeacherDao.getDistributeStudentNum(teacher.getTid())%></td>
						<td>
							<select name="needNum" class="needNum">
								<% 
									for(int i = 0; i < 17; i++)
									{
										%>
											<option value="<%=i%>"><%=i%></option>
										<%
									}
								%>
							</select>
						</td>
						<td>
							<button class="btn btn-primary btn-xs">确定</button>
						</td>
					</tr>
					<%
				}
				%>
			</tbody>
		</table>
	</div>
	<script src="public/js/random.js"></script>
</body>
</html>
<%@page import="edu.nefu.GraduationProject.bean.GroupInfo"%>
<%@page import="edu.nefu.GraduationProject.dao.Group"%>
<%@page import="edu.nefu.GraduationProject.dao.DataDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	ArrayList<Student> students = DataDao.getWhoJoinSecondEnd();
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>创建分组</title>
	<link rel="stylesheet" href="public/css/sedEndGroup.css">
	<link rel="stylesheet" href="public/css/bootstrapSwitch.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-send" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 二次答辩
		<span style="float: right;">
			参加二次答辩的人数: 
			<span id="join-num"><%=students.size()%></span>
			<span class="glyphicon glyphicon-download-alt" title="下载报表"></span>
			<span class="glyphicon glyphicon-cloud-download" title="生成报表"></span>	
		</span>
	</p>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>序号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>题目</th>
				<th>教师</th>
				<th>来源</th>
				<th>是否通过一次答辩</th>
				<th>答辩问题反馈</th>
				<th>成绩</th>
			</tr>
		</thead>
		<tbody>
		<%
			for(GroupInfo info:Group.getSecondEndGroup())
			{
				%>
					<tr>
						<td><%=info.getNumber() %></td>
						<td><%=info.getSid() %></td>
						<td><%=info.getSname() %></td>
						<td><%=info.getCtitle() %></td>
						<td><%=info.getTname() %></td>
						<td><%=info.getOrigin() %></td>
						<td>
						<%
							if(info.getEndPassed() == 1){
								%>
								<div class="switch switch-mini" data-on-label="是" data-off-label="否">
								    <input type="checkbox" checked />
								</div>
								<%
							}else if(info.getEndPassed() == 0){
								%>
								<div class="switch switch-mini" data-on-label="是" data-off-label="否">
								    <input type="checkbox"  />
								</div>
								<%
							}else{
								%>
								<div class="switch switch-mini" data-on-label="是" data-off-label="否">
								    <input type="checkbox"  />
								</div>
								<span style="color:red;">未确认</span>
								<%
							}
						%>
						</td>
						<td>
							<span class="glyphicon glyphicon-comment"></span>
							<span style="display:none;"><%=info.getEndQuestion() %></span> 
						</td>
						<%
							if(info.getScore() == -1){
								%>
								<td>未录入 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}else if(info.getScore() == 0){
								%>
								<td>不及格 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}else if(info.getScore() == 1){
								%>
								<td>及格 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}else if(info.getScore() == 2){
								%>
								<td>中 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}else if(info.getScore() == 3){
								%>
								<td>良 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}else{
								%>
								<td>优 <span class="glyphicon glyphicon-wrench"></span></td>
								<%
							}
						%>
					</tr>
				<%
			}
		%>
		</tbody>
	</table>
	<div class="pop-box show-sbt-res" >
		<div class="pop-title">二次答辩问题反馈</div>
		<div class="pop-content">
			<pre>未进行任何问题反馈</pre>
			<textarea name="sbt-res" id="sbt-res" class="form-control text-quesstion" cols="100" rows="5" style="display:none;"></textarea>
			<button class="btn btn-default chge" style="width:478px;">修改</button>
			<button class="btn btn-default chge-yes" style="width:237px;" style="display:none;">确定</button>
			<button class="btn btn-danger chge-no" style="width:237px;" style="display:none;">取消</button>
		</div>
	</div>
	<div class="pop-box upt-score" >
		<div class="pop-title">成绩修改</div>
		<div class="pop-content">
			<select name="score" id="score" class="form-control input-sm" style="margin-bottom:5px;">
				<option value="4">优</option>
				<option value="3">良</option>
				<option value="2">中</option>
				<option value="1">及格</option>
				<option value="0">不及格</option>
			</select>
			<button class="btn btn-default cg-score" style="width:100px;">确定</button>
			<button class="btn btn-danger cg-no" style="width:100px;">取消</button>
		</div>
	</div>
	<script src="public/js/bootstrapSwitch.js"></script>
	<script src="public/js/sedEndGroup.js"></script>
</body>
</html>
<%@page import="edu.nefu.GraduationProject.bean.GroupInfo"%>
<%@page import="edu.nefu.GraduationProject.dao.Group"%>
<%@page import="edu.nefu.GraduationProject.dao.DataDao"%>
<%@page import="edu.nefu.GraduationProject.bean.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	ArrayList<Student> students = DataDao.getWhoJoinSecondStart();
	int groupNum = Group.getGroupNumber("secondStartGroup");
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>二次开题分组</title>
	<link rel="stylesheet" href="public/css/subgroup.css">
	<link rel="stylesheet" href="public/css/bootstrapSwitch.css">
</head>
<body>
	<p class="title">
		<span class="glyphicon glyphicon-send" style="margin-right: 5px;"></span>  <span style="color: #bbb;">\</span> 创建分组
		<span style="float: right;">
			参加第二次开题的人数: 
			<span id="join-num"><%=students.size()%></span>
			<button class="btn btn-default btn-xs"><span class="glyphicon glyphicon-save"></span></button>
		</span>
	</p>	
<%
	if(groupNum > 0){
		%>
		<div id="show-group-info">
			<ul class="nav nav-tabs nav-justified">
				<li class="active">
					<a href="#">第一组 <span class="glyphicon glyphicon-cloud-download" title="生成报表"></span></a>
				</li>
				<%
					for(int i = 2; i <= groupNum; i++){
						%><li><a href="#">第<%=i%>组 <span class="glyphicon glyphicon-cloud-download" title="生成报表"></span></a></li><%
					}
				%>
			</ul>
			<br>
			<%
				for(int i = 0; i < groupNum; i++){
					%>
						<%if(i == 0){
							%><div class="showInfo show"><%
						}else{
							%><div class="showInfo"><%
						}%>
						
								<table class="table table-hover">
									<thead>
										<tr>
											<th>序号</th>
											<th>学号</th>
											<th>姓名</th>
											<th>题目</th>
											<th>教师</th>
											<th>来源</th>
											<th>是否通过一次开题</th>
											<th>开题问题反馈</th>
										</tr>
									</thead>
									<tbody>
									<%
									for(GroupInfo info:Group.getSecondGroup(i)){
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
														if(info.getStartPassed() == 1){
															%>
															<div class="switch switch-mini" data-on-label="是" data-off-label="否">
															    <input type="checkbox" checked />
															</div>
															<%
														}else if(info.getStartPassed() == 0){
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
														<span style="display:none;"><%=info.getStartQuestion() %></span> 
													</td>
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
		</div>
<%
	}else{
		%>
		<div id="subgroup-box">
			<span>请选择创建的组数:</span>
			<select name="" id="subgroup" class="form-control input-sm" style="display:inline-block; width:60px;">
				<%
					for(int i = 1; i < 11; i++){
						%><option value="<%=i%>"><%=i%></option><%
					}
				%>
			</select>
			<button class="btn btn-default btn-sm">创建</button>
			<div class="newGroup">
			
			</div>
			<div class="secondJoined">
					<br />
					<table class="table table-hover">
						<thead>
							<tr>
								<th>学号</th>
								<th>姓名</th>
								<th>题目</th>
								<th>教师</th>
								<th>来源</th>
							</tr>
						</thead>
						<tbody>
						<%
							for(GroupInfo studentInfo:Group.getAllSecondGroup())
							{
								%>
									<tr>
										<td><%=studentInfo.getSid() %></td>
										<td><%=studentInfo.getSname() %></td>
										<td><%=studentInfo.getCtitle() %></td>
										<td><%=studentInfo.getTname() %></td>
										<td><%=studentInfo.getOrigin() %></td>
									</tr>
								<%
							}
						%>
						</tbody>
					</table>
				</div>
		</div>
		<%
	}
%>
	<div class="pop-box show-sbt-res" >
		<div class="pop-title">二次开题问题反馈</div>
		<div class="pop-content">
			<pre>未进行任何问题反馈</pre>
			<textarea name="sbt-res" id="sbt-res" class="form-control text-quesstion" cols="100" rows="5" style="display:none;"></textarea>
			<button class="btn btn-default chge" style="width:478px;">修改</button>
			<button class="btn btn-default chge-yes" style="width:237px;">确定</button>
			<button class="btn btn-danger chge-no" style="width:237px;">取消</button>
		</div>
	</div>
	<script src="public/js/bootstrapSwitch.js"></script>
	<script src="public/js/sedSubgroup.js"></script>
</body>
</html>
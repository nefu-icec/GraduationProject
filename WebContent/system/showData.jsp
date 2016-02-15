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
		.changeMsg .radio+.radio,.changeMsg  .checkbox+.checkbox {
			margin-top: 10px !important;
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
					<th>学号<span style="display: none;">sid</span></th>
					<th>姓名<span style="display: none;">sname</span></th>
					<th>中文题目<span style="display: none;">ctitle</span></th>
					<th>英文题目<span style="display: none;">etitle</span></th>
					<th>指导教师<span style="display: none;">tid</span></th>
					<th>职称</th>
					<th>题目来源<span style="display: none;">origin</span></th>
					<th>毕业设计性质<span style="display: none;">type</span></th>
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
					<th>学号<span style="display: none;">sid</span></th>
					<th>姓名<span style="display: none;">sname</span></th>
					<th>开题时间<span style="display: none;">startTime</span></th>
					<th>二次开题时间<span style="display: none;">secondStartTime</span></th>
					<th>开题问题反馈<span style="display: none;">startQuestion</span></th>
					<th>开题通过<span style="display: none;">startPassed</span></th>
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
					<th>学号<span style="display: none;">sid</span></th>
					<th>姓名<span style="display: none;">sname</span></th>
					<th>答辩时间<span style="display: none;">endTime</span></th>
					<th>二次答辩时间<span style="display: none;">secondEndTime</span></th>
					<th>答辩问题反馈<span style="display: none;">endQuestion</span></th>
					<th>答辩通过<span style="display: none;">endPassed</span></th>
					<th>成绩<span style="display: none;">score</span></th>		
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
								
							}else if(i == 6){
								if(item == null){
									%>
									<td>未录入</td>
									<%
								}else if(Integer.parseInt(item) == 0){
									%>
									<td>不及格</td>
									<%
								}else if(Integer.parseInt(item) == 1){
									%>
									<td>及格</td>
									<%
								}else if(Integer.parseInt(item) == 2){
									%>
									<td>中</td>
									<%
								}else if(Integer.parseInt(item) == 3){
									%>
									<td>良</td>
									<%
								}else{
									%>
									<td>优</td>
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
	<div class="pop-box changeMsg">
		<div class="pop-title">
			信息修改
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
					<td>更新信息:</td>
					<td>
						<input type="text" class="form-control" id="new-info">
						<input size="16" type="text" value="2014-03-20" readonly class="form_datetime form-control" >
						<select name="allTeacher" id="all-teacher" class="form-control input-sm">
						<%
						int i = 0;
						for(Teacher teacher:teachers)
						{
							if(i > 0){
								%>
								<option value="<%=teacher.getTid() %>"><%=teacher.getTname() %></option>
								<%
							}
							i++;
						}%>
						</select>

						<div id="is-passed">
							<div class="radio" style="float: left;margin-right: 10px;">
							  <label>
							    <input type="radio" name="isPassed"  value="0" checked>
							    未通过
							  </label>
							</div>
							<div class="radio" style="float: left;">
							  <label>
							    <input type="radio" name="isPassed"  value="1">
							    通过
							  </label>
							</div>
						</div>
						<textarea class="form-control text-quesstion" rows="3" cols="100"></textarea>
						<select name="score" id="score" class="form-control input-sm">
							<option value="4">优</option>
							<option value="3">良</option>
							<option value="2">中</option>
							<option value="1">及格</option>
							<option value="0">不及格</option>
						</select>
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
			<button class="btn btn-default sbt-new-info" style="width:217px;margin-top:10px;margin-right:5px;">确认修改</button>
			<button class="btn btn-danger" style="width:218px;margin-top:10px;">取消</button>
		</div>
	</div>
	<script type="text/javascript" src="public/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="public/js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
	<script src="public/js/showData.js">

	</script>
</body>
</html>
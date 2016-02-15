var adminAlwaysShow=//管理员始终显示的一些<li>
	[
	 	"li-setPeriod",
	 	"li-downloadExcel",
	 	"li-historyExcel",
	 	"li-showData",
	 	"li-clearData",
	 	"li-notification",
	 	"li-showTeacher",
	 	"li-log",
	 	"li-online-teacher"
	 ];

var teacherAlwaysShow=//教师始终显示的一些<li>
	[
		"#li-doAffair",
		"#li-downloadExcel",
		"#li-historyExcel",
		"#li-showDataForTeacher",
		"#li-notification",
		"#li-showTeacher",
		"#li-online-teacher"
	 ];

window.onload=checkSession();

$(document).ready(function(){//文档就绪
	$("#main").load('welcome.jsp');	
	setName();
	setClick();
	setPushSpan();
});

function setClick()
{
	$('#menu > li > a').click(function(){
		$(this).next().slideToggle();
		return false;
	});	
	$('.menu-list > li > a').click(function(){
		checkSession();
		if($(this).attr('href') != '#'){
			$('#content').load( $(this).attr('href') );
		}
			return false;
	});
	$("#title").click(function(){
		location.reload(); 
	});
	//显示在线的教师
	$("#online-span").click(function(){
		showOnlineTeacher();
	});
	//显示当前的推送信息
	$("#new-push").click(function(){
		showPopBox("#pushContent");
		$(".pop-mask").one("click",function(){
			$("#push-table-content").html("");
			$("#new-push").text("");
			$("#new-push").hide();
		});
		$(".close").one("click",function(){
			$("#push-table-content").html("");
			$("#new-push").text("");
			$("#new-push").hide();
		});
	});
}

function setName()
{
	$.get("SessionServlet?task=getTeacher",null,function(data){
		if(data!="")
			$("#tname").html(data.split(",")[1]);
	});
}

//清除数据
function clearData()
{
	$("#clearData  .pop-content").load("system/clearData.html");
}

//显示管理员资料
function adminInfo()
{
	$("#userInfo .pop-content").load("system/adminInfo.html");
}

//显示教师资料
function teacherInfo()
{
	$("#userInfo .pop-content").load("system/teacherInfo.html");
}

//设置届
function setPeriod()
{
	$("#setPeriod .pop-content").load("start/setPeriod.html");
}

//设置开题时间
function setStartTime()
{
	$("#setStart .pop-content").load("start/setStartTime.html");
}

//设置结题时间
function setEndTime()
{
	$("#setEnd .pop-content").load("end/setEndTime.html");
}

//上传开题登记表
function uploadForm()
{
	$("#uploadForm .pop-content").load("start/uploadRegistrationForm.html");
}

//显示在线教师
function showOnlineTeacher()
{
	$("#onlineTeacher .pop-content").load("onlineTeacher.jsp");
	showPopBox("#onlineTeacher");
}

//为管理员设置Li
function setAdminLi()
{
	$('.menu-list > li').hide();
	for(var i in adminAlwaysShow)
		$("#"+adminAlwaysShow[i]).show();
}

function setTeacherLi()
{
	for(var i in teacherAlwaysShow)
		$("#"+teacherAlwaysShow[i]).show();
}
function checkSession()
{
	$.get("SessionServlet?task=getTeacher",null,function(data){
		if(data=="")
			location.href="sessionError.html";
		else
			$("body").show();
	});
}

function checkSystemStatus()
{
	$.get("StatusServlet?task=get",null,function(data){
		var type=location.href.split("GraduationProject/")[1].split(".html")[0];
		if(type=="teacher")
			setTeacherLiByStatus(data);
		else
			setAdminLiByStatus(data);
		refreshSystemStatus();
	});
}

function refreshSystemStatus()
{
	$.get("StatusServlet?task=getStatusInfo",null,function(data){
		var buffer=data.split("$");
		$("#system-period").text(buffer[0]);
		$("#system-last-status").text(buffer[1]);
		$("#system-status").text(buffer[2]);
		$("#system-task").text(buffer[3]);
		$("#system-next-status").text(buffer[4]);
	});	
}

function setAdminLiByStatus(status)
{
	status=parseInt(status);
	if(status>=2)
	{
		$("#li-work").show();//显示任务管理
		$("#li-checkAffair").show();//显示查看任务进度
	}	
	if(status>=5)
		$("#li-subgroup").show();//显示一次开题分组
	if(status>=7)
		$("#li-sedSubgroup").show();//显示二次开题分组
	if(status>=10)
		$("#li-endGroup").show();//显示答辩分组
	if(status>=12)
		$("#li-sedEndGroup").show();//显示二次答辩
	switch (status)
	{
	case 0://初始状态
		$('.menu-list > li').hide();
		for(var i in adminAlwaysShow)
			$("#"+adminAlwaysShow[i]).show();
		$("#li-uploadForm").show();//显示上传登记表
		break;
	case 1://已上传开题登记表
		$("#li-uploadForm").hide();//隐藏上传登记表
		$("#li-match").show();//显示自主选择教师
		$("#li-random").show();//显示随机分配教师
		break;
	case 2://已分配教师
		$("#li-match").hide();//隐藏自主选择教师
		$("#li-random").hide();//隐藏随机分配教师
		break;
	case 3://已录入题目信息-----------------------------------------------------------------------------------这是华丽分界线
		$("#li-setStartTime").show();//显示设置开题时间
		break;
	case 4://管理员已设置开题时间
		$("#li-setStartTime").hide();//隐藏设置开题时间	
		$("#li-startTimeSelectInfo").show();//显示开题时间选择情况
		break;
	case 5://教师已选择开题时间
		$("#li-startTimeSelectInfo").hide();//隐藏开题时间选择情况
		break;
	case 6://已完成一次开题分组
		break;
	case 7://一次开题已完成
		break;
	case 8://已完成二次开题分组
		break;
	case 9://二次开题已完成
		$("#li-setEndTime").show();//显示设置答辩时间		
		break;
	case 10://管理员已设置答辩时间
		$("#li-setEndTime").hide();//隐藏设置答辩时间
		break;
	case 11://已完成一次答辩分组
		
		break;
	case 12://一次答辩已完成
		
		break;
	case 13://已录入成绩
		
		break;
	default:
		break;
	}
}

function setTeacherLiByStatus(status)
{
	status=parseInt(status);
	switch (status)
	{
	case 4:
		$("#li-selectStartTime").show();
		break;
	default:
		$("#li-selectStartTime").hide();
		break;
	}	
}

$(document).ready(function(){
	$.get("PeriodServlet?task=getPeriod",null,function(data){
		$("#period").html(data);
		showPopBox("#setPeriod",300);
	});
});

$("#modify").click(function(){
	var period=$("#selectPeriod").val();	
	$.get("PeriodServlet?task=setPeriod&period="+period,null,function(data){
		if(data=='true')
		{
			checkSystemStatus();
			$("#setPeriod .pop-content").html("更改成功，当前系统现已工作在"+period+"届");
		}	
		else
			$("#setPeriod .pop-content").append("<br>更改失败，请重试！");
	});
});

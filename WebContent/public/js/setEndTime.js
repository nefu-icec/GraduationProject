$(document).ready(function(){
	loadEndTime();
	$('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	showPopBox("#setEnd",300);
});

// 显示当前时间设置
function loadEndTime()
{
	$.get("SysDateServlet?task=getFirstEnd",null,function(data){
		$("#firstEnd").val(initTime(data));
	});
	$.get("SysDateServlet?task=getSecondEnd",null,function(data){
		$("#secondEnd").val(initTime(data));
	});
}

// 设置第一次开题时间
$("#setFirstEnd").click(function(){
	var secondEnd=$("#secondEnd").val();
	var firstEnd=$("#firstEnd").val();
	if(firstEnd == ""){
		alert("时间未设置!");
	}
	// 获取服务器时间并和设置时间进行核实
	$.get('StatusServlet?task=getServerTime', null, function(data){

		var date = data.split(' ');
		var time = date[0].split('_');
		var tm = time[0] + '-' + time[1] + '-' + time[2];

		if(firstEnd.localeCompare(tm) > 0){//时间设置要在服务器时间以后
			if(secondEnd.localeCompare(firstEnd) > 0  || secondEnd.length == 0){
				$.get("SysDateServlet?task=setFirstEnd&date="+firstEnd,null,function(data){
					if(data=="true")
					{
						checkSystemStatus();
						alert("已设置一次答辩时间为"+firstEnd);
					}			
					else
						alert("设置失败，请重试！！");
				});				
			}else{
				alert('此时间晚于第二次答辩时间,请先设置第二次答辩时间!');
			}
		}else{
			alert('此时间早于服务器时间,请重新设置!');
		}

	});
});

// 设置第二次开题时间
$("#setSecondEnd").click(function(){

	var firstEnd=$("#firstEnd").val();
	var secondEnd=$("#secondEnd").val();

	if(secondEnd == ""){
		alert("时间未设置!");
	}
	if(secondEnd.localeCompare(firstEnd) > 0){//第二次时间要晚于第一次时间
		$.get("SysDateServlet?task=setSecondEnd&date="+secondEnd,null,function(data){
			if(data=="true")
			{
				checkSystemStatus();
				alert("已设置二次答辩时间为"+secondEnd);
			}
			else
				alert("设置失败，请重试！！");
		});	
	}else{
		alert('第二次答辩时间要晚于第一次,请重新设置');
	}
	
});



// 初始化时间格式为: yyyy-mm-dd
function initTime(date){
	var time = date.split('-');
	var tm = '';
	if(parseInt(time[1]) < 10){
		time[1] = '0' + time[1];
	}
	if(parseInt(time[2]) < 10){
		time[2] = '0' + time[2];
	}
	tm = time[0] + '-' + time[1] + '-' + time[2];
	return tm;
}

$(document).ready(function(){
	loadStartTime();
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
	showPopBox("#setStart",300);
});

// 显示之前时间设置
function loadStartTime()
{
	$.get("SysDateServlet?task=getFirstStart",null,function(data){
		$("#firstStart").val(initTime(data));
	});
	$.get("SysDateServlet?task=getSecondStart",null,function(data){
		$("#secondStart").val(initTime(data));
	});
}

//设置第一次开题时间
$("#setFirstStart").click(function(){
	var firstStart=$("#firstStart").val();
	var secondStart=$("#secondStart").val();

	$.get('StatusServlet?task=getServerTime', null, function(data){

		// 获取服务器时间
		var date = data.split(' ');
		var time = date[0].split('_');
		var tm = time[0] + '-' + time[1] + '-' + time[2];

		if(firstStart.localeCompare(tm) > 0){//时间设置要在服务器时间以后
			if(secondStart.localeCompare(firstStart) > 0 || secondStart.length == 0){
				$.get("SysDateServlet?task=setFirstStart&date="+firstStart,null,function(data){
					if(data=="true")
					{
						checkSystemStatus();
						alert("已设置一次开题时间为"+firstStart);
					}		
					else
						alert("设置失败，请重试！！");
				});				
			}else{
				alert('此时间晚于第二次开题时间,请先设置第二次开题时间!');
			}
		}else{
			alert('此时间早于服务器时间,请重新设置!');
		}
	});

});

//设置第二次开题时间
$("#setSecondStart").click(function(){

	var firstStart=$("#firstStart").val();
	var secondStart=$("#secondStart").val();

	if(secondStart.localeCompare(firstStart) > 0){//第二次时间要晚于第一次时间
		$.get("SysDateServlet?task=setSecondStart&date="+secondStart,null,function(data){
			if(data=="true")
			{
				checkSystemStatus();
				alert("已设置二次开题时间为"+secondStart);
			}
			else
				alert("设置失败，请重试！！");
		});			
	}else{
		alert('第二次开题时间要晚于第一次,请重新设置');
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

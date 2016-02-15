
$('.choose-box').mouseover(function(){
	$(this).children('.no-slt').hide();
});
$('.choose-box').mouseout(function(){
	$(this).children('.no-slt').show();
});
// 选择开题
$('.choose-box .opt').click(function(){
	$(this).siblings('.no-slt').remove();
	$(this).siblings('.opt').removeClass('chosen');
	$(this).addClass('chosen');
	var selectTime = $(this).parent().children('.opt').index($(this)) + 1;
	var sid = $(this).parent().parent().siblings(':eq(0)').text();
	if(selectTime == 1){
		$.get('StartServlet?task=joinFirstStart&sid=' + sid, null, function(data, status){
			if(data == 'true'){
				checkSystemStatus();
				$('#content').load('start/selectStartTime.jsp');
			}else{
				alert('操作失败,请重试');
			}
		});
	}else{
		$.get('StartServlet?task=joinSecondStart&sid=' + sid, null, function(data, status){
			if(data == 'true'){
				checkSystemStatus();
				$('#content').load('start/selectStartTime.jsp');
			}else{
				alert('操作失败,请重试');
			}
		});
	}
});

//初始化时间格式为: yyyy-mm-dd
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
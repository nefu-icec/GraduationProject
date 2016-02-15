$('.nav-tabs li').click(function(){
	$('.nav-tabs li').removeClass('active');
	$('.showInfo').removeClass('show')
	$(this).addClass('active');
	$('.showInfo').eq($('.nav-tabs li').index(this)).addClass('show');
});
$('.form_datetime').datetimepicker({
    format: 'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
    showMeridian: 1
});

// 此处为提交表格的全局变量,防止给提交按钮绑定多次事件易造成的事件冒泡现象
var sId, DbTitle, valNew = '#new-info', tblIdx = 0, $td = null;

// 注册表格双击事件
$('.table tbody td').on('dblclick',function(){

	// 初始化 *******************************************************************************
	showInit();
	$td = $(this);
	var tdIdx = $(this).prevAll().length;

	tblIdx = $('.table-hover').index($(this).parents('.table-hover'));
	sId = tblIdx == 0 ? $(this).siblings(':eq(1)').text() : $(this).siblings(':eq(0)').text();
	DbTitle = $(this).parents('.table-hover').find('th').eq(tdIdx).children('span').text();

	var	TitleTxt = $(this).parents('.table-hover').find('th').eq(tdIdx).text()
	,	rowTxt = $(this).text();
	valNew = '#new-info';
	TitleTxt = TitleTxt.replace(DbTitle,'');

	if(DbTitle.match('Time')){
		showChangeMsg('.changeMsg .form_datetime');
		valNew = '.form_datetime';
	}else if(DbTitle =='tid'){
		showChangeMsg('.changeMsg #all-teacher');
		valNew = '#all-teacher';
	}else if(DbTitle.match('Passed')){
		showChangeMsg('.changeMsg #is-passed');
		valNew = '#is-passed input:radio:checked';
	}else if(DbTitle.match('Question')){
		showChangeMsg('.changeMsg .text-quesstion');
		valNew = '.text-quesstion';
	}else if(DbTitle == 'origin'){
		showChangeMsg('#sbt-ogn');
		valNew = '#sbt-ogn';
	}else if(DbTitle == 'type'){
		showChangeMsg('#gdp');
		valNew = '#gdp';
	}else if(DbTitle == 'score'){
		showChangeMsg('#score');
		valNew = '#score';
	}
	// ****************************************************************************************
	showPopBox('.changeMsg', 470);

	$('.pop-content tr:eq(0) td:eq(1)').text(TitleTxt);
	$('.pop-content tr:eq(1) td:eq(1)').text(rowTxt);

});


function showInit(){
	$('.changeMsg tr:eq(2) td:eq(1)').children().hide();
	$('.changeMsg #new-info').show();
}

function showChangeMsg(title){
	$('.changeMsg tr:eq(2) td:eq(1)').children().hide();
	$(title).show();
}

//取消更新操作
$('.btn-danger').click(function(){
	closeAll();
});

//提交更新消息
$('.sbt-new-info').click(function(){
	var newVal = $(valNew).val();
	var data = {
		'sid': sId,
		'columnName': DbTitle,
		'value': newVal
	};
	if(!(DbTitle.match('Passed') || DbTitle.match('score') || DbTitle.match('tid'))){
		$.post('DataServlet?task=modify', data, function(data, status){
			if(data == 'true'){
				$td.text(newVal);
				closeAll();
				checkSystemStatus();
			}else{
				alert('修改失败,请重试!');
			}
		});
	}else{
		$.post('DataServlet?task=modify', data, function(data, status){
			if(data == 'true'){
				closeAll();
				checkSystemStatus();
				var tblNum = tblIdx;
				$('#content').load('system/showData.jsp',function(){
					$('.nav-tabs li').removeClass('active');
					$('.showInfo').removeClass('show');
					$('.nav-tabs li').eq(tblNum).addClass('active');
					$('.showInfo').eq(tblNum).addClass('show');

				});
			}else{
				alert('修改失败,请重试!');
			}
		});
	}
});

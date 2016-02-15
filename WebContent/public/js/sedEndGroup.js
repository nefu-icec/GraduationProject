// 报表下载
$('.glyphicon-cloud-download').click(function(){
	$.get('EndServlet?task=createExcel&type=secondEndGroup', function(data, status){
		if(data == 'true'){
			alert('成功生成此组报表,如需下载请点击"报表下载"按钮');
		}else{
			alert('生成失败,请重试!');
		}
	});
});

$('.title .glyphicon-download-alt').click(function(){
	$('#content').load('excel/historyExcel.jsp');
});

// 已经获得评论的条目用蓝色标识
showQtnInfo();

$('.switch-mini').on('switch-change', function (e, data) {
	$this = $(this);
    var isPassed = data.value ? 1 : 0;
    var sId = $(this).parent().siblings(':eq(1)').text();
    console.log(isPassed, sId);
    var data = {
		'sid': sId,
		'columnName': 'endPassed',
		'value': isPassed
	};
	$.post('DataServlet?task=modify', data, function(data, status){
		if(data == 'true'){
			checkSystemStatus();
			$this.next('span').hide();
		}else{
			alert('提交失败,请重试!');
		}
	});
});

var sid, tblIdx = 0;
$('.glyphicon-comment').click(function(){
	sid = $(this).parent().siblings(':eq(1)').text();
	tblIdx = $('.table-hover').index($(this).parents('.table-hover'));
	var startQtn = $.trim($(this).parent().text());
	if(startQtn != '' && startQtn != 'null'){
		$('.show-sbt-res pre').text(startQtn);
	}else{
		$('.show-sbt-res pre').text('未进行任何问题反馈');
	}
	initBox();
	showPopBox('.show-sbt-res');
});

// 提交问题反馈
$('.chge-yes').click(function(){
	var newVal = $('#sbt-res').val();
	var data = {
		'sid': sid,
		'columnName': 'endQuestion',
		'value': newVal
	};
	$.post('DataServlet?task=modify', data, function(data, status){
		if(data == 'true'){
			closeAll();
			checkSystemStatus();
			var tblNum = tblIdx;
			$('#content').load('end/sedEndGroup.jsp');
		}else{
			alert('修改失败,请重试!');
		}
	});
});

$('.chge').click(function(){
	$(this).fadeOut(function(){
		$('.chge-yes, .chge-no, #sbt-res').fadeIn();
	});
});

$('.chge-no').click(function(){
	initBox();
});

$('.glyphicon-wrench').click(function(){
	sid = $(this).parent().siblings(':eq(1)').text();
	tblIdx = $('.table-hover').index($(this).parents('.table-hover'));
	showPopBox('.upt-score', 224);
});

$('.cg-score').click(function(){
	var newVal = $('#score').val();
	var data = {
		'sid': sid,
		'columnName': 'score',
		'value': newVal
	};
	$.post('DataServlet?task=modify', data, function(data, status){
		if(data == 'true'){
			closeAll();
			checkSystemStatus();
			var tblNum = tblIdx;
			$('#content').load('end/sedEndGroup.jsp');
		}else{
			alert('修改失败,请重试!');
		}
	});
});
$('.cg-no').click(function(){
	closeAll();
});

function initBox(){
	$('#sbt-res').val('');
	$('#sbt-res').hide();
	$('.chge-yes, .chge-no').fadeOut(function(){
		$('.chge').fadeIn();
	});
}

function showQtnInfo(){
	$('.glyphicon-comment').filter(function(){
		return $.trim($(this).next().text()) != '' && $.trim($(this).next().text()) != 'null';
	}).css('color', 'blue').attr('title', '已填写问题反馈');
}
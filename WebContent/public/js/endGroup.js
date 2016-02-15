// 获取参加分组的同学数目
var cptNum = parseInt($('#join-num').text());

// 已经获得评论的条目用蓝色标识
showQtnInfo();

$(".btn-sm").on("click", function(){
	$('.newGroup').html('');


	var subnum = $('#subgroup').val();

	// 动态添加分组选择项
	for(var i = 0; i < subnum; i++){
		var opt = '';
		for(var j = 1; j <= cptNum; j++){
			if(j == cptNum / subnum){
				opt += '<option value="' + j + '" selected="selected">' + j + '</option>';
			}else{
				opt += '<option value="' + j + '">' + j + '</option>';
			}
		}
		opt = '第' + (i + 1) + '组: <select style="display: inline-block; width: 60px;" class="form-control input-sm">' + opt + '</select>' + '<br />';
		$('.newGroup').append(opt);
	}
	$('.newGroup').append('<button class="btn btn-default btn-sm sbt">提交</button>');
	$('.newGroup').append('<div class="rest">50</div>');	
	restNum();
});

// 提交分组信息
$('.newGroup').on('click', 'button', function(){
	var subValue = '';
	var sub = 0;
	for(var i = 0; i < $(this).siblings('select').length; i++){
		subValue += $(this).siblings('select').eq(i).val() + ',';
	}
	subValue = subValue.substr(0, subValue.length - 1);
	var iValue = 0;
	for(var i = 0; i < $('.newGroup button').siblings('select').length; i++){
		iValue += parseInt($('.newGroup button').siblings('select:eq(' + i + ')').val());
	}
	if(cptNum - iValue != 0){
		alert('还有学生未分配,请分配完在进行提交!');
	}else{
		$.get('EndServlet?task=endGroup&group=' + subValue, function(data, status){
			//do something......
			if(data > 0)
			{
				checkSystemStatus();
				alert('分配成功!');
				$('#content').load('end/endGroup.jsp');
			}
			else
				alert('分配失败,请重试!');
		});
	}
});

$('.newGroup').on('change', 'select', function(){
	restNum();
});

// 显示分组信息选项卡的控制
$('.nav-tabs li').click(function(){
	$('.nav-tabs li').removeClass('active');
	$('.showInfo').removeClass('show')
	$(this).addClass('active');
	$('.showInfo').eq($('.nav-tabs li').index(this)).addClass('show');
});

function restNum(){
	var iValue = 0;
	for(var i = 0; i < $('.newGroup button').siblings('select').length; i++){
		iValue += parseInt($('.newGroup button').siblings('select:eq(' + i + ')').val());
	}
	iValue = cptNum - iValue;
	$('.rest').text('还未分配的人数为:' + iValue);
}

// 报表下载
$('.glyphicon-cloud-download').click(function(){
	var groupId = $('.glyphicon-cloud-download').index(this);
	$.get('EndServlet?task=createExcel&type=endGroup&group=' + groupId, function(data, status){
		if(data == 'true'){
			alert('成功生成此组报表,如需下载请点击"报表下载"按钮');
		}else{
			alert('生成失败,请重试!');
		}
	});
});
$('.title .btn-xs').click(function(){
	$('#content').load('excel/historyExcel.jsp');
});

$('.switch-mini').on('switch-change', function (e, data) {
	$this = $(this);
    var isPassed = data.value ? 1 : 0;
    var sId = $(this).parent().siblings(':eq(1)').text();
    var tblNum = $('.table-hover').index($(this).parents('.table-hover'));
    console.log(isPassed, sId);
	$.get('EndServlet?task=setEndPassed&sid='+sId+'&passed='+isPassed, null, function(data){
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
			$('#content').load('end/endGroup.jsp',function(){
				$('.nav-tabs li').removeClass('active');
				$('.showInfo').removeClass('show');
				$('.nav-tabs li').eq(tblNum).addClass('active');
				$('.showInfo').eq(tblNum).addClass('show');

			});
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
			$('#content').load('end/endGroup.jsp',function(){
				$('.nav-tabs li').removeClass('active');
				$('.showInfo').removeClass('show');
				$('.nav-tabs li').eq(tblNum).addClass('active');
				$('.showInfo').eq(tblNum).addClass('show');

			});
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
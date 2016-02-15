// 显示第一组信息
firstTbl();
function firstTbl(){
	$('.showInfo:eq(0)').addClass('show');
}

// 显示分组信息选项卡的控制
$('.nav-tabs li').click(function(){
	$('.nav-tabs li').removeClass('active');
	$('.showInfo').removeClass('show');
	$(this).addClass('active');
	$('.showInfo').eq($('.nav-tabs li').index(this)).addClass('show');
});

// 弹弹弹,弹出层
var sId, DbTitle, tblIdx = 0, valNew = '#new-info';
$('.glyphicon-wrench').click(function(){
	showInit();
	var tdIdx = $(this).parent().prevAll().length;
	var rowTxt = $(this).parent().text()
	,   TitleTxt = $(this).parents('table').find('th').eq(tdIdx).text();
	DbTitle = $(this).parents('table').find('th').eq(tdIdx).children('span').text();
	sId = $(this).parent().siblings(':eq(0)').text();
	tblIdx = $('.table-hover').index($(this).parents('table'));
	TitleTxt = TitleTxt.replace(DbTitle,'');
	
	if(DbTitle == 'origin'){
		showChangeMsg('#sbt-ogn');
		valNew = '#sbt-ogn';
	}else if(DbTitle == 'type'){
		showChangeMsg('#gdp');
		valNew = '#gdp';
	}
	
	$('.pop-content tr:eq(0) td:eq(1)').text(TitleTxt);
	$('.pop-content tr:eq(1) td:eq(1)').text(rowTxt);
	$('.pop-content table tr > td:eq(0)').css('text-align', 'right');
	$("#mask").show();
	var iHeight = document.documentElement.clientHeight;
	var iWidth = document.documentElement.clientWidth;
	$('.pop-change-box').css({'left': (iWidth - 500)/2, 'top': (iHeight - 250)/2}).show(200);
	$('#mask').click(function(){
		closeAll();
	});
});

$('.sbt-new-info').click(function(){
	var newVal = $(valNew).val();
	var data = {
		'sid': sId,
		'columnName': DbTitle,
		'value': newVal
	};
	$.post('DataServlet?task=modify', data, function(data, status){
		if(data == 'true')
		{
			closeAll();
			checkSystemStatus();
			var tblNum = tblIdx;
			$('#content').load('teacher/doAffair.jsp',function(){
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

function showInit(){
	$('.pop-change-box tr:eq(2) td:eq(1)').children().hide();
	$('.pop-change-box #new-info').show();
}

function showChangeMsg(title){
	$('.pop-change-box tr:eq(2) td:eq(1)').children().hide();
	$(title).show();
}

$('.btn-danger').click(function(){
	closeAll();
});

// 关闭弹出层
$(document).on('click', '.close', closeAll);
function closeAll(){
	$("#mask").hide();
	$(".pop-change-box").hide();
}

$('.not-pass').click(function(){
	isPassed(1, $(this));
});
$('.passed').click(function(){
	isPassed(0, $(this));
});

function isPassed(val, emt){
	var tdIdx = emt.parent().prevAll().length;
	var DbTitle = emt.parents('table').find('th').eq(tdIdx).children('span').text()
	,   sId = emt.parent().siblings(':eq(0)').text()
	,	tblIdx = $('.table-hover').index(emt.parents('table'));
	var data = {
		'sid': sId,
		'columnName': DbTitle,
		'value': val
	};
	$.post('DataServlet?task=modify', data, function(data, status){
		if(data == 'true'){
			closeAll();
			$('#content').load('teacher/doAffair.jsp',function(){
				$('.nav-tabs li').removeClass('active');
				$('.showInfo').removeClass('show');
				$('.nav-tabs li').eq(tblIdx).addClass('active');
				$('.showInfo').eq(tblIdx).addClass('show');
			});
		}else{
			alert('修改失败,请重试!');
		}
	});
}
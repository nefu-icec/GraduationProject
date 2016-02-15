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

function showStudent(tid)
{
	if($("."+tid).css("display")=="none")
		$("."+tid).show('height');
	else
		$("."+tid).hide('height');
}
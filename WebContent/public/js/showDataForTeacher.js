$('.nav-tabs li').click(function(){
	$('.nav-tabs li').removeClass('active');
	$('.showInfo').removeClass('show')
	$(this).addClass('active');
	$('.showInfo').eq($('.nav-tabs li').index(this)).addClass('show');
});
var ws;

$(document).ready(function(){
	setWelcomeStyle();
	//setWebSocket();
});

function setWelcomeStyle()
{
	var iWidth = $(window).width()*0.844;
	var iHeight = $(window).height()-112;
	var iPartWidth = (iWidth-60)/2 + "px";
	var iPartHeight = (iHeight-40)/2  + "px";
	var iPartWidth1 = (iWidth-60)/2+10 + "px";
	var iPartHeight1 = (iHeight-40)/2+10  + "px";
	var iTop = (iHeight-40)/2 -45 + "px";
	var iLeft = (iWidth-60)/2 -45 + "px";
	var cssObj = 
	{
	  'width' : iPartWidth,
	  'height' : iPartHeight
	};
	$('.part').css(cssObj);	
	$('.part1').css({'position':'absolute','top':0,'left':0});
	$('.part2').css({'position':'absolute','top':0,'left':iPartWidth1});
	$('.part3').css({'position':'absolute','top':iPartHeight1,'left':0});
	$('.part4').css({'position':'absolute','top':iPartHeight1,'left':iPartWidth1});
	$('.box').css({'position':'absolute','top':iTop,'left':iLeft});
	$('.period').css({'position':'absolute','top':'30px','left':'22px'});
}

function showNotification(nid)
{
	$.get("NotificationServlet?task=getNotification&nid="+nid,null,function(data){
		var buffer=data.split("$&$&$");
		$("#notification .pop-title").text(buffer[0]);
		$("#notification .pop-content pre").html(buffer[1]);
		showPopBox("#notification",700); 
	});
}

function doAffair(){
	$('#content').load('teacher/doAffair.jsp');
}

function setWebSocket()
{
	$.get("IpServlet?task=getServerIP",null,function(data){
		ws=new WebSocket("ws://"+data+":8080/GraduationProject/LogSocketServlet");
		ws.onmessage=function(msg) 
		{
			alert(msg.data);
		};
	});
}
$("#checkAffair").click(function(){
	$('#content').load('system/checkAffair.jsp');	
});
function showNotification(nid)
{
	$.get("NotificationServlet?task=getNotification&nid="+nid,null,function(data){
		var buffer=data.split("$&$&$");
		$("#notification .pop-title").text(buffer[0]);
		$("#notification .pop-content pre").html(buffer[1]);
		showPopBox("#notification",700); 
	});
}

$("#notificationInfo").submit(function(){
	var notificationIpt = $('#notificationInfo :text');
	var notificationTxtarea = $('#notificationInfo textarea');
	var title = notificationIpt[0].value,
		content = notificationTxtarea.val();
	var data=
			{
				"title": title,
				"content": content
			};
	$.post("NotificationServlet?task=add",data,function(data){
		if(data == 'true'){
			alert('提交成功!!');
			closeAll();
			$('#content').load('system/notification.jsp');
		}else{
			alert('提交失败,请重试');
		}
	});	
	return false;
});

$('.btn-danger').click(function(){
	var nid = $(this).parent().siblings().eq(0).text();
	var str = confirm('是否确定删除' + $(this).parent().siblings().eq(1).text() + '的信息?');
	if(str){
		$.get('NotificationServlet?task=delete&nid='+nid, function(data, status){
			if(data == 'true')
			{
				alert('删除成功!');
				$('#content').load('system/notification.jsp');
			}
			else
				alert('操作失败,请重试!');
		});		
	}
});

//添加通知
$('#addNewNotification').click(function(){
	$iHeight = $(document).height();
	$iWidth = $(document).width();
	$boxWidth = $('#newNotification').outerWidth();
	$boxHeight = $('#newNotification').outerHeight();
	$('#newNotification').css({'left': ($iWidth - $boxWidth)/2, 'top': ($iHeight - $boxHeight)/2});
	$('#mask').show();
	$('#newNotification').show();
});
$('.glyphicon-remove').click(function(){
	$('#mask').hide();
	$('#newNotification').hide();
});
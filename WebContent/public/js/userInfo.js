$("#modiPass-btn").click(function(){
	$("#new-password").val('');
	$('#repeat-new-password').val('');
	modifyPassword();
});
$('#modiPass-reset').click(function(){
	$(this).siblings('input').val('');
});
$.get("SessionServlet?task=getTeacher",null,function(data){
	if(data!="")
	{
		var buffer=data.split(",");
		$("#userInfo .pop-title").text(buffer[1]+"的个人资料");
		$("#userInfo .pop-content .tid .userInfo-content").text(buffer[0]);
		$("#userInfo .pop-content .tname .userInfo-content").text(buffer[1]);
		$("#userInfo .pop-content .profession .userInfo-content").text(buffer[2]);
		$("#userInfo .pop-content .password .userInfo-content").text(buffer[3]);
		showPopBox("#userInfo",300);
	}	
});	

//修改密码
function modifyPassword()
{
	$("#modiPass-btn").css("display","none");
	$("#modiPass-input").css("display","inherit");
	$("#modiPass-cancel").on('click', function(){
		$("#modiPass-btn").css("display","inherit");
		$("#modiPass-input").css("display","none");
	});
	$("#modiPass-submit").on('click', function(){
		var password=$("#new-password").val();
		var rePwd = $('#repeat-new-password').val();
		if(password == rePwd){
			var data={"password":password};
			$.post("UserServlet?task=modifyPassword",data,function(data, status){
				if(data=="sessionError")
					location.href="sessionError.html";
				else if(data=="true")
				{
					$("#modiPass-input").css("display","none");
					$("#modiPass-success").css("display","inherit");
					$("#userInfo .pop-content .password .userInfo-content").text(password);		
				}		
				else
				{
					$("#modiPass-input").css("display","none");
					$("#modiPass-fail").css("display","inherit");
				}		
				setTimeout(function(){
					$("#modiPass-btn").css("display","inherit");
					$("#modiPass-fail").css("display","none");
					$("#modiPass-success").css("display","none");
				}, 2000);
			});	
		}else{
			alert('两次输入的密码不匹配');
		}
		
	});
}
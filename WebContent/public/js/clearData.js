$("#clearData-form").submit(function(){
	submitClearData();
	return false;
});

$.get("PeriodServlet?task=getPeriod",null,function(data){	
	$("#clearData .pop-title").text("是否确定清空"+data+"届的所有数据？");
	showPopBox("#clearData",270);
});

function submitClearData()
{
	var password=$("#clearData-pwd").val();
	if(password=="")
		$("#clearData-alert").text("密码为空！！");
	else
	{
		var data={"password":password};
		$.post("StatusServlet?task=clearData",data,function(data){
			if(data=="passwordError"){
				$('#clearData-pwd').addClass('danger');
				$('#clearData-form p').text('密码错误!').slideDown().delay(500).slideUp();
				$('.pwd-error').show();
			}
			else if(data=="true")
			{
				$("#clearData .pop-content").html("清空成功！");
				checkSystemStatus();
			}		
			else{
				$('#clearData-form p').text('系统错误,登录失败!').slideDown().delay(500).slideUp();
			}
		});
	}
}
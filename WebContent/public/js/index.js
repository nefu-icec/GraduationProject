var aCheckbox = document.getElementsByTagName('span');
for(var i = 0; i < aCheckbox.length; i++){
	aCheckbox[i].onclick = function(){
		for(var j = 0; j < aCheckbox.length; j++){
			aCheckbox[j].className = '';
		}
		this.className = 'checked';
	};
}

$("#tid").blur(function(){
	var tid=$("#tid").val();
	$.get("UserServlet?task=checkTid&tid="+tid,null,function(data){
		if(data=="false"){
			$('.username').addClass('new');
			$('.first-enter-item').addClass('new1');
		}
	});
});

$("#tid").focus(function(){
	$('.username').removeClass('new');
	$('.username').addClass('old');
	$('.first-enter-item').removeClass('new1');
	$('.first-enter-item').addClass('old1');
});

$("#login").click(function(){
	var tid=$("#tid").val();
	var ar=[];
	$("input[type=password]").each(function(){
		ar.push($(this).val());
	});
	var password=ar[0];
	$.get("UserServlet?task=checkPassword&password="+password+"&tid="+tid,null,function(data){
		if(data=="false"){
			$('.pwd').addClass('new');
			$('.last-enter-item').addClass('new1');
		}
		else
		{
			$.get("SessionServlet?task=setTeacherByTid&tid="+tid,null,function(data){
				if(data=="true")
				{
					$.get("SessionServlet?task=getTeacher",null,function(data){
						if(data!="")
						{
							if(data.split(",")[2]=="管理员")
								location.href="main.html";
							else
								location.href="teacher.html";
						}
					});
				}
			});	
		}		
	});
});

$(".last-enter-item").focus(function(){
	$('.pwd').removeClass('new');
	$('.pwd').addClass('old');
	$('.last-enter-item').removeClass('new1');
	$('.last-enter-item').addClass('old1');
});
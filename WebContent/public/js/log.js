$("#clearLog").click(function(){
	if(confirm("确定清空所有日志记录？"))
	{
		$.get("LogDispServlet?task=clear",null,function(data){
			if(data=="true")
				$("tr").hide();
			else
				alert("删除失败，请重试！");		
		});
	}
});

function deleteLog(lid)
{
	$.get("LogDispServlet?task=delete&lid="+lid,null,function(data){
		if(data=="true")
			$("#"+lid).hide();
		else
			alert("删除失败，请重试！");	
	});
}
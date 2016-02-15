function deleteExcel(eid)
{
	var str=confirm("是否删除下载记录");
	if(str){
		$.get("ExcelServlet?task=deleteExcel&eid="+eid,null,function(data){
			if(data=="true"){
				$('#content').load('excel/historyExcel.jsp');
			}
			else if(data="false")
				alert("删除失败请重试！");
			else
				alert("位置错误，请重试！！");
		});
	}
	
}

function reDownload(eid)
{
	$.get("ExcelServlet?task=reDownloadTest&eid="+eid,null,function(data){
		if(data=="fileExist")
			location.href="ExcelServlet?task=reDownload&eid="+eid;
		else if(data=="fileNotFound")
			alert("文件不存在！");
		else if(data=="itemNotFound")
			alert("未发现该项！");
		else
			alert("未知错误，请重试！！");
	});	
}
   $('.form_datetime').datetimepicker({
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
    showMeridian: 1
});

$("#addWork").click(function(){
	$("#mask").show();
	var oMask = document.getElementById('mask');
	var oNewWork= document.getElementById('newWork');
	var iClientHeight = document.documentElement.clientHeight;
	var iClientWidth = document.documentElement.clientWidth;
	oMask.style.width = iClientWidth + 'px';
	oMask.style.height = iClientHeight + 'px';
	oNewWork.style.left = (iClientWidth - 550)/2 + 'px';
	oNewWork.style.top = (iClientHeight - 350)/2 + 'px';
	$("#newWork").show();
});

$("#checkAffair").click(function(){
	$('#content').load('system/checkAffair.jsp');	
});

$(".close").click(closeAll);

function closeAll(){
	$("#mask").hide();
	$("#newWork").hide();
}

$("#sbt").click(function(){
	var work=$(':text')[0].value;
	var start=$(':text')[1].value;
	var end=$(':text')[2].value;
	var columns="";
	var arr=$(":checkbox").serializeArray();
	if(work && $(":checkbox:checked").length){
		for(var index in arr)
			columns+=arr[index].value+",";
		var data=
			{
				"work":work,
				"start":start,
				"end":end,
				"columns":columns
			};
		$.post("WorkServlet?task=addWork",data,function(data){
			if(data == 'true'){
				alert('提交成功!!');
				closeAll();
				$('#content').load('system/work.jsp');
			}else{
				alert('提交失败,请重试');
			}
		});
	}else{
		alert('请填写完整后再提交');
	}
	
	return false;
});

//删除任务
deleteTask();
function deleteTask(){
	$('.delet').click(function(){
		var taskId = $(this).parent().siblings(':eq(0)').text();
		$.get('WorkServlet?task=deleteAffair&id=' + taskId, function(data){
			if(data == 'true'){
				alert('删除成功!');
				$('#content').load('system/work.jsp');
			}else{
				alert('删除失败');
			}
		});
	});
}
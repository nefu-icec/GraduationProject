$(function(){
	$('.btn-xs').click(function(){
		var teacherId=$(this).parent().prev().find('.teachers').val();
		var teacher = $(this).parent().prev().find('.teachers').find('option:selected').text()
		var studentId=$(this).parent().siblings(':first').text();
		var student=$(this).parent().siblings(':eq(1)').text();
		if(teacherId != 'default'){
			$.get('DistributeTeacher?task=distribute&tid=' + teacherId + '&sid=' + studentId,function(data, status){
				if(status == 'success'){
					alert('提交成功!O(∩_∩)O哈哈~');
					$('#content').load('start/match.jsp');
					checkSystemStatus();
				}else{
					alert('由于未知原因,提交失败,请重试!!');
				}
			});
		}else{
			alert('未选择教师!!');
		}
		
	});
});
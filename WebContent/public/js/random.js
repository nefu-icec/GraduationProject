$(function(){
	$('.btn-xs').click(function(){
		var restNum = $('.rest-num').text();
		var needNum = $(this).parent().prev().find('.needNum').val();
		var teacherId = $(this).parent().siblings(':eq(0)').text();
		var teacherName = $(this).parent().siblings(':eq(1)').text();
		var teacherPro = $(this).parent().siblings(':eq(2)').text();
		if(parseInt(restNum) >= parseInt(needNum)){
			$.get('DistributeTeacher?task=randomDistribute&tid=' + teacherId + '&number=' + needNum, function(data, status){
				if(data){
					alert(teacherName + teacherPro + '新添加了 ' + data + '个学生');
					$('#content').load('start/random.jsp');
					checkSystemStatus();
				}else{
					alert(status);
				}
			});
		}else{
			alert('你选择的学生数超过未分配的学生数,请重新选择!');
		}
		
	});
});
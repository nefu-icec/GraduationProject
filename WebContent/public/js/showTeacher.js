	var iHeight = document.documentElement.clientHeight;
	var iWidth = document.documentElement.clientWidth;

// 显示添加教师窗口
$('#addTeacher').click(function(){
	$('#mask').show();
	var oNewWork = $('#newTeacher')[0];
	oNewWork.style.left = (iWidth - 500)/2 + 'px';
	oNewWork.style.top = (iHeight - 300)/2 + 'px';
	$('#newTeacher').show();
	$('#mask').click(function(){
		closeAll();
	});
	
});

// 提交添加信息
$('#sbt1').click(function(){
	var teacherIpt = $('#addNewTeacher :text');
	var teacherSlt = $('#addNewTeacher select');
	var tId = teacherIpt[0].value,
		tname = teacherIpt[1].value,
		tProfessor = teacherSlt.val(),
		tPwd = teacherIpt[2].value;
	var data=
			{
				"tid": tId,
				"tname": tname,
				"profession": tProfessor,
				"password": tPwd
			};	

	// 错误代码有待研究,请勿动!!************
			
	// if(tId && tName && tPwd){
		// alert(tId + tName + tProfessor + tPwd);
	//	************************************

	$.post("TeacherServlet?task=add",data,function(data){
		if(data == 'true'){
			alert('提交成功!!');
			closeAll();
			$('#content').load('teacher/showTeacher.jsp');
		}else{
			alert('提交失败,请重试');
		}
	});	

	// 错误代码有待研究,请勿动!!************
	// }else{
	// 	alert('请填写完整后,在进行提交!');
	// }
	//	************************************

	return false;
});

// 删除某条记录
$('.btn-danger').click(function(){
	var tid = $(this).parent().siblings().eq(0).text();
	var str = confirm('是否确定删除' + $(this).parent().siblings().eq(1).text() + '的信息?');
	if(str){
		$.get('TeacherServlet?task=delete&tid='+tid, function(data, status){
			if(data == 'true'){
				alert('删除成功!');
				$('#content').load('teacher/showTeacher.jsp');
			}else{
				alert('操作失败,请重试!');
			}
		});		
	}

});

// close popBox
$(document).on('click', '.close', closeAll);
function closeAll(){
	$("#mask").hide();
	$(this).parent().parent().hide();
}

//显示学生信息
function studentInfo(tid)
{
	$('.pop-info').html('');
	$.get("TeacherServlet?task=getDistributeStudent&tid="+tid,null,function(data){
		var sdtInfos = data.split(';');
		var str = '',
			sdtInfo = '',
			go = '';
		for(var i = 0; i < sdtInfos.length; i++){
			go = '';
			sdtInfo = sdtInfos[i].split(',');
			for(var j = 0; j < sdtInfo.length; j++){
				go += '<td>' + sdtInfo[j] + '</td>';
			}
			str += '<tr>' + go + '</tr>';
		}
		str = 	'<div class="pop-title">' +
					'<span class="addTask">学生信息</span>' +
					'<span class="glyphicon glyphicon-remove close"></span>' +
				'</div>' +
				'<table class="table table-hover">'+
					'<tr>' +
						'<th>学号</th>' +
						'<th>姓名</th>' +
						'<th>班级</th>' +
					'</tr>' +
					str + 
				'</table>';
		$('.pop-info').append(str);
		$('#mask').show();
		$('.pop-info').css({'left': (iWidth - 500)/2}).show(function(){
			$(this).css({'top': (iHeight - $(this).outerWidth())/2});
		});
	});
}
$(function(){
	setConent();
	$('#file_upload').uploadify({
		'swf'      : 'public/imgs/uploadify.swf',
		'uploader' : 'UploadServlet?task=uploadRegistrationForm',
		'buttonText' : '选择文件'
	});
	showPopBox("#uploadForm",400);
});

$(".pop-mask").one("click",function(){
	checkSystemStatus();
});

$(".close").one("click",function(){
	checkSystemStatus();
});

function setConent()
{
	$.get('StatusServlet?task=get',null,function(data){
		if(data>0)
		{
			$('#upload').hide();
			$('#message').show();
		}
		else
		{
			$('#upload').show();
			$('#message').hide();
		}
	});			
}
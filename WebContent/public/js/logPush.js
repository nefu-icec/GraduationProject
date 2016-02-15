var socket;//定义全局的WebSocket对象

$(document).ready(function(){
	$.get("IpServlet?task=getServerIpPort",null,function(data){
		var socketURL="ws://"+data+"/GraduationProject/LogServlet";
		socket=new WebSocket(socketURL);
		socket.onmessage=function(message)
		{
			pushLog(message.data);
		};
	});
});

function pushLog(message)
{
	$('#tip')[0].play();
	checkSystemStatus();
	var buffer=message.split(":=");
	var head=buffer[0];
	var data=buffer[1];
	newPush(head,data);
	switch (head)
	{
	case "OpenSuccess":
		$("#server-connect").show();
		break;
	case "NewTeacherLogin":
		setPushSpan();
		break;
	case "TeacherExit":
		setPushSpan();
		break;
	case "Modify":
		pushModify(data);
		break;
	case "ClearData":
		
		break;
	case "Set":
		
		break;
	default:
		alert(message);
		break;
	}
}

function setPushSpan()
{
	$.get("ApplicationServlet?task=getOnlineNumber",null,function(data){
		$("#online-span").text("在线"+data+"人");
	});
}

function pushModify(data)
{
	
}

function newPush(head,data)
{
	$("#new-push").show();
	if($("#new-push").text()=="")
		$("#new-push").text("1");
	else
		$("#new-push").text(parseInt($("#new-push").text())+1);
	$("#push-number").text($("#new-push").text());
	var tr="";
	switch (head)
	{
	case "OpenSuccess":
		tr="<tr><td>本人已连接。</td></tr>";
		break;
	case "NewTeacherLogin":
		var buffer=data.split(",");
		tr="<tr><td>"+buffer[0]+"号教师："+buffer[1]+"已连接。</td></tr>";
		break;
	case "TeacherExit":
		var buffer=data.split(",");
		tr="<tr><td>"+buffer[0]+"号教师"+buffer[1]+"已下线。</td></tr>";
		break;
	case "Modify":
		var buffer=data.split("$&$");
		tr="<tr><td>"+buffer[4]+"号教师"+buffer[5]+"把"+buffer[2]+"号学生"
			+buffer[3]+"的"+buffer[6]+"列更改为"+buffer[7]+"。</td></tr>";
		break;
	case "ClearData":
		tr="<tr><td>当前届的所有数据已清空！</td></tr>";
		break;
	case "Set":
		tr="<tr><td>管理员已"+data+"。</td></tr>";
		break;
	default:
		alert(message);
		break;
	}
	$("#push-table-content").append(tr);
}
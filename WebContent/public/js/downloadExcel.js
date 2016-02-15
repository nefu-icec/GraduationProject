var ColumnName = null;
var DBColumnName = null;

$().ready(function(){
	setCheckboxValues();
});

function setCheckboxValues(){
	$.get('ExcelServlet?task=getDBColumnName', null, function (data) {
		//分割字段,Eclipse的注释风格真操蛋!!!
		var column = data.split(';');
		DBColumnName = column[0].split(',');
		ColumnName = column[1].split(',');
		
		var checkboxs = '';
		for(var i = 0; i < ColumnName.length; i++){
			checkboxs += '<li>' + 
										'<input type="checkbox" name="ColumnName" value="' + DBColumnName[i] + '" id="label' + i + '">' + 
										'<label for="label' + i + '">' + ColumnName[i] + '</label>' + 
									'</li>';
		}
		checkboxs = '<ul>'
							 + checkboxs + 
							 '</ul>';
		//添加复选框元素
		$('#checkBoxs').html(checkboxs);
	});
}

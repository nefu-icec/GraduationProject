var ColumnName = null;
var DBColumnName = null;

$().ready(function(){
	setCheckboxValues();
});

function setCheckboxValues(){
	$.get('ExcelServlet?task=getDBColumnName', null, function (data) {
		//�ָ��ֶ�,Eclipse��ע�ͷ����ٵ�!!!
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
		//��Ӹ�ѡ��Ԫ��
		$('#checkBoxs').html(checkboxs);
	});
}

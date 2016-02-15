function showStudent(tid)
{
	if($("."+tid).css("display")=="none")
		$("."+tid).show('height');
	else
		$("."+tid).hide('height');
}
package edu.nefu.GraduationProject.util;

public class Constant
{	
	public static final int ServerPort=8080;
	
	//当前系统工作状态
	public static final String [] Status=
		{
			"未上传开题登记表",
			"已上传开题登记表",
			"已分配教师",
			"已录入项目信息",
			"管理员已设置开题时间",
			"教师已选择开题时间",
			"已完成一次开题分组",
			"一次开题已完成",
			"已完成二次开题分组",
			"二次开题已完成",
			"管理员已设置答辩时间",
			"已完成一次答辩分组",
			"一次答辩已完成",
			"已录入成绩"
		};
	
	//当前要完成的任务
	public static final String [] ToDo=
		{
			"管理员请请上传开题登记表",
			"管理员请请分配教师",
			"设置项目信息，请各位教师录入中、英文题目、题目来源和毕业设计性质 ",
			"管理员请设置开题时间",
			"教师请选择您所指导的学生是否参加一次开题分组",
			"管理员请进行一次开题分组",
			"管理员请确认参加一次开题分组的学生是否通过",
			"管理员请进行二次开题分组",
			"管理员请确认参加二次开题分组的学生是否通过",
			"开题完成，项目进行中。。。管理员请设置答辩时间",
			"管理员请进行答辩分组",
			"管理员请确认参加一次答辩的学生是否通过答辩",
			"管理员请确认参加二次答辩的学生是否通过答辩,管理员请录入成绩",
			"成绩已录入，没有要完成的任务"
		};
	
	public static final String [] WriteColumnName={"学院","专业名称","行政班","学号","姓名","联系电话","年级","学籍状态"};
	public static final String [] DBWriteColumnName={"college","major","className","sid","sname","tel","grade","status"};
	
	public static final String [] ColumnName=
		{
			"届","学院","专业名称","行政班","学号","姓名","联系电话","年级","学籍状态","中文题目",
			"英文题目","成绩","指导教师","题目来源","开题时间","二次开题时间",
			"开题问题反馈","开题通过","答辩时间","二次答辩时间","答辩问题反馈","答辩通过","毕业设计性质"
		};
	public static final String [] DBColumnName=
		{
			"period","college","major","className","sid","sname","tel","grade","status","ctitle",
			"etitle","score","tid","origin","startTime","secondStartTime",
			"startQuestion","startPassed","endTime","secondEndTime","endQuestion","endPassed","type"
		};
	
	public static final String [] Scores={"不及格","及个","中等","良好","优秀"};//成绩的等级
	
	public static int [] StudentInfoNum={0,4,5,6,1,2,7,3,8};//显示所有数据时候需要显示的学生信息
	public static int [] ProjectInfoNum={4,5,9,10,12,12,13,22};//显示所有数据时候需要显示的项目信息
	public static int [] StartInfoNum={4,5,14,15,16,17};//显示所有数据时候需要显示的开题信息
	public static int [] EndtInfoNum={4,5,18,19,20,21,11};//显示所有数据时候需要显示的答辩信息
	
	public static int [] DistrubuteStudentInfoNum={12,4,5,6,1,2,7,3};//分配教师时候需要显示的学生信息
	
	public static int getDBColumnNumber(String columnName)
	{
		int i=0;
		for(String name:DBColumnName)
		{
			if(name.equals(columnName))
				return i;
			i++;
		}
		return -1;
	}
	
	public static String getColumnName(int columnNumber)
	{
		return ColumnName[columnNumber];
	}
	
	/**
	 * 根据DBColumnName获取ColumnName
	 * @param dbColumnName
	 * @return
	 */
	public static String getColumnName(String dbColumnName)
	{
		for(int i=0;i<DBColumnName.length;i++)
			if(DBColumnName[i].equals(dbColumnName))
				return ColumnName[i];
		return null;
	}

	/**
	 * 根据ColumnName获取DBColumnName
	 * @param columnName
	 * @return
	 */
	public static String getDBColumnName(String columnName)
	{
		for(int i=0;i<ColumnName.length;i++)
			if(ColumnName[i].equals(columnName))
				return DBColumnName[i];
		return null;
	}
}

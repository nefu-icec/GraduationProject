package edu.nefu.GraduationProject.util;

public class Constant
{	
	public static final int ServerPort=8080;
	
	//��ǰϵͳ����״̬
	public static final String [] Status=
		{
			"δ�ϴ�����ǼǱ�",
			"���ϴ�����ǼǱ�",
			"�ѷ����ʦ",
			"��¼����Ŀ��Ϣ",
			"����Ա�����ÿ���ʱ��",
			"��ʦ��ѡ����ʱ��",
			"�����һ�ο������",
			"һ�ο��������",
			"����ɶ��ο������",
			"���ο��������",
			"����Ա�����ô��ʱ��",
			"�����һ�δ�����",
			"һ�δ�������",
			"��¼��ɼ�"
		};
	
	//��ǰҪ��ɵ�����
	public static final String [] ToDo=
		{
			"����Ա�����ϴ�����ǼǱ�",
			"����Ա��������ʦ",
			"������Ŀ��Ϣ�����λ��ʦ¼���С�Ӣ����Ŀ����Ŀ��Դ�ͱ�ҵ������� ",
			"����Ա�����ÿ���ʱ��",
			"��ʦ��ѡ������ָ����ѧ���Ƿ�μ�һ�ο������",
			"����Ա�����һ�ο������",
			"����Ա��ȷ�ϲμ�һ�ο�������ѧ���Ƿ�ͨ��",
			"����Ա����ж��ο������",
			"����Ա��ȷ�ϲμӶ��ο�������ѧ���Ƿ�ͨ��",
			"������ɣ���Ŀ�����С���������Ա�����ô��ʱ��",
			"����Ա����д�����",
			"����Ա��ȷ�ϲμ�һ�δ���ѧ���Ƿ�ͨ�����",
			"����Ա��ȷ�ϲμӶ��δ���ѧ���Ƿ�ͨ�����,����Ա��¼��ɼ�",
			"�ɼ���¼�룬û��Ҫ��ɵ�����"
		};
	
	public static final String [] WriteColumnName={"ѧԺ","רҵ����","������","ѧ��","����","��ϵ�绰","�꼶","ѧ��״̬"};
	public static final String [] DBWriteColumnName={"college","major","className","sid","sname","tel","grade","status"};
	
	public static final String [] ColumnName=
		{
			"��","ѧԺ","רҵ����","������","ѧ��","����","��ϵ�绰","�꼶","ѧ��״̬","������Ŀ",
			"Ӣ����Ŀ","�ɼ�","ָ����ʦ","��Ŀ��Դ","����ʱ��","���ο���ʱ��",
			"�������ⷴ��","����ͨ��","���ʱ��","���δ��ʱ��","������ⷴ��","���ͨ��","��ҵ�������"
		};
	public static final String [] DBColumnName=
		{
			"period","college","major","className","sid","sname","tel","grade","status","ctitle",
			"etitle","score","tid","origin","startTime","secondStartTime",
			"startQuestion","startPassed","endTime","secondEndTime","endQuestion","endPassed","type"
		};
	
	public static final String [] Scores={"������","����","�е�","����","����"};//�ɼ��ĵȼ�
	
	public static int [] StudentInfoNum={0,4,5,6,1,2,7,3,8};//��ʾ��������ʱ����Ҫ��ʾ��ѧ����Ϣ
	public static int [] ProjectInfoNum={4,5,9,10,12,12,13,22};//��ʾ��������ʱ����Ҫ��ʾ����Ŀ��Ϣ
	public static int [] StartInfoNum={4,5,14,15,16,17};//��ʾ��������ʱ����Ҫ��ʾ�Ŀ�����Ϣ
	public static int [] EndtInfoNum={4,5,18,19,20,21,11};//��ʾ��������ʱ����Ҫ��ʾ�Ĵ����Ϣ
	
	public static int [] DistrubuteStudentInfoNum={12,4,5,6,1,2,7,3};//�����ʦʱ����Ҫ��ʾ��ѧ����Ϣ
	
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
	 * ����DBColumnName��ȡColumnName
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
	 * ����ColumnName��ȡDBColumnName
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

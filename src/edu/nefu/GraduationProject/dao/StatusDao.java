package edu.nefu.GraduationProject.dao;

import java.sql.ResultSet;

import edu.nefu.GraduationProject.util.ConnectDB;

public class StatusDao
{
	/**
	 * ���õ�ǰ��ϵͳ״̬
	 * @param status ϵͳ״̬
	 * @return
	 */
	public static boolean set(int status)
	{
		String update="update status set value="+status
				+" where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		int row=db.exeUpdate(update);
		db.close();
		if(row>0)
			return true;
		else
			return false;
	}
	
	/**
	 * ��������set()����ֱ�����óɹ�Ϊֹ
	 * @param status
	 */
	public static boolean SET(int status)
	{
		boolean success=false;
		while(!success)
			success=set(status);
		return success;
	}
	
	/**
	 * ��õ�ǰ���ϵͳ״̬
	 * @return
	 */
	public static int get()
	{
		String select="select value from status where period="+PeriodDao.get();
		ConnectDB db=new ConnectDB();
		ResultSet rs=db.exeQuery(select);
		try
		{
			if(rs.next())
			{
				int value=Integer.parseInt(rs.getString("value"));
				db.close();
				return value;
			}
			else 
			{
				db.close();
				return -1;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.close();
			return -1;
		}
	}
	
	public static void check()
	{
		int status=get();
		switch (status) 
		{
		case 0://��ʼ״̬
			
			break;
		case 1://���ϴ�����ǼǱ�
			if(!DataDao.hasNull("tid"))
				SET(2);			
			break;
		case 2://�ѷ����ʦ
			if(!DataDao.hasNull("ctitle")&&!DataDao.hasNull("etitle")&&!DataDao.hasNull("origin")&&!DataDao.hasNull("type"))
				SET(3);
			break;
		case 3://��¼����Ŀ��Ϣ
			if(SysDateDao.get("firstStart")!=null&&SysDateDao.get("secondStart")!=null)
				SET(4);
			break;
		case 4://����Ա�����ÿ���ʱ��
			if(!DataDao.hasNull2("startTime", "secondStartTime"))
				SET(5);
			break;
		case 5://��ʦ��ѡ����ʱ��
			break;
		case 6://�����һ�ο������
			if(!DataDao.hasNull("startPassed", "where firstStartGroup is not null"))
				SET(7);
			break;
		case 7://һ�ο��������
		
			break;
		case 8://����ɶ��ο������
			if(!DataDao.hasNull("startPassed"))
				SET(9);
			break;
		case 9://���ο��������
			if(SysDateDao.get("firstEnd")!=null&&SysDateDao.get("secondEnd")!=null)
			{
				SET(10);
				Group.joinFirstEnd();
			}
			break;
		case 10://����Ա�����ô��ʱ��
			
			break;
		case 11://�����һ�δ�����
			if(!DataDao.hasNull("endPassed"," where startPassed=1"))
				SET(12);
			break;
		case 12://һ�δ�������
			if(!DataDao.hasNull("score"," where startPassed=1 and endPassed=1"))
				SET(13);
			break;
		case 13://��¼��ɼ�
			
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		check();
	}
}

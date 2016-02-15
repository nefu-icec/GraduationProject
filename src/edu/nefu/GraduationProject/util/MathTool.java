package edu.nefu.GraduationProject.util;

import java.util.Random;
import java.util.regex.Pattern;

public class MathTool 
{
	/**
	 * 在[0,range-1]区间生成need个随机数
	 * @param need 需要生成随机数的个数
	 * @param range 范围
	 * @return 随机数组
	 */
	public static int [] getRandom(int need,int range)
	{
		int [] rands=new int[need];
		Random random=new Random ();  
        boolean[] bool=new boolean[range];
        int randInt=0;  
        for(int i=0;i<need;i++)
        {
        	do
        		randInt=random.nextInt(range);  
        	while(bool[randInt]);
        	bool[randInt]=true;  
        	rands[i]=randInt;
        }
        return rands;
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	public static int getRandom(int range)
	{
		return getRandom(1, range)[0];
	}
	
}

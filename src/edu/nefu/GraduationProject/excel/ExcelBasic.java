package edu.nefu.GraduationProject.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import edu.nefu.GraduationProject.util.DateTool;

@SuppressWarnings("deprecation")
public class ExcelBasic 
{
	public static FileOutputStream createExcel(String pathName) 
	{
 		FileOutputStream stream;
		try 
		{
			stream = new FileOutputStream(pathName);
			return stream;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
 		return null;
	}
	
	public static void cteateCell(HSSFWorkbook workbook,HSSFSheet sheet,int row,int col,String val)
	{
		HSSFRow Row= sheet.createRow(row-1);
		HSSFCell cell = Row.createCell(col-1);
		cell.setCellValue(val);
		HSSFCellStyle cellstyle = workbook.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(cellstyle);
	}
	
	/**
	 * ��Ԫ����������������
	 * @param wbSheet
	 * @param row �к�
	 * @param col	�к�
	 * @param val ��������
	 */
	public static void cteateCell(WbSheet wbSheet,int row,int col,String val)
	{
		HSSFWorkbook workbook =wbSheet.getWorkbook();
		HSSFSheet sheet =wbSheet.getSheet();
		HSSFRow Row= sheet.createRow(row-1);
		HSSFCell cell = Row.createCell(col-1);
		cell.setCellValue(val);
		HSSFCellStyle cellstyle = workbook.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(cellstyle);
	}
	
	/**
	 * ����һ������
	 * @param sheet 
	 * @param row �к�
	 * @param values ��������
	 */
	public static void inputRow(HSSFSheet sheet,int row,String [] values)
	{
		HSSFRow Row= sheet.createRow(row-1);
		for(int i=0;i<values.length;i++)
			Row.createCell((short)i).setCellValue(values[i]); 
	}
	
	public static FileOutputStream formatEcxel(HSSFWorkbook workbook,
			FileOutputStream stream, HSSFSheet sheet, ExcelCreator creator)
	{
		mergeRegion(sheet, 1, 1, 1,creator.getColumnNumbers().length);
 		String name=creator.getTitle()+"  "+DateTool.getWordTime();
		cteateCell(workbook,sheet,1,1,name);
		inputRow(sheet,2,creator.getColumnNames());
		return stream;
	}
	
	public static FileOutputStream formatEcxel(HSSFWorkbook workbook,
			FileOutputStream stream, HSSFSheet sheet, GroupExcel excel)
	{
		mergeRegion(sheet, 1, 1, 1,6);
 		String name=excel.getTitle()+"  "+DateTool.getWordTime();
		cteateCell(workbook,sheet,1,1,name);
		inputRow(sheet,2,new String []{"���","����","ѧ��","��Ŀ","ָ����ʦ","��Դ"});
		return stream;
	}
	
	/**
	 * �ϲ���Ԫ��
	 * @param sheet sheet��
	 * @param startX ��ʼ�������
	 * @param startY ��ʼ��������
	 * @param endX �����������
	 * @param endY ������������
	 */
	public static void mergeRegion(HSSFSheet sheet,int startX,int startY,int endX,int endY)
	{
		startX--;
		startY--;
		endX--;
		endY--;
		short sStartY=(short)startX;
		short sEndY=(short)endY;
		sheet.addMergedRegion(new Region(startX,sStartY,endX,sEndY));
	}
}

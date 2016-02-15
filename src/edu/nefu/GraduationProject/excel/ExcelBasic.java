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
	 * 向单元格中输入数据内容
	 * @param wbSheet
	 * @param row 行号
	 * @param col	列号
	 * @param val 数据内容
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
	 * 输入一行数据
	 * @param sheet 
	 * @param row 行号
	 * @param values 数据内容
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
		inputRow(sheet,2,new String []{"序号","姓名","学号","题目","指导教师","来源"});
		return stream;
	}
	
	/**
	 * 合并单元格
	 * @param sheet sheet号
	 * @param startX 起始点横坐标
	 * @param startY 起始点纵坐标
	 * @param endX 结束点横坐标
	 * @param endY 结束点纵坐标
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

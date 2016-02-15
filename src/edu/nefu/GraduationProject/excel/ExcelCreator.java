package edu.nefu.GraduationProject.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import edu.nefu.GraduationProject.dao.DataDao;
import edu.nefu.GraduationProject.util.Constant;
import edu.nefu.GraduationProject.util.DateTool;

public class ExcelCreator 
{
	private String title;
	private String sheetName;
	private int [] columnNumbers;
	private String [] columnNames;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int[] getColumnNumbers() {
		return columnNumbers;
	}

	public void setColumnNumbers(int[] columnNumbers) {
		this.columnNumbers = columnNumbers;
	}
	
	public String [] getColumnNames() {
		return columnNames;
	}

	public ExcelCreator(String title,String sheetName, int[] columnNumbers)
	{
		super();
		this.title = title;
		this.sheetName=sheetName;
		this.columnNumbers = columnNumbers;
		setColumnNames();
	}
	
	private void setColumnNames() 
	{
		columnNames=new String[columnNumbers.length];
		for(int i=0;i<columnNumbers.length;i++)
			columnNames[i]=Constant.ColumnName[columnNumbers[i]];
	}

	/**
	 * 生成Excel文档
	 * @param savePath 返回以当前系统时间命名的文件名
	 * @return
	 */
	public String create(String savePath)
	{
 		String fileName=DateTool.getNowTime()+".xls";
		FileOutputStream stream=ExcelBasic.createExcel(savePath+"\\"+fileName);
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		stream=ExcelBasic.formatEcxel(workbook,stream,sheet,this);
		ArrayList<String []> data=DataDao.getColumnData(columnNumbers,null);
		int i=3;
		for(String [] rowData:data)
		{
			ExcelBasic.inputRow(sheet, i, rowData);
			i++;
		}
		try 
		{
			workbook.write(stream);
			stream.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		return fileName;
	}
}

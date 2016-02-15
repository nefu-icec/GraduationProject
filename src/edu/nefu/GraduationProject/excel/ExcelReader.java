package edu.nefu.GraduationProject.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader 
{
	Workbook wb=null;
  
	public ExcelReader(String path)
	{
		try
		{
			InputStream inp=new FileInputStream(path);
		    wb=WorkbookFactory.create(inp);   			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取所有数据
	 * @param sheetIndex sheet号
	 * @return
	 */
	public ArrayList<String []> getAllData(int sheetIndex)
	{
		int columnNum=0;
		ArrayList<String []> data=new ArrayList<String []>();
		Sheet sheet=wb.getSheetAt(sheetIndex);
	    if(sheet.getRow(0)!=null)
	        columnNum=sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();
	    if(columnNum>0)
	    {
	    	 for(Row row:sheet)
	    	 {
	    		 String[] singleRow=new String[columnNum];
	    		 for(int i=0;i<columnNum;i++)
	    		 {
	    			 Cell cell=row.getCell(i, Row.CREATE_NULL_AS_BLANK);
	    			 switch(cell.getCellType())
	    			 {
	    			 	case Cell.CELL_TYPE_BLANK:
	    			 		singleRow[i]="";
	    			 		break;
	    			 	case Cell.CELL_TYPE_BOOLEAN:
	    			 		singleRow[i]=Boolean.toString(cell.getBooleanCellValue());
	    			 		break;
	    			 	case Cell.CELL_TYPE_NUMERIC:
	    			 		if(DateUtil.isCellDateFormatted(cell))
	    			 			singleRow[i]=String.valueOf(cell.getDateCellValue());
	    			 		else
	    			 		{
	    			 			cell.setCellType(Cell.CELL_TYPE_STRING);
	    			 			String temp=cell.getStringCellValue();
	    			 			if(temp.indexOf(".")>-1)
	    			 				singleRow[i]=String.valueOf(new Double(temp)).trim();
	    			 			else
	    			 				singleRow[i]=temp.trim();
	    			 		}
	    			 		break;
	    			 	case Cell.CELL_TYPE_STRING:
	    			 		singleRow[i]=cell.getStringCellValue().trim();
	    			 		break;
	    			 	case Cell.CELL_TYPE_ERROR:
	    			 		singleRow[i]="";
	    			 		break; 		
						case Cell.CELL_TYPE_FORMULA:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							singleRow[i]=cell.getStringCellValue();
							if(singleRow[i]!=null)
							singleRow[i]=singleRow[i].replaceAll("#N/A","").trim();
							break;  
						default:
							singleRow[i]="";
						break;
	    			 }
	    		 }
	             if("".equals(singleRow[0]))//如果第一行为空，跳过
	            	 continue;
	             data.add(singleRow);
	    	 }
	    }
		return data;
	}
	
	/**
	 * 得到行数
	 * @param sheetIndex sheet号
	 * @return 行数
	 */
	public int getRowNum(int sheetIndex)
	{
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}

	/**
	 * 得到列数
	 * @param sheetIndex sheet号
	 * @return 列数
	 */
	public int getColumnNum(int sheetIndex)
	{
		Sheet sheet=wb.getSheetAt(sheetIndex);
		Row row=sheet.getRow(0);
		if(row!=null&&row.getLastCellNum()>0)
			return row.getLastCellNum();
		else
			return 0;
	}
	
	/**
	 * 获取某一行数据
	 * @param sheetIndex sheet号
	 * @param rowIndex 行号，计数从0开始，rowIndex为0代表header行
	 * @return 一行数据
	 */
	public String[] getRowData(int sheetIndex,int rowIndex)
	{
		 String[] dataArray=null;
		 if(rowIndex>this.getColumnNum(sheetIndex))
			 return dataArray;
		 else
		 {
			 dataArray = new String[this.getColumnNum(sheetIndex)];
			 return getAllData(sheetIndex).get(rowIndex);
		 }
	}
	
	/**
	 * 获取某一列数据
	 * @param sheetIndex sheet号
	 * @param colIndex 列号
	 * @return 一列数据
	 */
	public String[] getColumnData(int sheetIndex,int colIndex)
	{
		String[] dataArray=null;
		ArrayList<String[]> data=getAllData(sheetIndex);
		if(colIndex>this.getColumnNum(sheetIndex))
			return dataArray;
		else
		{
			if(data!=null&&data.size()>0)
			{
				dataArray=new String[this.getRowNum(sheetIndex)+1];
				int index=0;
				 for(String[] rowData:data)
				 {
					 if(rowData!=null)
					 {
						 dataArray[index]=rowData[colIndex];
						 index++;
					 }
				 }
			}
		}
		return dataArray;
	}
	 
	public static void main(String[] args)
	{
		ExcelReader reader=new ExcelReader("d:\\test.xls");
		ArrayList<String[]> data=reader.getAllData(0);
		for(String[] row:data)
		{
			for(String item:row)
				System.out.print(item+" ");
			System.out.println();
		}
	}
}
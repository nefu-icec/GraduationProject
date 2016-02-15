package edu.nefu.GraduationProject.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import edu.nefu.GraduationProject.bean.GroupInfo;
import edu.nefu.GraduationProject.dao.Group;
import edu.nefu.GraduationProject.util.DateTool;

public class GroupExcel
{
	private String title;
	private String sheetName;
	private ArrayList<GroupInfo> infos;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public ArrayList<GroupInfo> getInfos() {
		return infos;
	}

	public void setInfos(ArrayList<GroupInfo> infos) {
		this.infos = infos;
	}

	public GroupExcel(String title, String sheetName, ArrayList<GroupInfo> infos)
	{
		super();
		this.title = title;
		this.sheetName = sheetName;
		this.infos = infos;
	}

	public String create(String savePath)
	{
		String fileName=DateTool.getNowTime()+".xls";
		FileOutputStream stream=ExcelBasic.createExcel(savePath+"\\"+fileName);
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		stream=ExcelBasic.formatEcxel(workbook,stream,sheet,this);
		int i=3;
		for(GroupInfo info:infos)
		{
			String [] rowData=new String[6];
			rowData[0]=info.getNumber()+"";
			rowData[1]=info.getSid()+"";
			rowData[2]=info.getSname();
			rowData[3]=info.getCtitle();
			rowData[4]=info.getTname();
			rowData[5]=info.getOrigin();
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
	
	public static void main(String[] args) {
		new GroupExcel("aa", "aa", Group.getFirstGroup(4)).create("d://");
	}
}

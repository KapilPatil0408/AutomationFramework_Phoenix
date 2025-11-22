package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {
		//APACHE POI OOXML 
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook= new XSSFWorkbook(is);
		XSSFSheet mySheet = myWorkBook.getSheet("LoginTestData");
		
		
		XSSFRow myRow;
		XSSFCell myCell;
		
	//	System.out.println(myCell.getStringCellValue());
		
		int lastRowIndex= mySheet.getLastRowNum();
		
		XSSFRow rowHeader= mySheet.getRow(0);
		int lastCellIndex= rowHeader.getLastCellNum()-1;
		
		for(int row=0; row<=lastRowIndex; row++) {
			for(int cols=0; cols<=lastCellIndex; cols++) {
				myRow= mySheet.getRow(row);
				myCell= myRow.getCell(cols);
				
				System.out.print(myCell+ " ");
			}
			System.out.println("");
		}

	}

}

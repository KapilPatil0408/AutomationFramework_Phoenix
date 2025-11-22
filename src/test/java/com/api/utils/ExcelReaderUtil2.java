package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.record.model.UserCredentials;

public class ExcelReaderUtil2 {
	
	private ExcelReaderUtil2(){
		
	}

	public static Iterator<UserCredentials> loadTestData() {
		// APACHE POI OOXML
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet mySheet = myWorkBook.getSheet("LoginTestData");

		XSSFRow myRow;
		XSSFCell myCell;

		// Read Excel file -------> Store it in ArrayList<UserCredentials>

		// I want to know username and password indexes in our sheet

		XSSFRow rowHeader = mySheet.getRow(0);

		int usernameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : rowHeader) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				usernameIndex = cell.getColumnIndex();
			}

			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}

		System.out.println(usernameIndex + " " + passwordIndex);

		int lastRowIndex = mySheet.getLastRowNum();
		UserCredentials userCredentials;
		ArrayList<UserCredentials> userList= new ArrayList<>();
		
		for (int row = 1; row <= lastRowIndex; row++) {
			myRow = mySheet.getRow(row);
			userCredentials = new UserCredentials(myRow.getCell(usernameIndex).toString(),
					myRow.getCell(passwordIndex).toString());
			userList.add(userCredentials);
		}
		
		return userList.iterator();
	}

}

package MusalaSoft_Project.MusalaSoft_Assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Utilities {

	public String ReadDatafromPropFile(String property) throws FileNotFoundException, IOException {
		Properties prob = new Properties();
        FileInputStream file = new FileInputStream(".\\config.properties");
        prob.load(file);
        return prob.getProperty(property);
	}
	
	public String ReadDatafrimExcelFile(int i, int j) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(".\\data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet worksheet = workbook.getSheet("Sheet1");
        XSSFRow row1 = worksheet.getRow(i);
        XSSFCell cell = row1.getCell((short) j);
        String value = cell.getStringCellValue();
		return value;
	}
	
	public int NumberofRows() throws IOException {
		
		   FileInputStream fis = new FileInputStream(".\\data.xlsx");
	       XSSFWorkbook workbook = new XSSFWorkbook(fis);
	       XSSFSheet sheet = workbook.getSheet("Sheet1");
	       XSSFRow row = sheet.getRow(1);
	       int rowNum = sheet.getLastRowNum();
		   return rowNum;
	}
}

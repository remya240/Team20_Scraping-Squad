package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadExcel {
	public Map<String, String> readRecipeData() throws IOException {
        //creating an object named path where providing the path of excel sheet to get properties of the file
        String path = System.getProperty("C:\\Users\\s_thi\\eclipse-workspace\\Team20_Scraping-Squad\\src\\test\\resources\\Exceldata");
// creating the object excelFile of the file with earlier created object(path) as a parameter
        File excelFile = new File(path);
        // FileInputStream obtains input bytes from a file in a file system here excelFile and stores in the object fileInputStream
        FileInputStream fileInputStream = new FileInputStream(excelFile);
//creates object workbook to read or write the excelsheet
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        //create an object to get the data from sheet1 of workbook
        
        //XSSFSheet datasheet = workbook.getSheet("Sheet1");
        
        //XSSFSheet datasheet = workbook.getSheet("Recipe-Data (Expected output fo");
        XSSFSheet datasheet = workbook.getSheetAt(0);
        //Iterate the rows of sheet
        Iterator<Row> row = datasheet.rowIterator();
        //gets the details from row 0 & row 1 as K,V pair and store them as an object readRecipeData
        //Map<String, String> eliminators = new HashMap<String, String>();
        Map<String, String> recipeData = new HashMap<String, String>();
        
        while (row.hasNext()){
            Row currRow =row.next();
            if(currRow.getRowNum() > 0) {
            	//recipeData.put(currRow.getCell(0).getStringCellValue(), currRow.getCell(1).getStringCellValue());
            	recipeData.put(currRow.getCell(1).getStringCellValue(), currRow.getCell(1).getStringCellValue());

            }
        }
        workbook.close();
        return recipeData;

    }
}


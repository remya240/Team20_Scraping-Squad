package utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcel {
	public XSSFWorkbook workbook;
	public FileOutputStream fo;
	public FileInputStream fi;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
		String path = "./src/test/resources/outPutData.xlsx";
		File xlfile = new File(path);
		try {

			if (!xlfile.exists()) {
				workbook = new XSSFWorkbook();
				fo = new FileOutputStream(path);
				workbook.write(fo);
			}
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);

			if (workbook.getSheetIndex(sheetName) == -1)
				workbook.createSheet(sheetName);
			sheet = workbook.getSheet(sheetName);

			if (sheet.getRow(rownum) == null)
				sheet.createRow(rownum);
			row = sheet.getRow(rownum);

			cell = row.createCell(column);
			cell.setCellValue(data);
			fo = new FileOutputStream(path);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void AllergySetCellData(String sheetName, int rownum, int column, String data) throws IOException {
	    String path = "./src/test/resources/AllergyData.xlsx";
	    File xlfile = new File(path);
	    try {
	        if (!xlfile.exists()) {
	            workbook = new XSSFWorkbook();
	            fo = new FileOutputStream(path);
	            workbook.write(fo);
	        }
	        fi = new FileInputStream(path);
	        workbook = new XSSFWorkbook(fi);

	        sheet = workbook.getSheet(sheetName);

	        if (sheet == null) {
	            // If the sheet does not exist, create it and write headers
	            sheet = workbook.createSheet(sheetName);
	            row = sheet.createRow(0);
	            // Add headers here
	            row.createCell(0).setCellValue("Recipe ID");
	            row.createCell(1).setCellValue("Recipe Name");
	            // Add other headers similarly

	            // Increment rownum as we have added headers
	            rownum++;
	        }

	        if (sheet.getRow(rownum) == null)
	            sheet.createRow(rownum);

	        row = sheet.getRow(rownum);

	        cell = row.createCell(column);
	        cell.setCellValue(data);

	        fo = new FileOutputStream(path);
	        workbook.write(fo);
	    } catch (Exception e) {
	        System.out.print(e.getMessage());
	    } finally {
	        workbook.close();
	        fi.close();
	        fo.close();
	    }
	}
public void saveExcel(String path) throws IOException {
    try (FileOutputStream fo = new FileOutputStream(path)) {
        workbook.write(fo);
    }
}
}
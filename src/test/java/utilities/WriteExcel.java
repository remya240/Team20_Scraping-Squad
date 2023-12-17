package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class WriteExcel {
	public HSSFWorkbook workbook;
    public FileOutputStream fo;
    public FileInputStream fi;
    public HSSFSheet sheet;
    public HSSFRow row;
    public HSSFCell cell;
	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
        String path ="./src/test/resources/outPutData.xls";
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new HSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }
        fi = new FileInputStream(path);
        workbook = new HSSFWorkbook(fi);

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
    }
    public void setCellAllergyData(String sheetName, int rownum, int column, String data) throws IOException {
        String path ="./src/test/resources/AllergyData.xls";
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new HSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }
        fi = new FileInputStream(path);
        workbook = new HSSFWorkbook(fi);

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
    }
}



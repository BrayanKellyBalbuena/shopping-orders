package edu.unapec.shoppingorders.helpers;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ExcelFileHelper {

    private  FileInputStream fileInputStream;
    private HSSFWorkbook workbook;
    private  String filePath;

    public ExcelFileHelper(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        fileInputStream = new FileInputStream(filePath);
    }

    public  HSSFWorkbook getWorkBook() throws IOException {
        if( workbook != null) return workbook;

        workbook = new HSSFWorkbook(fileInputStream);

        return workbook;
    }

    public void closeWorkBook() throws IOException {
        workbook.close();
    }
    public String getFilePath() {
        return filePath;
    }

    public List<Sheet> getSheets() throws IOException {
        List<Sheet> sheets = new ArrayList<>();
        Iterator<Sheet> sheetIterator = getWorkBook().sheetIterator();

        while (sheetIterator.hasNext()) {
            sheets.add(sheetIterator.next());
        }

        return sheets;
    }

    public HSSFSheet getSheet(String name) throws IOException {
        return getWorkBook().getSheet(name);

    }

    public HSSFSheet getSheetAt(int index) throws IOException {
        return getWorkBook().getSheetAt(index);
    }

    public void Save() throws IOException {
        FileOutputStream  fileOutputStream = new FileOutputStream(filePath);
        getWorkBook().write(fileOutputStream);

        fileOutputStream.close();
        workbook.close();
    }
}

package com.realdolmen.chiro.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    // For testing purposes
    public File readExcel(File file, Boolean xlsx) throws IOException{

        FileInputStream fis = new FileInputStream(file);

        Workbook myWorkBook;
        // Finds the workbook instance for XLSX file
        if(xlsx){
            myWorkBook = new XSSFWorkbook (fis);
        }
        else{
            myWorkBook = new HSSFWorkbook (fis);
        }

        // Return first sheet from the XLSX workbook
        Sheet mySheet;
        mySheet = myWorkBook.getSheetAt(0);


        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next(); // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    default :
                }
            }
            System.out.println("");
        }
        myWorkBook.close();
        return null;
    }



    public File writeExcel(List<RegistrationParticipant> participants, Boolean xlsx) throws IOException, EncryptedDocumentException, InvalidFormatException {

        //Create Blank workbook
        Workbook workBook;
        if(xlsx){
            workBook = new XSSFWorkbook();
        }
        else{
            workBook = new HSSFWorkbook();
        }

        Sheet mySheet = workBook.createSheet();

        Map<String, Object[]> data = new HashMap<String, Object[]>();

        data = putRegistrationParticipantsIntoMap(participants);

        // Set to Iterate and add rows into XLS file
        Set<String> newRows = data.keySet();

        // get the last row number to append new data
        //		int rownum = mySheet.getLastRowNum();

        // Start from top row
        int rownum = 0;

        for (String key : newRows) {
            // Creating a new Row in existing XLSX sheet
            Row row = mySheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj); }
                else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj); }
                else if (obj instanceof Date) {
                    cell.setCellValue((Date) obj); }
                else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
                else if (obj instanceof Long) {
                    cell.setCellValue((Long) obj);
                }
            }
        }

        File file;
        if(xlsx){
            file = new File("test.xlsx");
        }
        else{
            file = new File("test.xls");
        }
        file.createNewFile();
        //Create file system using specific name
        FileOutputStream out = new FileOutputStream(file);

        // CLEARING ROWS NOT NECESSARY ANYMORE SINCE FILE IS RECREATED EVERYTIME
        // First clear all rows:
//		removeAllRows(workBook, mySheet, out); 

//		out = new FileOutputStream(file);
        //write operation workbook using file out object
        workBook.write(out);
        out.close();

        return file;
    }

    public Map<String, Object[]> putRegistrationParticipantsIntoMap(List<RegistrationParticipant> participants) {
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        RegistrationParticipant r = null;
        for(int i = 0; i < participants.size(); i++){
            r = participants.get(i);
            data.put(""+i, new Object[] {
                    r.getId(),
                    r.getFirstName(),
                    r.getLastName()
            });
        }
        return data;
    }

    private void removeAllRows(XSSFWorkbook workBook, Sheet mySheet, FileOutputStream fos) throws IOException {
        if(mySheet.getPhysicalNumberOfRows() > 0){ // check if there are rows filled in
            int lastRowNumber = mySheet.getLastRowNum();
            Row row = null;
            for(int i = lastRowNumber; i >= 0; i--){
                row = mySheet.getRow(i);
                mySheet.removeRow(row);
            }
        }
        workBook.write(fos);
    }
}

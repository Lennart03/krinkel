package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        Object [] o = new Object[] {
                "Id",
                "Ad-nummer",
                "Stamnummer",
                "Geslacht",
                "Voornaam",
                "Achternaam",
                "Geboortedatum",
                "Straat",
                "Huisnummer",
                "Postcode",
                "Gemeente",
                "Telefoonnummer",
                "E-mailadres",
                "Evenementsfunctie",
                "Data voorwacht",
                "Data nawacht",
                "Buddy",
                "Talen",
                "Eetgewoonte",
                "Bemerkingen eten",
                "Medische info",
                "Bemerkingen",
                "Kampgrond",
                "Gekozen functie",
                "Andere functie",
                "Sociale promotie",
                "Geregistreerd door",
                "E-mailadres inschrijver",
                "Status",
                "Syncstatus",
        };
        Row headersRow = mySheet.createRow(0);
        writeRowContent(headersRow, o);

        // Start from top row
        int rownum = 1;

        for (String key : newRows) {
            // Creating a new Row in existing XLSX sheet
            Row row = mySheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            writeRowContent(row, objArr);
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

    private void writeRowContent(Row row, Object[] objArr) {
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
            else if (obj instanceof Integer) {
                cell.setCellValue((Integer) obj);
            }
            else if (obj instanceof Long) {
                cell.setCellValue((Long) obj);
            }
        }
    }

public Map<String, Object[]> putRegistrationParticipantsIntoMap(List<RegistrationParticipant> participants) {
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        RegistrationParticipant r = null;
        for(int i = 0; i < participants.size(); i++){
            // Get current participant
            r = participants.get(i);

            // In case of volunteers:
            String campGround = "";
            String preset = "";
            String other = "";
            //Set precamp and postcamp data for volunteers
            String precampDates = "";
            String postcampDates = "";
            if(r instanceof RegistrationVolunteer) {
                RegistrationVolunteer volunteer = (RegistrationVolunteer) r;
                campGround = volunteer.getCampGround().getDescription();
                preset = volunteer.getFunction().getPreset().getDescription();
                other = volunteer.getFunction().getOther();
                precampDates = transformPreCamptListToString(volunteer.getPreCampList());
                postcampDates = transformPostCampListToString(volunteer.getPostCampList());
            }

            // Set languages if participant is a buddy
            String languages = "";
            if(r.isBuddy()){
                languages = transformLanguageListToString(r.getLanguage());
            }




            // Translate booleans to yes or no
            String buddy;
            if(r.isBuddy()){
                buddy = "Ja";
            }
            else{
                buddy = "Nee";
            }
            String socialPromotion;
            if(r.isSocialPromotion()) {
                socialPromotion = "Ja";
            } else {
                socialPromotion = "Nee";
            }

            // For formatting dates
            String birthDate = getDateFormatted(r.getBirthdate());

            // Checking for null
            String syncStatus = "";
            if(r.getSyncStatus()!=null){
                syncStatus = r.getSyncStatus().getDescription();
            }

            data.put(""+i, new Object[] {
                    r.getId(),
                    r.getAdNumber(),
                    r.getStamnumber(),
                    r.getGender().getDescription(),
                    r.getFirstName(),
                    r.getLastName(),
                    birthDate,
                    r.getAddress().getStreet(),
                    r.getAddress().getHouseNumber(),
                    r.getAddress().getPostalCode(),
                    r.getAddress().getCity(),
                    r.getPhoneNumber(),
                    r.getEmail(),
                    r.getEventRole().getDescription(),
                    precampDates,
                    postcampDates,
                    buddy,
                    languages,
                    r.getEatinghabbit().getDescription(),
                    r.getRemarksFood(),
                    r.getMedicalRemarks(),
                    r.getRemarks(),
                    campGround,
                    preset,
                    other,
                    socialPromotion,
                    r.getRegisteredBy(),
                    r.getEmailSubscriber(),
                    r.getStatus().getDescription(), //format?
                    syncStatus //format?
            });
        }
        return data;
    }

    private String transformPreCamptListToString(List<PreCamp> preCampList) {
        String dates = "";
        for(int i = 0; i < preCampList.size()-1; i++){
            dates += getDateFormatted(preCampList.get(i).getDate()) + ", ";
        }
        if(preCampList.size() > 0){
            dates += getDateFormatted(preCampList.get(preCampList.size()-1).getDate());
        }
        return dates;
    }

    private String transformPostCampListToString(List<PostCamp> postCampList) {
        String dates = "";
        for(int i = 0; i < postCampList.size()-1; i++){
            dates += getDateFormatted(postCampList.get(i).getDate()) + ", ";
        }
        if(postCampList.size() > 0){
            dates += getDateFormatted(postCampList.get(postCampList.size()-1).getDate());
        }
        return dates;
    }

    private String getDateFormatted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }


    private String transformLanguageListToString(List<Language> languagesList) {
        String languages = "";
        for(int i = 0; i < languagesList.size()-1; i++){
            languages += languagesList.get(i).getDescription() + ", ";
        }
        if(languagesList.size() > 0){
            languages += languagesList.get(languagesList.size()-1).getDescription();
        }
        return languages;
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

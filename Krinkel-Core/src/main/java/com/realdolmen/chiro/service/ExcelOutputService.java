package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;



/**
 * Created by JCPBB69 on 9/12/2016.
 */
@Service("excelOutputService")
public class ExcelOutputService{

    @Autowired
    RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    ExcelService excelService;

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ExcelOutputService.class));


    public WritableWorkbook createExcelOutputXls(HttpServletResponse response, File file) {


        String fileName = "Excel_Output.xls";
//        String fileName = file.getName();
        WritableWorkbook writableWorkbook = null;
        try {
            response.setContentType("application/vnd.ms-excel");

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            writableWorkbook = Workbook.createWorkbook(response.getOutputStream());


            WritableSheet excelOutputsheet = writableWorkbook.createSheet("Excel Output", 0);
            addExcelOutputHeader(excelOutputsheet);
            writeExcelOutputData(excelOutputsheet);

            writableWorkbook.write();
            writableWorkbook.close();

        } catch (Exception e) {
            LOGGER.info("Error occured while creating Excel file");
            e.printStackTrace();
        }

        return writableWorkbook;
    }

    public WritableWorkbook createExcelOutputXlsTestFile(HttpServletResponse response, File file) {


//        String fileName = "Excel_Output.xls";
        String fileName = "Excel_Output.xls";
        WritableWorkbook writableWorkbook = null;
            try {
                response.setContentType("application/vnd.ms-excel");

                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                writableWorkbook = Workbook.createWorkbook(response.getOutputStream());

                WritableSheet excelOutputsheet = writableWorkbook.createSheet("Excel Output", 0);
                addExcelOutputHeaderTest(excelOutputsheet);
                writeExcelOutputDataTest(excelOutputsheet);

                writableWorkbook.write();
                writableWorkbook.close();

        } catch (Exception e) {
            LOGGER.info("Error occured while creating Excel file");
        }

        return writableWorkbook;
    }

    /**
     * For creating an excel sheet with test data. Here a header row is added.
     * @param sheet
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected void addExcelOutputHeader(WritableSheet sheet) throws RowsExceededException, WriteException {
//        sheet.addCell(new Label(i, 0, "Column " + (i+1)));

        Object [] objArr = new Object[] {
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
        int i = 0;
        if(objArr == null){
            System.err.println("HET IS NULL, hier is de erorroaegjaegaegegjogizegujehguzehgzei");
        }

        for (Object obj : objArr) {
            // Nu alles naar string
            sheet.addCell(new Label(i, 0, obj.toString()));
            System.err.println(obj.toString());

            //TODO: alles type checken en dan wegschrijven met juiste type
//            if(obj instanceof Integer){
//                sheet.addCell(new Number(i, 0, (Integer) obj));
//            }

            i++;
        }

    }

    /**
     * For creating an excel sheet with test data. Here dummy data is added
     * @param sheet
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected void writeExcelOutputData(WritableSheet sheet) throws RowsExceededException, WriteException{

        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();

        Map<String, Object[]> stringMap = excelService.putRegistrationParticipantsIntoMap(all);

        Set<String> newRows = stringMap.keySet();

//        for(int rowNo = 1; rowNo<=10; rowNo++){
        int rowNr = 1;
        for (String key : newRows) {
            int columnNr = 0;
            Object [] objArr = stringMap.get(key);
            for (Object obj : objArr) {
                System.err.println("Tried to handle: " + obj.toString());
                // TODO: Nu nog alles naar string
                sheet.addCell(new Label(columnNr, rowNr, obj.toString()));

                //TODO: alles type checken en dan wegschrijven met juiste type
//            if(obj instanceof Integer){
//                sheet.addCell(new Number(i, 0, (Integer) obj));
//            }
                columnNr++;
            }
            rowNr++;
        }
    }

    /**
     * For creating an excel sheet with test data. Here a header row is added.
     * @param sheet
     * @throws RowsExceededException
     * @throws WriteException
     */
    private void addExcelOutputHeaderTest(WritableSheet sheet) throws RowsExceededException, WriteException {
        // create header row
        for(int i = 0; i <= 10; i++){
            sheet.addCell(new Label(i, 0, "Column " + (i+1)));
        }
    }

    /**
     * For creating an excel sheet with test data. Here dummy data is added
     * @param sheet
     * @throws RowsExceededException
     * @throws WriteException
     */
    private void writeExcelOutputDataTest(WritableSheet sheet) throws RowsExceededException, WriteException{

        for(int rowNo = 1; rowNo<=10; rowNo++){
            for(int colNo = 1; colNo <= 10; colNo++)
            sheet.addCell(new Label(colNo, rowNo, "Col Data "+ (rowNo+colNo)));
        }

    }

    public XSSFWorkbook createExcelOutputXlsx(HttpServletResponse response, File file){
        XSSFWorkbook workbook = null;
        try {
            String fileName = file.getName();

            System.err.println("FILE NAME IN createExcelOutputXlsx is : " + fileName);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            ClassLoader loader = getClass().getClassLoader();
//            File file = new File(loader.getResource("Excel_Output.xlsx").getFile());  //file should be at classpath
//            File file = new File(loader.getResource(file2.getName()).getFile());  //file should be at classpath
            FileInputStream is = new FileInputStream(file);



            // Get the workbook instance for XLSX file
            workbook = new XSSFWorkbook(is);
            XSSFSheet rankerSheet1 = workbook.getSheetAt(0);

            writeExcelOutputData(rankerSheet1, workbook);

            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private void writeExcelOutputData(XSSFSheet rankerSheet, XSSFWorkbook worksheet){

        XSSFRow row1 = rankerSheet.createRow(1);
        row1.createCell(0).setCellValue(1);
        row1.createCell(1).setCellValue(1234);
        row1.createCell(2).setCellValue("Test Excel");
        row1.createCell(3).setCellValue("Address");

        XSSFCell cell4 = row1.createCell(4);
        cell4.setCellValue(10.00);
        XSSFCellStyle style = worksheet.createCellStyle();
        style.setDataFormat((short)8);      // this will format cell with $ sign Ex: $10.00
        cell4.setCellStyle(style);


        //Creating Data format %
        XSSFCellStyle percentStyle = worksheet.createCellStyle();
        percentStyle.setDataFormat(worksheet.createDataFormat().getFormat("0.0%"));

        XSSFCell cell5 = row1.createCell(5);
        cell5.setCellStyle(percentStyle);
        cell5.setCellValue(20.00);

    }
}
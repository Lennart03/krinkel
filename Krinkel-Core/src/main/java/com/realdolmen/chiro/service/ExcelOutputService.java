package com.realdolmen.chiro.service;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Label;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by JCPBB69 on 9/12/2016.
 */
@Service("excelOutputService")
public class ExcelOutputService{
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ExcelOutputService.class));

    public WritableWorkbook createExcelOutputExcel(HttpServletResponse response) {
        String fileName = "Excel_Output.xls";
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
        }

        return writableWorkbook;
    }


    private void addExcelOutputHeader(WritableSheet sheet) throws RowsExceededException, WriteException {
        // create header row
        sheet.addCell(new Label(0, 0, "Column 1"));
        sheet.addCell(new Label(1, 0, " Column 2"));
        sheet.addCell(new Label(2, 0, " Column 3"));
        sheet.addCell(new Label(3, 0, " Column 4"));
        sheet.addCell(new Label(4, 0, " Column 5"));
        sheet.addCell(new Label(5, 0, " Column 6"));
        sheet.addCell(new Label(6, 0, " Column 7"));
        sheet.addCell(new Label(7, 0, " Column 8"));
        sheet.addCell(new Label(8, 0, " Column 9"));
        sheet.addCell(new Label(9, 0, " Column 10"));
        sheet.addCell(new Label(10, 0, " Column 11"));
    }

    private void writeExcelOutputData(WritableSheet sheet) throws RowsExceededException, WriteException{

        for(int rowNo = 1; rowNo<=10; rowNo++){
            sheet.addCell(new Label(0, rowNo, "Col Data "+ (rowNo+0)));
            sheet.addCell(new Label(1, rowNo, "Col Data "+ (rowNo+1)));
            sheet.addCell(new Label(2, rowNo, "Col Data "+ (rowNo+2)));
            sheet.addCell(new Label(3, rowNo, "Col Data "+ (rowNo+3)));
            sheet.addCell(new Label(4, rowNo, "Col Data "+ (rowNo+4)));
            sheet.addCell(new Label(5, rowNo, "Col Data "+ (rowNo+5)));
            sheet.addCell(new Label(6, rowNo, "Col Data "+ (rowNo+6)));
            sheet.addCell(new Label(7, rowNo, "Col Data "+ (rowNo+7)));
            sheet.addCell(new Label(8, rowNo, "Col Data "+ (rowNo+8)));
            sheet.addCell(new Label(9, rowNo, "Col Data "+ (rowNo+9)));
            sheet.addCell(new Label(10, rowNo, "Col Data "+ (rowNo+10)));

        }

    }
}
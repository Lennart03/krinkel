package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ExcelOutputService;
import com.realdolmen.chiro.service.ExcelService;
import com.realdolmen.chiro.service.ExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@RestController
public class ExportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExportService exportService;


//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    @ResponseBody
//    public Object downloadExcel(HttpServletResponse response) {
//        String xlsxFileName = exportService.writeRegistrationAllParticipantsToXlsx();
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition",
//                "attachment; filename=" + "" + ".xls");
//        try {
//            generateExcel(response.getOutputStream());
//        } catch (IOException e) {
//            System.out.println("ERROR: " + e);
//        }
//        return null;
//    }

    @RequestMapping(value = "api/exportCompleteEntryList", method = RequestMethod.GET, produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> exportCompleteEntryList() throws IOException{
        System.err.println("INSIDE getFile for completing exporting registration list");
        File file = exportService.writeRegistrationParticipantsToExcel(true);
        String name = file.getName();
        System.err.println("filename: " + file.getName());
        System.err.println("absolute path name: " + file.getAbsolutePath());
        System.err.println("canonical path name: " + file.getCanonicalPath());
        System.err.println(" path name: " + file.getPath());


        ClassPathResource excelFile = new ClassPathResource(file.getAbsolutePath());

        return ResponseEntity
                .ok()
                .contentLength(excelFile.contentLength())
                .contentType(
                        MediaType.parseMediaType("application/octet-stream"))

                .body(new InputStreamResource(excelFile.getInputStream()));

    }

    @Autowired
    ExcelOutputService excelOutputService;

    // TRYING WITH XLS
    @RequestMapping(value="/downloadExcelTest", method=RequestMethod.GET)
    public ModelAndView downloadExcelOutputExcelTest(HttpServletResponse response){
        System.err.println("INSIDE downloadExcelOutputExcelTest for exporting registration list");
        excelOutputService.createExcelOutputXls(response, exportService.writeRegistrationParticipantsToExcel(false));
        return null;
    }



    // TRYING WITH XLSX
    @RequestMapping(value="/downloadExcel", method=RequestMethod.GET)
    public ModelAndView downloadExcelOutputExcel(HttpServletResponse response){
        System.err.println("INSIDE downloadExcelOutputExl for exporting registration list");
        excelOutputService.createExcelOutputXlsx(response, exportService.writeRegistrationParticipantsToExcel(true));
        return null;
    }


}

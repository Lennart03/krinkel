package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ExcelService;
import com.realdolmen.chiro.service.ExportService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.loops.ProcessPath;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
//        String xlsxFileName = exportService.writeRegistrationParticipantsToXlsx();
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition",
//                "attachment; filename=" + xlsxFileName + ".xls");
//        try {
//            generateExcel(response.getOutputStream());
//        } catch (IOException e) {
//            System.out.println("ERROR: " + e);
//        }
//        return null;
//    }

    @RequestMapping(value = "api/exportCompleteEntryList", method = RequestMethod.GET)
    public void export(HttpServletResponse response) {
        System.err.println("INSIDE getFile for completing exporting registration list");

        try {
            // get your file as InputStream
//            InputStream is = new InputStream();
            String fileNameXlsx = exportService.writeRegistrationParticipantsToXlsx();
            if(fileNameXlsx.equals("failed")){
                logger.info("Writing xlsx file failed!");
                return;
            }

            File file = new File(fileNameXlsx);

            FileInputStream fis = new FileInputStream(file);
            // copy it to response's OutputStream
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
//            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }
}

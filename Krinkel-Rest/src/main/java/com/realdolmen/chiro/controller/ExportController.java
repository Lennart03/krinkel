package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ExcelOutputService;
import com.realdolmen.chiro.service.ExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@RestController
public class ExportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExportService exportService;

    @Autowired
    private ExcelOutputService excelOutputService;

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstAlles", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListComplete(HttpServletResponse response){
        exportService.createExcelOutputXlsRegistrationAll(response, "registratiesLijstAlles.xls");
        return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstMedewerkers", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListVolunteers(HttpServletResponse response){
        exportService.createExcelOutputXlsRegistrationVolunteers(response, "registratiesLijstAlles.xls");
        return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstDeelnemers", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListParticipants(HttpServletResponse response){
        exportService.createExcelOutputXlsRegistrationParticipants(response, "registratiesLijstAlles.xls");
        return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/downloadExcelTest", method=RequestMethod.GET)
    public ModelAndView downloadExcelOutputExcelTest(HttpServletResponse response){
        System.err.println("INSIDE downloadExcelOutputExcelTest for exporting registration list");
        exportService.createExcelOutputXlsRegistrationAll(response, "registratiesLijstAlles.xls");
        return null;
    }

    // TRYING WITH XLSX -- gives corrupted xlsx files
    @RequestMapping(value="/downloadExcel", method=RequestMethod.GET)
    public ModelAndView downloadExcelOutputExcel(HttpServletResponse response){
        System.err.println("INSIDE downloadExcelOutputExl for exporting registration list");
        excelOutputService.createExcelOutputXlsx(response, exportService.writeRegistrationParticipantsToExcel(true));
        return null;
    }

    //    @RequestMapping(value = "api/exportCompleteEntryList", method = RequestMethod.GET, produces = "application/octet-stream")
//    public ResponseEntity<InputStreamResource> exportCompleteEntryList() throws IOException{
//        System.err.println("INSIDE getFile for completing exporting registration list");
//        File file = exportService.writeRegistrationParticipantsToExcel(true);
//        String name = file.getName();
//        System.err.println("filename: " + file.getName());
//        System.err.println("absolute path name: " + file.getAbsolutePath());
//        System.err.println("canonical path name: " + file.getCanonicalPath());
//        System.err.println(" path name: " + file.getPath());
//
//        ClassPathResource excelFile = new ClassPathResource(file.getAbsolutePath());
//
//        return ResponseEntity
//                .ok()
//                .contentLength(excelFile.contentLength())
//                .contentType(
//                        MediaType.parseMediaType("application/octet-stream"))
//
//                .body(new InputStreamResource(excelFile.getInputStream()));
//
//    }

}

package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ExcelOutputService;
import com.realdolmen.chiro.service.ExportService;
import com.realdolmen.chiro.service.security.UserServiceSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserServiceSecurity userServiceSecurity;


    public String getBaseUrl(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
        System.err.println("##########################################");
        System.err.println("Retrieved base url is : \n" + base);

        return base;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * redirects to home page of application if user is not admin
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstAlles", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListComplete(HttpServletResponse response, HttpServletRequest request){
        if(userServiceSecurity.hasAdminRights()){
            exportService.createExcelOutputXlsRegistrationAll(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
        return null;
    }

    @RequestMapping(value="/exportRegistratieLijstAllesCSV", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListCompleteCSV(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            excelOutputService.exportCSV(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
            return null;
    }

    @RequestMapping(value="/exportRegistratieLijstAllesXLSX", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListCompleteXLSX(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            excelOutputService.exportXLSX(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
            return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstMedewerkers", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListVolunteers(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            exportService.createExcelOutputXlsRegistrationVolunteers(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
            return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstDeelnemers", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListParticipants(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            exportService.createExcelOutputXlsRegistrationParticipants(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
        return null;
    }

    /**
     * Downloads the excel sheet with all registrationParticipants.
     * This method is called by redirecting through a href with BASE_URL + /downloadExcelTest
     * @param response
     * @return
     */
    @RequestMapping(value="/downloadExcelTest", method=RequestMethod.GET)
    public ModelAndView downloadExcelOutputExcelTest(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            System.err.println("INSIDE downloadExcelOutputExcelTest for exporting registration list");
            exportService.createExcelOutputXlsRegistrationAll(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
        return null;
    }

    // TRYING WITH XLSX -- gives corrupted xlsx files
//    @RequestMapping(value="/downloadExcel", method=RequestMethod.GET)
//    public ModelAndView downloadExcelOutputExcel(HttpServletResponse response){
//        System.err.println("INSIDE downloadExcelOutputExl for exporting registration list");
//        excelOutputService.createExcelOutputXlsx(response, exportService.writeRegistrationParticipantsToExcel(true));
//        return null;
//    }

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

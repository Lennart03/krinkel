package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.payments.TicketType;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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


    /**
     * For redirecting to the base URL
     * @param req
     * @return
     */
    public String getBaseUrl(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
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

    @RequestMapping(value = "/exportTrainTickets", method = RequestMethod.GET)
    public ModelAndView exportTrainTickets(HttpServletResponse response, HttpServletRequest request) {
        if(userServiceSecurity.hasAdminRights()) {
            exportService.createExcelOutputXlsTickets(response, TicketType.TREIN);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
        return null;
    }

    @RequestMapping(value = "/exportCoupons", method = RequestMethod.GET)
    public ModelAndView exportCoupons(HttpServletResponse response, HttpServletRequest request) {
        if(userServiceSecurity.hasAdminRights()) {
            exportService.createExcelOutputXlsTickets(response, TicketType.BON);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
        return null;
    }

    /**
     * Downloads a zip with a backup of the complete DB in CSV's
     * @param response
     * @return
     */
    @RequestMapping(value="exportBackupZip", method=RequestMethod.GET, produces = "application/zip")
    public byte[] exportRegistrationParticipantListCompleteCSV2(HttpServletResponse response){
        if (userServiceSecurity.hasAdminRights()) {

            response.setContentType("application/zip");
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=backup.zip");
            exportService.createCSVBackups();
            try {
                return Files.readAllBytes((new File("backup.zip")).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * For downloading XLSX, it works but currently xls is used
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value="/exportRegistratieLijstAllesXLSX", method=RequestMethod.GET)
    public ModelAndView exportRegistrationParticipantListCompleteXLSX(HttpServletResponse response, HttpServletRequest request){
        if (userServiceSecurity.hasAdminRights()) {
            excelOutputService.exportXLSX(response);
        } else {
            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
        }
            return null;
    }



    //    @RequestMapping(value="/exportRegistratieLijstAllesCSVTest", method=RequestMethod.GET)
//    public ModelAndView exportRegistrationParticipantListCompleteCSVTest(HttpServletResponse response){
//        excelOutputService.exportCSV(response, "test.csv");
//        return null;
//    }
//
//    @RequestMapping(value="/exportRegistratieLijstAllesCSV", method=RequestMethod.GET)
//    public ModelAndView exportRegistrationParticipantListCompleteCSV(HttpServletResponse response, HttpServletRequest request){
//        if (userServiceSecurity.hasAdminRights()) {
//            excelOutputService.exportCSV(response, "test.csv");
//        } else {
//            return new ModelAndView("redirect:" + getBaseUrl(request) + "/index.html");
//        }
//        return null;
//    }
}

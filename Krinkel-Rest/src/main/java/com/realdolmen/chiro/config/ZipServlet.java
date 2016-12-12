package com.realdolmen.chiro.config;

import com.realdolmen.chiro.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;


/**
 * Created by JCPBB69 on 12/12/2016.
 */
public class ZipServlet extends HttpServlet {

    @Autowired
    private ExportService exportService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.err.println("doGEt in ZipServlet");
//        super.doGet(req, resp);
        resp.setContentType("application/zip");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.addHeader("Content-Disposition", "attachment; filename=backup.zip");
        exportService.createCSVBackups(resp);
        try {
            byte[] bytes = Files.readAllBytes((new File("backup.zip")).toPath());
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

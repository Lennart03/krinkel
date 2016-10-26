package com.realdolmen.chiro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerInfoService {

    @Value("${server.hostname}")
    private String serverHostname;

    @Value("${main-server.port}")
    private String serverPort;

    public String getServerHostname() {
        return serverHostname;
    }

    public String getServerPort() {
        return serverPort;
    }
}

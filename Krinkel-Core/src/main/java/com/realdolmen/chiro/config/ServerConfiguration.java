package com.realdolmen.chiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfiguration {

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

package com.realdolmen.chiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CasConfiguration {

    @Value("${cas.base_url}")
    private String baseCasUrl;

    @Value("${cas.service_url}")
    private String serviceUrl;

    public String getBaseCasUrl() {
        return baseCasUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @return Redirect url for login with the CAS.
     */
    public String getCasRedirectUrl(){
        return (this.getBaseCasUrl() + "login?service=" + this.getServiceUrl());
    }
}

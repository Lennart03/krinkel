package com.realdolmen.chiro.mspservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultiSafePayConfiguration {

    @Value("${multisafe.url}")
    private String URL;

    @Value("${multisafe.api_key}")
    private String apiKey;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

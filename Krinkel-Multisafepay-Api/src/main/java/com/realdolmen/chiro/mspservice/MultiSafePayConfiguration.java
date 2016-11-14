package com.realdolmen.chiro.mspservice;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultiSafePayConfiguration {

    @Value("${multisafe.url}")
    private String URL;

    @Value("${multisafe.api_key}")
    private String apiKey;

    @Value("${multisafe.notification_url}")
    private String notificationUrl;

    @Value("${multisafe.redirect_url}")
    private String redirectUrl;

    @Value("${multisafe.cancel_url}")
    private String cancelUrl;

    public String getURL() {
        return URL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public JSONObject getPaymentOptions(){
        JSONObject paymentOptions = new JSONObject();
        paymentOptions.put("notification_url", this.getNotificationUrl());
        paymentOptions.put("redirect_url", this.getRedirectUrl());
        paymentOptions.put("cancel_url", this.getCancelUrl());
        return paymentOptions;
    }
}

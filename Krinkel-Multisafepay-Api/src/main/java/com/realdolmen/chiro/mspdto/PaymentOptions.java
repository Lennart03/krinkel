package com.realdolmen.chiro.mspdto;

import com.sun.org.apache.xerces.internal.util.URI;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentOptions {
    private String notification_url;
    private String redirect_url;
    private String cancel_url;

    public PaymentOptions() {
        notification_url = "http://krinkel.be/notify";
        redirect_url = "http://krinkel.be/success";
        cancel_url = "http://krinkel.be/failure";
    }

    public String getNotification_url() {
        return notification_url;
    }

    public void setNotification_url(String notification_url) {
        this.notification_url = notification_url;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }


    @Override
    public String toString() {
        return "PaymentOptions{" +
                "notification_url='" + notification_url + '\'' +
                ", redirect_url='" + redirect_url + '\'' +
                ", cancel_url='" + cancel_url + '\'' +
                '}';
    }
}

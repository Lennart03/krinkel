package com.realdolmen.chiro.domain.units;

/**
 * Created by LVDBB73 on 15/12/2016.
 */
public class HttpChiroContact extends ChiroContact {

    private String httpStatus;

    public HttpChiroContact(ChiroContact chiroContact, String httpStatus) {
        super(chiroContact);
        this.httpStatus = httpStatus;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}

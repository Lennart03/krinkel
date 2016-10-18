package com.realdolmen.chiro.mspdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class represents the json that multisafepay returns when we ask about
 * the status for a certain order.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDto {
    private boolean success;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

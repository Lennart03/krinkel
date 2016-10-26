package com.realdolmen.chiro.mspdto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * This class represents the JSON object that will be sent to multisafepay to
 * create an order. The response will contain the link we can redirect the
 * user to.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    private boolean success;
    private Data data;
    private Integer error_code;
    private String error_info;

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

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                ", success=" + success +
                ", data=" + data +
                ", error_code=" + error_code +
                ", error_info='" + error_info + '\'' +
                '}';
    }
}

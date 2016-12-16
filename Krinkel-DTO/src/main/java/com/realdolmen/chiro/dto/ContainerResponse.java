package com.realdolmen.chiro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
import java.util.List;

@JsonComponent
public class ContainerResponse<T> {

    @JsonProperty("is_error")
    private boolean error;

    private int version;

    private int count;

    private List<T> values = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public static ContainerResponse createContainerResponse(@JsonProperty("is_error") String error, @JsonProperty("version") String version, @JsonProperty("count") String count, @JsonProperty("values") String values) {
        ContainerResponse r = new ContainerResponse();
        r.error = Boolean.parseBoolean(error);
        r.version = Integer.parseInt(version);
        r.count = Integer.parseInt(count);
//        this.values = values;
        return r;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}

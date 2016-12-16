package com.realdolmen.chiro.containers;

import com.realdolmen.chiro.dataholders.ChiroContactHolder;

import java.util.ArrayList;
import java.util.List;

public class ChiroContactContainer {

    private String is_error;
    private String version;
    private String count;
    private String id;
    private List<ChiroContactHolder> values = new ArrayList<>();

    public String getIs_error() {
        return is_error;
    }

    public void setIs_error(String is_error) {
        this.is_error = is_error;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ChiroContactHolder> getValues() {
        return values;
    }

    public void setValues(List<ChiroContactHolder> values) {
        this.values = values;
    }
}

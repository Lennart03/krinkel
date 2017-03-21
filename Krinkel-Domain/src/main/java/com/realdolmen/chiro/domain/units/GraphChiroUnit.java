package com.realdolmen.chiro.domain.units;

import java.util.List;

/**
 * Created by WVDAZ49 on 12/10/2016.
 */
public class GraphChiroUnit {
    private String name;
    private Integer size;
    private List<GraphChiroUnit> children;
    private String stamNumber;

    public GraphChiroUnit() {
    }

    public GraphChiroUnit(String name, Integer size, List<GraphChiroUnit> children,String stamNumber) {
        this.name = name;
        this.size = size;
        this.children = children;
        this.stamNumber = stamNumber;
    }

    public String getStamNumber(){
        return this.stamNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<GraphChiroUnit> getChildren() {
        return children;
    }

    public void setChildren(List<GraphChiroUnit> children) {
        this.children = children;
    }
}

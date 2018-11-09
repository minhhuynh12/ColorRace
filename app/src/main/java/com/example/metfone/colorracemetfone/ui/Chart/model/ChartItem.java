package com.example.metfone.colorracemetfone.ui.Chart.model;

import java.util.List;

public class ChartItem {
    private String id;
    private String code;
    private String name;
    private float total;
    private float used;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getUsed() {
        return used;
    }

    public void setUsed(float used) {
        this.used = used;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChartItem(String id, String code, String name, float total, float used, String type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.total = total;
        this.used = used;
        this.type = type;
    }
}

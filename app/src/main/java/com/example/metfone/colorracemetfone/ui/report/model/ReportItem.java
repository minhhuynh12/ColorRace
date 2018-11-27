package com.example.metfone.colorracemetfone.ui.report.model;

public class ReportItem {
    private String id;
    private String code;
    private String name;
    private String total;
    private String used;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public ReportItem(String name, String total, String used) {
        this.name = name;
        this.total = total;
        this.used = used;
    }
}

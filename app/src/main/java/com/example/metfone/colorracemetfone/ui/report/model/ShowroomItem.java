package com.example.metfone.colorracemetfone.ui.report.model;

public class ShowroomItem  {
    private String name;
    private String total;
    private String used;

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

    public ShowroomItem(String name, String total, String used) {
        this.name = name;
        this.total = total;
        this.used = used;
    }
}

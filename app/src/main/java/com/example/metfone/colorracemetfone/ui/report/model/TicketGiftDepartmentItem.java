package com.example.metfone.colorracemetfone.ui.report.model;

import java.util.List;

public class TicketGiftDepartmentItem {
    private String department;
    private List<TicketGiftItem> lstGift;

    public List<TicketGiftItem> getLstGift() {
        return lstGift;
    }

    public void setLstGift(List<TicketGiftItem> lstGift) {
        this.lstGift = lstGift;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

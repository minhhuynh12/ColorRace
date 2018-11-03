package com.example.metfone.colorracemetfone.ui.login.model;

import java.util.List;

public class TicketItem {
    private String isdn;
    private String qrCode;
    private String status;
    private String ticketType;
    private List<String> lstGift;

    public List<String> getLstGift() {
        return lstGift;
    }

    public void setLstGift(List<String> lstGift) {
        this.lstGift = lstGift;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}

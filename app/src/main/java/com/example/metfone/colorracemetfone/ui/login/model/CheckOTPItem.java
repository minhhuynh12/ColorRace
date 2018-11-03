package com.example.metfone.colorracemetfone.ui.login.model;

import java.util.List;

public class CheckOTPItem {
    private String desc;
    private String errorCode;
    private String messageEn;
    private String messageKh;
    private String status;
    private String systemDate;
    private String timeNightRace;
    private String latStadium;
    private String longStadium;
    private RoleItem role;
    private TicketItem ticket;

    public String getLatStadium() {
        return latStadium;
    }

    public void setLatStadium(String latStadium) {
        this.latStadium = latStadium;
    }

    public String getLongStadium() {
        return longStadium;
    }

    public void setLongStadium(String longStadium) {
        this.longStadium = longStadium;
    }

    public String getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

    public String getTimeNightRace() {
        return timeNightRace;
    }

    public void setTimeNightRace(String timeNightRace) {
        this.timeNightRace = timeNightRace;
    }

    public TicketItem getTicket() {
        return ticket;
    }

    public void setTicket(TicketItem ticket) {
        this.ticket = ticket;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public String getMessageKh() {
        return messageKh;
    }

    public void setMessageKh(String messageKh) {
        this.messageKh = messageKh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RoleItem getRole() {
        return role;
    }

    public void setRole(RoleItem role) {
        this.role = role;
    }
}

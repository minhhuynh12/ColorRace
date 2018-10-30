package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.model;

public class TicketItems {
    private String gift;
    private String Amount;
    private String Receive;

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getReceive() {
        return Receive;
    }

    public void setReceive(String receive) {
        Receive = receive;
    }

    public TicketItems(String gift, String amount, String receive) {
        this.gift = gift;
        Amount = amount;
        Receive = receive;
    }
}

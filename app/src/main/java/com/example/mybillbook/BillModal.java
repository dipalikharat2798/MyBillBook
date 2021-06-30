package com.example.mybillbook;
public class BillModal {
    int id;
    String categary;
    String billnumber;
    String billname;
    String date;
    String time;
    int amountpaid;
    String satus;
    String photo;

    public BillModal(String categary, String billnumber, String billname, String date, String time, int amountpaid, String satus, String photo) {
        this.categary = categary;
        this.billnumber = billnumber;
        this.billname = billname;
        this.date = date;
        this.time = time;
        this.amountpaid = amountpaid;
        this.satus = satus;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategary() {
        return categary;
    }

    public void setCategary(String categary) {
        this.categary = categary;
    }

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(int amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getSatus() {
        return satus;
    }

    public void setSatus(String satus) {
        this.satus = satus;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

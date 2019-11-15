package com.sidorin.myhotel;

public class DataItem {

    private String code;
    private String name;
    private String surname;
    private String phone;
    private String check_in_year;
    private String check_in_month;
    private String check_in_day;
    private String check_out_year;
    private String check_out_month;
    private String check_out_day;
    private String room;

    public DataItem(String code, String name, String surname, String phone,
                    String check_in_year, String check_in_month, String check_in_day,
                    String check_out_year, String check_out_month, String check_out_day,
                    String room) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.check_in_year = check_in_year;
        this.check_in_month = check_in_month;
        this.check_in_day = check_in_day;
        this.check_out_year = check_out_year;
        this.check_out_month = check_out_month;
        this.check_out_day = check_out_day;
        this.room = room;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCheck_in_year() {
        return check_in_year;
    }

    public void setCheck_in_year(String check_in_year) {
        this.check_in_year = check_in_year;
    }

    public String getCheck_in_month() {
        return check_in_month;
    }

    public void setCheck_in_month(String check_in_month) {
        this.check_in_month = check_in_month;
    }

    public String getCheck_in_day() {
        return check_in_day;
    }

    public void setCheck_in_day(String check_in_day) {
        this.check_in_day = check_in_day;
    }

    public String getCheck_out_year() {
        return check_out_year;
    }

    public void setCheck_out_year(String check_out_year) {
        this.check_out_year = check_out_year;
    }

    public String getCheck_out_month() {
        return check_out_month;
    }

    public void setCheck_out_month(String check_out_month) {
        this.check_out_month = check_out_month;
    }

    public String getCheck_out_day() {
        return check_out_day;
    }

    public void setCheck_out_day(String check_out_day) {
        this.check_out_day = check_out_day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

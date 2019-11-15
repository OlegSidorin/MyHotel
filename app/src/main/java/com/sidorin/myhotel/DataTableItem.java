package com.sidorin.myhotel;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.Date;

public class DataTableItem {

    private String code;
    private String room, surnameOfGuest, nameOfGuest;
    Integer daysBeforeCheckOut, daysAfterCheckIn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSurnameOfGuest() {
        return surnameOfGuest;
    }

    public void setSurnameOfGuest(String surnameOfGuest) {
        this.surnameOfGuest = surnameOfGuest;
    }

    public String getNameOfGuest() {
        return nameOfGuest;
    }

    public void setNameOfGuest(String nameOfGuest) {
        this.nameOfGuest = nameOfGuest;
    }

    public Integer getDaysBeforeCheckOut() {
        return daysBeforeCheckOut;
    }

    public void setDaysBeforeCheckOut(Integer daysBeforeCheckOut) {
        this.daysBeforeCheckOut = daysBeforeCheckOut;
    }

    public Integer getDaysAfterCheckIn() {
        return daysAfterCheckIn;
    }

    public void setDaysAfterCheckIn(Integer daysAfterCheckIn) {
        this.daysAfterCheckIn = daysAfterCheckIn;
    }

    public DataTableItem() {
        this.code = "code";
        this.room = "room";
        this.surnameOfGuest = "surnameOfGuest";
        this.nameOfGuest = "nameOfGuest";
        this.daysBeforeCheckOut = 0;
        this.daysAfterCheckIn = 0;
    }


    /*
         сформировать массив данных на текущую дату, который будет отображаться в RecyclerView
         данных будет на 2 номера хостела 8ми местный и 6ти местный итого 14 данных и 2 заголовка итого 16 строк
         в качестве параметров массив данных и дата
         ..... потом реализую
    */


    public static ArrayList<DataTableItem> getCurrentData(ArrayList<DataItem> dataItems, Calendar date) {
        System.out.println("запуск метода сортировки данных на текущую дату getCurrentData в DataTableItem");
        ArrayList<DataTableItem> currentData = new ArrayList<>();
        System.out.println("создали пустой массив");
        Calendar varDateOfCheckOut = Calendar.getInstance(); // для проверки на выезд до текущей даты
        Calendar varDateOfCheckIn = Calendar.getInstance(); // для проверки на въезд до текущей даты
        // первым этапом сформировать массив с данными на дату, вторым его сортировка
        for (int i = 0; i < dataItems.size(); i++) {
            // установка дат въезда / выезда в тип Calendar
            DataTableItem dataTableItem = new DataTableItem();
            varDateOfCheckIn.set(java.util.Calendar.YEAR, Integer.parseInt(dataItems.get(i).getCheck_in_year()));
            varDateOfCheckIn.set(java.util.Calendar.MONTH, Integer.parseInt(dataItems.get(i).getCheck_in_month())-1);
            varDateOfCheckIn.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dataItems.get(i).getCheck_in_day()));
            varDateOfCheckOut.set(java.util.Calendar.YEAR, Integer.parseInt(dataItems.get(i).getCheck_out_year()));
            varDateOfCheckOut.set(java.util.Calendar.MONTH, Integer.parseInt(dataItems.get(i).getCheck_out_month())-1);
            varDateOfCheckOut.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dataItems.get(i).getCheck_out_day()));

            int daysAfterCheckIn = (daysBetweenAfterCheckIn(varDateOfCheckIn.getTime(), date.getTime()));
            int daysBeforeCheckOut = daysBetweenBeforeCheckOut(date.getTime(),varDateOfCheckOut.getTime());

            if ((daysAfterCheckIn >= 0) && (daysBeforeCheckOut >= 0)) {
                dataTableItem.setCode(dataItems.get(i).getCode());
                dataTableItem.setRoom(dataItems.get(i).getRoom());
                dataTableItem.setSurnameOfGuest(dataItems.get(i).getSurname());
                dataTableItem.setNameOfGuest(dataItems.get(i).getName());
                dataTableItem.setDaysBeforeCheckOut(daysBeforeCheckOut);
                dataTableItem.setDaysAfterCheckIn(daysAfterCheckIn);
                currentData.add(dataTableItem);
                System.out.println("++   в момент сортировки в DataTableItem: "
                        + dataTableItem.getSurnameOfGuest() + ", code: " + dataTableItem.getCode());
            }

        }

        return currentData;
    }

// после заезда
    private static int daysBetweenAfterCheckIn(Date d1, Date d2){
        double hours = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60);
        System.out.println("часов после заезда " + hours);
        if (hours > 0 && hours < 24) hours = 24;
        if (hours >= 24) hours += 1;
        return (int)(hours/24);
    }
    // перед выездом
    private static int daysBetweenBeforeCheckOut(Date d1, Date d2) {
        double hours = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60);
        System.out.println("часов перед выездом " + hours);
        if (hours < 0 && hours > -24) hours = -24;
        return (int)(hours/24);
    }
}

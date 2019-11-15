package com.sidorin.myhotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GuestDatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "guests_db"; // название БД
    private static final int DB_VERSION = 1;  // ее номер


    public GuestDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE GUESTS (_id TEXT PRIMARY KEY,"
                + "NAME TEXT,"
                + "SURNAME TEXT,"
                + "PHONE TEXT,"
                + "CINY TEXT,"
                + "CINM TEXT,"
                + "CIND TEXT,"
                + "COUTY TEXT,"
                + "COUTM TEXT,"
                + "COUTD TEXT,"
                + "ROOM TEXT);");

        String[] code = MyHotel.getAppContext().getResources().getStringArray(R.array.code);
        String[] name = MyHotel.getAppContext().getResources().getStringArray(R.array.name);
        String[] surname = MyHotel.getAppContext().getResources().getStringArray(R.array.surname);
        String[] phone = MyHotel.getAppContext().getResources().getStringArray(R.array.phone);
        String[] check_in_year = MyHotel.getAppContext().getResources().getStringArray(R.array.check_in_year);
        String[] check_in_month = MyHotel.getAppContext().getResources().getStringArray(R.array.check_in_month);
        String[] check_in_day = MyHotel.getAppContext().getResources().getStringArray(R.array.check_in_day);
        String[] check_out_year = MyHotel.getAppContext().getResources().getStringArray(R.array.check_out_year);
        String[] check_out_month = MyHotel.getAppContext().getResources().getStringArray(R.array.check_out_month);
        String[] check_out_day = MyHotel.getAppContext().getResources().getStringArray(R.array.check_out_day);
        String[] room = MyHotel.getAppContext().getResources().getStringArray(R.array.room);


        int i_all = MyHotel.getAppContext().getResources().getStringArray(R.array.name).length;

        for (int i = 0; i < i_all; i++) {

            ContentValues contactValues = new ContentValues();

            contactValues.put("_id", code[i]);
            contactValues.put("NAME", name[i]);
            contactValues.put("SURNAME", surname[i]);
            contactValues.put("PHONE", phone[i]);
            contactValues.put("CINY", check_in_year[i]);
            contactValues.put("CINM", check_in_month[i]);
            contactValues.put("CIND", check_in_day[i]);
            contactValues.put("COUTY", check_out_year[i]);
            contactValues.put("COUTM", check_out_month[i]);
            contactValues.put("COUTD", check_out_day[i]);
            contactValues.put("ROOM", room[i]);

            db.insert("GUESTS", null, contactValues);
            System.out.println("----------- добавили в БД при создании контакт: " + surname[i]);
        }

        // private String code; _id в БД
        // private String name;
        // private String surname;
        // private String phone;
        // private String check_in_year;
        // private String check_in_month;
        // private String check_in_day;
        // private String check_out_year;
        // private String check_out_month;
        // private String check_out_day;
        // private String room;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}


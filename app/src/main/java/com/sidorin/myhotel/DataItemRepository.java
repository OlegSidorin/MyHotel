package com.sidorin.myhotel;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class DataItemRepository {

    // заполняем словарь так как работать будем именно с ним по ключевым значениям
    /*public static HashMap<String, DataItem> dataItemHashMapGenerator(Context context) {

        HashMap<String, DataItem> dataItemHashMap = new HashMap<>();

        String[] key = context.getResources().getStringArray(R.array.code);
        String[] code = context.getResources().getStringArray(R.array.code);
        String[] name = context.getResources().getStringArray(R.array.name);
        String[] surname = context.getResources().getStringArray(R.array.surname);
        String[] phone = context.getResources().getStringArray(R.array.phone);
        String[] check_in_year = context.getResources().getStringArray(R.array.check_in_year);
        String[] check_in_month = context.getResources().getStringArray(R.array.check_in_month);
        String[] check_in_day = context.getResources().getStringArray(R.array.check_in_day);
        String[] check_out_year = context.getResources().getStringArray(R.array.check_out_year);
        String[] check_out_month = context.getResources().getStringArray(R.array.check_out_month);
        String[] check_out_day = context.getResources().getStringArray(R.array.check_out_day);
        String[] room = context.getResources().getStringArray(R.array.room);

        int i_all = context.getResources().getStringArray(R.array.name).length;

        for (int i = 0; i < i_all; i++) {
            DataItem item = new DataItem(
                    code[i],
                    name[i],
                    surname[i],
                    phone[i],
                    check_in_year[i], check_in_month[i], check_in_day[i],
                    check_out_year[i], check_out_month[i], check_out_day[i],
                    room[i]
            );
            dataItemHashMap.put(key[i], item);
        }
        return dataItemHashMap;
    }


    // заполняем первоначально списочный массив из данных контекста values
    public static ArrayList<DataItem> dataGenerator(Context context) {

        ArrayList<DataItem> data = new ArrayList<>();

        String[] code = context.getResources().getStringArray(R.array.code);
        String[] name = context.getResources().getStringArray(R.array.name);
        String[] surname = context.getResources().getStringArray(R.array.surname);
        String[] phone = context.getResources().getStringArray(R.array.phone);
        String[] check_in_year = context.getResources().getStringArray(R.array.check_in_year);
        String[] check_in_month = context.getResources().getStringArray(R.array.check_in_month);
        String[] check_in_day = context.getResources().getStringArray(R.array.check_in_day);
        String[] check_out_year = context.getResources().getStringArray(R.array.check_out_year);
        String[] check_out_month = context.getResources().getStringArray(R.array.check_out_month);
        String[] check_out_day = context.getResources().getStringArray(R.array.check_out_day);
        String[] room = context.getResources().getStringArray(R.array.room);

        int i_all = context.getResources().getStringArray(R.array.name).length;

        for (int i = 0; i < i_all; i++) {
            DataItem item = new DataItem(
                    code[i],
                    name[i],
                    surname[i],
                    phone[i],
                    check_in_year[i], check_in_month[i], check_in_day[i],
                    check_out_year[i], check_out_month[i], check_out_day[i],
                    room[i]
            );
            data.add(item);
        }
        return data;
    }

*/
}



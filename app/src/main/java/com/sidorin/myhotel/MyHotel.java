package com.sidorin.myhotel;

import android.app.Application;
import android.content.Context;

public class MyHotel extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyHotel.context = getApplicationContext();
    }
    public static Context getAppContext(){
        return MyHotel.context;
    }
}

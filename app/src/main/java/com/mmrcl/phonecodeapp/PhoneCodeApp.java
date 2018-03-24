package com.mmrcl.phonecodeapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 07-01-2017.
 */

public class PhoneCodeApp extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
    }
}

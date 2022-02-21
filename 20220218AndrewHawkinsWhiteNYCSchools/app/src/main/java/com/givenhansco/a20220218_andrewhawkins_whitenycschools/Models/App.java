package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.givenhansco.a20220218_andrewhawkins_whitenycschools.R;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by bseverson on 12/1/2016.
 */

public class App extends MultiDexApplication {

    private static final String PROD_API = "https://api-gh-prod.azurewebsites.net/";//Prod
    private final String PROD_HUB = "https://ghwebgps.azurewebsites.net/";//Prod


    public static String TAG = "App Class";
    private int gpsIntervalServiceCounter;
    private static int apiPref;
    private static WeakReference<Context> contextOfApplication;
    public WeakReference<SharedPreferences> _settings;
    public static boolean startingUp = true;

    public static boolean FirstRun(){
        return startingUp;
    }

    public static void ChangeFirstRunStatus(boolean status){
        startingUp = status;
    }

    public static Context GetAppContext(){
        return contextOfApplication.get();
    }

    public static String getAPI(){
         return PROD_API;
    }

    public static String getHost() throws MalformedURLException {
        URL url = new URL(PROD_API);
        return url.getHost();
    }

    public String getHubConnString(){
        return PROD_HUB;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
        _settings = new WeakReference<>(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        gpsIntervalServiceCounter = 0;

        contextOfApplication = new WeakReference<>(getApplicationContext());
        _settings = new WeakReference<>(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        apiPref = _settings.get().getInt("apiSelected", 0);

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                //keep from crash looping
                handleUncaughtException (thread, e);
            }
        });
    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        System.exit(1);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        _settings = null;
        contextOfApplication = null;
    }
}//EOC

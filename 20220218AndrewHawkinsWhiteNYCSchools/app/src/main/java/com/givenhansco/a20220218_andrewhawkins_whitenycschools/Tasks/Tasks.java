package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Tasks;
/**
 * Async task
 * this is where the api calls
 * are put together
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.givenhansco.a20220218_andrewhawkins_whitenycschools.API.RestAPI;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Activity.MainActivity;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Fragment.School;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.App;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school_meta;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

public class Tasks {
    private static final String TAG = "Tasks";

    //AsyncTask<Params, Progress, Result>
    //    Params – the input. what you pass to the AsyncTask
    //    Progress – if you have any updates, passed to onProgressUpdate()
    //    Result – the output. what returns doInBackground()

    //get list of schools async
    public static class getSchools extends AsyncTask<String, Integer, String> {

        school[] school;
        WeakReference<MainActivity> activity;

        //Constructors
        public getSchools(MainActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            Call<school[]> call = RestAPI.Factory.getInstance(App.GetAppContext()).getSchools();
            try {
                retrofit2.Response response = call.execute();
                int responseCode = response.code();
                Log.d("response code", "" + responseCode);
                if (responseCode == 200) {
                    String bod = response.body().toString();
                    this.school = (school[]) response.body();
                    return "success";
                }
                return response.message();
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (activity.get() != null && !activity.get().isFinishing()) {
                this.activity.get().GetSchoolTaskComplete(s, this.school);
            }
            super.onPostExecute(s);
        }


    }

    //get schools meta data async
    public static class getSchoolsMeta extends AsyncTask<String, Integer, String> {

        school_meta school_meta;
        WeakReference<MainActivity> activity;
        String dbn;

        //Constructors
        public getSchoolsMeta(MainActivity activity, String dbn) {
            this.activity = new WeakReference<>(activity);
            this.dbn = dbn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            Call<school_meta[]> call = RestAPI.Factory.getInstance(App.GetAppContext()).getSchoolMeta(dbn);
            try {
                retrofit2.Response response = call.execute();
                int responseCode = response.code();
                Log.d("response code", "" + responseCode);
                if (responseCode == 200) {
                    String bod = response.body().toString();
                    this.school_meta = ((school_meta[]) response.body())[0];
                    return "success";
                }
                return response.message();
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (activity.get() != null && !activity.get().isFinishing()) {
                this.activity.get().GetSchoolMetaTaskComplete(s, this.school_meta);
            }
            super.onPostExecute(s);
        }


    }

}

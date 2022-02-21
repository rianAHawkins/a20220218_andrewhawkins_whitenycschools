package com.givenhansco.a20220218_andrewhawkins_whitenycschools.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.App;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school_meta;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.App;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Tasks.Tasks;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

//import retrofit2.converter.gson.GsonConverterFactory;

public interface RestAPI
{
    //@POST("Account/Login")
    //Call<LoginResponseModel> login( @Body LoginPostModel loginPostModel);//Parameters

    /*
    here would be API calls for various data
    since we are not working with multi api environments
    it has been more hardcoded than a real project
    dont have to support a (dev,prod,hot fix) options
     */
    //
    @GET("https://data.cityofnewyork.us/resource/s3k6-pzi2.json")
    Call<school[]> getSchools();

    @GET("https://data.cityofnewyork.us/resource/f9bf-2cp4.json")
    Call<school_meta[]> getSchoolMeta( @Query("dbn") String Dbn);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    class Factory {
        private static RestAPI service;

        public static void resetService(){
            service = null;
        }

        public static RestAPI getInstance(Context context)
        {
            App app = ((App) context);
            String API = app.getAPI();
            //check Token

            /*
             This normally would be an auth token
             however in this case theres a static app_token
             so im hard coding it
            */
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.GetAppContext());
            String Token = sp.getString("access token", "no Token");
            Token = "GVDqT5KDmtpRPab9j12ptP4Cf";


            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            httpClient.addInterceptor(logging);
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);

            //interceptor adds value to all calls
            //httpClient.addInterceptor(new OAuthInterceptor("app_token",Token));

            // set your desired log level
            logging.setLevel(Level.BODY);
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(API)
                        .client(httpClient.build())
                        .build();
                service = retrofit.create(RestAPI.class);
                return service;
            } else {
                return service;
            }
        }

    }
}


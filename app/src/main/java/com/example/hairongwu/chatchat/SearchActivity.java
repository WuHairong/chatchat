package com.example.hairongwu.chatchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class SearchActivity extends AppCompatActivity {

    private LocationData locationData = LocationData.getLocationData();
    private String lat;
    private String lng;
    private String user_id;
    private String nickname = "Hobbes";
    private String message = "Tuna";
    private String message_id = "8755";
    public static String LOG_TAG = "MyApplication";


    public interface postService {

        @POST("default/post_message")
        Call<Example> location(@Query("lat") String lat,  //latitude
                               @Query("lng") String lng,  //longitude
                               @Query("user_id") String user_id,     //user_id
                               @Query("nickname") String nickname,    //nickname
                               @Query("message") String message,    //message send
                               @Query("message_id") String message_id    //message_id



        );

    }

    public interface getService {
        @GET("default/get_messages")
        Call<Example> message(@Query("lat") String lat,  //latitude
                              @Query("lng") String lng,  //longitude
                              @Query("user_id") String user_id     //user_id

        );

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onResume(){
        Intent intent = getIntent();
        String nickname = intent.getStringExtra(MainActivity.nickname);//get search word


        // Gets the settings, and creates a random user id if missing.
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //user_id = settings.getString("user_id", null);
        Log.i(LOG_TAG, "user_id"+ " is: " + user_id);
        if (user_id == null) {
            // Creates a random one, and sets it.
            //SecureRandomString srs = new SecureRandomString();
            user_id = getRandom();
            //Log.i(LOG_TAG, "00"+ "Code is: " + user_id);
            //SharedPreferences.Editor e = settings.edit();
            //e.putString("user_id", user_id);
            //e.commit();
        }
        //Log.i(LOG_TAG, "user_id"+ "00 is: " + user_id);

        // Let's register the user.
        // In truth, it may be better to keep a flag in preferences that tells us
        // whether we have already registered?

        //Log.i(LOG_TAG, "22Code is: " + lat);
        //Log.i(LOG_TAG, "22Code is: " + lng);
        Log.i(LOG_TAG, "user_id is: " + user_id);
        Log.i(LOG_TAG, "nickname is: " + nickname);
        Log.i(LOG_TAG, "message is: " + message);
        Log.i(LOG_TAG, "message_id is: " + message_id);
        //Postservice(lat, lng, user_id, nickname, message, message_id);
        Getservice(lat, lng, user_id);
        super.onResume();

    }


    //generate random number for user_id
    public String getRandom(){
        Random ran= new Random();
        int randomInt = ran.nextInt(100)+1;
        Log.i(LOG_TAG, "random Num is: " + randomInt);
        return ""+ randomInt;
    }



    public void Postservice(String lat, String lng, String user_id, String nickname,
                            String message, String message_id){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://luca-teaching.appspot.com/localmessages/")
                .addConverterFactory(GsonConverterFactory.create())    //parse Gson string
                .client(httpClient)    //add logging
                .build();

        postService service = retrofit.create(postService.class);

        if(LocationData.getLocationData() == null ) {
            return;
        }
        lat =  Double.toString(locationData.getLocation().getLatitude());
        lng = Double.toString(locationData.getLocation().getLongitude());
        Log.i(LOG_TAG, "9999999Code is: " + lat);
        Log.i(LOG_TAG, "9999999Code is: " + lng);

        Call<Example> queryResponseCall =
                service.location(lat, lng, user_id, nickname, message, message_id);
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {


                Log.i(LOG_TAG, "33Code is: " + response.code());
                Log.i(LOG_TAG, "33Code is: " + response.code());
                Log.i(LOG_TAG, "33Code is: " + response.code());
                Log.i(LOG_TAG, "33Code is: " + response.code());
                Log.i(LOG_TAG, "33Code is: " + response.code());
                Log.i(LOG_TAG, "33The result is: " + response.body());
                Log.i(LOG_TAG, "33The result is: " + response.body().result);
                Log.i(LOG_TAG, "33The resultList is: " + response.body().resultList);
                Log.i(LOG_TAG, "33The resultList is: " + response.body().resultList.toArray());

            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });




    }

    public void Getservice(String lat, String lng, String user_id){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://luca-teaching.appspot.com/localmessages/")
                .addConverterFactory(GsonConverterFactory.create())    //parse Gson string
                .client(httpClient)    //add logging
                .build();

        getService service = retrofit.create(getService.class);
        Call<Example> queryResponseCall =
                service.message(lat, lng, user_id);
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {
                Log.i(LOG_TAG, "44 Code is: " + response.code());
                Log.i(LOG_TAG, "44 The body is: " + response.body());
                Log.i(LOG_TAG, "44The result is: " + response.body().result);
                //Log.i(LOG_TAG, "44The resultList is: " + response.body().resultList.get(0).timestamp);
                for (int i = 0; i < response.body().resultList.size(); ++i) {
                    Log.i(LOG_TAG, "45The timestamp is: " + response.body().resultList.get(i).timestamp);
                    Log.i(LOG_TAG, "45The message is: " + response.body().resultList.get(i).message);
                    Log.i(LOG_TAG, "45The nickname is: " + response.body().resultList.get(i).nickname);
                    Log.i(LOG_TAG, "45The userId is: " + response.body().resultList.get(i).userId);
                    Log.i(LOG_TAG, "45The messageId is: " + response.body().resultList.get(i).messageId);
                }

            }

            //need to show string content on the screen

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });
    }






}
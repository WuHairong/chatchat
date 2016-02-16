package com.example.hairongwu.chatchat;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.ListView;
import android.widget.TextView;
import com.example.hairongwu.chatchat.Example;
import com.example.hairongwu.chatchat.ResultList;


import java.util.ArrayList;
import java.util.List;
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

    private String lat = "9.99999";
    private String lng = "10.0001";
    private String user_id ;
    private String nickname;
    private String message = "Tuna";
    private String message_id = "8755";
    public static String LOG_TAG = "MyApplication";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private MyAdapter adapter;
    private ArrayList<ListElement> aList;
    static private Boolean postmessage = false;
    static private Boolean refreshAllowed = false;
    static int count=0;
    static int countClick = 0;

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

        //get nickname from user input
        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.nickname);//get search word
        user_id  = intent.getStringExtra(MainActivity.user_id);

        //use adapter to show list of view
        aList = new ArrayList<ListElement>();
        adapter = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //retrive user_id


        //test user_id, if it is null, give it a random number
        //Log.i(LOG_TAG, "22222222 name" + " is: " + name);//test user_id
        //if (user_id == null) {
        //    user_id = getRandom();
        //}


        Log.i(LOG_TAG, "user_id is: " + user_id);
        Log.i(LOG_TAG, "nickname is: " + nickname);
        Log.i(LOG_TAG, "message is: " + message);
        Log.i(LOG_TAG, "message_id is: " + message_id);

        Getservice(lat, lng, user_id);

        Button rtn = (Button) findViewById(R.id.button3);
        final EditText editText = (EditText)findViewById(R.id.editText);
        rtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        message = editText.getText().toString();
                        Log.i(LOG_TAG, "1111new message is: " + message);
                    }
                });
        editText.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        Log.i(LOG_TAG, "2222new message is: " + message);
        Postservice(lat, lng, user_id, nickname, message, message_id);
        if(postmessage ==true){
            postmessage = false;
            editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }



    }



    @Override
    protected void onResume(){


        //Log.i(LOG_TAG, "22Code is: " + lat);
        //Log.i(LOG_TAG, "22Code is: " + lng);
        Log.i(LOG_TAG, "11user_id is: " + user_id);
        Log.i(LOG_TAG, "11nickname is: " + nickname);
        Log.i(LOG_TAG, "11message is: " + message);
        Log.i(LOG_TAG, "11message_id is: " + message_id);

        //if(refreshAllowed==true){
        //    Getservice(lat, lng, user_id);
        //    refreshAllowed = false;
        //}
        //Postservice(lat, lng, user_id, nickname, message, message_id);
        //Getservice(lat, lng, user_id);
        //Log.i(LOG_TAG, "4444The aList size is: " + aList.size());


        super.onResume();

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



        Log.i(LOG_TAG, "9999999Code is: " + lat);
        Log.i(LOG_TAG, "9999999Code is: " + lng);

        Call<Example> queryResponseCall =
                service.location(lat, lng, user_id, nickname, message, message_id);
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {

                if (response.code() == 200) {
                    postmessage = true;
                    Log.i(LOG_TAG, "33Code is: " + response.code());
                    Log.i(LOG_TAG, "33The result is: " + response.body());
                    Log.i(LOG_TAG, "33The result is: " + response.body().result);
                    Log.i(LOG_TAG, "33The resultList is: " + response.body().resultList);
                    Log.i(LOG_TAG, "33The resultList is: " + response.body().resultList.toArray());
                    //need to send message to view

                }

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
        //Log.i(LOG_TAG, "lat is: " + lat);
        //Log.i(LOG_TAG, "lng is: " + lng);
        count++;
        Call<Example> queryResponseCall =
                service.message(lat, lng, user_id);
        //Log.i(LOG_TAG, "lat is: " + lat);
        //Log.i(LOG_TAG, "lng is: " + lng);
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {
                aList.clear();//remove previous content
                //need to put reponse result into aList
                Log.i(LOG_TAG, "alist size is: " + aList.size());
                Log.i(LOG_TAG, "44444 Code is: " + response.code());
                Log.i(LOG_TAG, "44444 Code is: " + response.body());
                Log.i(LOG_TAG, "44444 Code is: " + response.body().result);

                ListElement aListElement = new ListElement();
                if(response.code() == 500 ||response.code() == 400 ){
                    aListElement.setMessage("error");
                    aListElement.setTimestamp("none");
                    aListElement.setNickname("none");
                    aListElement.setUser_id("none");
                    aList.add(0, aListElement);
                    return;
                }else if(response.body().result == "error"){
                    aListElement.setMessage("error");
                    aListElement.setTimestamp("none");
                    aListElement.setNickname("none");
                    aListElement.setUser_id("none");
                    aList.add(0, aListElement);
                    return;
                }


                int i = response.body().resultList.size();
                if (i==0){
                    aListElement.setMessage("no result");
                    aListElement.setTimestamp("none");
                    aListElement.setNickname("none");
                    aListElement.setUser_id("none");
                    aList.add(0, aListElement);
                }else {
                    Log.i(LOG_TAG, "44 count is: " + count);
                    Log.i(LOG_TAG, "44 Code is: " + response.code());
                    Log.i(LOG_TAG, "44 The body is: " + response.body());
                    Log.i(LOG_TAG, "44The result is: " + response.body().result);
                    Log.i(LOG_TAG, "44The List size is: " + response.body().resultList.size());
                    for (int j = 0; j < i; ++j) {
                        String timestamp = response.body().resultList.get(j).timestamp;
                        String message   = response.body().resultList.get(j).message;
                        String nickname  = response.body().resultList.get(j).nickname;
                        String userId    = response.body().resultList.get(j).userId;
                        aListElement.setMessage(message);
                        aListElement.setTimestamp(timestamp);
                        aListElement.setNickname(nickname);
                        aListElement.setUser_id(userId);
                        aList.add(j, aListElement);

                        //Log.i(LOG_TAG, "45The timestamp is: " + aList.get(j).timestamp);
                        //Log.i(LOG_TAG, "45The message is: " + aList.get(j).message);
                        //Log.i(LOG_TAG, "45The nickname is: " + aList.get(j).nickname);
                        //Log.i(LOG_TAG, "45The userId is: " + aList.get(j).user_id);

                    }
                    Log.i(LOG_TAG, "44444The List size is: " + response.body().resultList.size());
                    aList = sortList(aList);
                }
                adapter.notifyDataSetChanged();

                for(int j=0; j<aList.size();++j) {
                    Log.i(LOG_TAG, "The message is: " + aList.get(j).message);
                    Log.i(LOG_TAG, "The timestamp is: " + aList.get(j).timestamp);
                    Log.i(LOG_TAG, "The nickname is: " + aList.get(j).nickname);
                    Log.i(LOG_TAG, "The user_id is: " + aList.get(j).user_id);
                }
            }




            //need to show string content on the screen

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }



        });
    }

    //sort the ArrayList according to the timestamp
    public ArrayList<ListElement> sortList(ArrayList<ListElement> aList){
        ArrayList<ListElement> anewList= new ArrayList<ListElement>();
        int size = aList.size();
        for(int i=0; i<size; ++i) {
            String mintime = mintimestamp(aList);
            for (int j = 0; j < aList.size(); ++j) {
                String temp = aList.get(j).timestamp;
                if (temp.equals(mintime)) {
                    ListElement tempElement = new ListElement();
                    tempElement.message = aList.get(j).message;
                    tempElement.nickname = aList.get(j).nickname;
                    tempElement.user_id = aList.get(j).user_id;
                    tempElement.timestamp = aList.get(j).timestamp;
                    anewList.add(tempElement);
                    aList.remove(j);
                    break;
                }
            }
        }
        Log.i(LOG_TAG, "8888The List size is: " + anewList.size());
        for(int i = 0; i< anewList.size(); ++i){
            ListElement tempElement = new ListElement();
            tempElement.message = anewList.get(i).message;
            tempElement.nickname = anewList.get(i).nickname;
            tempElement.user_id = anewList.get(i).user_id;
            tempElement.timestamp = anewList.get(i).timestamp;
            aList.add(tempElement);
        }
        return aList;
    }


    //find the smallest timestamp from ArrayList<ListElement>
    public String mintimestamp(ArrayList<ListElement> aList){
        if (aList.size()==0)
                return null;
        String mintimestamp = aList.get(0).timestamp;
        for (int i=0; i< aList.size(); ++i){
            String temptime =aList.get(i).timestamp;
            if (mintimestamp.compareTo(temptime) > 0){
                mintimestamp = temptime;
            }
        }
        return mintimestamp;
    }


    public void refresh(View view){
        refreshAllowed= true;
        aList.clear();
        Getservice(lat, lng, user_id);

    }

    public void change(View view){
        countClick++;
        Button button = (Button) findViewById(R.id.Button4);
        button.setBackgroundColor(444);
        button.setText("kkkkk");
        button.setVisibility(View.VISIBLE);
        }





}
package com.paul_resume.smarthome.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by paul on 16.05.2015.
 */
public class WeatherService extends IntentService{

    public static final String TAG = "WeatherService";
    public static final String ACTION_WEATHER_SERVICE = WeatherService.class.getName();
    public static final String API_ENDPOINT = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Bucharest%2C%20RO%22)%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String CELSIUS = "\u2103";


    public static final String TAG_QUERY = "query" ,
            TAG_RESULTS = "results",
            TAG_CHANNEL = "channel",
            TAG_ATMOSPHERE = "atmosphere",
            TAG_ITEM = "item",
            TAG_CONDITION = "condition";

    public static final String EXTRA_CODE = "code",
                               EXTRA_TEMP = "temp",
                               EXTRA_TEXT = "text" ,
                               EXTRA_HUMI = "humi";

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if(checkInternetConnection()){

            try {
                URL url = new URL(API_ENDPOINT);
                URLConnection connection = url.openConnection();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                String line = null;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }

                reader.close();

                JSONObject obj = new JSONObject(builder.toString());
                JSONObject query = obj.getJSONObject(TAG_QUERY);
                JSONObject results = query.getJSONObject(TAG_RESULTS);
                JSONObject channel = results.getJSONObject(TAG_CHANNEL);

                JSONObject atmosphere = channel.getJSONObject(TAG_ATMOSPHERE);
                JSONObject item = channel.getJSONObject(TAG_ITEM);
                JSONObject condition = item.getJSONObject(TAG_CONDITION);

                Log.d(TAG, condition.toString());

                int code = condition.getInt("code");
                String temp = condition.getString("temp") + CELSIUS;
                String text = condition.getString("text");
                String humi = "Humidity : " +atmosphere.getString("humidity") + "%";

                sendBroadcast(code , temp , text, humi);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(TAG , "No internet connection available !");
        }

    }

    protected boolean checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return  isConnected;
    }

    protected void sendBroadcast(int code , String temp , String text , String humi){
        Intent intent = new Intent(ACTION_WEATHER_SERVICE);
        intent.putExtra(EXTRA_CODE , code);
        intent.putExtra(EXTRA_TEMP , temp);
        intent.putExtra(EXTRA_TEXT , text);
        intent.putExtra(EXTRA_HUMI , humi);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

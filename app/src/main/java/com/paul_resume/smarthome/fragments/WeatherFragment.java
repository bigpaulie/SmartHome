package com.paul_resume.smarthome.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paul_resume.smarthome.R;
import com.paul_resume.smarthome.services.WeatherService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    public ImageView weatherIcon = null;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Find all views
        final TextView temp = (TextView)getActivity().findViewById(R.id.txtTemp);
        final TextView humi = (TextView)getActivity().findViewById(R.id.txtHumi);
        final TextView text = (TextView)getActivity().findViewById(R.id.txtText);
        weatherIcon = (ImageView)getActivity().findViewById(R.id.weatherIcon);

        /*
        * Broadcast receiver for weather service
        * Receive and assign weather info
        * */
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Assign temperature
                temp.setText(intent.getStringExtra(WeatherService.EXTRA_TEMP));
                // Assign weather short description
                text.setText(intent.getStringExtra(WeatherService.EXTRA_TEXT));
                // Assign humidity
                humi.setText(intent.getStringExtra(WeatherService.EXTRA_HUMI));
                // Set the weather icon
                setWeatherIcon(intent.getIntExtra(WeatherService.EXTRA_CODE , 0));
            }
        } , new IntentFilter(WeatherService.ACTION_WEATHER_SERVICE));

        /*
        * Weather auto-update scheduler
        * Starts a new intent to the weather service to download weather info
        * By default the weather updates at an interval of 30 minutes
        * */
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Start a new intent service and grab new weather data
                Intent intent = new Intent(getActivity(), WeatherService.class);
                getActivity().startService(intent);
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    /**
     * Set the weather icon
     * @param  code
     */
    protected void setWeatherIcon(int code){

        if(code == 3 || code == 4 || code == 45){
            /*
            * severe thunderstorms
            * thunderstorms
            * */
            weatherIcon.setImageResource(R.drawable.cloud310);
        }else if(code == 5 || code == 6 || code == 7){
            /*
            * mixed rain and snow
            * mixed rain and sleet
            * mixed snow and sleet
            * */
            weatherIcon.setImageResource(R.drawable.rain20);
        }else if(code == 8){
            // freezing drizzle
            weatherIcon.setImageResource(R.drawable.thermometer10);
        }else if(code == 9){
            // drizzle
            weatherIcon.setImageResource(R.drawable.rain53);
        }else if(code == 10){
            // freezing rain
            weatherIcon.setImageResource(R.drawable.thermometer10);
        }else if(code == 11 || code == 12){
            /*
            * showers
            * showers
            * */
            weatherIcon.setImageResource(R.drawable.rain20);
        }else if(code == 13 || code == 14 || code == 15 || code == 16){
            /*
            * snow flurries
            * light snow showers
            * blowing snow
            * snow
            * */
            weatherIcon.setImageResource(R.drawable.snowflake3);
        }else if(code == 17){
            // hail
        }else if(code == 18){
            // sleet
        }else if(code == 19){
            // dust
        }else if(code == 20){
            // foggy
            weatherIcon.setImageResource(R.drawable.river3);
        }else if(code == 21){
            // haze
        }else if(code == 22){
            // smoky
        }else if(code == 23){
            // blustery
        }else if(code == 24){
            // windy
            weatherIcon.setImageResource(R.drawable.windy9);
        }else if(code == 25){
            // cold
            weatherIcon.setImageResource(R.drawable.thermometer10);
        }else if(code == 26 || code == 27 || code == 28){
            // cloudy
            weatherIcon.setImageResource(R.drawable.clouds11);
        }else if(code == 29 || code == 30 || code == 44){
            // partly cloudy
            weatherIcon.setImageResource(R.drawable.cloud76);
        }else if(code == 31){
            // clear
        }else if(code == 32){
            // sunny
            weatherIcon.setImageResource(R.drawable.sunny18);
        }else if(code == 33 || code == 34){
            // fair
            weatherIcon.setImageResource(R.drawable.cloud);
        }else if(code == 35){
            // mixed rain and hail
        }else if(code == 36){
            // hot
            weatherIcon.setImageResource(R.drawable.hotweather);
        }else if(code == 37 || code == 38 || code == 39){
            /*
            * isolated thunderstorms
            * scattered thunderstorms
            * scattered thunderstorms
            * */
            weatherIcon.setImageResource(R.drawable.cloud310);
        }else if(code == 40){
            // scattered showers
            weatherIcon.setImageResource(R.drawable.rain20);
        }else if(code == 41 || code == 42 || code == 43){
            /*
            * heavy snow
            * scattered snow showers
            * heavy snow
            * */
            weatherIcon.setImageResource(R.drawable.snowflake3);
        }else if(code == 46){
            // snow showers
            weatherIcon.setImageResource(R.drawable.snowflake3);
        }else if(code == 47){
            // isolated thundershowers
            weatherIcon.setImageResource(R.drawable.cloud310);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }


}

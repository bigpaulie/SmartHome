package com.paul_resume.smarthome.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.paul_resume.smarthome.services.MqttService;

/**
 * Created by paul on 25.05.2015.
 */
public class NetworkReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        sendBroadcast(context);
    }

    public void sendBroadcast(Context context){
        Intent intent = new Intent(MqttService.ACTION_NETWORK_CHANGE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}

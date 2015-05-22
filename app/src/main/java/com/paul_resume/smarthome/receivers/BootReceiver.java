package com.paul_resume.smarthome.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.paul_resume.smarthome.services.MqttService;

/**
 * Created by paul on 22.05.2015.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context , MqttService.class);
        context.startService(service);
    }
}

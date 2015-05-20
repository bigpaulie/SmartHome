package com.paul_resume.smarthome.mqtt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.paul_resume.smarthome.AppSettings;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by paul on 20.05.2015.
 */
public class MQTTService extends Service {

    public static final String TAG = "MQTT Service",
            ANDROID_ID = Settings.Secure.ANDROID_ID;

    MQTTThread thread;
    MqttClient client;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("MQTT", "onHandleIntent");
        thread = new MQTTThread(getApplicationContext());
        thread.start();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MQTTThread extends Thread {

        Context context;
        AppSettings settings;

        public MQTTThread(Context context) {

            this.context = context;
            settings = new AppSettings(context);
        }

        @Override
        public void run() {

            try {

                if (!settings.getBroker().isEmpty()) {

                    client = new MqttClient(settings.getBroker(), ANDROID_ID, new MemoryPersistence());
                    client.setCallback(new MQTTPushCallback(context));

                    if (!settings.getUsername().isEmpty() || !settings.getPassword().isEmpty()) {
                        MqttConnectOptions options = new MqttConnectOptions();
                        options.setUserName(settings.getUsername());
                        options.setPassword(settings.getPassword().toCharArray());
                        client.connect(options);
                    } else {
                        client.connect();
                    }

                    client.subscribe(settings.getTopic());

                } else {
                    Log.d(TAG, "Broker URL not available !");
                }

            } catch (MqttException e) {
                e.printStackTrace();
            }

        }
    }
}

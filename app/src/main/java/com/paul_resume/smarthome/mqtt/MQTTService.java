package com.paul_resume.smarthome.mqtt;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by paul on 20.05.2015.
 */
public class MQTTService extends Service{

    public static final String TAG_COMMAND = "command";
    public static final String ANDROID_ID = Settings.Secure.ANDROID_ID;
    public static final String BROKER_URL = "tcp://mqtt.paul-resume.com:1883",
                               TOPIC = "/paul/relays",
                               USERNAME = "paul",
                               PASSWORD ="1q2w3e4r)#";

    MQTTThread thread;
    MqttClient client;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("MQTT" , "onHandleIntent");
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

        public MQTTThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {

            try {
                client = new MqttClient(BROKER_URL , ANDROID_ID , new MemoryPersistence());
                client.setCallback(new MQTTPushCallback(context));

                MqttConnectOptions options = new MqttConnectOptions();
                options.setUserName(USERNAME);
                options.setPassword(PASSWORD.toCharArray());

                client.connect(options);
                client.subscribe(TOPIC);

            } catch (MqttException e) {
                e.printStackTrace();
            }

        }
    }
}

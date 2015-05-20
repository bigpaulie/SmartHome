package com.paul_resume.smarthome.mqtt;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by paul on 20.05.2015.
 */
public class MQTTPublisher extends AsyncTask<String, Void, Void> {

    MqttClient client;
    Context context;
    public static final String TAG = "MQTT";

    public MQTTPublisher(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {

            client = new MqttClient(MQTTService.BROKER_URL, MQTTService.ANDROID_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTTService.USERNAME);
            options.setPassword(MQTTService.PASSWORD.toCharArray());
            client.setCallback(new MQTTPushCallback(context));
            client.connect(options);

            if (client.isConnected()) {
                client.publish(MQTTService.TOPIC, new MqttMessage(params[0].getBytes()));
            }else {
                Log.d(TAG , "Client is not connected to the mqtt service");
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

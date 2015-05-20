package com.paul_resume.smarthome.mqtt;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.paul_resume.smarthome.AppSettings;

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

    public static final String TAG = "MQTT Publisher";
    MqttClient client;
    Context context;
    AppSettings settings;

    public MQTTPublisher(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            settings = new AppSettings(context);

            if (!settings.getBroker().isEmpty()) {

                client = new MqttClient(settings.getBroker(), MQTTService.ANDROID_ID, new MemoryPersistence());

                if (!settings.getUsername().isEmpty() || settings.getPassword().isEmpty()) {
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setUserName(settings.getUsername());
                    options.setPassword(settings.getPassword().toCharArray());
                    client.setCallback(new MQTTPushCallback(context));
                    client.connect(options);
                } else {
                    client.setCallback(new MQTTPushCallback(context));
                    client.connect();
                }

                if (client.isConnected()) {
                    client.publish(settings.getTopic(), new MqttMessage(params[0].getBytes()));
                } else {
                    Log.d(TAG, "Client is not connected to the mqtt service");
                }

            } else {
                Log.d(TAG, "Broker URL unavailable !");
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
            if(client != null) {
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

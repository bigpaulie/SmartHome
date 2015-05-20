package com.paul_resume.smarthome.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by paul on 20.05.2015.
 */
public class MQTTPushCallback implements MqttCallback {

    public static final String TAG = "MQTT Callback";
    Context context;

    public MQTTPushCallback(Context context) {
        this.context = context;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(TAG , "Connection lost !");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.d(TAG , "messageArrived() " + mqttMessage.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//        Toast.makeText(context , "Command send !", Toast.LENGTH_SHORT).show();
        Log.d(TAG , "deliveryComplete()");
    }
}

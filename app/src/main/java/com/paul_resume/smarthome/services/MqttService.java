package com.paul_resume.smarthome.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paul_resume.smarthome.AppSettings;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttService extends Service implements MqttCallback{

    MqttClient client;
    MqttConnectOptions options;
    AppSettings settings;

    public static final String TAG = "MQTTService";

    public static final String ACTION_PUBLISH = "MQTTPublish",
                               ACTION_RECEIVED = "MQTTReceived",
                               ACTION_CONNECTION_LOST = "MQTTConnectionLost",
                               ACTION_DELIVERY_COMPLETE = "MQTTDeliveryComplete",
                               ACTION_SETTINGS_CHANGE = "MQTTSettingsChanged";
    public static final String EXTRA_SEND_MESSAGE = "MQTTMessage",
                               EXTRA_RECEIVED_MESSAGE = "MQTTReceivedMessage",
                               EXTRA_ERROR_MESSAGE = "MQTTErrorMessage";

    public static final int KEEP_ALIVE = 20 * 60;

    public MqttService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        settings = new AppSettings(getApplicationContext());
        // connect to mqtt service
        doConnect();

        // register broadcast receivers
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(new PublishBroadcastReceiver() , new IntentFilter(ACTION_PUBLISH));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doDisconnect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Intent intent = new Intent(ACTION_CONNECTION_LOST);
        intent.putExtra(EXTRA_ERROR_MESSAGE , throwable.getCause());
        sendBroadcast(intent);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Intent intent = new Intent(ACTION_DELIVERY_COMPLETE);
        intent.putExtra(EXTRA_RECEIVED_MESSAGE , mqttMessage.toString());
        sendBroadcast(intent);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Intent intent = new Intent(ACTION_DELIVERY_COMPLETE);
        sendBroadcast(intent);
    }

    public void doConnect(){
        Log.d(TAG , "doConnect()");
        try {
            client = new MqttClient(settings.getBroker()+":"+settings.getPort() , "" , new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setKeepAliveInterval(KEEP_ALIVE);
            client.setCallback(this);

            if(!settings.getUsername().isEmpty() || !settings.getPassword().isEmpty()){
                options.setUserName(settings.getUsername());
                options.setPassword(settings.getPassword().toCharArray());
            }

            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void doDisconnect(){
        if(client != null){
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendBroadcast(Intent intent){
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }

    public class PublishBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if((client != null) && client.isConnected()){
                String payload = intent.getStringExtra(EXTRA_SEND_MESSAGE);
                try {
                    client.publish(settings.getTopic() , new MqttMessage(payload.getBytes()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class SettingsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                doDisconnect();
                this.wait(1000);
                doConnect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

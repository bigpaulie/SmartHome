package com.paul_resume.smarthome.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

/**
 * MQTT Service
 * Used to publish and subscribe to an MQTT Broker
 *
 * @author Paul Purcel <bigpaulie25ro@yahoo.com>
 *         TODO: add broadcast receiver for network status
 *         TODO: add mqtt ping method to keep alive the connection
 */
public class MqttService extends Service implements MqttCallback {

    /**
     * Service constants
     * The ACTION_ constants represent Intents that the service
     * broadcasts or receive
     * The EXTRA_ constants represent various messages attached to the Intents
     */
    public static final String TAG = "MQTTService";
    public static final String ACTION_PUBLISH = "MQTTPublish",
            ACTION_RECEIVED = "MQTTReceived",
            ACTION_CONNECTION_LOST = "MQTTConnectionLost",
            ACTION_DELIVERY_COMPLETE = "MQTTDeliveryComplete",
            ACTION_SETTINGS_CHANGE = "MQTTSettingsChanged",
            ACTION_NETWORK_CHANGE = "MQTTNetworkChange";

    public static final String EXTRA_SEND_MESSAGE = "MQTTMessage",
            EXTRA_RECEIVED_MESSAGE = "MQTTReceivedMessage",
            EXTRA_ERROR_MESSAGE = "MQTTErrorMessage";

    /**
     * MQTT configuration constants
     */
    public static final int KEEP_ALIVE = 20 * 60;

    /**
     * Declare service wise variables
     */
    MqttClient client;
    MqttConnectOptions options;
    AppSettings settings;

    /**
     * Empty constructor
     */
    public MqttService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * Get application preferences
         * */
        settings = new AppSettings(getApplicationContext());
        // connect to mqtt service
        if(isConnected(getApplicationContext())) {
            doConnect();
        }

        /**
         * Register broadcast receivers
         * ACTION_PUBLISH for publishing over MQTT
         * ACTION_SETTINGS_CHANGE for settings changing
         * TODO: write a method to register the broadcasts receivers
         * */
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(new PublishBroadcastReceiver(), new IntentFilter(ACTION_PUBLISH));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(new SettingsBroadcastReceiver(), new IntentFilter(ACTION_SETTINGS_CHANGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(new NetworkBroadcastReceiver(),
                        new IntentFilter(ACTION_NETWORK_CHANGE));
    }

    /**
     * Fired when service is started
     * return START_STICKY to keep the service running in background
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     * Fired when service is stopped
     * disconnect form the MQTT Broker
     */
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

    /**
     * MQTT Connection lost callback
     * We use this method to fire an event when the connection fails
     */
    @Override
    public void connectionLost(Throwable throwable) {
        Intent intent = new Intent(ACTION_CONNECTION_LOST);
        intent.putExtra(EXTRA_ERROR_MESSAGE, throwable.getCause());
        sendBroadcast(intent);
    }

    /**
     * MQTT message arrived callback
     * We use this method to fire an event when a message arrives
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Intent intent = new Intent(ACTION_DELIVERY_COMPLETE);
        intent.putExtra(EXTRA_RECEIVED_MESSAGE, mqttMessage.toString());
        sendBroadcast(intent);
    }

    /**
     * MQTT Delivery complete callback
     * Fired when the message has been delivered
     * We use this method to fire an event
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Intent intent = new Intent(ACTION_DELIVERY_COMPLETE);
        sendBroadcast(intent);
    }

    /**
     * Connecting to the MQTT Broker
     * This method opens a connection to the MQTT Broker
     * TODO: check if internet connection is available before attempting to connect
     */
    public void doConnect() {
        Log.d(TAG, "doConnect()");
        try {
            /**
             * Connect to the broker and set the connection options
             */
            client = new MqttClient(settings.getBroker() + ":" + settings.getPort(), "", new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setKeepAliveInterval(KEEP_ALIVE);
            client.setCallback(this);

            if (!settings.getUsername().isEmpty() || !settings.getPassword().isEmpty()) {
                options.setUserName(settings.getUsername());
                options.setPassword(settings.getPassword().toCharArray());
            }

            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect form the MQTT Broker
     */
    public void doDisconnect() {
        if (client != null) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send Broadcasts
     *
     * @param intent
     */
    public void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }

    /**
     * Check if internet connection is available
     *
     * @param context
     * @return boolean
     */
    public boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        boolean isConnected = info != null && info.isConnected();
        return isConnected;
    }

    /**
     * Broadcast receiver for publishing
     * Receives an intent to publish a message to the Broker
     */
    public class PublishBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((client != null) && client.isConnected()) {
                String payload = intent.getStringExtra(EXTRA_SEND_MESSAGE);
                try {
                    client.publish(settings.getTopic(), new MqttMessage(payload.getBytes()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Broadcast receiver for settings
     * Receives an intent when the application settings have been modified
     */
    public class SettingsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            doDisconnect();
            doConnect();
        }
    }

    public class NetworkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isConnected(context)) {
                Log.d(TAG, "Network is disconnected !");
                doDisconnect();
            } else {
                Log.d(TAG, "Network is connected !");
                if (!client.isConnected()) {
                    doConnect();
                }
            }
        }
    }
}

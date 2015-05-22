package com.paul_resume.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.paul_resume.smarthome.services.MqttService;

/**
 * Created by paul on 20.05.2015.
 */
public class AppSettings {

    public static final String SETTINGS_BROKER_URL = "broker_url",
            SETTINGS_USERNAME = "username",
            SETTINGS_PASSWORD = "password",
            SETTINGS_PORT = "port",
            SETTINGS_TOPIC = "topic";

    SharedPreferences preferences = null;
    SharedPreferences.Editor editor = null;
    Context context;

    public AppSettings(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("com.paul_resume.smarthome",
                Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public String getBroker() {
        return preferences.getString(SETTINGS_BROKER_URL, "");
    }

    public void setBroker(String url) {
        editor.putString(SETTINGS_BROKER_URL, url);
    }

    public String getUsername() {
        return preferences.getString(SETTINGS_USERNAME, "");
    }

    public void setUsername(String username) {
        editor.putString(SETTINGS_USERNAME, username);
    }

    public String getPassword() {
        return preferences.getString(SETTINGS_PASSWORD, "");
    }

    public void setPassword(String password) {
        editor.putString(SETTINGS_PASSWORD, password);
    }

    public int getPort() {
        return preferences.getInt(SETTINGS_PORT, 1883);
    }

    public void setPort(int port) {
        editor.putInt(SETTINGS_PORT, port);
    }

    public String getTopic() {
        return preferences.getString(SETTINGS_TOPIC, "");
    }

    public void setTopic(String topic) {
        editor.putString(SETTINGS_TOPIC, topic);
    }

    public boolean commit() {
        if (editor.commit()) {
            // broadcast message
            Intent intent = new Intent(MqttService.ACTION_SETTINGS_CHANGE);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            return true;
        }
        return false;
    }

}

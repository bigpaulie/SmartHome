package com.paul_resume.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.paul_resume.smarthome.services.MqttService;

/**
 * Application preferences
 * @author Paul Purcel. <bigpaulie25ro@yahoo.com>
 * TODO: add a spinner for weather update period
 * TODO: add alets and indoor temperature topics
 */
public class AppSettings {

    /**
     * Declare constants
     */
    public static final String SETTINGS_BROKER_URL = "broker_url",
            SETTINGS_USERNAME = "username",
            SETTINGS_PASSWORD = "password",
            SETTINGS_PORT = "port",
            SETTINGS_TOPIC = "topic";

    SharedPreferences preferences = null;
    SharedPreferences.Editor editor = null;
    Context context;

    /**
     * Constructor
     * @param context
     */
    public AppSettings(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("com.paul_resume.smarthome",
                Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Get the broker URL
     * @return String
     */
    public String getBroker() {
        return preferences.getString(SETTINGS_BROKER_URL, "tcp://test.mosquitto.org");
    }

    /**
     * Set the broker URL
     * @param url String
     */
    public void setBroker(String url) {
        editor.putString(SETTINGS_BROKER_URL, url);
    }

    /**
     * Get the username
     * @return String
     */
    public String getUsername() {
        return preferences.getString(SETTINGS_USERNAME, "");
    }

    /**
     * Set the username
     * @param username String
     */
    public void setUsername(String username) {
        editor.putString(SETTINGS_USERNAME, username);
    }

    /**
     * Get the password
     * @return String
     */
    public String getPassword() {
        return preferences.getString(SETTINGS_PASSWORD, "");
    }

    /**
     * Set the password
     * @param password String
     */
    public void setPassword(String password) {
        editor.putString(SETTINGS_PASSWORD, password);
    }

    /**
     * Get the mqtt port
     * @return int
     */
    public int getPort() {
        return preferences.getInt(SETTINGS_PORT, 1883);
    }

    /**
     * Set the mqtt port
     * @param port int
     */
    public void setPort(int port) {
        editor.putInt(SETTINGS_PORT, port);
    }

    /**
     * Get the topic
     * @return String
     */
    public String getTopic() {
        return preferences.getString(SETTINGS_TOPIC, "");
    }

    /**
     * Set the topic
     * @param topic String
     */
    public void setTopic(String topic) {
        editor.putString(SETTINGS_TOPIC, topic);
    }

    /**
     * Commit the settings
     * @return boolean
     */
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

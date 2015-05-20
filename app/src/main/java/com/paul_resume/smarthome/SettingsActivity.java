package com.paul_resume.smarthome;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity {

    AppSettings settings = null;
    EditText broker;
    EditText username;
    EditText password;
    EditText port;
    EditText topic;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // AppSettings
        settings = new AppSettings(this);

        // find all views
        broker = (EditText) findViewById(R.id.txtURL);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        port = (EditText) findViewById(R.id.txtPort);
        topic = (EditText) findViewById(R.id.txtTopic);
        save = (Button) findViewById(R.id.settingsSave);

        // populate fields
        broker.setText(settings.getBroker());

        if (!settings.getUsername().isEmpty()) {
            username.setText(settings.getUsername());
        }

        if (!settings.getPassword().isEmpty()) {
            password.setText(settings.getPassword());
        }

        if (settings.getPort() != 1883) {
            port.setText(settings.getPort());
        }

        if (!settings.getTopic().isEmpty()) {
            topic.setText(settings.getTopic());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    settings.setBroker(broker.getText().toString());
                    settings.setUsername(username.getText().toString());
                    settings.setPassword(password.getText().toString());
                    settings.setPort(Integer.parseInt(port.getText().toString()));
                    settings.setTopic(topic.getText().toString());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    settings.commit();
                }

                Toast.makeText(SettingsActivity.this, "AppSettings Saved !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.paul_resume.smarthome;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.paul_resume.smarthome.fragments.ControlFragment;
import com.paul_resume.smarthome.services.MqttService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int Orientation = getResources().getConfiguration().orientation;
        if (Orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("PAUL", "Landscape");
        } else if (Orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("PAUL", "Portrait");
        }

        // start service
        Intent intent = new Intent(this, MqttService.class);
        startService(intent);

        /**
         * Add the control fragment to the view
         * */
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ControlFragment controlFragment = new ControlFragment();
        ft.add(R.id.controlView, controlFragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

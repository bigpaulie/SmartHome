package com.paul_resume.smarthome.fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.paul_resume.smarthome.R;
import com.paul_resume.smarthome.adapters.Lights;
import com.paul_resume.smarthome.adapters.LightsListAdapter;
import com.paul_resume.smarthome.mqtt.MQTTPublisher;
import com.paul_resume.smarthome.mqtt.MQTTService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightsControlFragment extends Fragment {

    /**
     * Define lights constants
     */
    public static final String LIGHTS_LIGHT1 = "room:relay1",
            LIGHTS_LIGHT2 = "room:relay2",
            LIGHTS_LIGHT3 = "room:relay3";
    List<Lights> lights = new ArrayList<Lights>();

    public LightsControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Find the list view
         * Populate lights
         * Create adapter
         * Assign adapter
         * */
        ListView list = (ListView) getActivity().findViewById(R.id.lstLights);
        addItems();

        LightsListAdapter adapter = new LightsListAdapter(getActivity(), lights);
        list.setAdapter(adapter);

        /**
         * Register event listener
         * */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    ControlFragment fragment = new ControlFragment();
                    transaction.replace(R.id.controlView , fragment);
                    transaction.commit();
                }else if (position == 3) {
                    Toast.makeText(getActivity(), "Not yet implemented !", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MQTT", "Publishing");
//                    new MQTTPublisher(getActivity()).execute(lights.get(position).getCommand());
                    Intent intent = new Intent(MQTTService.ACTION_RECEIVE);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lights_control, container, false);
    }

    protected void addItems() {

        Lights light = new Lights();
        light.setIcon(R.drawable.back57);
        light.setName("Back");
        lights.add(light);

        Lights light3 = new Lights();
        light3.setIcon(R.drawable.lightbulb14);
        light3.setName("Ceiling");
        light3.setCommand(LIGHTS_LIGHT1);
        lights.add(light3);

        Lights light1 = new Lights();
        light1.setIcon(R.drawable.light63);
        light1.setName("Ceiling hidden");
        light1.setCommand(LIGHTS_LIGHT2);
        lights.add(light1);

        Lights light2 = new Lights();
        light2.setIcon(R.drawable.light14);
        light2.setName("Reading light");
        light2.setCommand(LIGHTS_LIGHT3);
        lights.add(light2);
    }
}

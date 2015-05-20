package com.paul_resume.smarthome.fragments;


import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.paul_resume.smarthome.MainActivity;
import com.paul_resume.smarthome.R;
import com.paul_resume.smarthome.SettingsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    public ControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button ctrlSettings = (Button)getActivity().findViewById(R.id.ctrlSettings);
        final Button ctrlButton1 = (Button)getActivity().findViewById(R.id.ctrlButton1);
        Button ctrlButton2 = (Button)getActivity().findViewById(R.id.ctrlButton2);
        Button ctrlButton3 = (Button)getActivity().findViewById(R.id.ctrlButton3);
        Button ctrlButton4 = (Button)getActivity().findViewById(R.id.ctrlButton4);
        Button ctrlButton5 = (Button)getActivity().findViewById(R.id.ctrlButton5);
        Button ctrlButton6 = (Button)getActivity().findViewById(R.id.ctrlButton6);
        Button ctrlButton7 = (Button)getActivity().findViewById(R.id.ctrlButton7);
        Button ctrlButton8 = (Button)getActivity().findViewById(R.id.ctrlButton8);
        Button ctrlButton9 = (Button)getActivity().findViewById(R.id.ctrlButton9);

        /**
         * Register onClick listener for all control buttons
         * for new buttons replace the control fragment with
         * icon selection list fragment
         * */

        ctrlButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Replace fragment with LightsControlFragment
                 * */
                LightsControlFragment fragment = new LightsControlFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.controlView , fragment , null);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ctrlButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        ctrlButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "Not yet implemented !" , Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Register onClick listener for control settings button
         * start new Activity
         * */
        ctrlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        return view;
    }


}

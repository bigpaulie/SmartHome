package com.paul_resume.smarthome;


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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


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

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                    Toast.makeText(getActivity() , "Received " + intent.getIntExtra("BUTTONICON" , 0) , Toast.LENGTH_SHORT).show();
                    Button ctrlButton1 = (Button)getActivity().findViewById(R.id.ctrlButton1);
                    ctrlButton1.setText("");
                    ctrlButton1.setBackgroundResource(intent.getIntExtra("BUTTONICON" , 0));
            }
        } , new IntentFilter(ButtonsIncoListFragment.ACTION_UPDATE_BUTTON));

        /**
         * Register onClick listener for all control buttons
         * for new buttons replace the control fragment with
         * icon selection list fragment
         * */

        ctrlButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctrlButton1.getText().toString().contentEquals("+")){
                    ButtonsIncoListFragment fragment = new ButtonsIncoListFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.controlView , fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    MainActivity.BUTTON_ID = "ctrlButton1";
                }
            }
        });

        ctrlButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ctrlButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

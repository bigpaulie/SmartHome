package com.paul_resume.smarthome.fragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.paul_resume.smarthome.MainActivity;
import com.paul_resume.smarthome.R;
import com.paul_resume.smarthome.adapters.Buttons;
import com.paul_resume.smarthome.adapters.ButtonsListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsIncoListFragment extends Fragment {

    ButtonsListAdapter adapter;
    List<Buttons> buttons = new ArrayList<Buttons>();

    public static final String ACTION_UPDATE_BUTTON = "ACTIONUPDATEBUTTON";

    public ButtonsIncoListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // load the icons
        loadIcons();

        /**
         * Find listview assign adapter
         */
        ListView list = (ListView) getActivity().findViewById(R.id.lstButtons);
        adapter = new ButtonsListAdapter(getActivity() , buttons);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ChoiceFragment fragment = new ChoiceFragment();
                transaction.replace(R.id.controlView, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    protected void loadIcons(){
        buttons.clear();

        Buttons buttonSettings = new Buttons();
        buttonSettings.setIcon(R.drawable.settings48);
        buttonSettings.setName("AppSettings icon");
        buttons.add(buttonSettings);

        Buttons buttonWallSocket = new Buttons();
        buttonWallSocket.setIcon(R.drawable.electric43);
        buttonWallSocket.setName("Wallsocket icon");
        buttons.add(buttonWallSocket);

        Buttons buttonPlug = new Buttons();
        buttonPlug.setIcon(R.drawable.basic13);
        buttonPlug.setName("Plug icon");
        buttons.add(buttonPlug);

        Buttons buttonPlug2 = new Buttons();
        buttonPlug2.setIcon(R.drawable.energy7);
        buttonPlug2.setName("Plug icon");
        buttons.add(buttonPlug2);

        Buttons buttonPlans = new Buttons();
        buttonPlans.setIcon(R.drawable.building113);
        buttonPlans.setName("Plans icon");
        buttons.add(buttonPlans);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttons_inco_list, container, false);
    }


}

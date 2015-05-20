package com.paul_resume.smarthome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paul_resume.smarthome.R;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 20.05.2015.
 */
public class LightsListAdapter extends BaseAdapter {

    Context context;
    List<Lights> lights = new ArrayList<Lights>();

    public LightsListAdapter(Context context , List<Lights> lights) {
        this.context = context;
        this.lights = lights;
    }

    @Override
    public int getCount() {
        return lights.size();
    }

    @Override
    public Object getItem(int position) {
        return lights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.buttons_icon_listitem , parent , false);

        // get views
        ImageView icon = (ImageView) view.findViewById(R.id.selectIcon);
        TextView text = (TextView) view.findViewById(R.id.selectIconText);

        icon.setImageResource(lights.get(position).getIcon());
        text.setText(lights.get(position).getName());

        return view;
    }
}

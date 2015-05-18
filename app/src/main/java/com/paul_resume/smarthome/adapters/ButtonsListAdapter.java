package com.paul_resume.smarthome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paul_resume.smarthome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 18.05.2015.
 */
public class ButtonsListAdapter extends BaseAdapter {

    Context context;
    List<Buttons> buttons = new ArrayList<Buttons>();

    public ButtonsListAdapter(Context context , List<Buttons> buttons){
        this.context = context;
        this.buttons = buttons;
    }

    @Override
    public int getCount() {
        return buttons.size();
    }

    @Override
    public Object getItem(int position) {
        return buttons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.buttons_icon_listitem , parent , false);

        ImageView icon = (ImageView)view.findViewById(R.id.selectIcon);
        TextView text = (TextView)view.findViewById(R.id.selectIconText);

        icon.setImageResource(buttons.get(position).getIcon());
        text.setText(buttons.get(position).getName());

        return view;
    }
}

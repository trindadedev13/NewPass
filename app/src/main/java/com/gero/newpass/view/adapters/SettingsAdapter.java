package com.gero.newpass.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gero.newpass.R;
import com.gero.newpass.model.SettingsItem;

import java.util.List;

public class SettingsAdapter extends BaseAdapter {

    private Context context;
    private String[] settingsList;
    private int[] iconList;
    private LayoutInflater inflater;

    public SettingsAdapter(Context context, String[] settingsList, int[] iconList) {
        this.context = context;
        this.settingsList = settingsList;
        this.iconList = iconList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return settingsList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.textView);
        ImageView settingsImg = (ImageView) convertView.findViewById(R.id.ImageIconSX);
        txtView.setText(settingsList[position]);
        settingsImg.setImageResource(iconList[position]);
        return convertView;
    }
}

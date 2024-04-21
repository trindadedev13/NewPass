package com.gero.newpass.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gero.newpass.R;
import com.gero.newpass.model.SettingData;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsAdapter extends ArrayAdapter<SettingData> {

    private final Context mContext;
    private final int mResource;

    public SettingsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SettingData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView txtName = convertView.findViewById(R.id.txtName);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchView = convertView.findViewById(R.id.switch1);
        ImageView arrowImage = convertView.findViewById(R.id.arrow);

        imageView.setImageResource(Objects.requireNonNull(getItem(position)).getImage());
        txtName.setText(Objects.requireNonNull(getItem(position)).getName());

        // Show or hide the switch based on the value of showSwitch
        if (Objects.requireNonNull(getItem(position)).getSwitchPresence()) {
            switchView.setVisibility(View.VISIBLE);
        } else {
            switchView.setVisibility(View.GONE);
        }

        // Show or hide the switch based on the value of showSwitch
        if (Objects.requireNonNull(getItem(position)).getImagePresence()) {
            arrowImage.setVisibility(View.VISIBLE);
        } else {
            arrowImage.setVisibility(View.GONE);
        }

        return convertView;
    }
}

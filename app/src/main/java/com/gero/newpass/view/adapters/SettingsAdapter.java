package com.gero.newpass.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;
import com.gero.newpass.model.SettingData;
import com.gero.newpass.utilities.VibrationHelper;
import com.gero.newpass.view.activities.MainViewActivity;
import com.gero.newpass.view.fragments.SettingsFragment;

import java.util.ArrayList;

public class SettingsAdapter extends ArrayAdapter<SettingData> {

    private final Context mContext;
    private final int mResource;
    private Boolean isDarkModeSet;
    private final Activity mActivity;

    // Constructor
    public SettingsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SettingData> objects, Activity activity) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mActivity = activity;
    }

    // View holder class
    private static class ViewHolder {
        ImageView imageView;
        TextView txtName;
        Switch switchView;
        ImageView arrowImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image);
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.switchView = convertView.findViewById(R.id.switch1);
            holder.arrowImage = convertView.findViewById(R.id.arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SettingData setting = getItem(position);
        if (setting != null) {
            holder.imageView.setImageResource(setting.getImage());
            holder.txtName.setText(setting.getName());

            if (setting.getSwitchPresence()) {
                holder.switchView.setVisibility(View.VISIBLE);
                holder.switchView.setChecked(SharedPreferencesHelper.isDarkModeSet(mContext));

                holder.switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (buttonView.isPressed() && buttonView.isShown()) {

                        VibrationHelper.vibrate(mContext, mContext.getResources().getInteger(R.integer.vibration_duration1));

                        isDarkModeSet = SharedPreferencesHelper.isDarkModeSet(mContext);

                        if (isChecked && !isDarkModeSet) {
                            SharedPreferencesHelper.setAndEditSharedPrefForDarkMode(mContext);
                        } else if (!isChecked && isDarkModeSet) {
                            SharedPreferencesHelper.setAndEditSharedPrefForLightMode(mContext);
                        }
                        if (mActivity instanceof MainViewActivity) {
                            SharedPreferencesHelper.updateNavigationBarColor(isChecked, mActivity);
                        }
                    }
                });
            } else {
                holder.switchView.setVisibility(View.GONE);
            }

            if (setting.getImagePresence()) {
                holder.arrowImage.setVisibility(View.VISIBLE);
            } else {
                holder.arrowImage.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}

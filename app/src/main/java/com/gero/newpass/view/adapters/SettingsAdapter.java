package com.gero.newpass.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
    private final int DARK_THEME_SWITCH = 1;
    private final int SCREEN_LOCK_SWITCH = 2;

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
        ImageButton switchView;
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

                //int imageResource = (SharedPreferencesHelper.isDarkModeSet(mContext)) ? R.drawable.btn_yes : R.drawable.btn_no;
                //holder.switchView.setImageDrawable(ContextCompat.getDrawable(mContext, imageResource));

                int switchID = setting.getSwitchID();
                int imageResource;

                if (switchID == DARK_THEME_SWITCH) {
                    imageResource = (SharedPreferencesHelper.isDarkModeSet(mContext)) ? R.drawable.btn_yes : R.drawable.btn_no;

                } else if (switchID == SCREEN_LOCK_SWITCH) {
                    imageResource = (SharedPreferencesHelper.isScreenLockEnabled(mContext)) ? R.drawable.btn_yes : R.drawable.btn_no;
                    //Log.i("switches", String.valueOf(imageResource));
                } else {
                    imageResource = R.drawable.btn_yes; // Imposta un valore predefinito nel caso in cui l'ID dello switch non sia valido
                }

                holder.switchView.setImageDrawable(ContextCompat.getDrawable(mContext, imageResource));

                holder.switchView.setOnClickListener(v -> {
                    VibrationHelper.vibrate(v, VibrationHelper.VibrationType.Weak);

                    switch (switchID) {

                        case DARK_THEME_SWITCH:
                            toggleDarkMode();
                            break;

                        case SCREEN_LOCK_SWITCH:
                            toggleScreenLock(holder);

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

    private void toggleDarkMode() {
        boolean isDarkModeSet = SharedPreferencesHelper.isDarkModeSet(mContext);
        if (isDarkModeSet) {
            SharedPreferencesHelper.setAndEditSharedPrefForLightMode(mContext);
        } else {
            SharedPreferencesHelper.setAndEditSharedPrefForDarkMode(mContext);
        }
        if (mActivity instanceof MainViewActivity) {
            SharedPreferencesHelper.updateNavigationBarColor(isDarkModeSet, mActivity);
        }
    }

    private void toggleScreenLock(ViewHolder holder) {

        SharedPreferencesHelper.setUseScreenLockToUnlock(mContext);
        int imageResource = (SharedPreferencesHelper.isScreenLockEnabled(mContext)) ? R.drawable.btn_yes : R.drawable.btn_no;
        holder.switchView.setImageDrawable(ContextCompat.getDrawable(mContext, imageResource));
    }
}

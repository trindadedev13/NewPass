package com.gero.newpass.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gero.newpass.R;
import com.gero.newpass.model.UserData;
import com.gero.newpass.utilities.VibrationHelper;
import com.gero.newpass.view.fragments.UpdatePasswordFragment;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final List<UserData> userDataList;
    private final Activity activity;

    public CustomAdapter(Activity activity, Context context, List<UserData> userDataList) {
        this.activity = activity;
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Populate every row of the recyclerView in the main activity
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        UserData userData = userDataList.get(position);

        String name = userData.getName();
        String email = userData.getEmail();

        String tw;
        if (name.length() > 2) {
            tw = name.substring(0, 2);
        } else {
            tw = name;
        }

        holder.row_tw_txt.setText(tw);
        holder.row_name_txt.setText(name);
        holder.row_email_txt.setText(email);

        holder.mainLayout.setOnClickListener(view -> {
            VibrationHelper.vibrate(view, VibrationHelper.VibrationType.Weak);

            UpdatePasswordFragment updatePasswordFragment = new UpdatePasswordFragment();
            Bundle args = new Bundle();
            args.putString("entry", userData.getId());
            args.putString("name", userData.getName());
            args.putString("email", userData.getEmail());
            args.putString("password", userData.getPassword());
            updatePasswordFragment.setArguments(args);

            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                    .replace(R.id.fragment_container, updatePasswordFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }


    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView row_name_txt, row_email_txt, row_tw_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            row_name_txt = itemView.findViewById(R.id.row_name_txt);
            row_tw_txt = itemView.findViewById(R.id.row_tw_txt);
            row_email_txt = itemView.findViewById(R.id.row_email_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

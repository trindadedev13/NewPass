package com.gero.newpass.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gero.newpass.model.UserData;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.view.activities.AddActivity;
import com.gero.newpass.view.activities.GeneratePasswordActivity;
import com.gero.newpass.view.activities.SettingsActivity;
import com.gero.newpass.view.adapters.CustomAdapter;
import com.gero.newpass.model.database.DatabaseHelper;
import com.gero.newpass.model.database.DatabaseServiceLocator;
import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView noData, count;
    private DatabaseHelper myDB;
    private ArrayList<UserData> userDataList;
    private ImageView empty_imageview;
    private RecyclerView recyclerView;
    private ImageButton buttonGenerate, buttonAdd, buttonSettings;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        initViews(binding);

        userDataList = new ArrayList<>();

        DatabaseServiceLocator.init(getApplicationContext());
        myDB = DatabaseServiceLocator.getDatabaseHelper();
        storeDataInArrays();

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, this, userDataList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        count.setText("["+ customAdapter.getItemCount() +"]");

        buttonGenerate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GeneratePasswordActivity.class);
            startActivity(intent);
        });

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays() {

        Cursor cursor = myDB.readAllData();

        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility((View.VISIBLE));
            noData.setVisibility((View.VISIBLE));
        } else {

            while (cursor.moveToNext()) {
                UserData userData = new UserData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                userDataList.add(userData);
            }

            empty_imageview.setVisibility((View.INVISIBLE));
            noData.setVisibility((View.INVISIBLE));
        }
    }

    private void initViews(ActivityMainBinding binding) {
        recyclerView = binding.recyclerView;
        buttonGenerate = binding.buttonGenerate;
        buttonAdd = binding.buttonAdd;
        buttonSettings = binding.buttonSettings;
        count = binding.textViewCount;
        empty_imageview = binding.emptyImageview;
        noData = binding.noData;
    }
}
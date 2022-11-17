package com.example.funpark.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.funpark.R;
import com.example.funpark.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        //setTitle(R.string.title_activity_settings);

    getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();


    }


}
package com.example.funpark.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.funpark.R;
import com.example.funpark.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "SharedPrefs";
    public static final String KEY_PREF_DARKMODE_SWITCH = "darkmode_switch";
    public static final String KEY_PREF_LANGUAGE = "list_language";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        //setTitle(R.string.title_activity_settings);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

    }

}
package com.example.funpark.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;

import com.example.funpark.R;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "SharedPrefs";
    public static final String KEY_PREF_DARKMODE_SWITCH = "darkmode_switch";
    public static final String KEY_PREF_LANGUAGE = "list_language";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();
        setActionBarTitle(getString(R.string.title_activity_settings));
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

    }
    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }


}
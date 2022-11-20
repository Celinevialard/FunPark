package com.example.funpark.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.funpark.R;
import com.example.funpark.util.PreferenceHelper;

/**
 * Activiter de base sans menu de navigation pour le coté visiteur
 */
public class BaseCustomerActivity extends AppCompatActivity {

    protected FrameLayout frameLayout;

    // valeur pour connaitre le focus
    protected static int tabulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Récupération et check des préférence utilisateur modifiable dans les settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        PreferenceHelper.checkPreferences(this);

        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Mise à jour des préférences utilisateur dans le cas d'un retour sur l'activité
        PreferenceHelper.checkPreferences(this);
    }
}
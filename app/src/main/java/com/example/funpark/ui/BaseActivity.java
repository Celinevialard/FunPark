package com.example.funpark.ui;

import static androidx.appcompat.app.AppCompatDelegate.*;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.example.funpark.R;
import com.example.funpark.ui.ticket.TicketsActivity;
import com.example.funpark.ui.visitor.VisitorsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout frameLayout;

    protected DrawerLayout drawerLayout;

    protected NavigationView navigationView;

    // valeur pour connaitre le focus
    protected static int tabulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences,false);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        BaseActivity.tabulation = 0;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == BaseActivity.tabulation) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseActivity.tabulation = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);

        if (id == R.id.nav_tickets) {
            intent = new Intent(this, TicketsActivity.class);
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        } else if (id == R.id.nav_visitor) {
            intent = new Intent(this, VisitorsActivity.class);
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkPreferences();

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public void checkPreferences(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkMode = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DARKMODE_SWITCH, false);
        String language = sharedPref.getString(SettingsActivity.KEY_PREF_LANGUAGE, "-1");

        setDefaultNightMode(darkMode ? MODE_NIGHT_YES : MODE_NIGHT_NO);
        updateResources(this, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResources(Context context, String language) {
        Toast.makeText(this,language,Toast.LENGTH_SHORT).show();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

}
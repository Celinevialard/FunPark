package com.example.funpark.util;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import androidx.preference.PreferenceManager;

import com.example.funpark.ui.SettingsActivity;

import java.util.Locale;

/**
 * Helper qui permet de géré les préférences
 */
public class PreferenceHelper {

    public static void checkPreferences(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean darkMode = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DARKMODE_SWITCH, false);
        String language = sharedPref.getString(SettingsActivity.KEY_PREF_LANGUAGE, "-1");

        setDefaultNightMode(darkMode ? MODE_NIGHT_YES : MODE_NIGHT_NO);
        updateResources(context, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    public static String getLanguage(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(SettingsActivity.KEY_PREF_LANGUAGE, "-1");
    }
}

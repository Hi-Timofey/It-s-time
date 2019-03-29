package com.hitim.android.itstime;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static SharedPreferences itsSettings;
    public static final String APP_PREFERENCES = "its_settings";
    public static final String APP_FIRST_OPEN = "first_open";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}

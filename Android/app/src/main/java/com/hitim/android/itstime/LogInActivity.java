package com.hitim.android.itstime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }


    public void onLogIn(View view) {
        Intent i = new Intent(LogInActivity.this,SphereActivity.class);
        startActivity(i);
    }
}

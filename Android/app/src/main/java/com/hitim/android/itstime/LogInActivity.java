package com.hitim.android.itstime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class LogInActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText edLogin, edPass;
    private ImageButton regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getAlltems();
        regButton.setOnTouchListener(this);
    }

    private void getAlltems() {
        edLogin = findViewById(R.id.edit_log);
        edPass = findViewById(R.id.edit_pass);
        regButton = findViewById(R.id.register_btn);
    }


    public void onLogIn(View view) {
        Intent i = new Intent(LogInActivity.this,SphereActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            
        }
        return false;
    }

}

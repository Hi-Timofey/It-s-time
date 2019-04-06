package com.hitim.android.itstime;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mailLayout, passLayout;
    private TextInputEditText edMail, edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*mailLayout = findViewById();
        passLayout = findViewById();
        edMail = findViewById();
        edPass = findViewById();*/

    }
    /*
    public void onRegIn(View view) {
    }*/
}

package com.hitim.android.itstime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText edLogin, edPass;
    private ImageButton regButton;
    //Firebase
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getAlltems();
        regButton.setOnTouchListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        startFireBase();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
    }

    //Обрабтка Firebase
    private void startFireBase() {
        FirebaseApp.initializeApp(LogInActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void getAlltems() {
        edLogin = findViewById(R.id.edit_log);
        edPass = findViewById(R.id.edit_pass);
        regButton = findViewById(R.id.register_btn);
    }


    public void onLogIn(View view) {
        signIn(edLogin.getText().toString(),edPass.getText().toString());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return false;
    }

    private void signIn(String email, String password){

    }

}

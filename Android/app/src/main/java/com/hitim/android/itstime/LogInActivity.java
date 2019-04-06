package com.hitim.android.itstime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity implements View.OnTouchListener {
    private TextInputLayout mailLayout, passLayout;
    private TextInputEditText edLogin, edPass;
    private ImageButton regButton;
    private ProgressDialog dialog;
    //Firebase
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getAlltems();
        dialog= new ProgressDialog(LogInActivity.this);
        dialog.setTitle(R.string.dialog_title);
        dialog.setMessage(getString(R.string.dialog_text));
        regButton.setOnTouchListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        startFireBase();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogInActivity.this, SphereActivity.class));
            finish();
        }
    }

    //Обрабтка Firebase
    private void startFireBase() {
        FirebaseApp.initializeApp(LogInActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void getAlltems() {
        regButton = findViewById(R.id.register_btn);
        mailLayout = findViewById(R.id.til_1);
        passLayout = findViewById(R.id.til_2);
        edLogin = findViewById(R.id.edit_log);
        edPass = findViewById(R.id.edit_pass);
    }

    public void onLogIn(View view) {
        signIn(edLogin.getText().toString(), edPass.getText().toString());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //TODO Нормальная анимация переключеия(снизу вверх)
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        }
        return false;
    }

    private void signIn(String email, String password) {
        if (!validate()) {
            return;
        }
        //TODO 3 раза ввел неправильно пароль - вывд диалога с попыткой помочь(восстановление)
        dialog.show();
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, SphereActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        String email = edLogin.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if (email.isEmpty()) {
            mailLayout.setError(getString(R.string.emptyString));
            valid = false;
        } else {
            if (mat.matches()) {
                mailLayout.setError("");
            } else {
                mailLayout.setError(getString(R.string.incorect_adress));
                valid = false;
            }
        }

        if (pass.isEmpty()) {
            passLayout.setError(getString(R.string.emptyString));
            valid = false;
        } else {
            passLayout.setError("");
        }

        return valid;
    }

}

package com.hitim.android.itstime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mailLayout, passLayout;
    private TextInputEditText edMail, edPass;
    private ProgressDialog dialogProgress;
    //Firebase
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mailLayout = findViewById(R.id.reg_til_1);
        passLayout = findViewById(R.id.reg_til_2);
        edMail = findViewById(R.id.reg_edit_log);
        edPass = findViewById(R.id.reg_edit_pass);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(RegisterActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        dialogProgress = new ProgressDialog(RegisterActivity.this);
        dialogProgress.setTitle(R.string.dialog_registration);
        dialogProgress.setMessage(getString(R.string.dialog_text));
    }

    private void createAccount(String email, String pass) {
        if (!validate()) {
            return;
        }
        dialogProgress.show();
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mFirebaseAuth.getCurrentUser().sendEmailVerification();
                    Toast.makeText(getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();
                    dialogProgress.hide();
                    startActivity(new Intent(RegisterActivity.this, SphereActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
                    dialogProgress.hide();
                }
            }
        });
        //TODO Сделать что бы можно было так же запомнить имя пользователя
    }

    private boolean validate() {
        boolean valid = true;
        String email = edMail.getText().toString();
        String pass = edPass.getText().toString();

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matMail = pattern.matcher(email);

        if (email.isEmpty()) {
            mailLayout.setError(getString(R.string.emptyString));
            valid = false;
        } else {
            if (matMail.matches()) {
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
            if (pass.length() >= 8) {
                passLayout.setError("");
            } else {
                passLayout.setError(getString(R.string.error_password));
                valid = false;
            }
        }
        return valid;
    }

    public void onRegInWithMail(View view) {
        createAccount(edMail.getText().toString(), edPass.getText().toString());
    }
}

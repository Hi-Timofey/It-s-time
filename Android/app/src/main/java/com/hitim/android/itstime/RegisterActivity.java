package com.hitim.android.itstime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mailLayout, passLayout, nameLayout;
    private TextInputEditText edMail, edPass, edName;
    private ProgressDialog dialog;
    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference databaseReference;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueApplicationStyle_LightTheme);
        setContentView(R.layout.activity_register);
        mailLayout = findViewById(R.id.reg_til_1);
        passLayout = findViewById(R.id.reg_til_2);
        nameLayout = findViewById(R.id.reg_til_0);
        edMail = findViewById(R.id.reg_edit_log);
        edPass = findViewById(R.id.reg_edit_pass);
        edName = findViewById(R.id.reg_edit_name);
        databaseReference = FirebaseDatabase.getInstance().getReference("DataUsers");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(RegisterActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setTitle(R.string.dialog_registration);
        dialog.setMessage(getString(R.string.dialog_text));
    }

    private void createAccount(String email, String pass, String name) {
        if (!validate(email, pass, name)) {
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //TODO: Доделать обработку имени
                FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                UserId = fire_user.getUid();
                User user = new User(email,name);
                databaseReference.child("Users").child(UserId).setValue(user);
                Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).sendEmailVerification();
                Toast.makeText(getApplicationContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();
                dialog.hide();
                startActivity(new Intent(RegisterActivity.this, SphereActivity.class));
                overridePendingTransition(R.anim.fade_out_3, R.anim.fade_in_5);
                finish();
            } else {
                String st = task.getException().getMessage();
                Toast.makeText(getApplicationContext(), getString(R.string.register_failed) + "\n"+ st, Toast.LENGTH_SHORT).show();
                Crashlytics.logException(task.getException());
                dialog.hide();
            }
        });

        //TODO Сделать что бы можно было так же запомнить имя пользователя
    }

    private boolean validate(String email, String pass, String name) {
        boolean valid = true;

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matMail = pattern.matcher(email);

        if (email.isEmpty()) {
            mailLayout.setError(getString(R.string.emptyString));
            valid = false;
        } else {
            if (matMail.matches()) {
                mailLayout.setError("");
            } else {
                mailLayout.setError(getString(R.string.incorrect_address));
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
        if (name.trim().isEmpty()) {
            nameLayout.setError(getString(R.string.emptyString));
            valid = false;
        } else {
            nameLayout.setError("");
        }
        return valid;
    }

    public void onRegInWithMail(View view) {
        dialog.show();
        createAccount(
                Objects.requireNonNull(edMail.getText()).toString(),
                Objects.requireNonNull(edPass.getText()).toString(),
                Objects.requireNonNull(edName.getText().toString())
        );
    }
}

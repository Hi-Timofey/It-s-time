package com.hitim.android.itstime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    private AlertDialog.Builder dialogVerify;
    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        regButton = findViewById(R.id.log_register_btn);
        mailLayout = findViewById(R.id.log_til_1);
        passLayout = findViewById(R.id.log_til_2);
        edLogin = findViewById(R.id.log_edit_log);
        edPass = findViewById(R.id.log_edit_pass);

        dialog= new ProgressDialog(LogInActivity.this);
        dialog.setTitle(R.string.dialog_signing_in);
        dialog.setMessage(getString(R.string.dialog_text));
        regButton.setOnTouchListener(this);

        dialogVerify =  new AlertDialog.Builder(this);
        dialogVerify.setTitle(getString(R.string.register_complete));
        dialogVerify.setMessage(getString(R.string.verify_plz));
        dialogVerify.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edPass.setText("");
            }
        });
    }

    //Обрабтка Firebase
    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(LogInActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i("INFORMATION","Пользователь получен");
            if(currentUser.isEmailVerified()){
                Log.i("INFORMATION","Пользователь верифицирован");
                Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInActivity.this, SphereActivity.class));
                finish();
            } else {
                Log.i("INFORMATION","Пользователь Не верифицирован");
                AlertDialog verify = dialogVerify.show();
                verify.hide();
            }
        }
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
        dialog.show();
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.hide();
                    if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, SphereActivity.class));
                        finish();
                    }else {
                        Log.i("INFORMATION","Пользователь Не верифицирован");
                        AlertDialog verify = dialogVerify.show();
                        verify.hide();
                    }
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
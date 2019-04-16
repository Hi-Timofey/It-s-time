package com.hitim.android.itstime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity implements View.OnTouchListener {

    private final int GOOGLE_INTENT = 4003;
    private TextInputLayout mailLayout, passLayout;
    private TextInputEditText edLogin, edPass;
    private ProgressDialog dialog;
    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ImageButton regButton = findViewById(R.id.log_register_btn);
        mailLayout = findViewById(R.id.log_til_1);
        passLayout = findViewById(R.id.log_til_2);
        edLogin = findViewById(R.id.log_edit_log);
        edPass = findViewById(R.id.log_edit_pass);

        dialog = new ProgressDialog(LogInActivity.this);
        dialog.setTitle(R.string.dialog_signing_in);
        dialog.setMessage(getString(R.string.dialog_text));
        regButton.setOnTouchListener(this);
    }

    //Обрабтка Firebase
    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(LogInActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogInActivity.this, SphereActivity.class));
            finish();
        }
    }

    public void onLogIn(View view) {
        switch (view.getId()) {
            case R.id.signInButtonGoogle:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_INTENT);
                break;
            case R.id.log_button_login:
                signIn(edLogin.getText().toString(), edPass.getText().toString());
                break;
        }
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
                mailLayout.setError(getString(R.string.incorrect_address));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_INTENT) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                Toast.makeText(this,getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        dialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.hide();
                            Toast.makeText(getApplicationContext(),getString(R.string.login_complete),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogInActivity.this,SphereActivity.class));
                        } else {
                            dialog.hide();
                            Toast.makeText(getApplicationContext(),getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

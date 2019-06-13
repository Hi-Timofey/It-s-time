package com.hitim.android.itstime;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity implements View.OnTouchListener, GoogleApiClient.OnConnectionFailedListener {

    private final int GOOGLE_INTENT = 4003;

    private TextInputLayout mailLayout, passLayout;
    private TextInputEditText edLogin, edPass;
    private ProgressDialog dialog;
    private AlertDialog alert;
    private ImageButton regButton;
    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.BlueApplicationStyle_LightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initViews();
        initGoogle();
        dialogsBuilder();
    }

    //Обрабтка Firebase
    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(LogInActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LogInActivity.this, SphereActivity.class));
            finish();
        }
        signInButton.setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, GOOGLE_INTENT);
        });
    }

    public void onLogIn(View view) {
        signIn(Objects.requireNonNull(edLogin.getText()).toString(), Objects.requireNonNull(edPass.getText()).toString());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        }
        return false;
    }

    private void signIn(String email, String password) {
        if (!validate()) {
            return;
        }
        dialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInActivity.this, SphereActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                Crashlytics.logException(task.getException());
                dialog.hide();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_INTENT) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResultGoogleAuth(result);
        }
    }

    private void handleResultGoogleAuth(GoogleSignInResult result) {
        dialog.show();
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            signInWithGoogle(account);
        } else {
            dialog.dismiss();
            Toast.makeText(this, result.getStatus().getStatusMessage(), Toast.LENGTH_LONG).show();
            Crashlytics.log(result.getStatus().getStatusMessage());
        }
    }

    private void signInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        addUserInfoInDataBase();
                        Toast.makeText(getApplicationContext(), getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, SphereActivity.class));
                        finish();

                    } else {
                        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                        Crashlytics.logException(task.getException());
                    }
                });
    }

    private void addUserInfoInDataBase() {
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        assert fire_user != null;
        User user = new User(fire_user.getEmail(), fire_user.getDisplayName());
        //Ветка на FirebaseDatabase
        String DATA_USERS = "DataUsers";
        FirebaseDatabase.getInstance().getReference(DATA_USERS)
                .child("Users")
                .child(fire_user.getUid())
                .setValue(user);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.isSuccess()) {
            Toast.makeText(this, getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
        }
    }

    //Обработки и инициализация ====================================

    @Override
    public void onBackPressed() {
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alert != null) {
            alert.dismiss();
            alert = null;
        }
    }

    private void initViews() {
        regButton = findViewById(R.id.log_register_btn);
        mailLayout = findViewById(R.id.log_til_1);
        passLayout = findViewById(R.id.log_til_2);
        edLogin = findViewById(R.id.log_edit_log);
        edPass = findViewById(R.id.log_edit_pass);
        signInButton = findViewById(R.id.signInButtonGoogle);
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        googleApiClient = new GoogleApiClient.Builder(LogInActivity.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private boolean validate() {
        boolean valid = true;
        String email = Objects.requireNonNull(edLogin.getText()).toString().trim();
        String pass = Objects.requireNonNull(edPass.getText()).toString().trim();
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

    @SuppressLint("ClickableViewAccessibility")
    private void dialogsBuilder() {
        dialog = new ProgressDialog(LogInActivity.this);
        dialog.setTitle(R.string.dialog_signing_in);
        dialog.setMessage(getString(R.string.dialog_text));
        regButton.setOnTouchListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
        builder.setTitle(getString(R.string.did_you_want_to_quit));
        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            dialog.dismiss();
            finishAffinity();
        });
        builder.setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss());
        alert = builder.create();
    }
}

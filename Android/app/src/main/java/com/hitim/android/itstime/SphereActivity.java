package com.hitim.android.itstime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class SphereActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private Toolbar toolbar;
    private SphereFragment sphereFragment = new SphereFragment();
    private FragmentManager fragmentManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere);
        FirebaseApp.initializeApp(SphereActivity.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFireUser = mFirebaseAuth.getCurrentUser();
        if (mFireUser == null) {
            mFirebaseAuth.signOut();
            //TODO: Обработать вылет юзера из сети
            Intent i = new Intent(SphereActivity.this, LogInActivity.class);
            startActivity(i);
            finish();
        }
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, sphereFragment, getString(R.string.sphere_fragment))
                .commit();
    }

    public void OnFabClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.create_sphere_fab:
                break;
            case R.id.create_task_fab:
                fragment = new CreateTaskFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.create_task_fragment))
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
        }
    }

    //Обработка ColorPicker фрагмента(не работает внутри фрагмента CreateTaskFragment
    @Override
    public void onColorSelected(int dialogId, int color) {
        CreateTaskFragment f = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.create_task_fragment));
        f.setTaskColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, sphereFragment, getString(R.string.sphere_fragment))
                .commit();

    }
}

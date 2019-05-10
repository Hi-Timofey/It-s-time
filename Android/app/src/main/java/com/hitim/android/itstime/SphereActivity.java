package com.hitim.android.itstime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SphereActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionMenu fabMenu;
    private Toolbar toolbar;
    private SphereFragment sphereFragment;
    private FragmentManager fragmentManager;
    private CardView healthCard, workCard, allCard, yourselfCard, routineCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        cardViewEnable();

        fabMenu = findViewById(R.id.floating_button_menu);
        com.github.clans.fab.FloatingActionButton createTask = findViewById(R.id.create_task_fab);
        com.github.clans.fab.FloatingActionButton createSphere = findViewById(R.id.create_sphere_fab);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sphereFragment = new SphereFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, sphereFragment, getString(R.string.sphere_fragment))
                .addToBackStack(null)
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

    private void cardViewEnable() {
        healthCard = findViewById(R.id.card_routine);
        workCard = findViewById(R.id.card_work);
        allCard = findViewById(R.id.card_all_tasks);

    }
}

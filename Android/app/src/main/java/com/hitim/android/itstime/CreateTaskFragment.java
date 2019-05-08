package com.hitim.android.itstime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

public class CreateTaskFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;
    private AppBarLayout l;
    private TextView deadText, reminderText, tagsText, sphereCardText;
    private ImageButton deadImg, remindImg, taskIcon;
    private CheckBox dateBox, remindBox;
    private CardView sphereCard;

    private String taskName, taskDecsription;

    public CreateTaskFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        reminderText = v.findViewById(R.id.userToDoRemindMeTextView);
        deadText = v.findViewById(R.id.deadline_text_view);
        dateBox = v.findViewById(R.id.date_check_box);
        remindBox = v.findViewById(R.id.reminder_check_box);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        sphereCard = v.findViewById(R.id.sphere_mini_card_view);
        sphereCardText = v.findViewById(R.id.mini_card_text);


        sphereCard.setOnClickListener(this);
        dateBox.setOnCheckedChangeListener(this);
        remindBox.setOnCheckedChangeListener(this);
        toolbar.setTitle(getString(R.string.create_task));
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container,fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit();
            }
        });
        l = getActivity().findViewById(R.id.tool_bar_layout);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        l.setElevation(0);
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        l.setElevation(2);
        floatingActionMenu.setVisibility(View.VISIBLE);
        floatingActionMenu.showMenuButton(true);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.date_check_box:
                break;
            case R.id.reminder_check_box:
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}

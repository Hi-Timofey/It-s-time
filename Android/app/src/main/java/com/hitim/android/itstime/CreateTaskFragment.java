package com.hitim.android.itstime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;

    public CreateTaskFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = (Toolbar) container.findViewById(R.id.tool_bar);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        floatingActionMenu.setVisibility(View.VISIBLE);
        floatingActionMenu.showMenuButton(true);

    }
}

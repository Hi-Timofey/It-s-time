package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private Task task;
    private Toolbar toolbar;
    private ConstraintLayout inCardView;
    private TextView taskNameTextView;

    public DetailsFragment() {
    }

    public DetailsFragment(Task task) {
        this.task = task;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        inCardView = v.findViewById(R.id.constraint_into_card_view);
        taskNameTextView = v.findViewById(R.id.details_task_name);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        toolbar.setElevation(0);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        initTaskDetails();

    }

    private void initTaskDetails() {
        try{
            taskNameTextView.setText(task.getName());
        } catch (NullPointerException e) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,fm.findFragmentByTag(getString(R.string.task_list_fragment))).commit();
        }
        switch (task.getSphere()) {
            case "Work":
                inCardView.setBackground(getResources().getDrawable(R.drawable.work_background));
                break;
            case "Routine":
                inCardView.setBackground(getResources().getDrawable(R.drawable.routine_background));
                break;
            case "Yourself":
                inCardView.setBackground(getResources().getDrawable(R.drawable.yourself_background));
                break;
            case "Health":
                inCardView.setBackground(getResources().getDrawable(R.drawable.health_background));
                break;
        }


    }
}

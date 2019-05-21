package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private String taskName;
    private Toolbar toolbar;
    private TextView nameTextView;
    private Task task;

    public DetailsFragment() {
        // Required empty public constructor
    }

    //TODO: КАВОООООООООООООО????

    public DetailsFragment(String taskName) {
        this.taskName = taskName;
        task = new AsyncWorker().getTaskByName(taskName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        nameTextView = v.findViewById(R.id.details_task_name);
        toolbar.setTitle("");
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
        nameTextView.setText(task.getName());
    }
}

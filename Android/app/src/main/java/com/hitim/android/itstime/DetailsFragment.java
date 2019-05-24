package com.hitim.android.itstime;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DialogInterface.OnClickListener {

    private Task task;
    private Toolbar toolbar;
    private FragmentManager fm;
    private AlertDialog sureToDelete;
    private TextView taskNameTextView;
    private AppBarLayout l;

    public DetailsFragment() {
    }

    public DetailsFragment(Task task) {
        this.task = task;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        taskNameTextView = v.findViewById(R.id.details_task_name);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        l = getActivity().findViewById(R.id.tool_bar_layout);
        l.setElevation(0);
        toolbar.setTitle("");
        toolbar.setElevation(100);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_Light);
        builder.setMessage(getString(R.string.sure_want_delete) + " " + task.getName() + "?");
        builder.setPositiveButton(R.string.yes, this);
        builder.setNegativeButton(R.string.no, this);
        sureToDelete = builder.create();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_task) {
            sureToDelete.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        fm = getFragmentManager();
        initTaskDetails();
    }

    private void initTaskDetails() {
        if (task != null) {
            taskNameTextView.setText(task.getName());
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            AsyncWorker worker = new AsyncWorker();
            worker.deleteTask(task);
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        } else {
            dialog.dismiss();
        }
    }
}

package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class TaskListFragment extends ListFragment {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;
    private ListView taskListview;
    private List<Task> taskArrayList;
    private int sphere;
    public TaskListFragment() {
    }

    public TaskListFragment(int sphere) {
        this.sphere = sphere;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskListview = v.findViewById(android.R.id.list);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
        toolbar.setTitle(sphere);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });
        fillListView();
    }

    public void fillListView() {
        AsyncWorker worker = new AsyncWorker();
        TaskAdapter taskAdapter;
        try {
            if (sphere == R.string.all_tasks) {
                taskArrayList = worker.getAllTasks();
                taskAdapter = new TaskAdapter(taskArrayList, getContext());
                taskListview.setAdapter(taskAdapter);
            } else {
                taskArrayList = worker.getAllTasksWithSphere(getString(sphere));
                taskAdapter = new TaskAdapter(taskArrayList, getContext());
                taskListview.setAdapter(taskAdapter);
            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Ooops:  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DetailsFragment f = new DetailsFragment(taskArrayList.get(position));
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, f, getString(R.string.details_fragment))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

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
    private FragmentManager fm;
    private String sphere = "";
    private View viewEmpty;
    private String[] sphereArr = {"All Task's", "Work", "Health", "Routine", "Yourself"};

    public TaskListFragment() {
    }

    public TaskListFragment(int sphere) {
        switch (sphere) {
            case R.string.all_tasks:
                this.sphere = sphereArr[0];
                break;
            case R.string.work:
                this.sphere = sphereArr[1];
                break;
            case R.string.health:
                this.sphere = sphereArr[2];
                break;
            case R.string.routine:
                this.sphere = sphereArr[3];
                break;
            case R.string.yourself:
                this.sphere = sphereArr[4];
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getActivity().getSupportFragmentManager();
        if (sphere.equals("")) {
            fm.beginTransaction().replace(R.id.fragment_container, new SphereFragment()).commit();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskListview = v.findViewById(android.R.id.list);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        viewEmpty = getActivity().findViewById(R.id.empty_list_view);
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
        toolbar.setNavigationOnClickListener(v1 -> fm.beginTransaction()
                .replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit());
        fillListView();
    }

    public void fillListView() {
        AsyncWorker worker = new AsyncWorker();
        TaskAdapter taskAdapter;
        try {
            if (sphere.equals(sphereArr[0])) {

                taskArrayList = worker.getAllTasks();
                if (!taskArrayList.isEmpty()) {
                    taskAdapter = new TaskAdapter(taskArrayList, getContext());
                    taskListview.setAdapter(taskAdapter);
                } else taskListview.setEmptyView(viewEmpty);

            } else {

                taskArrayList = worker.getAllTasksWithSphere(sphere);
                if (!taskArrayList.isEmpty()) {
                    taskAdapter = new TaskAdapter(taskArrayList, getContext());
                    taskListview.setAdapter(taskAdapter);
                } else taskListview.setEmptyView(viewEmpty);

            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Ooops:  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DetailsFragment f = new DetailsFragment(taskArrayList.get(position));
        fm.beginTransaction()
                .replace(R.id.fragment_container, f, getString(R.string.details_fragment))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}

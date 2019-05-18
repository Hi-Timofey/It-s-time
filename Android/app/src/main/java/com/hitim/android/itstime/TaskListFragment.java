package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class TaskListFragment extends ListFragment {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;
    private ListView myListview;
    private List<Task> taskArrayList;
    private int sphere;

    public TaskListFragment(int sphere) {
        this.sphere = sphere;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);
        myListview = v.findViewById(android.R.id.list);
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
        fillListView();
        Toast.makeText(getContext(),sphere,Toast.LENGTH_SHORT).show();
    }

    public void fillListView() {
        AsyncWorker worker = new AsyncWorker();
        TaskAdapter taskAdapter;
        if (sphere == R.string.all_tasks){
            taskArrayList = worker.getAllTasks();
            taskAdapter = new TaskAdapter(taskArrayList, getContext());
            myListview.setAdapter(taskAdapter);
        } else {
            taskArrayList = worker.getAllTasksWithSphere(getString(sphere));
            taskAdapter = new TaskAdapter(taskArrayList, getContext());
            myListview.setAdapter(taskAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView tv = v.findViewById(R.id.task_layout_name);
        String taskName = tv.getText().toString();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new DetailsFragment(taskName), getString(R.string.details_fragment))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

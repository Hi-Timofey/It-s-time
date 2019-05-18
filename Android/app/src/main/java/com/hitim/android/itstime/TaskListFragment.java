package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class TaskListFragment extends ListFragment {

    private FloatingActionMenu floatingActionMenu;
    private ListView myListview;
    private List<Task> taskArrayList;

    public TaskListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);
        myListview = v.findViewById(android.R.id.list);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
        fillListView();
    }

    public void fillListView() {
        AsyncWorker worker = new AsyncWorker();
        taskArrayList = worker.getAllTasks();
        TaskAdapter myAdapter = new TaskAdapter(taskArrayList, getContext());
        myListview.setAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView tv = v.findViewById(R.id.task_layout_name);
        String taskName = tv.getText().toString();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new DetailsFragment(taskName), getString(R.string.details_fragment))
                .addToBackStack()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

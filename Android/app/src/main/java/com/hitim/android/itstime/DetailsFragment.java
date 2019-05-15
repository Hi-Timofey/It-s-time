package com.hitim.android.itstime;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class DetailsFragment extends ListFragment {

    private FloatingActionMenu floatingActionMenu;
    private ListView myListview;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
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
        List<Task> taskArrayList = worker.getAllTasks();

        TaskAdapter myAdapter = new TaskAdapter(taskArrayList, getContext());
        myListview.setAdapter(myAdapter);
    }

}

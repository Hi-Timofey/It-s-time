package com.hitim.android.itstime;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
    public static List<Task> taskArrayList;
    private FragmentManager fm;
    private String sphere = "";
    private View viewEmpty;
    private TaskAdapter taskAdapter;

    public TaskListFragment() {
    }

    public TaskListFragment(String sphere, Context c) {
        this.sphere = sphere;
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
        setHasOptionsMenu(true);
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
        switch (sphere) {
            case "All task's":
                toolbar.setTitle(getString(R.string.all_tasks));
                break;
            case "Work":
                toolbar.setTitle(getString(R.string.work));
                break;
            case "Health":
                toolbar.setTitle(getString(R.string.health));
                break;
            case "Yourself":
                toolbar.setTitle(getString(R.string.yourself));
                break;
            case "Routine":
                toolbar.setTitle(getString(R.string.routine));
                break;
        }
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
        try {
            if (sphere.equals(getString(R.string.all_tasks_db))) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.task_list_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) mSearch.getActionView();
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.whiteColor));
        searchEditText.setHintTextColor(getResources().getColor(R.color.whiteColor));
        searchView.setQueryHint(getString(R.string.search_text));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}

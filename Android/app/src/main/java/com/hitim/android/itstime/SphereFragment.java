package com.hitim.android.itstime;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class SphereFragment extends Fragment implements ItemClickListener {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private final Activity activity = getActivity();
    private FloatingActionMenu floatingActionMenu;
    //For Recycler View
    private RecyclerView rv;
    private List<Sphere> spheres;
    private int nextSphere;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sphere, container, false);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        rv = v.findViewById(R.id.sphere_fragment_recycler_view);
        return v;
    }

    @SuppressLint("Assert")
    @Override
    public void onStart() {
        super.onStart();
        floatingActionMenu.setVisibility(View.VISIBLE);
        floatingActionMenu.showMenuButton(true);
        toolbar.setTitle(R.string.sphere);
        toolbar.setNavigationIcon(null);
        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
        toolbar.setFocusable(false);
        toolbar.setElevation(2);
        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //For recycler view
        LinearLayoutManager linLayManager = new LinearLayoutManager(getContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linLayManager);
        Sphere.initDefaultSpheres(getContext());
        spheres = Sphere.getDefaultSpheres();
        RVAdapter adapter = new RVAdapter(spheres, getContext());
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
            floatingActionMenu.hideMenuButton(true);
            floatingActionMenu.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.sphere_menu, menu);
        /*MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint(getString(R.string.search_text));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        //TODO: Вынести в отдельный фрагмент
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //floatingActionButton.show(false);
                //do the search here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(view -> {
            //floatingActionButton.hide(false);
        });*/
    }

    //Обработка нажатий кнопок на Toolbar'е
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        if (item.getItemId() == R.id.action_profile) {
            fragment = new UserProfileFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, getString(R.string.user_profile_fragment))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
                /*case R.id.action_settings:
                fragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.settings_fragment))
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;*/
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View view, int position) {
        final Sphere sphere = spheres.get(position);
        String name = sphere.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment(name, getContext()), getString(R.string.task_list_fragment))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}

package com.hitim.android.itstime;


import android.app.Activity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.github.clans.fab.FloatingActionMenu;

public class SphereFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private final Activity activity = getActivity();
    private FloatingActionMenu floatingActionMenu;
    private CardView allCard, workCard, healthCard, routineCard, youCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sphere, container, false);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        allCard = v.findViewById(R.id.card_all_tasks);
        allCard.setOnClickListener(this);
        healthCard = v.findViewById(R.id.card_health);
        healthCard.setOnClickListener(this);
        routineCard = v.findViewById(R.id.card_routine);
        routineCard.setOnClickListener(this);
        workCard = v.findViewById(R.id.card_work);
        workCard.setOnClickListener(this);
        youCard = v.findViewById(R.id.card_yourself);
        youCard.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
            floatingActionMenu.setVisibility(View.VISIBLE);
            floatingActionMenu.showMenuButton(true);
        toolbar.setTitle(R.string.sphere);
        toolbar.setFocusable(false);
        toolbar.setElevation(2);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (floatingActionMenu.isOpened()){
            floatingActionMenu.close(true);
            floatingActionMenu.hideMenuButton(true);
            floatingActionMenu.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sphere_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint(getString(R.string.search_text));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //TODO: делать нормальную анимацию для Floating Button когад идет поиск текста
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
        });
    }

    //Обработка нажатий кнопок на Toolbar'е
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.action_profile:
                fragment = new UserProfileFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.user_profile_fragment))
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.settings_fragment))
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_all_tasks:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new TaskListFragment(), getString(R.string.task_list_fragment))
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
            case R.id.card_health:
                Toast.makeText(getContext(),"Health",Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_work:
                Toast.makeText(getContext(),"Work",Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_routine:
                Toast.makeText(getContext(),"Routine",Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_yourself:
                Toast.makeText(getContext(),"Yourself",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(),"Ooops",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

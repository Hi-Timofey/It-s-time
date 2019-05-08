package com.hitim.android.itstime;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

public class SphereFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private final Activity activity = getActivity();


    public SphereFragment() {
    }

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
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.setTitle(R.string.sphere);
        toolbar.setFocusable(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //floatingActionButton.hide(false);
            }
        });
    }

    //Обработка нажатий кнопок на Toolbar'е
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
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

                break;
        }
    }
}

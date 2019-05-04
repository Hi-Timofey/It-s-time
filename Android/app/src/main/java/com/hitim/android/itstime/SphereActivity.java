package com.hitim.android.itstime;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SphereActivity extends AppCompatActivity {

    public com.github.clans.fab.FloatingActionMenu fabMenu;
    public Toolbar toolbar;
    private SphereFragment sphereFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.sphere);
        toolbar.setFocusable(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        fabMenu = findViewById(R.id.floating_button_menu);
        com.github.clans.fab.FloatingActionButton createTask = findViewById(R.id.create_task_fab);
        com.github.clans.fab.FloatingActionButton createSphere = findViewById(R.id.create_sphere_fab);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sphereFragment = new SphereFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, sphereFragment, getString(R.string.sphere_fragment))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sphere_menu, menu);
        searchManager(menu);
        return true;
    }

    //Обработка поиска через Toolbar
    public void searchManager(Menu menu) {
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint(getString(R.string.search_text));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
                        .commit();
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.settings_fragment))
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void OnFabClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.create_sphere_fab:

                break;
            case R.id.create_task_fab:
                fragment = new CreateTaskFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, getString(R.string.create_task_fragment))
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}

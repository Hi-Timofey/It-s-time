package com.hitim.android.itstime;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SphereActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere);

        //public MenuItem searchMenuItem;
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.sphere);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        fabMenu = findViewById(R.id.floating_button_menu);
        com.github.clans.fab.FloatingActionButton createTask = findViewById(R.id.create_task_fab);
        com.github.clans.fab.FloatingActionButton createSphere = findViewById(R.id.create_sphere_fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sphere_menu, menu);
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
        return true;
    }

    //Обработка нажатий кнопок на Toolbar'е
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_profile:
                i = new Intent(SphereActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                i = new Intent(SphereActivity.this, SettingsActivity.class);
                startActivity(i);
        }
        return true;
    }

    public void gotoTaskCreator(View view) {
        startActivity(new Intent(SphereActivity.this,TaskCreator.class));
        fabMenu.close(true);
    }

    public void gotoSphereCreator(View view) {
        startActivity(new Intent(SphereActivity.this, SphereCreator.class));
        fabMenu.close(true);
    }
}

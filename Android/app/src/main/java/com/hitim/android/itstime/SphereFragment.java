package com.hitim.android.itstime;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
=======
>>>>>>> parent of 332edc5... Фиксы фиксов
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SphereFragment extends Fragment{

<<<<<<< HEAD
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private final Activity activity = getActivity();

=======
>>>>>>> parent of 332edc5... Фиксы фиксов

    public SphereFragment() {
    }

<<<<<<< HEAD
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fragmentManager = getActivity().getSupportFragmentManager();
    }
=======
>>>>>>> parent of 332edc5... Фиксы фиксов

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sphere, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        
    }
<<<<<<< HEAD
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
=======
>>>>>>> parent of 332edc5... Фиксы фиксов
}

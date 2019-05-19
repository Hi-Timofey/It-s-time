package com.hitim.android.itstime;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.auth.AuthUI;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends Fragment {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;
    private FirebaseUser mUser;
    private TextView userNameText, userEmailText;
    private SelectableRoundedImageView userPictureImageView;

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        userNameText = v.findViewById(R.id.user_name_textview);
        userPictureImageView = v.findViewById(R.id.user_picture_image_view);
        userEmailText = v.findViewById(R.id.user_email_textview);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.profile);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });
        if (mUser != null) {
            String name = mUser.getDisplayName();
            String email = mUser.getEmail();
            if (name == null) {
                userNameText.setText(email);
                userNameText.setTextSize(18);
            } else {
                userNameText.setText(name);
                userEmailText.setText(email);
            }
            try {
                String photoUrl = mUser.getPhotoUrl().toString();
                Picasso.with(getContext())
                        .load(photoUrl)
                        .fit().centerCrop()
                        .placeholder(R.drawable.user_profile_image_placeholder)
                        .error(R.drawable.ic_account)
                        .into(userPictureImageView);
            } catch (Exception e) {
                e.printStackTrace();
                Picasso.with(getContext())
                        .load(R.drawable.ic_account)
                        .fit().centerCrop()
                        .into(userPictureImageView);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),LogInActivity.class));
                getActivity().finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}

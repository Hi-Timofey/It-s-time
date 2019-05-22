package com.hitim.android.itstime;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends Fragment {

    private FloatingActionMenu floatingActionMenu;
    private Toolbar toolbar;
    private FirebaseUser mUser;
    private TextView userNameText, userEmailText;
    private SelectableRoundedImageView userPictureImageView;
    private DatabaseReference databaseReference;
    private String name, email, uid;
    private ProgressDialog dialog;

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("DataUsers").child("Users").child(mUser.getUid());

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
        dialog = new ProgressDialog(getContext(),R.style.AlertDialogStyle_Light);
        dialog.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
        dialog.setMessage(getString(R.string.fui_progress_dialog_loading));
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
        dialog.show();
        initUserProfileInformation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LogInActivity.class));
            getActivity().finish();
        } else {
            super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initUserProfileInformation() {
        if (mUser != null) {
            getUserData();
            Picasso.with(getContext())
                    .load(mUser.getPhotoUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.user_profile_image_placeholder)
                    .error(R.drawable.ic_account)
                    .into(userPictureImageView);

        } else {
            startActivity(new Intent(getContext(), LogInActivity.class));
            getActivity().finish();
        }
    }

    private void getUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.getName();
                email = user.getEmail();
                userNameText.setText(name);
                userEmailText.setText(email);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw new DatabaseException(databaseError.getMessage());
            }
        });
    }
}

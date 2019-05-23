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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private FloatingActionMenu floatingActionMenu;
    private ConstraintLayout googleSyncLayout;
    private Toolbar toolbar;
    private FirebaseUser mUser;
    private TextView userNameText, userEmailText;
    private SelectableRoundedImageView userPictureImageView;
    private DatabaseReference databaseReference;
    private String name, email, uid;
    private ProgressDialog dialogOnStart, dialogOnGoogleSync;

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
        googleSyncLayout = v.findViewById(R.id.constraintLayout9);
        googleSyncLayout.setOnClickListener(this);
        userPictureImageView = v.findViewById(R.id.user_picture_image_view);
        userEmailText = v.findViewById(R.id.user_email_textview);
        toolbar = getActivity().findViewById(R.id.tool_bar);

        dialogOnStart = new ProgressDialog(getContext(),R.style.AlertDialogStyle_Light);
        dialogOnStart.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
        dialogOnStart.setMessage(getString(R.string.fui_progress_dialog_loading));

        dialogOnGoogleSync = new ProgressDialog(getContext(),R.style.AlertDialogStyle_Light);
        dialogOnGoogleSync.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
        dialogOnGoogleSync.setMessage(getString(R.string.synchronization));
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
        dialogOnStart.show();
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
                dialogOnStart.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw new DatabaseException(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.constraintLayout9) {
            dialogOnGoogleSync.show();
            AsyncWorker worker = new AsyncWorker();
            Exception result = worker.syncAllTasks();
            dialogOnGoogleSync.dismiss();
            if (result == null){
                 //Результат положительный
            } else {
                Toast.makeText(getContext(),getString(R.string.oops) + result.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}

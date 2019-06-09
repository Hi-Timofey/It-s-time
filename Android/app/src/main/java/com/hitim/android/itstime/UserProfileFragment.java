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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.textfield.TextInputEditText;
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
    private ConstraintLayout confirmEmailLayout, passwordResetLayout;
    private Toolbar toolbar;
    private FirebaseUser mUser;
    private TextView userNameText, userEmailText;
    private SelectableRoundedImageView userPictureImageView;
    private DatabaseReference databaseReference;
    private String name, email;
    private TextView emailVerified;
    private ProgressDialog dialogOnStart;

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        userNameText = v.findViewById(R.id.user_name_textview);

        confirmEmailLayout = v.findViewById(R.id.constraintLayout9);
        confirmEmailLayout.setOnClickListener(this);
        passwordResetLayout = v.findViewById(R.id.constraint_password_reset);
        passwordResetLayout.setOnClickListener(this::onClick);
        userPictureImageView = v.findViewById(R.id.user_picture_image_view);
        userEmailText = v.findViewById(R.id.user_email_textview);
        emailVerified = v.findViewById(R.id.textView3);
        toolbar = getActivity().findViewById(R.id.tool_bar);

        dialogOnStart = new ProgressDialog(getContext(), R.style.AlertDialogStyle_Light);
        dialogOnStart.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
        dialogOnStart.setMessage(getString(R.string.fui_progress_dialog_loading));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("DataUsers").child("Users").child(mUser.getUid());
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
        initUserProfileInformation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }



    private void initUserProfileInformation() {
        dialogOnStart.show();
        if (mUser != null) {
            getUserData();
            Picasso.with(getContext())
                    .load(mUser.getPhotoUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.user_profile_image_placeholder)
                    .error(R.drawable.ic_account)
                    .into(userPictureImageView);
            if(mUser.isEmailVerified()){
                emailVerified.setText(getString(R.string.confirmed));
            } else {
                emailVerified.setText(getString(R.string.confirm_your_email));
            }
        } else {
            startActivity(new Intent(getContext(), LogInActivity.class));
            getActivity().finish();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.constraintLayout9) {
            if (!mUser.isEmailVerified()) {
                mUser.sendEmailVerification();
                Toast.makeText(getContext(),getString(R.string.after_verified),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),getString(R.string.confirmed),Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == R.id.constraint_password_reset) {
            AlertDialog.Builder builderForText = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_Light)
                    .setCancelable(true);
            View viewTextEdit = LayoutInflater.from(getContext()).inflate(R.layout.edit_dialog_layout, null);
            TextInputEditText tiet = viewTextEdit.findViewById(R.id.edit_dialog_edit_text);
            viewTextEdit.findViewById(R.id.edit_dialog_edit_text);
            builderForText.setView(viewTextEdit);
            builderForText.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                String newPass = tiet.getText().toString();
                String tmp = newPass;
                if(!tmp.trim().equals("") && tmp.length() >= 8){
                    mUser.updatePassword(newPass);
                    Toast.makeText(getContext(),getString(R.string.password_changed),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),getString(R.string.incorrect_pass),Toast.LENGTH_LONG).show();
                }
            });
            builderForText.create().show();
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
}

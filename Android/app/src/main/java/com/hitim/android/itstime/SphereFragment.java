package com.hitim.android.itstime;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SphereFragment extends Fragment{


    public SphereFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sphere, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        
    }
}

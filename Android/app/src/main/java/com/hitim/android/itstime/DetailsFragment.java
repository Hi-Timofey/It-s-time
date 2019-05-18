package com.hitim.android.itstime;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private String taskName;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(String taskName){
        this.taskName = taskName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        return v;
    }

}

package com.pyramidions.bajugali.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyramidions.bajugali.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitAd extends Fragment {


    public SubmitAd() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_submit_ad, container, false);
        return view;
    }

}

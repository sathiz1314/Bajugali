package com.pyramidions.bajugali.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.SharedHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubAdvertisement extends Fragment {

    Context context;
    SharedHelper sharedHelper;
    private RecyclerView subAdv_recyclerview;

    public SubAdvertisement() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_advertisement, container, false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        subAdv_recyclerview = (RecyclerView) view.findViewById(R.id.subAdv_recyclerview);
        subAdv_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        subAdv_recyclerview.setLayoutManager(linearLayoutManager);
        return view;
    }

}

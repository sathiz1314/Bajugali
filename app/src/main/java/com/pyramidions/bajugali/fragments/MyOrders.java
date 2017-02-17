package com.pyramidions.bajugali.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pyramidions.bajugali.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment {

    private TextView toolbar_textview;
    public MyOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
       // toolbar.setLogo(null);
      //  toolbar_textview = (TextView) toolbar.findViewById(R.id.toolbar_textview);
      //  toolbar_textview.setText("My ORDERS");
      //  toolbar.setTitle("My ORDERS");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        return view;
    }

}

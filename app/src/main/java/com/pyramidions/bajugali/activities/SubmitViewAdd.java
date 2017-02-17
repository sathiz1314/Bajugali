package com.pyramidions.bajugali.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.adapters.AddAdapter;
import com.pyramidions.bajugali.dataModels.AddModel;
import com.pyramidions.bajugali.helpers.DividerItemDecoration;

import java.util.ArrayList;

public class SubmitViewAdd extends AppCompatActivity {

    private TextView toolbar_tv;
    private RecyclerView subViewAdv_recyclerview;
    private ArrayList<AddModel> addModelList = new ArrayList<AddModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_view_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("My Advertisement");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
            }
        });
        subViewAdv_recyclerview = (RecyclerView) findViewById(R.id.subViewAdv_recyclerview);
        subViewAdv_recyclerview.setHasFixedSize(true);
        subViewAdv_recyclerview.setLayoutManager(new LinearLayoutManager(this));
     /* *//*  addModelList.add(new AddModel(R.drawable.coverpic,"ToothPaste","Advertisement","To be improved"));
        addModelList.add(new AddModel(R.drawable.coverpic,"ToothPaste","Advertisement","To be improved"));
        AddAdapter addAdapter = new AddAdapter(this,addModelList);*//*
        subViewAdv_recyclerview.setAdapter(addAdapter);*/


    }
}

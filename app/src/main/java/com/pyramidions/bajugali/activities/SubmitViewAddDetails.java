package com.pyramidions.bajugali.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.SharedHelper;

public class SubmitViewAddDetails extends AppCompatActivity {

    private TextView toolbar_tv,ed_SubmitAd_Titledata,ed_SubmitAd_Categorydata,
            ed_SubmitAd_Descriptiondata,ed_SubmitAd_Statusdata,ed_SubmitAd_Pricedata,tv_AgreeStartDate,tv_AgreeEndDate,tv_SubmitAd_Cbdata;
    private ImageView iv_SubmitAd;
    private CheckBox SubmitAd_Checkboxdata;
    private SharedHelper sharedHelper;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_view_add_details);

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
        sharedHelper = new SharedHelper();
        ed_SubmitAd_Titledata = (TextView) findViewById(R.id.ed_SubmitAd_Titledata);
        ed_SubmitAd_Categorydata = (TextView) findViewById(R.id.ed_SubmitAd_Categorydata);
        ed_SubmitAd_Descriptiondata = (TextView) findViewById(R.id.ed_SubmitAd_Descriptiondata);
        ed_SubmitAd_Statusdata = (TextView) findViewById(R.id.ed_SubmitAd_Statusdata);
        ed_SubmitAd_Pricedata = (TextView) findViewById(R.id.ed_SubmitAd_Pricedata);
        tv_AgreeStartDate = (TextView) findViewById(R.id.tv_AgreeStartDate);
        tv_AgreeEndDate = (TextView) findViewById(R.id.tv_AgreeEndDate);
        tv_SubmitAd_Cbdata = (TextView) findViewById(R.id.tv_SubmitAd_Cbdata);
        iv_SubmitAd = (ImageView) findViewById(R.id.iv_SubmitAd);
        SubmitAd_Checkboxdata = (CheckBox) findViewById(R.id.SubmitAd_Checkboxdata);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_SubmitAd_Cbdata.setText(sharedHelper.getKey(this,"ContactNumber"));
        ed_SubmitAd_Titledata.setText(getIntent().getStringExtra("getAdtitle"));
        ed_SubmitAd_Categorydata.setText(getIntent().getStringExtra("getCategory"));
        ed_SubmitAd_Descriptiondata.setText(getIntent().getStringExtra("getDescription"));
        ed_SubmitAd_Statusdata.setText(getIntent().getStringExtra("getStatus"));
        ed_SubmitAd_Pricedata.setText(getIntent().getStringExtra("getPrice"));
        tv_AgreeStartDate.setText(getIntent().getStringExtra("getAdStartDate"));
        tv_AgreeEndDate.setText(getIntent().getStringExtra("getAdEndDate"));

        Glide.with(this).load(getIntent().getStringExtra("getAdimage")).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(iv_SubmitAd);
        SubmitAd_Checkboxdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SubmitAd_Checkboxdata.isChecked()){
                    tv_SubmitAd_Cbdata.setVisibility(View.VISIBLE);
                }else {
                    tv_SubmitAd_Cbdata.setVisibility(View.GONE);

                }
            }
        });

    }
}

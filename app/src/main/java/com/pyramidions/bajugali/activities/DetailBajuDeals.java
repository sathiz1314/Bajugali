package com.pyramidions.bajugali.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.SharedHelper;

import static com.pyramidions.bajugali.R.id.toolbar_tv;
import static com.pyramidions.bajugali.R.id.txt_normalprice;

/**
 * Created by User on 2/8/2017.
 */

public class DetailBajuDeals extends AppCompatActivity {
    private TextView toolbar_tv,DealId,DealName,NormalPrice,OfferPrice,Description,DealStartDate,DealEndDate,DealLocation,NoOfRedemption;
    private ImageView dealImage;
   // private CheckBox SubmitAd_Checkboxdata;
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailbajudeals);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
       // toolbar.setTitle("DEAL DETAILS");
      /*  toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("DEAL DETAILS");*/
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
            }
        });
        sharedHelper = new SharedHelper();
        //DealId = (TextView) findViewById(R.id.tv_DealId);
        DealName = (TextView) findViewById(R.id.tv_DealName);
        NormalPrice = (TextView) findViewById(R.id.tv_DealNormalPrice);
        OfferPrice = (TextView) findViewById(R.id.tv_DealOfferPrice);
        Description = (TextView) findViewById(R.id.tv_DealDescription);
        DealStartDate = (TextView) findViewById(R.id.tv_DealStartDate);
        DealEndDate = (TextView) findViewById(R.id.tv_DealEndDate);
        DealLocation = (TextView) findViewById(R.id.tv_DealLocation);
        dealImage = (ImageView) findViewById(R.id.dealImage);

       // NoOfRedemption = (CheckBox) findViewById(R.id.SubmitAd_Checkboxdata);
        //tv_SubmitAd_Cbdata.setText(sharedHelper.getKey(this,"ContactNumber"));

        try {

            //DealId.setText(getIntent().getStringExtra("getDealId"));
            DealName.setText(getIntent().getStringExtra("DealName"));
            NormalPrice.setText(getIntent().getStringExtra("NormalPrice"));
            OfferPrice.setText(getIntent().getStringExtra("OfferPrice"));
            Description.setText(getIntent().getStringExtra("Description"));
            DealStartDate.setText(getIntent().getStringExtra("DealStartDate"));
            DealEndDate.setText(getIntent().getStringExtra("DealEndDate"));
            DealLocation.setText(getIntent().getStringExtra("DealLocation"));
         //   NoOfRedemption.setText(getIntent().getStringExtra("getAdEndDate"));

           Glide.with(this).load(getIntent().getStringExtra("DealImage")).into(dealImage);
            NormalPrice.setPaintFlags(NormalPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

        catch (Exception e){
            e.printStackTrace();
        }



    }
}

package com.pyramidions.bajugali.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.SharedHelper;

public class SubmitRequestDetails extends AppCompatActivity {

    private SharedHelper sharedHelper;
    private TextView toolbar_tv,subject,ticket,submitrequestDescription,submit_date,assigned_to,resolution_comment,status,resolved_on;
    private Button submitrequestDetailsEdit,submitrequestDetailsSave;
    private CardView submitRequest_CardView,submitRequest_SpinnerCardView;
    private LinearLayout submitrequestLinear;
    private Spinner ed_SubmitRequestSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request_details);
        sharedHelper = new SharedHelper();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("MY REQUEST DETAILS");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        subject = (TextView) findViewById(R.id.subject);
        ticket = (TextView) findViewById(R.id.ticket);
        submitrequestDescription = (TextView) findViewById(R.id.submitrequestDescription);
        submit_date = (TextView) findViewById(R.id.submit_date);
        assigned_to = (TextView) findViewById(R.id.assigned_to);
        resolution_comment = (TextView) findViewById(R.id.resolution_comment);
        status = (TextView) findViewById(R.id.status);
        resolved_on = (TextView) findViewById(R.id.resolved_on);
        submitRequest_CardView = (CardView) findViewById(R.id.submitRequest_CardView);
        submitRequest_SpinnerCardView = (CardView) findViewById(R.id.submitRequest_SpinnerCardView);
        submitrequestDetailsEdit = (Button) findViewById(R.id.submitrequestDetailsEdit);
        submitrequestDetailsSave = (Button) findViewById(R.id.submitrequestDetailsSave);
        submitrequestLinear = (LinearLayout) findViewById(R.id.submitrequestLinear);
        ed_SubmitRequestSpinner = (Spinner) findViewById(R.id.ed_SubmitRequestSpinner);

        try{
           // subject.setText(getIntent().getStringExtra("getSubject"));
            subject.setText(getIntent().getStringExtra("getDescription"));
            ticket.setText(getIntent().getStringExtra("getTicketNum"));
          //  submitrequestDescription.setText(getIntent().getStringExtra("getDescription"));
            submitrequestDescription.setText(getIntent().getStringExtra("getResolutioncomment"));
            submit_date.setText(getIntent().getStringExtra("getSubmitdate"));
            assigned_to.setText(getIntent().getStringExtra("getAssignedto"));
            resolution_comment.setText(getIntent().getStringExtra("getResolutioncomment"));
            status.setText(getIntent().getStringExtra("getStatus"));
            resolved_on.setText(getIntent().getStringExtra("getResolvedOnDate"));
        }catch (Exception e){
            e.printStackTrace();
        }
        submitrequestDetailsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest_CardView.setVisibility(View.GONE);
                submitrequestDetailsEdit.setVisibility(View.GONE);
                submitrequestLinear.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight).duration(800).playOn(submitrequestLinear);
                YoYo.with(Techniques.SlideInRight).duration(800).playOn(submitrequestDetailsSave);
                String data[] = new String[]{"Reopen","Resolved"};
                ArrayAdapter arrayAdapter = new ArrayAdapter(SubmitRequestDetails.this,android.R.layout.simple_list_item_1,data);
                ed_SubmitRequestSpinner.setAdapter(arrayAdapter);

            }
        });
        submitrequestDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

package com.pyramidions.bajugali.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pyramidions.bajugali.R;

public class NoticeBoardDetails extends AppCompatActivity {

    private TextView NoticeDateCreate,NoticeDateExpiry,NoticeName,NoticeSummary,NoticeDescription,NoticeContactUserId,Noticecontactpersonname,Noticecontactpersonnumber,Noticecontactpersonemail;
    private TextView toolbar_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
      //  toolbar.setTitle("NoticeBoard Details");
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("Notice Details");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        NoticeName = (TextView) findViewById(R.id.NoticeName);
        NoticeDateCreate = (TextView) findViewById(R.id.NoticeDateCreate);
        NoticeDateExpiry = (TextView) findViewById(R.id.NoticeDateExpiry);
        NoticeSummary = (TextView) findViewById(R.id.NoticeSummary);
        NoticeDescription = (TextView) findViewById(R.id.NoticeDescription);
        NoticeContactUserId = (TextView) findViewById(R.id.NoticeContactUserId);
        Noticecontactpersonname = (TextView) findViewById(R.id.Noticecontactpersonname);
        Noticecontactpersonnumber = (TextView) findViewById(R.id.Noticecontactpersonnumber);
        Noticecontactpersonemail = (TextView) findViewById(R.id.Noticecontactpersonemail);

        NoticeName.setText(getIntent().getStringExtra("NoticeName"));
        NoticeSummary.setText(getIntent().getStringExtra("NoticeSummary"));
        NoticeDateCreate.setText(getIntent().getStringExtra("NoticeCreateDate"));
        NoticeDateExpiry.setText(getIntent().getStringExtra("NoticeExpiryDate"));
        NoticeDescription.setText(getIntent().getStringExtra("NoticeDescription"));
        NoticeContactUserId.setText(getIntent().getStringExtra("NoticeContactUserId"));
        Noticecontactpersonname.setText(getIntent().getStringExtra("Contactpersonname"));
       /* Noticecontactpersonnumber.setText(getIntent().getStringExtra("Contactpersonnumber"));
        Noticecontactpersonemail.setText(getIntent().getStringExtra("Contactpersonemail"));*/

    }
}

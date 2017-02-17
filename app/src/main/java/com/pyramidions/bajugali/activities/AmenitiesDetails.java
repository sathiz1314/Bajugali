package com.pyramidions.bajugali.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pyramidions.bajugali.R;

import org.w3c.dom.Text;

public class AmenitiesDetails extends AppCompatActivity {

    private TextView amenitiesName,amenitiesDescription,first_shift,second_shift,working_days,rules,availablefor_booking,price;
    private TextView toolbar_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
       // toolbar.setTitle("Amenities Details");
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("Amenities Details");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        amenitiesName = (TextView) findViewById(R.id.amenitiesName);
        amenitiesDescription = (TextView) findViewById(R.id.amenitiesDescription);
        first_shift = (TextView) findViewById(R.id.first_shift);
        second_shift = (TextView) findViewById(R.id.second_shift);
        working_days = (TextView) findViewById(R.id.working_days);
        rules = (TextView) findViewById(R.id.rules);
        availablefor_booking = (TextView) findViewById(R.id.availablefor_booking);
        price = (TextView) findViewById(R.id.price);
        amenitiesName.setText(getIntent().getStringExtra("Amenityname"));
        amenitiesDescription.setText(getIntent().getStringExtra("Description"));
        first_shift.setText(String.valueOf(getIntent().getStringExtra("Firstshift")));
        second_shift.setText(String.valueOf(getIntent().getStringExtra("Seconfdshift")));
        working_days.setText(getIntent().getStringExtra("WorkingDays"));
        rules.setText(getIntent().getStringExtra("Rulesandregulations"));
        price.setText(getIntent().getStringExtra("Price"));
        availablefor_booking.setText(getIntent().getStringExtra("booking"));


    }
}

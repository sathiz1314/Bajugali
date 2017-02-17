package com.pyramidions.bajugali.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pyramidions.bajugali.R;

/**
 * Created by Saran Charan on 14-09-2016.
 */
public class ContactusActivity extends AppCompatActivity {
    private Button backpg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        backpg = (Button)findViewById(R.id.back);
        backpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

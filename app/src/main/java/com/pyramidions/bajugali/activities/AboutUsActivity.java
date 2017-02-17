package com.pyramidions.bajugali.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pyramidions.bajugali.R;


public class AboutUsActivity extends AppCompatActivity {
    private Button backpg;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
       /* Typeface regular = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.otf");
        title = (TextView)findViewById(R.id.titleheader);
        title.setTypeface(regular);*/
        backpg = (Button)findViewById(R.id.back);
        backpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

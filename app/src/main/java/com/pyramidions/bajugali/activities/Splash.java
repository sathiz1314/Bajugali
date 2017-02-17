package com.pyramidions.bajugali.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pyramidions.bajugali.MainActivity;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.SharedHelper;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private static final String TAG = "TKNS";
    SharedHelper sharedHelper;
    String log = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedHelper = new SharedHelper();

        log = sharedHelper.getKey(Splash.this, "login_type");



            Timer RunSplash = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (log.equalsIgnoreCase("Logged")) {
                        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        Splash.this.finish();
                    }
                    else {
                        startActivity(new Intent(Splash.this,SignIn.class));
                        Splash.this.finish();
                    }
                }
            };
            RunSplash.schedule(timerTask,2000);
        }





}

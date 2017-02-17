package com.pyramidions.bajugali.fcm;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzd;
import com.pyramidions.bajugali.helpers.GlobalData;
import com.pyramidions.bajugali.helpers.SharedHelper;

/**
 * Created by User on 12/2/2016.
 */

public class MyFireBaseInstanceId extends FirebaseInstanceIdService {



    SharedHelper sharedHelper;
    public MyFireBaseInstanceId(){
        sharedHelper = new SharedHelper();
    }
    @Override
    public void onTokenRefresh() {
        String recent_Token = FirebaseInstanceId.getInstance().getToken();
        Log.v("Token",recent_Token);
        GlobalData.deviceToken = recent_Token;
        sharedHelper.putKey(MyFireBaseInstanceId.this,"DeviceId",recent_Token);

     //   Toast.makeText(this, ids+"", Toast.LENGTH_SHORT).show();


    }
}

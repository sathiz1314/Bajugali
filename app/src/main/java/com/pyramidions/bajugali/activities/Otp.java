package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.GlobalData;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Otp extends AppCompatActivity {

    private EditText et_Otp;
    private ProgressDialog progressDialog;
    SharedHelper sharedHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        et_Otp = (EditText) findViewById(R.id.et_Otp);
        et_Otp.setText(String.valueOf(sharedHelper.getKey(Otp.this,"OTP")));
      //  Toast.makeText(this, GlobalData.deviceToken+"", Toast.LENGTH_SHORT).show();
    }

    public void send(View view) {
        operation();
    }

    private void operation() {
        String url = "http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/users_otp_validation";
       // final String otp = et_Otp.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.Users_Otp_Validation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("Respo",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.optString("response");
                            if (res.equalsIgnoreCase("true")){
                                startActivity(new Intent(Otp.this,ChangePassword.class));
                                Otp.this.finish();
                            }
                            else if (res.equalsIgnoreCase("false")){
                                Toast.makeText(Otp.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                            }

                           // Toast.makeText(Otp.this,num+"",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(Otp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(Otp.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(Otp.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(Otp.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(Otp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(Otp.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("otp",sharedHelper.getKey(Otp.this,"OTP"));
                params.put("userid",sharedHelper.getKey(Otp.this,"USERID"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotOtp extends AppCompatActivity {

    private EditText et_Otp;
    private ProgressDialog progressDialog;
    SharedHelper sharedHelper;
    private Toolbar toolbar;
    private TextView toolbar_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("OTP");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        et_Otp = (EditText) findViewById(R.id.et_Otp);
        et_Otp.setText(String.valueOf(sharedHelper.getKey(ForgotOtp.this,"OTP")));

    }

    public void send(View view) {
        operation();
    }

    private void operation() {

        // final String otp = et_Otp.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.BaseUrl+Constants.Users_Otp_Validation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("Respo",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.optString("response");
                            if (res.equalsIgnoreCase("true")){
                                startActivity(new Intent(ForgotOtp.this,ForgotOtp.class));
                                ForgotOtp.this.finish();
                            }
                            else if (res.equalsIgnoreCase("false")){
                                Toast.makeText(ForgotOtp.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ForgotOtp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(ForgotOtp.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(ForgotOtp.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(ForgotOtp.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(ForgotOtp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(ForgotOtp.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("otp",sharedHelper.getKey(ForgotOtp.this,"OTP"));
                params.put("userid",sharedHelper.getKey(ForgotOtp.this,"USERID"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pyramidions.bajugali.MainActivity;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

    private EditText mobileFld;
    private ProgressDialog progressDialog;
    SharedHelper sharedHelper;
    String mobNumber;
    String tkn = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        mobileFld = (EditText) findViewById(R.id.mobileFld);
        tkn = FirebaseInstanceId.getInstance().getToken();
      //  Toast.makeText(this, tkn+"", Toast.LENGTH_SHORT).show();

    }

    public void register(View view) {
        progressDialog.show();
         mobNumber = mobileFld.getText().toString().trim();
        if (mobNumber.length()==0){
            Toast.makeText(this, "Required Field", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else {
            operation();
        }

    }

    private void operation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.SignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("Respo",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                                String userId = jsonObject2.optString("UserId");
                             //   Toast.makeText(SignUp.this, userId +"", Toast.LENGTH_SHORT).show();
                                String otp = jsonObject2.optString("otp");
                                String owneruserid = jsonObject2.optString("owneruserid");
                                sharedHelper.putKey(SignUp.this,"OTP",otp);
                                sharedHelper.putKey(SignUp.this,"USERID",userId);
                                sharedHelper.putKey(SignUp.this,"OWNERUSERID",owneruserid);
                           //     Toast.makeText(SignUp.this,otp+"",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp.this,Otp.class));
                                SignUp.this.finish();
                            }
                            else if (responseCode.equalsIgnoreCase("0")){
                                Toast.makeText(SignUp.this, jsonObject.optString("responseMessage")+"", Toast.LENGTH_SHORT).show();
                            }
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
                            Toast.makeText(SignUp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(SignUp.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(SignUp.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(SignUp.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(SignUp.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(SignUp.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("mobileoremail",mobNumber);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}

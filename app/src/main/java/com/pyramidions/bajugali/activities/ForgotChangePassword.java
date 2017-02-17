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

public class ForgotChangePassword extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_tv;
    private ProgressDialog progressDialog;
    private SharedHelper sharedHelper;
    private EditText ed_email, ed_confirmPassword, ed_newPassword;
    private String email, confirmPass, newPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_change_password);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("CHANGE PASSWORD");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_confirmPassword = (EditText) findViewById(R.id.ed_confirmPassword);
        ed_newPassword = (EditText) findViewById(R.id.ed_newPassword);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
    }

    public void change(View view) {
        progressDialog.show();
        email = ed_email.getText().toString().trim();
        confirmPass = ed_confirmPassword.getText().toString().trim();
        newPass = ed_newPassword.getText().toString().trim();
        if (email.length() == 0 &&   newPass.length() == 0&& confirmPass.length() == 0) {
            Toast.makeText(this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (email.length() == 0) {
            ed_email.setError("Required Field !");
            ed_email.requestFocus();
            progressDialog.dismiss();
        } else if (newPass.length() == 0) {
            ed_newPassword.setError("Required Field !");
            ed_newPassword.requestFocus();
            progressDialog.dismiss();
        }else if (confirmPass.length() == 0) {
            ed_confirmPassword.setError("Required Field !");
            ed_confirmPassword.requestFocus();
            progressDialog.dismiss();
        }  else {
            operation();
        }


    }

    private void operation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.ChangePassword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("Respo", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {
                                String responseStatus = jsonObject.optString("responseStatus");
                                Toast.makeText(ForgotChangePassword.this, responseStatus + "", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotChangePassword.this, SignIn.class));
                                ForgotChangePassword.this.finish();
                            } else if (responseCode.equalsIgnoreCase("0")) {
                                String responseMessage = jsonObject.optString("responseMessage");
                                Toast.makeText(ForgotChangePassword.this, responseMessage+"", Toast.LENGTH_SHORT).show();                  }
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
                            Toast.makeText(ForgotChangePassword.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(ForgotChangePassword.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(ForgotChangePassword.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(ForgotChangePassword.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(ForgotChangePassword.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(ForgotChangePassword.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("newpassword", newPass);
                params.put("confirmnewpassword", confirmPass);
                params.put("userid", sharedHelper.getKey(ForgotChangePassword.this, "USERID"));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

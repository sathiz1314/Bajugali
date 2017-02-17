package com.pyramidions.bajugali.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pyramidions.bajugali.MainActivity;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.apiClients.APIService;
import com.pyramidions.bajugali.apiClients.ApiUtils;
import com.pyramidions.bajugali.dataModels.SignInResponceModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.GlobalData;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class SignIn extends AppCompatActivity {

    private EditText emailFld, ed_DialogueMobileNumber, passFld;
    private Button btn_DialogueSubmit;
    private ProgressDialog progressDialog;
    private SharedHelper sharedHelper;
    private String email, pass, tkn, tknid = "",mobValid="";
    private TextView forgotPassword;
    private static final String TAG = SignIn.class.getSimpleName();
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        emailFld = (EditText) findViewById(R.id.emailFld);
        passFld = (EditText) findViewById(R.id.passFld);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordOperation();
            }
        });
        apiService = ApiUtils.getAPIService();


    }

    private void forgotPasswordOperation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.setCancelable(true);
        View dialogView= inflater.inflate(R.layout.dialogue_forgetpassword, null);
        alertDialog.setView(dialogView);
        ed_DialogueMobileNumber = (EditText) dialogView.findViewById(R.id.ed_DialogueMobileNumber);
        btn_DialogueSubmit = (Button) dialogView.findViewById(R.id.btn_DialogueSubmit);
        final AlertDialog dialog = alertDialog.show();
        btn_DialogueSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobValid = ed_DialogueMobileNumber.getText().toString().trim();
                if (mobValid.length()==0){
                    progressDialog.dismiss();
                    Toast.makeText(SignIn.this, "Required Field", Toast.LENGTH_SHORT).show();
                }else {
                    operationForgot();
                }

            }
        });


    }

    public void submit(View view) {

        progressDialog.show();
        email = emailFld.getText().toString().trim();
        pass = passFld.getText().toString().trim();

        if (email.length() == 0 && pass.length() == 0) {
            progressDialog.dismiss();
            Toast.makeText(SignIn.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        } else if (email.length() == 0) {
            progressDialog.dismiss();
            emailFld.setError("Required Field");
            emailFld.requestFocus();

        } else if (email.length() > 10 && email.length() < 10) {
            progressDialog.dismiss();
            Toast.makeText(this, " Enter 10 Digit Mobile Number ", Toast.LENGTH_SHORT).show();
        }
       /* else if (!isValidEmail(email)){
            progressDialog.dismiss();
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
        }*/
        else if (pass.length() == 0) {
            progressDialog.dismiss();
            passFld.setError("Required Filed");
            passFld.requestFocus();

        } else {
            tknid = sharedHelper.getKey(SignIn.this, "DeviceId");
            if (tknid.equalsIgnoreCase(null)) {
                tkn = FirebaseInstanceId.getInstance().getToken();
                tknid = tkn;
                //   Toast.makeText(this, " Null Token --- >" + tknid +"", Toast.LENGTH_SHORT).show();
            } else {
                tknid = sharedHelper.getKey(SignIn.this, "DeviceId");
                //   Toast.makeText(this, " Default Token --- > " + tknid +"", Toast.LENGTH_SHORT).show();
            }
            Operation();
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void Operation() {

       /* apiService.SIGN_IN_RESPONCE_MODEL_CALL("bajugali","",sharedHelper.getKey(SignIn.this, "DeviceId"),pass,email).enqueue(new Callback<SignInResponceModel>() {
            @Override
            public void onResponse(Call<SignInResponceModel> call, retrofit2.Response<SignInResponceModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<SignInResponceModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.SignInUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // Log.e(TAG," SignIn : ",+ response.get);
                        Log.v(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseCode = jsonObject.optString("responseCode");

                            if (responseCode.equalsIgnoreCase("1")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                JSONArray jsonArray = jsonObject1.getJSONArray("userdetails");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    sharedHelper.putKey(SignIn.this, "ContactNumber", jsonObject2.optString("ContactNumber"));
                                    sharedHelper.setSignUserToken(SignIn.this, "accessToken", jsonObject2.optString("accesstoken"));
                                    sharedHelper.putKey(SignIn.this, "USERID", jsonObject2.optString("UserId"));
                                    sharedHelper.putKey(SignIn.this, "OWNERUSERID", jsonObject2.optString("owneruserid"));
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(intent);
                                    sharedHelper.putKey(SignIn.this, "login_type", "Logged");
                                    SignIn.this.finish();
                                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                }
                            } else if (Integer.parseInt(responseCode) ==0) {
                                // Toast.makeText(SignIn.this,jsonObject.optString("responseMessage") + "", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignIn.this, " Invalid Username/Password", Toast.LENGTH_SHORT).show();
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
                        Log.v("SignIn Error", error.toString());
                        if( error instanceof NetworkError) {
                            Toast.makeText(SignIn.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(SignIn.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(SignIn.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(SignIn.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(SignIn.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(SignIn.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("mobileoremail", email);
                params.put("password", pass);
                //  params.put("userid", sharedHelper.getKey(SignIn.this, "USERID"));
                params.put("deviceid", sharedHelper.getKey(SignIn.this, "DeviceId"));
                //  params.put("deviceid",tknid);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(SignIn.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public void textSignUp(View view) {
        startActivity(new Intent(SignIn.this, SignUp.class));
        finish();
    }

    private void operationForgot() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.Forgot_Password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoForgot", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseCode = jsonObject.optString("responseCode");

                            if (responseCode.equalsIgnoreCase("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    sharedHelper.putKey(SignIn.this, "OTP", jsonObject2.optString("otp"));
                                    sharedHelper.putKey(SignIn.this, "USERID", jsonObject2.optString("UserId"));
                                    startActivity(new Intent(SignIn.this, ForgotOtp.class));
                                    SignIn.this.finish();
                                }
                            } else if (responseCode.equalsIgnoreCase("0")) {
                                Toast.makeText(SignIn.this, jsonObject.optString("responseMessage") + "", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignIn.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(SignIn.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(SignIn.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(SignIn.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(SignIn.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(SignIn.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("mobilenumber",mobValid);
               /* params.put("userid",sharedHelper.getKey(SignIn.this,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(SignIn.this,"accessToken"));*/
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

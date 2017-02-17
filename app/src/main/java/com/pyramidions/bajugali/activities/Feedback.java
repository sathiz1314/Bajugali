package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private Spinner wantspinner, wantspinner2;
    private String userquery, want2Data, ratingData, issueData;
    private RatingBar ratingbar;
    private EditText issue;
    private Button backpg;
    private ProgressDialog progressDialog;
    SharedHelper sharedHelper;
    private ArrayAdapter<String> secondAdapter;
    private List<String> queryTwo = new ArrayList<String>();
    private LinearLayout wantolayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        sharedHelper = new SharedHelper();
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        issue = (EditText) findViewById(R.id.comments);
        wantspinner = (Spinner) findViewById(R.id.wantspinner);
        wantspinner2 = (Spinner) findViewById(R.id.wantspinner2);
        wantolayout2 = (LinearLayout) findViewById(R.id.wantolayout2);

        List<String> query = new ArrayList<String>();
      //  query.add("Select");
        query.add("Send Request To Society");
        query.add("Send Feedback To BajuGali");

        operation();
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, query);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wantspinner.setAdapter(serviceAdapter);

//        spinnerOperation(queryTwo);

        backpg = (Button) findViewById(R.id.back);
        backpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        wantspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userquery = adapterView.getItemAtPosition(i).toString();
                if (userquery == "Send Feedback To BajuGali") {
                    ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            ratingData = String.valueOf(ratingBar.getRating());
                        }
                    });
                    ratingbar.setVisibility(View.VISIBLE);
                    issue.setVisibility(View.VISIBLE);
                    wantolayout2.setVisibility(View.GONE);
                } else if (userquery == "Send Request To Society") {

                    wantolayout2.setVisibility(View.VISIBLE);
                    ratingbar.setVisibility(View.GONE);
                    issue.setVisibility(View.VISIBLE);
                } else {
                    wantolayout2.setVisibility(View.GONE);
                    ratingbar.setVisibility(View.GONE);
                    issue.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerOperation() {
        ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_list_item_1, queryTwo);
        wantspinner2.setAdapter(secondAdapter);
        wantspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ratingData = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void operation() {
        queryTwo.add("Select");
        progressDialog.show();

        final String url = "http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/append_fedback";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.Append_Fedback,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoFeedbackData", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("response");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    queryTwo.add(jsonObject2.optString("Amenityname"));
                                }
                                queryTwo.add("Know your Parking");
                                queryTwo.add("Know your Owner’s detail");
                                queryTwo.add("Bill Related Issues");
                                queryTwo.add("Event Related Issues");
                                queryTwo.add("Ad Related Issues");
                                queryTwo.add("Others");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        spinnerOperation();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(Feedback.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(Feedback.this,"Oops ! Something Wrong in Server",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(Feedback.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(Feedback.this,"Oops ! Something Wrong in Server",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(Feedback.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(Feedback.this,"Oops ! Takes Much Time To Process Reload It Again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(Feedback.this, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(Feedback.this, "accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void feebbackSubmit(View view) {
        issueData = issue.getText().toString().trim();
       /* if (issueData.length() == 0) {
            issue.setError("Reqiured Field");
            issue.requestFocus();
            progressDialog.dismiss();
        } else if (ratingData.length() == 0) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (issueData.length() == 0 && ratingData.length() == 0) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else {

        }*/
        operationData();


    }

    private void operationData() {
        progressDialog.show();
        final String url = "http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/userfeedback";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.UserFeedback,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoFeedback", response);
                      //  Toast.makeText(Feedback.this, response +"", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");

                            if (responseCode.equalsIgnoreCase("1")) {
                                Toast.makeText(Feedback.this,  "Ticket successfully created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Feedback.this,SubmitRequest.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                            } else if (responseCode.equalsIgnoreCase("0")) {
                                Toast.makeText(Feedback.this, jsonObject.optString("responseMessage") + "", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Feedback.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(Feedback.this, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(Feedback.this, "accessToken"));
                params.put("feedbacktype", userquery);
                params.put("feedbacksummary", ratingData);
                params.put("feedbackcomments", issueData);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

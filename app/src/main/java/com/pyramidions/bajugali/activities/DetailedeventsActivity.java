package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.dataModels.BillModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saran Charan on 29-08-2016.
 */
public class DetailedeventsActivity extends AppCompatActivity {

    private CheckBox interested, going, notgoing;
    private Button submit, pay, paynow, back;
    private ImageView detailedeventimage;
    private TextView eventdatedetail, eventtimedetail, chiefguestdetail, eventamountdetail, detailedeventdescrp;
    private String data = "", eventId = "", Interestedcount = "", Goingcount = "", Notgoingcount = "";
    private ProgressDialog progressDialog;
    private SharedHelper sharedHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedevents);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        detailedeventimage = (ImageView) findViewById(R.id.detailedeventimage);
        eventdatedetail = (TextView) findViewById(R.id.eventdatedetail);
        eventtimedetail = (TextView) findViewById(R.id.eventtimedetail);
        chiefguestdetail = (TextView) findViewById(R.id.chiefguestdetail);
        eventamountdetail = (TextView) findViewById(R.id.eventamountdetail);
        detailedeventdescrp = (TextView) findViewById(R.id.detailedeventdescrp);

        interested = (CheckBox) findViewById(R.id.interestedcheckbox);
        going = (CheckBox) findViewById(R.id.goingcheckbox);
        notgoing = (CheckBox) findViewById(R.id.notgoingcheckbox);
        submit = (Button) findViewById(R.id.submit);
        pay = (Button) findViewById(R.id.pay);
        paynow = (Button) findViewById(R.id.paynow);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        String eventName = getIntent().getStringExtra("EventName");
        String eventDate = getIntent().getStringExtra("Date");
        String eventTime = getIntent().getStringExtra("Time");
        String eventImage = getIntent().getStringExtra("EventImage");
        String eventChiefGuest = getIntent().getStringExtra("ChiefGuest");
        String amount = getIntent().getStringExtra("amount");
        String paymentstatus = getIntent().getStringExtra("paymentstatus");
        eventId = getIntent().getStringExtra("EventId");
        try {
            Picasso.with(this).load(eventImage).placeholder(R.drawable.background).into(detailedeventimage);
            eventdatedetail.setText(String.valueOf(eventDate));
            eventtimedetail.setText(String.valueOf(eventTime));
            chiefguestdetail.setText(String.valueOf(eventChiefGuest));
            detailedeventdescrp.setText(getIntent().getStringExtra("EventDescription"));
            eventamountdetail.setText("Rs." + " " + String.valueOf(amount));
            if (paymentstatus.equalsIgnoreCase("paid")) {
                pay.setVisibility(View.VISIBLE);
            } else if (paymentstatus.equalsIgnoreCase("notpaid")) {
                paynow.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        initialOperation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((interested.isChecked() == false) && (going.isChecked() == false) && (notgoing.isChecked() == false)) {

                    Toast.makeText(DetailedeventsActivity.this,
                            "Please Select your Interest", Toast.LENGTH_LONG).show();
                } else {
                    operation();
                }
            }
        });
        interested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    going.setChecked(false);
                    notgoing.setChecked(false);
                    data = "1";
                }

            }
        });
        going.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    interested.setChecked(false);
                    notgoing.setChecked(false);
                    data = "2";
                }
            }
        });
        notgoing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    going.setChecked(false);
                    interested.setChecked(false);
                    data = "3";
                }
            }
        });


    }

    private void initialOperation() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.DisplayEventCount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoDetailedEvents", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {
                                JSONArray jsonArray = jsonObject.optJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                                    Interestedcount = jsonObject2.optString("Interestedcount");
                                    if (Interestedcount.equalsIgnoreCase("1")) {
                                        interested.setChecked(true);

                                    } else if (Interestedcount.equalsIgnoreCase("0")) {
                                        interested.setChecked(false);
                                    }
                                    Goingcount = jsonObject2.optString("Goingcount");
                                    if (Goingcount.equalsIgnoreCase("1")) {
                                        going.setChecked(true);


                                    } else {
                                        going.setChecked(false);
                                    }
                                    Notgoingcount = jsonObject2.optString("Notgoingcount");
                                    if (Notgoingcount.equalsIgnoreCase("1")) {
                                        notgoing.setChecked(true);

                                    } else {
                                        notgoing.setChecked(false);
                                    }
                                }
                            } else if (responseCode.equalsIgnoreCase("0")) {
                                Toast.makeText(DetailedeventsActivity.this, "Denied", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DetailedeventsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(DetailedeventsActivity.this, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(DetailedeventsActivity.this, "accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void operation() {
        progressDialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.EventMemberCount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoDetailedEvents", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {
                                Toast.makeText(DetailedeventsActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            } else if (responseCode.equalsIgnoreCase("0")) {
                                Toast.makeText(DetailedeventsActivity.this, "Denied", Toast.LENGTH_SHORT).show();
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
                        if (error instanceof NetworkError) {
                            Toast.makeText(DetailedeventsActivity.this, "Oops ! No Internet Connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(DetailedeventsActivity.this, "Oops ! Something went wrong in server side", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(DetailedeventsActivity.this, "Oops ! Already Signed In Other Device", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(DetailedeventsActivity.this, "Oops ! Something went wrong in fetching data...", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(DetailedeventsActivity.this, "Oops ! No Internet Connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(DetailedeventsActivity.this, "Bad Network ! Slow loading reload it again...  ", Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(DetailedeventsActivity.this, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(DetailedeventsActivity.this, "accessToken"));
                params.put("membercount", data);
                params.put("eventid", eventId);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

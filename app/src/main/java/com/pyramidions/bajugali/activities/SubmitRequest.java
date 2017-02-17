package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.pyramidions.bajugali.adapters.SubmitRequestAdapter;
import com.pyramidions.bajugali.dataModels.NoticeModel;
import com.pyramidions.bajugali.dataModels.SubmitRequestModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SubmitRequest extends AppCompatActivity {

    private RecyclerView submitrrequestRecyclerview;
    SharedHelper sharedHelper;
    private TextView toolbar_tv;
    private ArrayList<SubmitRequestModel> submitRequestModelList = new ArrayList<SubmitRequestModel>();
    SubmitRequestAdapter submitRequestAdapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("MY REQUEST");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        submitrrequestRecyclerview = (RecyclerView) findViewById(R.id.submitrrequestRecyclerview);
        submitrrequestRecyclerview.setHasFixedSize(true);
        submitrrequestRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        operation();
        submitRequestAdapter = new SubmitRequestAdapter(this,submitRequestModelList);
        submitrrequestRecyclerview.setAdapter(submitRequestAdapter);

    }

    private void operation() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.GrevianceAddressal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoNotice",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final int responseCode = jsonObject.optInt("responseCode");
                            if (responseCode == 1 ){

                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    SubmitRequestModel submitRequestModel = new SubmitRequestModel();
                                    submitRequestModel.setTicketNum(jsonObject2.optString("TicketNum"));
                                    submitRequestModel.setSubject(jsonObject2.optString("subject"));
                                    submitRequestModel.setDescription(jsonObject2.optString("description"));
                                    submitRequestModel.setAssignedto(jsonObject2.optString("assignedto"));
                                    submitRequestModel.setResolutioncomment(jsonObject2.optString("Resolutioncomment"));
                                    submitRequestModel.setResolvedOnDate(jsonObject2.optString("ResolvedOnDate"));
                                    submitRequestModel.setSubmitdate(jsonObject2.optString("submitdate"));
                                    submitRequestModel.setStatus(jsonObject2.optString("status"));
                                    submitRequestModelList.add(submitRequestModel);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        submitRequestAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(SubmitRequest.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(SubmitRequest.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(SubmitRequest.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(SubmitRequest.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(SubmitRequest.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(SubmitRequest.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){


            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("userid",sharedHelper.getKey(SubmitRequest.this,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(SubmitRequest.this,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void submit_NewRequest(View view) {
        startActivity(new Intent(this,Feedback.class));
    }
}

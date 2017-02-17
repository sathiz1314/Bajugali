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
import android.widget.Button;
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
import com.pyramidions.bajugali.adapters.BillAdapter;
import com.pyramidions.bajugali.dataModels.BillModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bills extends AppCompatActivity {

    private TextView billmonth,billamountdue,billduedate;
    SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    private ArrayList<BillModel> billModelsList = new ArrayList<BillModel>();
    private RecyclerView bill_RecyclerView;
    BillAdapter billAdapter;
    private Button button_Paid,button_Pay;
    private TextView toolbar_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        bill_RecyclerView = (RecyclerView) findViewById(R.id.bill_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bill_RecyclerView.setLayoutManager(linearLayoutManager);
        bill_RecyclerView.setHasFixedSize(true);
        button_Paid = (Button) findViewById(R.id.button_Paid);
        button_Pay = (Button) findViewById(R.id.button_Pay);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
      //  toolbar.setTitle("BILLS");
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("BILLS");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        operation();
        billAdapter = new BillAdapter(billModelsList,this);
        bill_RecyclerView.setAdapter(billAdapter);


    }

    private void operation() {
        progressDialog.show();
        final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/billdetails";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.Billdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoBill",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){
                                JSONArray jsonArray = jsonObject.getJSONArray("response");

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String duedate = jsonObject2.optString("duedate");
                                    String monthname = jsonObject2.optString("monthname");
                                    String sum = jsonObject2.optString("BasicDueAmount");
                                    String StatusOfPayment = jsonObject2.optString("StatusOfPayment");
                                    BillModel billModel = new BillModel();
                                    billModel.setDueAmount(sum);
                                    billModel.setDueDate(duedate);
                                    billModel.setMonth(monthname);
                                    billModel.setStatusOfPayment(jsonObject2.optString("StatusOfPayment"));
                                    billModelsList.add(billModel);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        billAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(Bills.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(Bills.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(Bills.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(Bills.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(Bills.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(Bills.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("owneruserid",sharedHelper.getKey(Bills.this,"OWNERUSERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(Bills.this,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

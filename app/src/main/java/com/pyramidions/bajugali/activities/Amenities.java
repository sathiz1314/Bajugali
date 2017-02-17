package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.pyramidions.bajugali.adapters.AmenitiesAdapter;
import com.pyramidions.bajugali.dataModels.AmenitiesModel;
import com.pyramidions.bajugali.dataModels.BillModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Amenities extends AppCompatActivity {
    private Spinner reqspinner,wenspinner;
    private String requestfor,whenrequired;
    private Button submitreq,back;
    private RecyclerView amenities_RecyclerView;
    private ArrayList<AmenitiesModel> amenitiesModelList = new ArrayList<AmenitiesModel>();
    AmenitiesAdapter amenitiesAdapter;
    SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        amenities_RecyclerView = (RecyclerView) findViewById(R.id.amenities_RecyclerView);
        amenities_RecyclerView.setHasFixedSize(true);
        amenities_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        operation();
        amenitiesAdapter = new AmenitiesAdapter(this,amenitiesModelList);
        amenities_RecyclerView.setAdapter(amenitiesAdapter);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });



    }

    private void operation() {
        progressDialog.show();
        final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/amenitiesdetails";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.AmenitiesDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoAmenities",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){
                                JSONArray jsonArray = jsonObject.getJSONArray("response");

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    AmenitiesModel amenitiesModel = new AmenitiesModel();
                                    amenitiesModel.setAmID(jsonObject2.optString("AmID"));
                                    amenitiesModel.setApId(jsonObject2.optString("ApId"));
                                    amenitiesModel.setAmenityname(jsonObject2.optString("Amenityname"));
                                    amenitiesModel.setDescription(jsonObject2.optString("Description"));
                                    amenitiesModel.setFirstshift(jsonObject2.optString("firstshift"));
                                    amenitiesModel.setSeconfdshift(jsonObject2.optString("seconfdshift"));
                                    amenitiesModel.setWorkingDays(jsonObject2.optString("WorkingDays"));
                                    amenitiesModel.setRulesandregulations(jsonObject2.optString("rulesandregulations"));
                                    amenitiesModel.setPrice(jsonObject2.optString("price"));
                                    amenitiesModel.setBooking(jsonObject2.optString("booking"));
                                    amenitiesModelList.add(amenitiesModel);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        amenitiesAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(Amenities.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(Amenities.this,"Oops ! Something Wrong in Server",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(Amenities.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(Amenities.this,"Oops ! Something Wrong in Server",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(Amenities.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(Amenities.this,"Oops ! Takes Much Time To Process Reload It Again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("userid",sharedHelper.getKey(Amenities.this,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(Amenities.this,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
/*

 */
/* reqspinner = (Spinner)findViewById(R.id.reqspinner);
        wenspinner = (Spinner)findViewById(R.id.wenspinner);
        submitreq = (Button) findViewById(R.id.submitreq);*//*
*/
/*

    List<String> service = new ArrayList<String>();
service.add("Plumber");
        service.add("Electrician");
        service.add("Carpenter");
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, service);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reqspinner.setAdapter(serviceAdapter);
        reqspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        requestfor = adapterView.getItemAtPosition(i).toString();

        }

@Override
public void onNothingSelected(AdapterView<?> adapterView) {

        }
        });
final List<String> wenrequired = new ArrayList<String>();
        wenrequired.add("Immediately");
        wenrequired.add("After 30 mins");
        wenrequired.add("After 1 hour");
        wenrequired.add("After 2 hour");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wenrequired);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wenspinner.setAdapter(dataAdapter);
        wenspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        whenrequired = adapterView.getItemAtPosition(i).toString();
        }

@Override
public void onNothingSelected(AdapterView<?> adapterView) {

        }
        });
*/

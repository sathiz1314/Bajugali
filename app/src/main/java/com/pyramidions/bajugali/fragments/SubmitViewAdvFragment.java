package com.pyramidions.bajugali.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.pyramidions.bajugali.activities.Constants;
import com.pyramidions.bajugali.activities.PromoteMyBussiness;
import com.pyramidions.bajugali.adapters.AddAdapter;
import com.pyramidions.bajugali.dataModels.AddModel;
import com.pyramidions.bajugali.dataModels.OnGoingDataModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.DividerItemDecoration;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitViewAdvFragment extends Fragment {

    private Context context;
    private SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    private Button subViewAdv_button;
    private RecyclerView subViewAdv_recyclerview;
    private ArrayList<AddModel> addModelList = new ArrayList<AddModel>();
    private AddAdapter addAdapter;

    public SubmitViewAdvFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_view_adv, container, false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        subViewAdv_button = (Button) view.findViewById(R.id.subViewAdv_button);
        subViewAdv_recyclerview = (RecyclerView) view.findViewById(R.id.subViewAdv_recyclerview);
        subViewAdv_recyclerview.setHasFixedSize(true);
        subViewAdv_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        // subViewAdv_recyclerview.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        operation();
        addAdapter = new AddAdapter(context, addModelList);
        subViewAdv_recyclerview.setAdapter(addAdapter);
        subViewAdv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PromoteMyBussiness.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        return view;
    }

    private void operation() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.DisplayAdvertisement,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.v("RespoDisplayAds", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {
                                JSONObject jsonObject3 = jsonObject.optJSONObject("response");

                                AddModel addModel = new AddModel();
                                addModel.setAdimage(jsonObject3.optString("adimage"));
                                addModel.setAdtitle(jsonObject3.optString("adtitle"));
                                addModel.setCategory(jsonObject3.optString("category"));
                                addModel.setDescription(jsonObject3.optString("description"));
                                addModel.setDisplaycontactnumber(jsonObject3.optString("displaycontactnumber"));
                                addModel.setPrice(jsonObject3.optString("price"));
                                addModel.setStatus(jsonObject3.optString("status"));
                                addModel.setAdStartDate(jsonObject3.optString("AdStartDate"));
                                addModel.setAdEndDate(jsonObject3.optString("AdEndDate"));
                                addModelList.add(addModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        addAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if( error instanceof NetworkError) {
                            Toast.makeText(context,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(context,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(context,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(context,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(context,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(context,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(context, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(context, "accessToken"));
                params.put("advertisementid", sharedHelper.getKey(context, "AdvertisementId"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}

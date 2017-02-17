package com.pyramidions.bajugali.fragments;

//import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.pyramidions.bajugali.adapters.BajuDealRecyclerAdapter;

import com.pyramidions.bajugali.dataModels.DealModel;
import com.pyramidions.bajugali.dataModels.DealModel;
import com.pyramidions.bajugali.dataModels.OnGoingDataModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2/7/2017.
 */

public class BajuDeal extends Fragment {
    private ListView listview;
    private CardView cardview;
    private RecyclerView bajuDeal_RecyclerView;
    private RelativeLayout relativeBg1;
    Context context;
    SharedHelper sharedHelper;
    ArrayList<DealModel> DealModelList = new ArrayList<DealModel>();
    BajuDealRecyclerAdapter bajuDealRecyclerAdapter ;
    private ProgressDialog progressDialog;

    public BajuDeal(){

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.baju_deals_list,container,false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        bajuDeal_RecyclerView = (RecyclerView) fragment.findViewById(R.id.baju_deals_list);
        relativeBg1 = (RelativeLayout) fragment.findViewById(R.id.relativeBg1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        bajuDeal_RecyclerView.setLayoutManager(linearLayoutManager);
        bajuDeal_RecyclerView.setHasFixedSize(true);
        operation();
        bajuDealRecyclerAdapter = new BajuDealRecyclerAdapter(context, DealModelList);
        bajuDeal_RecyclerView.setAdapter(bajuDealRecyclerAdapter);

        return fragment;

    }

    private void operation() {

        DealModelList.clear();
        progressDialog.show();
        final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/bajudeals";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.Bajudeals,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.v("RespoEvent",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){


                                JSONArray jsonArray = jsonObject.optJSONArray("response");
                                if (jsonArray.equals(null)){
                                    relativeBg1.setBackground(getActivity().getResources().getDrawable(R.drawable.oops));
                                }
                                else {


                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        Log.v("Upcoming",jsonObject2.toString());
                                        String DealId = jsonObject2.optString("DealId");
                                        String DealName = jsonObject2.optString("DealName");
                                        String DealImage = jsonObject2.optString("DealImage");
                                        String NormalPrice = jsonObject2.optString("NormalPrice");
                                        String OfferPrice = jsonObject2.optString("OfferPrice");
                                        String Description = jsonObject2.optString("Description");
                                        String DealStartDate = jsonObject2.optString("DealStartDate");
                                        String amount = jsonObject2.optString("amount");
                                        String DealEndDate = jsonObject2.optString("DealEndDate");
                                        String DealLocation = jsonObject2.optString("DealLocation");
                                        String NoOfRedemption = jsonObject2.optString("NoOfRedemption");
                                        DealModel dataModel = new DealModel();
                                        dataModel.setDealId(DealId);
                                        dataModel.setDealName(DealName);
                                        dataModel.setDealImage(DealImage);
                                        dataModel.setNormalPrice(NormalPrice);
                                        dataModel.setOfferPrice(OfferPrice);
                                        dataModel.setDescription(Description);
                                        dataModel.setDealStartDate(DealStartDate);
                                        dataModel.setAmount(amount);
                                        dataModel.setDealEndDate(DealEndDate);
                                        dataModel.setDealLocation(DealLocation);
                                        dataModel.setNoOfRedemption(NoOfRedemption);
                                        DealModelList.add(dataModel);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bajuDealRecyclerAdapter.notifyDataSetChanged();

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
                        } Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("userid",sharedHelper.getKey(context,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(context,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }




}



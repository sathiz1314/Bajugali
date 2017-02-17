package com.pyramidions.bajugali.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Constants;
import com.pyramidions.bajugali.activities.Feedback;
import com.pyramidions.bajugali.adapters.MyApartmentAdapter;
import com.pyramidions.bajugali.adapters.MyApartmentRecyclerAdapter;
import com.pyramidions.bajugali.dataModels.MyApartmentModel;
import com.pyramidions.bajugali.helpers.AppController;
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
public class MyApartments extends Fragment {
    private TextView tv_ApartmentName, tv_address, tv_Description, tv_AgreeStartDate, tv_AgreeEndDate, tv_Name, tv_Designation, toolbar_textview;
    private CircularImageView iv_apartmentProfile;
    Context context;
    SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    ArrayList<MyApartmentModel> myApartmentModelList = new ArrayList<MyApartmentModel>();
    private ViewPager myapartmentViewPager;
    MyApartmentAdapter myApartmentAdapter;
    MyApartmentRecyclerAdapter myApartmentRecyclerAdapter;
    private RecyclerView mycommiteeRecyclerView;


    public MyApartments() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_apartments, container, false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(context);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //  toolbar.setLogo(null);
        // toolbar.setTitle("My APARTMENT");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        //  toolbar_textview = (TextView) toolbar.findViewById(R.id.toolbar_textview);
        //  toolbar_textview.setText("My Apartment");

        tv_ApartmentName = (TextView) view.findViewById(R.id.tv_ApartmentName);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_Description = (TextView) view.findViewById(R.id.tv_Description);
        tv_AgreeStartDate = (TextView) view.findViewById(R.id.tv_AgreeStartDate);
        tv_AgreeEndDate = (TextView) view.findViewById(R.id.tv_AgreeEndDate);
        tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        tv_Designation = (TextView) view.findViewById(R.id.tv_Designation);
        iv_apartmentProfile = (CircularImageView) view.findViewById(R.id.iv_apartmentProfile);
        myapartmentViewPager = (ViewPager) view.findViewById(R.id.myapartmentViewPager);
        mycommiteeRecyclerView = (RecyclerView) view.findViewById(R.id.mycommiteeRecyclerView);
        mycommiteeRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,LinearLayoutManager.HORIZONTAL,false);
        mycommiteeRecyclerView.setLayoutManager(gridLayoutManager);
        operation();
        myApartmentRecyclerAdapter = new MyApartmentRecyclerAdapter(context,myApartmentModelList);
        mycommiteeRecyclerView.setAdapter(myApartmentRecyclerAdapter);
      //  myApartmentAdapter = new MyApartmentAdapter(context,myApartmentModelList);
      //  myapartmentViewPager.setAdapter(myApartmentAdapter);
        return view;
    }

    private void operation() {

        final String url = "http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/apartmentdetails";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.ApartmentDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoMyApartment", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");

                            if (responseCode.equalsIgnoreCase("1")) {
                                JSONObject jsonObject1 = jsonObject.optJSONObject("response");

                                JSONArray jsonArray = jsonObject1.optJSONArray("apartmentdetails");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    tv_ApartmentName.setText(jsonObject2.optString("ApName"));
                                    tv_Description.setText(jsonObject2.optString("Description"));
                                    tv_address.setText(jsonObject2.optString("Address"));
                                    tv_ApartmentName.setText(jsonObject2.optString("ApName"));
                                    tv_ApartmentName.setText(jsonObject2.optString("ApName"));
//                                    tv_Name.setText(jsonObject2.optString("Name"));
//                                    tv_Designation.setText(jsonObject2.optString("designation"));
//                                   Glide.with(context).load(jsonObject2.optString("Memberimage")).into(iv_apartmentProfile);
                                }

                                JSONArray jsonArray1 = jsonObject1.optJSONArray("committeemember");
                                for (int j=0 ;j<jsonArray1.length();j++){

                                    JSONObject jsonObject2 = jsonArray1.optJSONObject(j);
                                    Log.v("Commitee",jsonObject2.toString());
                                    MyApartmentModel myApartmentModel = new MyApartmentModel();
                                    myApartmentModel.setName(jsonObject2.optString("Name"));
                                    myApartmentModel.setDesignation(jsonObject2.optString("designation"));
                                    myApartmentModel.setMemberimage(jsonObject2.optString("Memberimage"));
                                    myApartmentModelList.add(myApartmentModel);
                                }

                            } else if (responseCode.equalsIgnoreCase("0")) {

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                          myApartmentRecyclerAdapter.notifyDataSetChanged();
                        //    myApartmentAdapter.notifyDataSetChanged();

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
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}

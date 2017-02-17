package com.pyramidions.bajugali.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.pyramidions.bajugali.MainActivity;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Constants;
import com.pyramidions.bajugali.activities.NoticeBoard;
import com.pyramidions.bajugali.dataModels.NoticeModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {

    private TextView txt_name,txt_mobile,txt_email,txt_flat_name,txt_apartment_name,txt_Profile,txt_idproof,txt_idproofnumber,txt_bajucredits,toolbar_textview;
    SharedHelper sharedHelper;
    Context context;
    private ProgressDialog progressDialog;

    public MyProfile() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
      //  MainActivity.toolbar.setLogo(null);
      //  MainActivity.toolbar.setTitle("MY PROFILE");
      //  toolbar_textview = (TextView) MainActivity.toolbar.findViewById(R.id.toolbar_textview);
      //  toolbar_textview.setText("My Profile");
        MainActivity.toolbar.setTitleTextColor(getResources().getColor(R.color.black_text_color));

        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_mobile = (TextView) view.findViewById(R.id.txt_mobile);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txt_flat_name = (TextView) view.findViewById(R.id.txt_flat_name);
        txt_apartment_name = (TextView) view.findViewById(R.id.txt_apartment_name);
        txt_idproof = (TextView) view.findViewById(R.id.txt_idproof);
        txt_idproofnumber = (TextView) view.findViewById(R.id.txt_idproofnumber);
        txt_bajucredits = (TextView) view.findViewById(R.id.txt_bajucredits);
        txt_Profile = (TextView) view.findViewById(R.id.txt_Profile);

        operation();
        return  view;
    }

    private void operation() {
        progressDialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.UsersProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoProfile",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final int responseCode = jsonObject.optInt("responseCode");
                            if (responseCode == 1 ){

                                JSONObject jsonObject2 = jsonObject.getJSONObject("response");

                                 String n1 = jsonObject2.optString("name");
                                 String n2 =n1.substring(0,1);

                                    txt_Profile.setText(n2);
                                    txt_name.setText(jsonObject2.optString("name"));
                                    txt_mobile.setText(jsonObject2.optString("ContactNumber"));
                                    txt_email.setText(jsonObject2.optString("EmailId"));
                                    txt_apartment_name.setText(jsonObject2.optString("apartmentname"));
                                    txt_idproof.setText(jsonObject2.optString("IdProofType"));
                                    txt_idproofnumber.setText(jsonObject2.optString("IdProofNbr"));
                                    txt_bajucredits.setText(jsonObject2.optString("name"));
                                    String floor = jsonObject2.optString("FloorNum");
                                    String DoorNum = jsonObject2.optString("DoorNum");
                                    String block = jsonObject2.optString("block");
                                    txt_flat_name.setText(floor +" "+ DoorNum + " " + block);
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

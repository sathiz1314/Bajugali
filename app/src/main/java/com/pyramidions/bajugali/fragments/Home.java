package com.pyramidions.bajugali.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pyramidions.bajugali.MainActivity;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Amenities;
import com.pyramidions.bajugali.activities.Bills;
import com.pyramidions.bajugali.activities.Constants;
import com.pyramidions.bajugali.activities.Events;
import com.pyramidions.bajugali.activities.NoticeBoard;
import com.pyramidions.bajugali.activities.SubmitRequest;
import com.pyramidions.bajugali.adapters.SlidingImage_Adapter;
import com.pyramidions.bajugali.dataModels.ImageModel;
import com.pyramidions.bajugali.dataModels.NoticeModel;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    Context context;
    SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    private TextView customerName,apartmentName;
    private ImageView apartmentImage;
    ArrayList<ImageModel> imageModelsList = new ArrayList<ImageModel>();
    SlidingImage_Adapter slidingImage_adapter;
    private ViewPager viewpagerad;
    private TextView toolbar_textview,toolbar_textview2;
    private LinearLayout line;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView iv_Notice,iv_Events,iv_Bills,iv_Amenities,iv_Feedback;

    public Home() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
       // toolbar.setLogo(R.drawable.logoss);
        toolbar.setTitle(null);
        toolbar_textview = (TextView) toolbar.findViewById(R.id.toolbar_textview);
        toolbar_textview.setText("BajuGali");
        toolbar.setTitleTextColor(getResources().getColor(R.color.tab_text_color));
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");

        customerName = (TextView) view.findViewById(R.id.customerName);
        apartmentName = (TextView) view.findViewById(R.id.apartmentName);
        apartmentImage = (ImageView) view.findViewById(R.id.apartmentImage);
        viewpagerad = (ViewPager) view.findViewById(R.id.viewpagerad);
        line = (LinearLayout) view.findViewById(R.id.line);

        iv_Notice = (ImageView) view.findViewById(R.id.iv_Notice);
        iv_Events = (ImageView) view.findViewById(R.id.iv_Events);
        iv_Bills = (ImageView) view.findViewById(R.id.iv_Bills);
        iv_Amenities = (ImageView) view.findViewById(R.id.iv_Amenities);
        iv_Feedback = (ImageView) view.findViewById(R.id.iv_Feedback);
        imageOperation();
        operation();
        slidingImage_adapter = new SlidingImage_Adapter(context,imageModelsList);
        viewpagerad.setAdapter(slidingImage_adapter);
        //setting time
        runnable = new Runnable() {
            int position;

            public void run()
            {
                if (position >= imageModelsList.size())
                {
                    position = 0;
                } else
                {
                    position = position + 1;
                }
                viewpagerad.setCurrentItem(position, true);
                handler.postDelayed(runnable, 2000);
            }
        };
        return view;
    }

    private void imageOperation() {
        iv_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, NoticeBoard.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        iv_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Events.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        iv_Bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Bills.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        iv_Amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Amenities.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        iv_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SubmitRequest.class));
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    private void operation() {
        progressDialog.show();
       // final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/homepage";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.HomePage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.v("RespoImage",response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            final int responseCode = jsonObject.optInt("responseCode");

                            if (responseCode == 1 ){
                                final JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                customerName.setText("Hello" + " ," +jsonObject1.optString("name"));
                                apartmentName.setText(jsonObject1.optString("apartmentname"));
                                sharedHelper.putKey(context,"AdvertisementId",jsonObject1.optString("advertisementid"));
                                try {
                                    Picasso.with(context).load(jsonObject1.optString("apartmentimage")).placeholder(R.drawable.background).into(apartmentImage);
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            try {
/*
                                                apartmentImage.setVisibility(View.GONE);
                                                apartmentName.setVisibility(View.GONE);
                                                YoYo.with(Techniques.SlideInUp).duration(2000).playOn(line);*/

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 3000);


                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                JSONArray jsonArray = jsonObject1.getJSONArray("advertisementimages");
                                for (int i=0;i<jsonArray.length();i++){
                                    String str = jsonArray.getString(i);
                                    ImageModel imageModel = new ImageModel();
                                    imageModel.setSlideImage(str);
                                    imageModelsList.add(imageModel);

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       slidingImage_adapter.notifyDataSetChanged();

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

       /* JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-API-KEY","bajugali");
            jsonObject.put("userid",sharedHelper.getKey(context,"USERID"));
            jsonObject.put("accesstoken",sharedHelper.getSignUserToken(context,"accessToken"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, response.toString()+"", Toast.LENGTH_SHORT).show();
                Log.v("ImageRespo",response.toString());
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error +"", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);*/
    }

}

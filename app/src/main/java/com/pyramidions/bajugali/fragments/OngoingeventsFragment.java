package com.pyramidions.bajugali.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pyramidions.bajugali.activities.Events;
import com.pyramidions.bajugali.adapters.OnGoingEventAdapter;
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
 * Created by Saran Charan on 29-08-2016.
 */
public class OngoingeventsFragment extends Fragment {
    private ListView ongoinglistview;
    private CardView eventscardview;
    private RecyclerView onGoingEvents_RecyclerView;
    private RelativeLayout relativeBg;
    Context context;
    SharedHelper sharedHelper;
    ArrayList<OnGoingDataModel> onGoingDataModelList = new ArrayList<OnGoingDataModel>();


    OnGoingEventAdapter onGoingEventAdapter ;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.activity_eventslistitems,container,false);
        context = getActivity();
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        onGoingEvents_RecyclerView = (RecyclerView) fragment.findViewById(R.id.onGoingEvents_RecyclerView);
        relativeBg = (RelativeLayout) fragment.findViewById(R.id.relativeBg);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        onGoingEvents_RecyclerView.setLayoutManager(linearLayoutManager);
        onGoingEvents_RecyclerView.setHasFixedSize(true);
        operation();
        onGoingEventAdapter = new OnGoingEventAdapter(context, onGoingDataModelList);
        onGoingEvents_RecyclerView.setAdapter(onGoingEventAdapter);

        return fragment;

    }

    private void operation() {
        onGoingDataModelList.clear();
        progressDialog.show();

        final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/eventinformation";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.EventInformation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.v("RespoEvent",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){
                                JSONObject jsonObject3 = jsonObject.optJSONObject("response");

                                JSONArray jsonArray = jsonObject3.getJSONArray("eventlist");
                                if (jsonArray.equals(null)){
                                    relativeBg.setBackground(getActivity().getResources().getDrawable(R.drawable.oops));
                                }
                                else {


                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    Log.v("Upcoming",jsonObject2.toString());
                                    String EventName = jsonObject2.optString("EventName");
                                    String date = jsonObject2.optString("date");
                                    String time = jsonObject2.optString("time");
                                    String Chiefguest = jsonObject2.optString("Chiefguest");
                                    String EventImage = jsonObject2.optString("EventImage");
                                    OnGoingDataModel dataModel = new OnGoingDataModel();
                                    dataModel.setChiefguest(Chiefguest);
                                    dataModel.setEventName(EventName);
                                    dataModel.setDate(date);
                                    dataModel.setTime(time);
                                    dataModel.setEventImage(EventImage);
                                    dataModel.setEventId(jsonObject2.optString("EventId"));
                                    dataModel.setAmount(jsonObject2.optString("amount"));
                                    dataModel.setPaymentstatus(jsonObject2.optString("paymentstatus"));
                                    dataModel.setEventDescription(jsonObject2.optString("EventDescription"));
                                    onGoingDataModelList.add(dataModel);
                                }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                         onGoingEventAdapter.notifyDataSetChanged();

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

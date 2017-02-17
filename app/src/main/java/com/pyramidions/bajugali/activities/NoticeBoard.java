package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.pyramidions.bajugali.adapters.NoticeAdapter;
import com.pyramidions.bajugali.apiClients.APIService;
import com.pyramidions.bajugali.apiClients.ApiUtils;
import com.pyramidions.bajugali.dataModels.BillModel;
import com.pyramidions.bajugali.dataModels.ModelDataNotice;
import com.pyramidions.bajugali.dataModels.NoticeModel;
import com.pyramidions.bajugali.dataModels.Noticeboard;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class NoticeBoard extends AppCompatActivity {

    private TextView t;
    private ArrayList<NoticeModel> noticeModelsList = new ArrayList<NoticeModel>();
    private RecyclerView notice_RecyclerView;
    private SharedHelper sharedHelper;
    private NoticeAdapter noticeAdapter;
    private ProgressDialog progressDialog;
    private TextView toolbar_tv;
    private ProgressBar progressBar;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        apiService = ApiUtils.getAPIService();


        notice_RecyclerView = (RecyclerView) findViewById(R.id.notice_RecyclerView);
        notice_RecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notice_RecyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
      //  toolbar.setTitle("NOTICEBOARD");
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("NOTICE BOARD");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        operation();
        noticeAdapter = new NoticeAdapter(noticeModelsList,this);
        notice_RecyclerView.setAdapter(noticeAdapter);

    }

    private void operation() {

        progressBar.setVisibility(View.VISIBLE);

      /*  apiService.MODEL_DATA_NOTICE_CALL("bajugali",sharedHelper.getKey(NoticeBoard.this,"USERID"),sharedHelper.getSignUserToken(NoticeBoard.this,"accessToken")).enqueue(new Callback<ModelDataNotice>() {
            @Override
            public void onResponse(Call<ModelDataNotice> call, retrofit2.Response<ModelDataNotice> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponseCode()==1){
                        response.body().getResponse().getNoticeboard().get(0).getNoticeName();
                        List<Noticeboard> noticeboardArrayList = response.body().getResponse().getNoticeboard();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelDataNotice> call, Throwable t) {

            }
        });*/
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl+Constants.Noticeboard,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        Log.v("RespoNotice",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final int responseCode = jsonObject.optInt("responseCode");
                            if (responseCode == 1 ){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                JSONArray jsonArray = jsonObject1.getJSONArray("noticeboard");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    NoticeModel noticeModel = new NoticeModel();
                                    noticeModel.setNoticeName(jsonObject2.optString("NoticeName"));
                                    noticeModel.setNoticeSummary(jsonObject2.optString("NoticeSummary"));
                                    noticeModel.setNoticeDescription(jsonObject2.optString("NoticeDescription"));
                                    noticeModel.setNoticeContactUserId(jsonObject2.optString("NoticeContactUserId"));
                                    noticeModel.setNoticeCreateDate(jsonObject2.optString("NoticeCreateDate"));
                                    noticeModel.setNoticeExpiryDate(jsonObject2.optString("NoticeExpiryDate"));
                                    noticeModel.setContactpersonname(jsonObject2.optString("contactpersonname"));
                                    noticeModel.setContactpersonnumber(jsonObject2.optString("contactpersonnumber"));
                                    noticeModel.setContactpersonemail(jsonObject2.optString("contactpersonemail"));
                                    noticeModel.setNoticeID(jsonObject2.optString("NoticeID"));
                                    noticeModelsList.add(noticeModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        noticeAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        if( error instanceof NetworkError) {
                            Toast.makeText(NoticeBoard.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ServerError) {
                            Toast.makeText(NoticeBoard.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                        } else if( error instanceof AuthFailureError) {
                            Toast.makeText(NoticeBoard.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                        } else if( error instanceof ParseError) {
                            Toast.makeText(NoticeBoard.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                        } else if( error instanceof NoConnectionError) {
                            Toast.makeText(NoticeBoard.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                        } else if( error instanceof TimeoutError) {
                            Toast.makeText(NoticeBoard.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("userid",sharedHelper.getKey(NoticeBoard.this,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(NoticeBoard.this,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

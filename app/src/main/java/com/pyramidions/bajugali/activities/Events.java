package com.pyramidions.bajugali.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.dataModels.BillModel;
import com.pyramidions.bajugali.dataModels.OnGoingDataModel;
import com.pyramidions.bajugali.fragments.OngoingeventsFragment;
import com.pyramidions.bajugali.fragments.PreviouseventsFragment;
import com.pyramidions.bajugali.fragments.UpcomingeventsFragment;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedHelper sharedHelper;
    private TextView toolbar_tv;
    public static ArrayList<OnGoingDataModel> onGoingDataModelList = new ArrayList<OnGoingDataModel>();
    public static ArrayList<OnGoingDataModel> upcomingDataModelList = new ArrayList<OnGoingDataModel>();
    public static ArrayList<OnGoingDataModel> previousDataModelList = new ArrayList<OnGoingDataModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        sharedHelper = new SharedHelper();

        toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
     //   toolbar.setTitle("EVENTS");
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("EVENTS");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
      //  operation();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OngoingeventsFragment(), "ONGOING");
        adapter.addFragment(new UpcomingeventsFragment(), "UPCOMING");
        adapter.addFragment(new PreviouseventsFragment(), "PREVIOUS");
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


 /* private void operation() {
        final String url ="http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/eventinformation";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.v("RespoEvent",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1") ){
                                JSONObject jsonObject3 = jsonObject.optJSONObject("response");

                                JSONArray jsonArray = jsonObject3.getJSONArray("eventlist");
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
                                    onGoingDataModelList.add(dataModel);
                                }

                                JSONArray jsonArray1 = jsonObject3.getJSONArray("upcoming");
                                for (int j=0;j<jsonArray1.length();j++){
                                    JSONObject jsonObject21 = jsonArray1.optJSONObject(j);
                                    String EventName = jsonObject21.optString("EventName");
                                    String date = jsonObject21.optString("date");
                                    String time = jsonObject21.optString("time");
                                    String Chiefguest = jsonObject21.optString("Chiefguest");
                                    String EventImage = jsonObject21.optString("EventImage");
                                    OnGoingDataModel dataModel = new OnGoingDataModel();
                                    dataModel.setChiefguest(Chiefguest);
                                    dataModel.setEventName(EventName);
                                    dataModel.setDate(date);
                                    dataModel.setTime(time);
                                    dataModel.setEventImage(EventImage);
                                    upcomingDataModelList.add(dataModel);
                                }

                                JSONArray jsonArray11 = jsonObject3.getJSONArray("previous");
                                for (int k=0;k<jsonArray11.length();k++){
                                    JSONObject jsonObject21 = jsonArray11.optJSONObject(k);
                                    String EventName = jsonObject21.optString("EventName");
                                    String date = jsonObject21.optString("date");
                                    String time = jsonObject21.optString("time");
                                    String Chiefguest = jsonObject21.optString("Chiefguest");
                                    String EventImage = jsonObject21.optString("EventImage");
                                    OnGoingDataModel dataModel = new OnGoingDataModel();
                                    dataModel.setChiefguest(Chiefguest);
                                    dataModel.setEventName(EventName);
                                    dataModel.setDate(date);
                                    dataModel.setTime(time);
                                    dataModel.setEventImage(EventImage);
                                    previousDataModelList.add(dataModel);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      //  billAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Events.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-API-KEY","bajugali");
                params.put("userid",sharedHelper.getKey(Events.this,"USERID"));
                params.put("accesstoken",sharedHelper.getSignUserToken(Events.this,"accessToken"));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }*/
package com.pyramidions.bajugali.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class RentSaleAdvFragment extends Fragment {

    private Context context;
    private SharedHelper sharedHelper;
    private Spinner spinner_RentSaleData;
    private EditText ed_RentSale_Price, ed_RentSale_Description;
    private String spinnerData = "", price = "", des = "";
    private Button btn_Edit;
    private ProgressDialog progressDialog;
    private TextView tv_RentSale_DescriptionLabel,tv_RentSale_PriceLabel;
    private CardView cardViewRentSale;

    public RentSaleAdvFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rent_sale_adv, container, false);
        context = getActivity();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        sharedHelper = new SharedHelper();
        spinner_RentSaleData = (Spinner) view.findViewById(R.id.spinner_RentSaleData);
        ed_RentSale_Price = (EditText) view.findViewById(R.id.ed_RentSale_Price);
        ed_RentSale_Description = (EditText) view.findViewById(R.id.ed_RentSale_Description);
        tv_RentSale_DescriptionLabel = (TextView) view.findViewById(R.id.tv_RentSale_DescriptionLabel);
        tv_RentSale_PriceLabel = (TextView) view.findViewById(R.id.tv_RentSale_PriceLabel);
        cardViewRentSale = (CardView) view.findViewById(R.id.cardViewRentSale);
        btn_Edit = (Button) view.findViewById(R.id.btn_Edit);
        spinnerData();
        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = ed_RentSale_Price.getText().toString().trim();
                des = ed_RentSale_Description.getText().toString().trim();
                if (price.length() == 0 && des.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();

                } else if (price.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Price Is Required Field", Toast.LENGTH_SHORT).show();

                } else if (des.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Description Is Required Field", Toast.LENGTH_SHORT).show();

                } else {
                    operation();
                }


            }


        });

        return view;
    }

    private void operation() {
        progressDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BaseUrl + Constants.Flatrent_Sale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.v("Flatrent_Sale", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String responseCode = jsonObject.optString("responseCode");
                            if (responseCode.equalsIgnoreCase("1")) {
                                try {
                                    Fragment fragment = new Home();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    @SuppressLint("CommitTransaction") FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.flContent, fragment);
                                    transaction.commit();
                                    Toast.makeText(context, jsonObject.optString("responseMessage") + "", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(context, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(context, "accessToken"));
                params.put("type", spinnerData);
                params.put("price", ed_RentSale_Price.getText().toString().trim());
                params.put("description", ed_RentSale_Description.getText().toString().trim());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private void spinnerData() {
        String data[] = new String[]{"None", "Rent", "Sale"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
        spinner_RentSaleData.setAdapter(arrayAdapter);
        spinner_RentSaleData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = parent.getItemAtPosition(position).toString();
                if (spinnerData.equalsIgnoreCase("None")) {
                    ed_RentSale_Price.setText("0.00");
                    ed_RentSale_Description.setText("none");
                    tv_RentSale_DescriptionLabel.setVisibility(View.GONE);
                    ed_RentSale_Description.setVisibility(View.GONE);
                    tv_RentSale_PriceLabel.setVisibility(View.GONE);
                    ed_RentSale_Price.setVisibility(View.GONE);
                    cardViewRentSale.setVisibility(View.GONE);
                } else {
                    ed_RentSale_Price.setText("");
                    ed_RentSale_Description.setText("");
                    tv_RentSale_DescriptionLabel.setVisibility(View.VISIBLE);
                    ed_RentSale_Description.setVisibility(View.VISIBLE);
                    tv_RentSale_PriceLabel.setVisibility(View.VISIBLE);
                    ed_RentSale_Price.setVisibility(View.VISIBLE);
                    cardViewRentSale.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}

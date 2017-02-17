package com.pyramidions.bajugali.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.helpers.AppController;
import com.pyramidions.bajugali.helpers.AppHelper;
import com.pyramidions.bajugali.helpers.SharedHelper;
import com.pyramidions.bajugali.helpers.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PromoteMyBussiness extends AppCompatActivity {
    private TextView toolbar_tv, ed_pmpt_Checkboxdata;
    private ImageView iv_SubmitAd, imageViewSelect;
    private EditText ed_pmpt_Title, ed_pmpt_Category, ed_pmpt_Description, ed_pmpt_Budjet;
    private static final int SELECT_PHOTO = 100;
    private String title, category, desc, budjet, cbData = "", rbData = "";
    private SharedHelper sharedHelper;
    private ProgressDialog progressDialog;
    private CheckBox pmpt_Checkbox;
    private RadioButton pmpt_Rb_Sale, pmpt_Rb_Rent;
    private Bitmap bitmap;
    private RadioGroup pmpt_Rg;
    private CardView submitRequest_CardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_my_bussiness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlobal);
        toolbar.setNavigationIcon(R.drawable.ic_left_action);
        toolbar_tv = (TextView) toolbar.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("PROMOTE MY BUSINESS");

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        sharedHelper = new SharedHelper();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);

        ed_pmpt_Title = (EditText) findViewById(R.id.ed_pmpt_Title);
        ed_pmpt_Category = (EditText) findViewById(R.id.ed_pmpt_Category);
        ed_pmpt_Description = (EditText) findViewById(R.id.ed_pmpt_Description);
        ed_pmpt_Budjet = (EditText) findViewById(R.id.ed_pmpt_Budjet);
        ed_pmpt_Checkboxdata = (TextView) findViewById(R.id.ed_pmpt_Checkboxdata);
        iv_SubmitAd = (ImageView) findViewById(R.id.iv_SubmitAd);
        imageViewSelect = (ImageView) findViewById(R.id.imageViewSelect);
        pmpt_Checkbox = (CheckBox) findViewById(R.id.pmpt_Checkbox);
        pmpt_Rb_Sale = (RadioButton) findViewById(R.id.pmpt_Rb_Sale);
        pmpt_Rb_Rent = (RadioButton) findViewById(R.id.pmpt_Rb_Rent);
        pmpt_Rg = (RadioGroup) findViewById(R.id.pmpt_Rg);

        imageViewSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
            }
        });

        pmpt_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pmpt_Checkbox.isChecked()){
                    cbData = "enabled";
                  //  Toast.makeText(PromoteMyBussiness.this, cbData+"", Toast.LENGTH_SHORT).show();
                }else {
                    cbData = "disabled";
                  //  Toast.makeText(PromoteMyBussiness.this, cbData+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

      /* pmpt_Rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.pmpt_Rb_Rent:
                       rbData = "0";
                      // Toast.makeText(PromoteMyBussiness.this, rbData +"", Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.pmpt_Rb_Sale:
                       rbData = "1";
                     //  Toast.makeText(PromoteMyBussiness.this, rbData +"", Toast.LENGTH_SHORT).show();
                       break;
               }
           }
       });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv_SubmitAd.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void submitForApproval(View view) {
        title = ed_pmpt_Title.getText().toString().trim();
        desc = ed_pmpt_Description.getText().toString().trim();
        budjet = ed_pmpt_Budjet.getText().toString().trim();
        category = ed_pmpt_Category.getText().toString().trim();

        try {
            if (bitmap.equals(null) && title.length()==0 && desc.length()==0 && budjet.length()==0 && rbData.length()==0) {
                Toast.makeText(this, " All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            else if (bitmap.equals(null)){
                Toast.makeText(this, " Image is Required Filed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            else if (title.length()==0){
                ed_pmpt_Title.setError("Required Field !");
                ed_pmpt_Title.requestFocus();
                progressDialog.dismiss();
            }
            else if (category.length()==0){
                ed_pmpt_Category.setError("Required Field !");
                ed_pmpt_Category.requestFocus();
                progressDialog.dismiss();
            }
            else if (desc.length()==0){
                ed_pmpt_Description.setError("Required Field !");
                ed_pmpt_Description.requestFocus();
                progressDialog.dismiss();
            }
            else if (budjet.length()==0){
                ed_pmpt_Budjet.setError("Required Field !");
                ed_pmpt_Budjet.requestFocus();
                progressDialog.dismiss();
            }
            else {
                operation();
            }
        }catch (Exception e){
            Toast.makeText(this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



    }

    private void operation() {
        progressDialog.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.BaseUrl + Constants.PromoteAdvertisement, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                Log.d("ImageResponce1", response.toString());
                String res = new String(response.data);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    Log.d("MultipartRespo", jsonObject.toString());
                    if (jsonObject.optString("responseCode").equalsIgnoreCase("1")){
                        onBackPressed();
                        finish();
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        Toast.makeText(PromoteMyBussiness.this, jsonObject.optString("response")+"", Toast.LENGTH_SHORT).show();
                    }
                    else if (jsonObject.optString("responseCode").equalsIgnoreCase("0")){
                        Toast.makeText(PromoteMyBussiness.this, jsonObject.optString("responseMessage")+"", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if( error instanceof NetworkError) {
                    Toast.makeText(PromoteMyBussiness.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(PromoteMyBussiness.this,"Oops ! Something went wrong in server side",Toast.LENGTH_LONG).show();
                } else if( error instanceof AuthFailureError) {
                    Toast.makeText(PromoteMyBussiness.this,"Oops ! Already Signed In Other Device",Toast.LENGTH_LONG).show();
                } else if( error instanceof ParseError) {
                    Toast.makeText(PromoteMyBussiness.this,"Oops ! Something went wrong in fetching data...",Toast.LENGTH_LONG).show();
                } else if( error instanceof NoConnectionError) {
                    Toast.makeText(PromoteMyBussiness.this,"Oops ! No Internet Connection",Toast.LENGTH_LONG).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(PromoteMyBussiness.this,"Bad Network ! Slow loading reload it again...  ",Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-API-KEY", "bajugali");
                params.put("userid", sharedHelper.getKey(PromoteMyBussiness.this, "USERID"));
                params.put("accesstoken", sharedHelper.getSignUserToken(PromoteMyBussiness.this, "accessToken"));
                params.put("adtitle", title);
                params.put("addescription", desc);
                params.put("category", category);
                params.put("budget", budjet);
                params.put("displaycontactnumber", cbData);
                return params;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws AuthFailureError {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                params.put("adimages", new VolleyMultipartRequest.DataPart("image1.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), iv_SubmitAd.getDrawable()), "image/jpeg"));
                return params;
            }
        };
        AppController.getInstance()
                .addToRequestQueue(volleyMultipartRequest);
    }
}

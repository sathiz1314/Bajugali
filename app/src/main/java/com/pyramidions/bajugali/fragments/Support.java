package com.pyramidions.bajugali.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pyramidions.bajugali.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support extends Fragment {
    private WebView webViewAbt;
    private WebSettings webSettings;
    private Context context;
    private ProgressDialog progressDialog;

    public Support() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_support, container, false);
        context = getActivity();
        progressDialog = new ProgressDialog(context);
        webViewAbt = (WebView) view.findViewById(R.id.webViewAbt);
        webSettings = webViewAbt.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewAbt.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        webViewAbt.loadUrl("http://dazecorp.com/demos/Bajugali/bajugalirules/terms_and_conditions.html");
        return view;
    }

}

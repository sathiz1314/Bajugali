package com.pyramidions.bajugali.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.pyramidions.bajugali.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Aboutus extends Fragment {

    private WebView webViewAbt;
    private WebSettings webSettings;
    private Context context;
    private WebView mWebviewPop;
    private FrameLayout mContainer;
    private Context mContext;
    private ProgressDialog progressDialog;
    public Aboutus() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_aboutus, container, false);
        context = getActivity();
        progressDialog = new ProgressDialog(context);
        webViewAbt = (WebView) view.findViewById(R.id.webViewAbt);
        webSettings = webViewAbt.getSettings();
        webSettings.setJavaScriptEnabled(true);
      //  webViewAbt.setWebChromeClient(new WebChromeClient());
     //   webViewAbt.setWebViewClient(new MyCustomWebViewClient());
       /* webViewAbt.setWebViewClient(new WebViewClient(){
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
        });*/
        webViewAbt.loadUrl("http://dazecorp.com/demos/Bajugali/bajugalirules/about_us.html");
        return view;
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String host = Uri.parse(url).getHost();

            if (url.startsWith("http:") || url.startsWith("https:")) {

                if (Uri.parse(url).getPath().equals("/connection-compte.html")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dazecorp.com/demos/Bajugali/bajugalirules/about_us.html"));
                    startActivity(browserIntent);

                    return true;
                }

                if (host.equals("http://dazecorp.com/demos/Bajugali/bajugalirules/about_us.html")) {
                    if (mWebviewPop != null) {
                        mWebviewPop.setVisibility(View.GONE);
                        mContainer.removeView(mWebviewPop);
                        mWebviewPop = null;
                    }
                    return false;
                }
                if (host.equals("m.facebook.com") || host.equals("www.facebook.com") || host.equals("facebook.com")) {
                    return false;
                }
                // Otherwise, the link is not for a page on my site, so launch
                // another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            // Otherwise allow the OS to handle it
            else if (url.startsWith("tel:")) {
                Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(tel);
                return true;
            }
            //This is again specific for my website
            else if (url.startsWith("mailto:")) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("application/octet-stream");
                String AdressMail = new String(url.replace("mailto:", ""));
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{AdressMail});
                mail.putExtra(Intent.EXTRA_SUBJECT, "");
                mail.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(mail);
                return true;
            }
            return true;
        }


    }

}

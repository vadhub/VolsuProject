package com.vad.volsuproject;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.pushnotification.JobSchedulerHelper;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private boolean isRedirect = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mWebView = (WebView) findViewById(R.id.webViewVolsu);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //enable js
        mWebView.getSettings().setJavaScriptEnabled(true);

        //for get data from datastorage
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);

        //items from datastorege
        //_ym_uid
        //_ym_cc
        //_ym_zzlc

        getItemDataStorage("_ym_uid");

        mWebView.loadUrl("https://lk.volsu.ru/student/index");

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void getItemDataStorage(String item) {
        String script = "(function(){" +
                "var x = ''; " +
                "  x = x + localStorage.key(3); x = x + ': '; x = x + localStorage.getItem('" + item + "');" +
                "return x;" +
                "})();";

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.evaluateJavascript(script, value -> Toast.makeText(MainActivity.this, "" + value, Toast.LENGTH_SHORT).show());
                view.loadUrl(url);
                isRedirect = true;
                return false;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (!isRedirect) {
                    view.setAlpha((float) 0.5);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                isRedirect = true;
                view.setAlpha(1);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
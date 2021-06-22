package com.vad.volsuproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.socketresponse.Client;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //JobSchedulerHelper.jobScheduler(this);

        Client client = new Client();

        mWebView = (WebView) findViewById(R.id.webViewVolsu);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //enable js
        mWebView.getSettings().setJavaScriptEnabled(true);

        //for get data from datastorage
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadUrl("https://lk.volsu.ru/student/index");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        //items from datastorege
        //_ym_uid
        //_ym_cc
        //_ym_zzlc

        getItemDatastorege("_ym_uid");

        new Thread(new Runnable() {
            @Override
            public void run() {
                client.getMessage();
            }
        }).start();

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
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
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

    private void getItemDatastorege(String item){
        String script = "(function(){" +
                "var x = ''; "+
                "  x = x + localStorage.key(3); x = x + ': '; x = x + localStorage.getItem('"+item+"');" +
                "return x;" +
                "})();";

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                    }
                });
                super.onPageFinished(view, url);
            }
        });
    }
}
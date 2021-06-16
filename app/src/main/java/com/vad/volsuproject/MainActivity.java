package com.vad.volsuproject;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.socketresponse.Client;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //JobSchedulerHelper.jobScheduler(this);

        mWebView = (WebView) findViewById(R.id.webViewVolsu);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadUrl("https://lk.volsu.ru/student/index");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                client.getMessage();
//            }
//        }).start();

    }


}
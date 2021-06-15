package com.vad.volsuproject;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.socketresponse.Client;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private String URL = "https://lk.volsu.ru/student/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //JobSchedulerHelper.jobScheduler(this);

        mWebView = (WebView) findViewById(R.id.webViewVolsu);
        mWebView.loadUrl(URL);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.getMessage();
            }
        }).start();

    }


}
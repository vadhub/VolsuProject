package com.vad.volsuproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.pushnotification.JobSchedulerHelper;
import com.vad.volsuproject.socketresponse.Client;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //JobSchedulerHelper.jobScheduler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.getMessage();
            }
        }).start();

    }


}
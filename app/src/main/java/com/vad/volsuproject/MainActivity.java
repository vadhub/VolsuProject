package com.vad.volsuproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.pushnotification.JobSchedulerHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JobSchedulerHelper.jobScheduler(this);

    }


}
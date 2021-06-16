package com.vad.volsuproject.pushnotification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class JobSchedulerHelper {

    private final static int JOB_ID = 12340;
    public static void jobScheduler(Context context){
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID,
                new ComponentName(context, JobServiceNotification.class))
                .setMinimumLatency(10*1000)
                .setOverrideDeadline(30*1000)
                .build();

        JobScheduler scheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);
    }
}


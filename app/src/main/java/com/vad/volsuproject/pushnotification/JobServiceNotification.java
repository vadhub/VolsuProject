package com.vad.volsuproject.pushnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.vad.volsuproject.R;

import com.vad.volsuproject.websector.Notificate;
import com.vad.volsuproject.websector.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobServiceNotification extends JobService {

    private static final String CHANNEL_ID = "idchennal";
    private Thread thread;

    private SharedPreferences sPref;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if(thread==null){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    response();
                    //showNotification("title", ""+Math.random()*100);
                }
            });
            thread.start();
        }
        JobSchedulerHelper.jobScheduler(this);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void response(){
        RetrofitClient.getRetrofit().getJsonApi().getNotificates().enqueue(new Callback<List<Notificate>>() {
            @Override
            public void onResponse(Call<List<Notificate>> call, Response<List<Notificate>> response) {
                String temp = "";
                if(response.isSuccessful()&&response.body()!=null)
                    for(Notificate notificate: response.body()){
                        temp+=notificate.getBody().getTitle();
                        temp+=notificate.getBody().getMessage();

                        if(!temp.equals(load())){
                            showNotification(notificate.getBody().getTitle(), notificate.getBody().getMessage());
                            save(temp);
                        }
                    }
            }

            @Override
            public void onFailure(Call<List<Notificate>> call, Throwable t) {
                Toast.makeText(JobServiceNotification.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(String title, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText(text);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void save(String temp){
        sPref = getSharedPreferences("tempData", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("dataTemp", temp);
        ed.commit();
    }

    private String load(){
        sPref = getSharedPreferences("tempData", MODE_PRIVATE);
        return sPref.getString("dataTemp", "null");
    }
}

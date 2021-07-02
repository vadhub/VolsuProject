package com.vad.volsuproject.pushnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
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

    private static final String CHANNEL_ID = "idChannelVolsu";
    private static final int notificationId = 133344;
    private Thread thread;

    private SharedPreferences sPref;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        createNotificationChannel();
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

    private void createNotificationChannel(){

        //NotificationManager.IMPORTANCE_HIGH - sound and interrupt height, with image in toolbar
        //NotificationManager.IMPORTANCE_DEFAULT - sound and interrupt not, with image in toolbar
        //NotificationManager.IMPORTANCE_LOW - without sound and interrupt not, without image in toolbar
        //NotificationManager.IMPORTANCE_MIN - the same as NotificationManager.IMPORTANCE_LOW
        //NotificationManager.IMPORTANCE_NONE - without notification
        //NotificationManager.IMPORTANCE_UNSPECIFIED - the same as NotificationManager.IMPORTANCE_LOW


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "notificationChannel";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
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
        notificationManager.notify(notificationId, notification);

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

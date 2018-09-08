package com.example.user.moviecatalogappv2.utils.upcoming;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.user.moviecatalogappv2.API.APIResponder;
import com.example.user.moviecatalogappv2.DetailActivity;
import com.example.user.moviecatalogappv2.MVP_Core.model.upcoming_data.ResultsItem;
import com.example.user.moviecatalogappv2.MVP_Core.model.upcoming_data.UpcomingModel;
import com.example.user.moviecatalogappv2.MainActivity;
import com.example.user.moviecatalogappv2.R;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerService extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    private Call<UpcomingModel> apiCall;
    private APIResponder apiResponder = new APIResponder();

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)){
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }


    private void loadData() {
        apiCall = apiResponder.getService().getUpcomingMovie();
        apiCall.enqueue(new Callback<UpcomingModel>() {
            @Override
            public void onResponse(Call<UpcomingModel> call, Response<UpcomingModel> response) {
                if (response.isSuccessful()){
                    List<ResultsItem>items = response.body().getResults();
                    int index = new Random().nextInt(items.size());

                    ResultsItem item = items.get(index);
                    String title = items.get(index).getTitle();
                    String message = items.get(index).getOverview();
                    int notificationId = 200;

                    showNotification(getApplicationContext(),title,message,notificationId,item);
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<UpcomingModel> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this,"Sorry Failed to load data.\\nPlease check your Internet connections!",Toast.LENGTH_SHORT).show();
    }
    private void showNotification(Context ctx, String title, String message, int notificationId, ResultsItem item) {
        NotificationManager notificationManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(ctx, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_ITEM, new Gson().toJson(item));
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,notificationId,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.movie_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(ctx,android.R.color.holo_purple))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1200,1200,1200,1200,1200})
                .setSound(alarmSound);

        notificationManager.notify(notificationId, builder.build());

    }


}

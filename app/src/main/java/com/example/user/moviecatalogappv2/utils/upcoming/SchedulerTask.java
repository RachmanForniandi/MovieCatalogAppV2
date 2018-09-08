package com.example.user.moviecatalogappv2.utils.upcoming;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager gcmNetworkManager;

    public SchedulerTask(Context context){
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void initiatePeriodicTask(){
        Task initiateTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(60)
                .setFlex(12)
                .setTag(SchedulerService.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(initiateTask);
    }

    public void abortPeriodicTask(){
        if (gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(SchedulerService.TAG_TASK_UPCOMING,SchedulerService.class);
        }
    }
}

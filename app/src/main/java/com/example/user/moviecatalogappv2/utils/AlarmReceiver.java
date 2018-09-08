package com.example.user.moviecatalogappv2.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.user.moviecatalogappv2.MainActivity;
import com.example.user.moviecatalogappv2.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING ="RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE= "type";

    public static final int NOTIFICATION_ID_ONETIME=100;
    public static final int NOTIFICATION_ID_REPEATING=110;

    public AlarmReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? "One Time Alarm" : "Repeating Alarm";
        int notificationId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIFICATION_ID_ONETIME : NOTIFICATION_ID_REPEATING;

        title = context.getResources().getString(R.string.app_name);
        
        showAlarmNotification(context, title, message, notificationId);

        
    }

    private void showAlarmNotification(Context context, String title, String message, int notificationId) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,notificationId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.movie_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context,android.R.color.holo_blue_light))
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1200,1200,1200,1200,1200})
                .setSound(alarmSound);

        notificationManager.notify(notificationId, builder.build());
    }

    public void setOneTimeAlarm(Context context, String type, String date, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent= new Intent(context,AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String dateArray[] = date.split("-");
        String timeArray[] = date.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH,Integer.parseInt(dateArray[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateArray[2]));
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int uniqueCode = NOTIFICATION_ID_ONETIME;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,uniqueCode,intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }

    public void setRepeatingTime(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent= new Intent(context,AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int uniqueCode = NOTIFICATION_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,uniqueCode,intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent= new Intent(context,AlarmReceiver.class);

        int uniqueCode = type.equalsIgnoreCase(TYPE_ONE_TIME)? NOTIFICATION_ID_ONETIME : NOTIFICATION_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,uniqueCode,intent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
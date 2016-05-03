package com.gravestristan.mypda;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;

/**
 *
 * Created by Tristan on 4/12/2016.
 */
public class ScheduleNotificationService extends IntentService implements AppStatics{

    private static final String TAG = "NotificationService";

    private static final int NOTIFICATION_INTERVAL = 1000000 * 18;

    /**
     *
     */
    public ScheduleNotificationService(){
        super(TAG);
    }

    /**
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent){

        PDADBHandler dbHandler = new PDADBHandler(getApplicationContext(), null, null, DATABASE_VERSION);
        if(dbHandler.checkForUpcomingTasks()){
            Resources r = getResources();
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MyPDAMainActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(r.getString(R.string.upcoming_schedule_items))
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle(r.getString(R.string.upcoming_schedule_items_title))
                    .setContentText(r.getString(R.string.upcoming_schedule_items_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }
        dbHandler.close();
    }

    /**
     *
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = new Intent(context, ScheduleNotificationService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), NOTIFICATION_INTERVAL, pi);
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean isServiceAlarmOn(Context context){
        Intent i = new Intent(context, ScheduleNotificationService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}

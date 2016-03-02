package com.gravestristan.mypda;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tristan on 2/20/2016.
 */
public class PDASingleton {

    private static PDASingleton sPDASingleton;

    private ArrayList<ScheduleItems> mScheduleItems;

    private Context mAppContext;

    /**
     *
     * @param appContext
     */
    private PDASingleton(Context appContext) {
        mAppContext = appContext;
        mScheduleItems = new ArrayList<ScheduleItems>();

        // Populate with dummy data here
        /*for (int i = 0; i < 5; i++) {
            ScheduleItems scheduleItem = new ScheduleItems();
            scheduleItem.setTaskName("sample name #" + i);
            mScheduleItems.add(scheduleItem);
        }*/

    }

    /**
     *
     * @param context
     * @return
     */
    public static PDASingleton get(Context context) {
        if(sPDASingleton == null){
            sPDASingleton = new PDASingleton(context.getApplicationContext());
        }
        return sPDASingleton;
    }

    /**
     *
     * @return
     */
    public ArrayList<ScheduleItems> getScheduleNames(){
        return mScheduleItems;
    }

    /**
     *
     * @param id
     * @return
     */
    public ScheduleItems getScheduleItem(UUID id) {
        for (ScheduleItems scheduleItem : mScheduleItems) {
            if(scheduleItem.getId().equals(id)){
                return scheduleItem;
            }
        }
        return null;
    }
}

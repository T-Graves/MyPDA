package com.gravestristan.mypda;

import android.content.Context;

/**
 * Created by Tristan on 2/20/2016.
 */
public class PDASingleton {

    private static PDASingleton sPDASingleton;

    private Context mAppContext;

    /**
     *
     * @param appContext
     */
    private PDASingleton(Context appContext) {
        mAppContext = appContext;

        // Populate with dummy data here

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

}

package com.gravestristan.mypda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 *
 * This Class is used to add a fragment to the activity.
 * It's used to move this class out of the main activity class making it cleaner.
 *
 * Created by Tristan on 2/20/2016.
 */
public abstract class FragmentActivityStarter extends AppCompatActivity implements AppStatics{

    ArrayList<ScheduleItems> mScheduleItems;

    protected abstract Fragment createFragment();

    /**
     * This is the onCreate method for the apps main activity. It is moved into a separate class
     * to make the MyPDAMainActivity cleaner. This method will load any saved preferences and it
     * will display the first fragment that the app uses.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pdamain);

        loadSavedPreferences();


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }

        });
    }

    /**
     * This method loads saved preferences when called.
     */
    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notificationIsOn = sharedPreferences.getBoolean("notify", true);
        if(notificationIsOn){
            ScheduleNotificationService.setServiceAlarm(this, notificationIsOn);
        }else{
            ScheduleNotificationService.setServiceAlarm(this, notificationIsOn);
        }
    }

    /**
     * This method makes sure the home and up buttons are still displayed when a fragment is
     * resumed after rotation.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
        if (stackHeight > 0) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }
}

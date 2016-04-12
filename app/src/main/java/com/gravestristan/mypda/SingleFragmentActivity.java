package com.gravestristan.mypda;

import android.os.Bundle;
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
public abstract class SingleFragmentActivity extends AppCompatActivity implements AppStatics{

    ArrayList<ScheduleItems> mScheduleItems;

    protected abstract Fragment createFragment();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pdamain);


        mScheduleItems = PDASingleton.get(getApplicationContext()).getScheduleItems();
        if(mScheduleItems.isEmpty()){
            PDADBHandler dbHandler = new PDADBHandler(this, null, null, DATABASE_VERSION);
            dbHandler.getAllTaskItems();
        }

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

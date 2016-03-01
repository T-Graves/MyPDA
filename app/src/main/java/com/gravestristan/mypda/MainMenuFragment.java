package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * The main fragment that the user will see when the app starts up. It will
 * handle the navigation to the other pages.
 * Created by Tristan on 2/20/2016.
 */
public class MainMenuFragment extends Fragment implements AppStatics{

    Button mScheduleButton;
    Button mNotesButton;
    Button mWorkoutsButton;
    Button mGeneralButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        mScheduleButton = (Button) view.findViewById(R.id.schedule);
        mScheduleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Schedule button clicked");
                ScheduleItemsMenuFragment scheduleItems = new ScheduleItemsMenuFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, scheduleItems);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mNotesButton = (Button) view.findViewById(R.id.notes);
        mNotesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Notes button clicked");
            }
        });

        mWorkoutsButton = (Button) view.findViewById(R.id.workouts);
        mWorkoutsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Workouts button clicked");
            }
        });

        mGeneralButton = (Button) view.findViewById(R.id.general);
        mGeneralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "General button clicked");
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "main menu fragment destroyed");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "main menu fragment detached");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "main menu fragment paused");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "main menu fragment resumed");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "main menu fragment started");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "main menu fragment stopped");
        super.onStop();
    }
}

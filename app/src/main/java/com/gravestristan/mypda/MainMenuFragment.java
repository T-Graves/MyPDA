package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The main fragment that the user will see when the app starts up. It will
 * handle the navigation to the other pages.
 * Created by Tristan on 2/20/2016.
 */
public class MainMenuFragment extends Fragment implements AppStatics{

    TextView mTaskNumberOne;
    TextView mTaskNumberTwo;
    TextView mTaskNumberThree;

    Button mScheduleButton;
    Button mNotesButton;
    Button mWorkoutsButton;
    Button mGeneralButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

        ArrayList<ScheduleItems> topThree = dbHandler.getThreeClosestTaskItems();

        mTaskNumberOne = (TextView) view.findViewById(R.id.schedule_item_one);
        mTaskNumberTwo = (TextView) view.findViewById(R.id.schedule_item_Two);
        mTaskNumberThree = (TextView) view.findViewById(R.id.schedule_item_Three);
        if(topThree.size() == 1){
            mTaskNumberOne.setText(topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate());
            mTaskNumberTwo.setText("");
            mTaskNumberThree.setText("");
        }else if(topThree.size() == 2){
            mTaskNumberOne.setText(topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate());
            mTaskNumberTwo.setText(topThree.get(1).getTaskName() + " : " + topThree.get(1).getTaskDate());
            mTaskNumberThree.setText("");
        }else if(topThree.size() == 3){
            mTaskNumberOne.setText(topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate());
            mTaskNumberTwo.setText(topThree.get(1).getTaskName() + " : " + topThree.get(1).getTaskDate());
            mTaskNumberThree.setText(topThree.get(2).getTaskName() + " : " + topThree.get(2).getTaskDate());
        }else{
            mTaskNumberOne.setText("");
            mTaskNumberTwo.setText("You Have No Tasks Yet");
            mTaskNumberThree.setText("");
        }

        mScheduleButton = (Button) view.findViewById(R.id.schedule_button);
        mScheduleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Schedule button clicked");
                ScheduleItemsMenuFragment scheduleItems = new ScheduleItemsMenuFragment();

                swapFragmentHandler(scheduleItems);
            }
        });

        mNotesButton = (Button) view.findViewById(R.id.notes_button);
        mNotesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Notes button clicked");
                NotesMenuFragment notesMenu = new NotesMenuFragment();

                swapFragmentHandler(notesMenu);
            }
        });

        mWorkoutsButton = (Button) view.findViewById(R.id.workouts_button);
        mWorkoutsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Workouts button clicked");
                WorkoutsMenuFragment workoutsMenu = new WorkoutsMenuFragment();

                swapFragmentHandler(workoutsMenu);
            }
        });

        mGeneralButton = (Button) view.findViewById(R.id.general_button);
        mGeneralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "General button clicked");
                GeneralsMenuFragment generalsMenu = new GeneralsMenuFragment();

                swapFragmentHandler(generalsMenu);
            }
        });

        return view;
    }

    /**
     * This method is used to reduce redundant code. It takes in a new fragment and swaps it out
     * with the current fragment. it puts the current fragment on the backstack to be returned later.
     * @param newFragment The fragment to change to.
     */
    private void swapFragmentHandler(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "main menu fragment destroyed");
        super.onDestroy();
    }

    /**
     *
     */
    @Override
    public void onDetach() {
        Log.d(TAG, "main menu fragment detached");
        super.onDetach();
    }

    /**
     *
     */
    @Override
    public void onPause() {
        Log.d(TAG, "main menu fragment paused");
        super.onPause();
    }

    /**
     *
     */
    @Override
    public void onResume() {
        Log.d(TAG, "main menu fragment resumed");
        super.onResume();
    }

    /**
     *
     */
    @Override
    public void onStart() {
        Log.d(TAG, "main menu fragment started");
        super.onStart();
    }

    /**
     *
     */
    @Override
    public void onStop() {
        Log.d(TAG, "main menu fragment stopped");
        super.onStop();
    }
}

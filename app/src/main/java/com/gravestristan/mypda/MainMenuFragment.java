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

    private ArrayList<ScheduleItems> topThree;

    private TextView mTaskNumberOne;
    private TextView mTaskNumberTwo;
    private TextView mTaskNumberThree;

    private Button mScheduleButton;
    private Button mNotesButton;
    private Button mCalculatorsButton;

    private CalculatorsMenuFragment calculatorsMenu;
    private NotesMenuFragment notesMenu;
    private ScheduleCalendar scheduleCalendar;

    /**
     * The onCreate method for the main menu fragment. Nothing is done here.
     * @param savedInstanceState The savedInstanceState to be used.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * The onCreateView method for the main menu fragment. All the navigation button click listeners
     * are set up here.
     * @param inflater The layout inflater being passed in.
     * @param container The view group being passed in.
     * @param savedInstanceState The bundle being passed in.
     * @return The inflated view is returned when this method is finished.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

        topThree = dbHandler.getThreeClosestTaskItems();
        dbHandler.close();

        mTaskNumberOne = (TextView) view.findViewById(R.id.schedule_item_one);
        mTaskNumberTwo = (TextView) view.findViewById(R.id.schedule_item_Two);
        mTaskNumberThree = (TextView) view.findViewById(R.id.schedule_item_Three);

        // Populate top three schedule items list
        topThreeHandler();

        mScheduleButton = (Button) view.findViewById(R.id.schedule_button);
        mScheduleButton.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                scheduleCalendar = new ScheduleCalendar();

                swapFragmentHandler(scheduleCalendar);
            }
        });

        mNotesButton = (Button) view.findViewById(R.id.notes_button);
        mNotesButton.setOnClickListener(new View.OnClickListener(){
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v){
                notesMenu = new NotesMenuFragment();

                swapFragmentHandler(notesMenu);
            }
        });

        mCalculatorsButton = (Button) view.findViewById(R.id.calculators_button);
        mCalculatorsButton.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                calculatorsMenu = new CalculatorsMenuFragment();

                swapFragmentHandler(calculatorsMenu);
            }
        });

        return view;
    }

    /**
     * This method handles populating the top three section with text. It has been moved out into a
     * separate method to keep the onCreateView method a little more clean.
     */
    public void topThreeHandler(){
        if(topThree.size() == 1){
            if(topThree.get(0).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(0).getTaskName().substring(0,24) + "... : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(taskNameAndDate);
            }
            mTaskNumberTwo.setText("");
            mTaskNumberThree.setText("");
        }else if(topThree.size() == 2){
            if(topThree.get(0).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(0).getTaskName().substring(0,24) + "... : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(taskNameAndDate);
            }

            if(topThree.get(1).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(1).getTaskName().substring(0,24) + "... : " + topThree.get(1).getTaskDate();
                mTaskNumberTwo.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(1).getTaskName() + " : " + topThree.get(1).getTaskDate();
                mTaskNumberTwo.setText(taskNameAndDate);
            }
            mTaskNumberThree.setText("");
        }else if(topThree.size() == 3){
            if(topThree.get(0).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(0).getTaskName().substring(0,24) + "... : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(0).getTaskName() + " : " + topThree.get(0).getTaskDate();
                mTaskNumberOne.setText(taskNameAndDate);
            }

            if(topThree.get(1).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(1).getTaskName().substring(0,24) + "... : " + topThree.get(1).getTaskDate();
                mTaskNumberTwo.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(1).getTaskName() + " : " + topThree.get(1).getTaskDate();
                mTaskNumberTwo.setText(taskNameAndDate);
            }

            if(topThree.get(2).getTaskName().length() > 25){
                String slimmedTaskNameAndDate = topThree.get(2).getTaskName().substring(0,24) + "... : " + topThree.get(2).getTaskDate();
                mTaskNumberThree.setText(slimmedTaskNameAndDate);
            }else{
                String taskNameAndDate = topThree.get(2).getTaskName() + " : " + topThree.get(2).getTaskDate();
                mTaskNumberThree.setText(taskNameAndDate);
            }
        }else{
            mTaskNumberOne.setText("");
            mTaskNumberTwo.setText("You Have No Upcoming Tasks");
            mTaskNumberThree.setText("");
        }
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
}

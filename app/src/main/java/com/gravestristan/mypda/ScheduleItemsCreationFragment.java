package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * The Schedule item creation fragment.
 * Created by Tristan on 3/1/2016.
 */
public class ScheduleItemsCreationFragment extends Fragment implements AppStatics {

    ArrayList<ScheduleItems> mScheduleItems;

    EditText mTaskName;
    EditText mTaskDate;
    EditText mTaskNote;

    Button mCreateButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mScheduleItems = PDASingleton.get(getActivity()).getScheduleNames();
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
        View view = inflater.inflate(R.layout.fragment_schedule_item_creation, container, false);

        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskDate = (EditText) view.findViewById(R.id.task_date);
        mTaskNote = (EditText) view.findViewById(R.id.task_note);

        mCreateButton = (Button) view.findViewById(R.id.new_task_button);

        // keep near bottom as it should be the last thing to happen. easier to follow this way
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleItems newItem = new ScheduleItems();

                newItem.setTaskName(mTaskName.getText().toString());
                newItem.setTaskDate(mTaskDate.getText().toString());
                newItem.setTaskNote(mTaskNote.getText().toString());

                mScheduleItems.add(newItem);
                getFragmentManager().popBackStack();

            }
        });
        return view;
    }

    /**
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

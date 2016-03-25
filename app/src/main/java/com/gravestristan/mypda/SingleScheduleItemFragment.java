package com.gravestristan.mypda;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Tristan on 3/1/2016.
 */
public class SingleScheduleItemFragment extends Fragment implements AppStatics {

    ArrayList<ScheduleItems> mScheduleItems;

    EditText mTaskName;
    EditText mTaskDate;
    EditText mTaskNote;

    Button mUpdateButton;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    private UUID mCurrentUUID;
    private int position;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mScheduleItems = PDASingleton.get(getActivity()).getScheduleItems();
        position = getArguments().getInt("currentItemPosition");
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

        mCurrentUUID = mScheduleItems.get(position).getId();

        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskName.setText(mScheduleItems.get(position).getTaskName());
        mTaskDate = (EditText) view.findViewById(R.id.task_date);
        mTaskDate.setText(mScheduleItems.get(position).getTaskDate());
        mTaskNote = (EditText) view.findViewById(R.id.task_note);
        mTaskNote.setText(mScheduleItems.get(position).getTaskNote());

        mUpdateButton = (Button) view.findViewById(R.id.create_or_update_button);
        mUpdateButton.setText("Update");

        mTaskDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleItems newItem = new ScheduleItems();

                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

                newItem.setId(mCurrentUUID);
                newItem.setTaskName(mTaskName.getText().toString());
                newItem.setTaskDate(mTaskDate.getText().toString());
                newItem.setTaskNote(mTaskNote.getText().toString());

                mScheduleItems.set(position, newItem);

                dbHandler.updateTask(mScheduleItems.get(position));
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }

    /**
     *
     */
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTaskDate.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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

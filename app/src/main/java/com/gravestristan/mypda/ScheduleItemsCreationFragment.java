package com.gravestristan.mypda;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mScheduleItems = PDASingleton.get(getActivity()).getScheduleItems();
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

        mCreateButton = (Button) view.findViewById(R.id.create_or_update_button);

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

        // keep near bottom as it should be the last thing to happen. easier to follow this way
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTaskDate.getText().toString().equals("")){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());
                    mTaskDate.setText(currentDate);
                }
                ScheduleItems newItem = new ScheduleItems();

                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

                newItem.setTaskName(mTaskName.getText().toString());
                newItem.setTaskDate(mTaskDate.getText().toString());
                newItem.setTaskNote(mTaskNote.getText().toString());

                mScheduleItems.add(newItem);
                dbHandler.addTask(newItem);
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

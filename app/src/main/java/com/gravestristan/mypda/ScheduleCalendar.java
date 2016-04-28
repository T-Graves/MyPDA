package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by student on 4/27/2016.
 */
public class ScheduleCalendar extends Fragment implements AppStatics {

    public Calendar month;
    public ScheduleCalendarAdapter adapter;

    private GridView mDateItems;
    private TextView mMonthAndYear;
    private TextView mBackOneMonth;
    private TextView mForwardOneMonth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_calendar, container, false);

        month = Calendar.getInstance(Locale.getDefault());
        //month.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);

        adapter = new ScheduleCalendarAdapter(getContext(), month);

        mDateItems = (GridView) view.findViewById(R.id.date_items);
        mDateItems.setAdapter(adapter);

        mMonthAndYear = (TextView) view.findViewById(R.id.month_and_year);
        mMonthAndYear.setText(DateFormat.format("MMMM yyyy", month));

        mBackOneMonth = (TextView) view.findViewById(R.id.back_one_month);
        mBackOneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
                    month.set((month.get(Calendar.YEAR)-1), month.getActualMaximum(Calendar
                            .MONTH),1);
                } else {
                    month.set(Calendar.MONTH, month.get(Calendar.MONTH) -1);
                }
                refreshCalendar();
            }
        });

        mForwardOneMonth = (TextView) view.findViewById(R.id.forward_one_month);
        mForwardOneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
                    month.set((month.get(Calendar.YEAR) + 1), month.getActualMinimum(Calendar
                            .MONTH),1);
                } else {
                    month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
                }
                refreshCalendar();
            }
        });

        mDateItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "day" + adapter.getItem(position));
                // use adapter.getItem(position) to find the day clicked.
                // pass the day to a method in the database handler to pull all schedule items
                // set for that day.
                // dbHandler fills an arraylist in the singleton that will be displayed by the
                // schedule main menu fragment that was formerly used.
                // also gonna need some better definition for the dates and arrows to make it
                // clear you can click on them and select using them
            }
        });

        return view;
    }

    public void refreshCalendar()
    {

        adapter.refreshDays();
        adapter.notifyDataSetChanged();

        mMonthAndYear.setText(DateFormat.format("MMMM yyyy", month));
    }

    /**
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //menu.findItem(R.id.action_new_task).setVisible(true);
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
            /*case R.id.action_new_task:
                ScheduleItemsCreationFragment createNewItem = new ScheduleItemsCreationFragment();
                swapFragmentHandler(createNewItem);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by student on 4/27/2016.
 */
public class ScheduleCalendar extends Fragment implements AppStatics {

    private ArrayList<ScheduleItems> mScheduleItems;

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
        mScheduleItems = PDASingleton.get(getActivity()).getScheduleItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_calendar, container, false);

        month = Calendar.getInstance(Locale.getDefault());

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
                if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
                    month.set((month.get(Calendar.YEAR) + 1), month.getActualMinimum(Calendar
                            .MONTH), 1);
                } else {
                    month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
                }
                refreshCalendar();
            }
        });

        mDateItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thisDay = adapter.getItem(position).toString();
                if (thisDay.length() == 1) {
                    thisDay = "0" + adapter.getItem(position).toString();
                }
                String thisMonth = "" + (month.get(Calendar.MONTH) + 1);
                if (thisMonth.length() == 1) {
                    thisMonth = "0" + (month.get(Calendar.MONTH) + 1);
                }
                String thisYear = "" + month.get(Calendar.YEAR);
                String fullDate = thisYear + "-" + thisMonth + "-" + thisDay;


                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
                dbHandler.getTasksByDate(fullDate);
                dbHandler.close();

                if (!mScheduleItems.isEmpty()) {
                    ScheduleItemsMenuFragment scheduleItems = new ScheduleItemsMenuFragment();
                    swapFragmentHandler(scheduleItems);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(view, "No schedule items for this date", Snackbar.LENGTH_SHORT);

                    snackbar.show();
                }
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
        menu.findItem(R.id.action_new_task).setVisible(true);
        menu.findItem(R.id.action_all_tasks).setVisible(true);
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
            case R.id.action_new_task:
                ScheduleItemsCreationFragment createNewItem = new ScheduleItemsCreationFragment();
                swapFragmentHandler(createNewItem);
                return true;
            case R.id.action_all_tasks:
                mScheduleItems = PDASingleton.get(getContext()).getScheduleItems();
                mScheduleItems.clear();
                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
                dbHandler.getAllTaskItems();
                dbHandler.close();
                ScheduleItemsMenuFragment scheduleItems = new ScheduleItemsMenuFragment();
                swapFragmentHandler(scheduleItems);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param newFragment
     */
    private void swapFragmentHandler(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

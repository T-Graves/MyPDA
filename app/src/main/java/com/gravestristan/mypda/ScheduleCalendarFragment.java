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
 * The ScheduleCalendarFragment. It creates a calendar that the user can use to access all created
 * Schedule items. The user can access the create Schedule items menu from here as well as see all
 * Schedule items at the same time.
 * Created by student on 4/27/2016.
 */
public class ScheduleCalendarFragment extends Fragment implements AppStatics {

    private ArrayList<ScheduleItems> mScheduleItems;

    public Calendar month;
    public ScheduleCalendarAdapter adapter;

    private GridView mDateItems;
    private TextView mMonthAndYear;
    private TextView mBackOneMonth;
    private TextView mForwardOneMonth;

    /**
     * The onCreate method for the ScheduleCalendarFragment fragment.
     * @param savedInstanceState The savedInstanceState being passed in.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mScheduleItems = PDASingleton.get(getActivity()).getScheduleItems();
    }

    /**
     * The onCreateView method of the ScheduleCalendarFragment fragment. It sets up all the view contents.
     * @param inflater The LayoutInflater being passed in.
     * @param container The ViewGroup being passed in.
     * @param savedInstanceState The savedInstanceState being passed in.
     * @return
     */
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
            /**
             * Sets the month backwards by one each time it is clicked. If the current month being shown
             * is January, it sets the year backwards by one and shows December.
             * @param v The view being passed in.
             */
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
            /**
             * Sets the month forward by one each time it is clicked. If the current month being shown
             * is December, it sets the year forward by 1 and shows January.
             * @param v The view being passed in.
             */
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
            /**
             * The onItemClick method for each date item.
             * It creates a full date string (yyyy-MM-dd) and sends it to the dbHandler.
             * It then sends the user to a fragment containing a recycler view that shows
             * all schedule items for that date. If the date clicked has no items the user is informed
             * using a SnackBar message.
             * @param parent The AdapterView<> parent being passed in.
             * @param view The View being passed in.
             * @param position The clicked item's position.
             * @param id The clicked item's id.
             */
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

    /**
     * refreshCalendar method.
     * This method is called whenever the calendar adapter needs to be refreshed.
     * Called on the backOneMonth and forwardOneMonth buttons.
     */
    public void refreshCalendar()
    {

        adapter.refreshDays();
        adapter.notifyDataSetChanged();

        mMonthAndYear.setText(DateFormat.format("MMMM yyyy", month));
    }

    /**
     * The onPrepareOptionsMenu for the ScheduleCalendarFragment fragment.
     * It sets action_new_task and action_all_tasks to visible.
     * @param menu The menu being passed in.
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_new_task).setVisible(true);
        menu.findItem(R.id.action_all_tasks).setVisible(true);
    }

    /**
     * The onOptionsItemSelected method for ScheduleCalendarFragment. It defines what happens when
     * a menu item is clicked.
     * @param item The menu item being passed in.
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

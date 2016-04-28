package com.gravestristan.mypda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
/**
 * Created by student on 4/27/2016.
 */
public class ScheduleCalendarAdapter extends BaseAdapter {
    private static final int FIRST_WEEK_DAY = 0;

    private Context mContext;

    private Calendar month;
    private Calendar selectedDate;

    public String[] days;

    public ScheduleCalendarAdapter(Context c, Calendar monthCalendar) {
        month = monthCalendar;
        selectedDate = (Calendar)monthCalendar.clone();
        mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    public int getCount() {
        return days.length;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return days[position];
    }

    public View getView(int position, View v, ViewGroup parent) {
        View view = v;
        TextView dayView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.calendar_day_item, null);
        }
        dayView = (TextView)view.findViewById(R.id.date);

        if (days[position].equals("")) {
            dayView.setClickable(false);
            dayView.setFocusable(false);
        }

        dayView.setText(days[position]);

        String date = days[position];

        if (date.length() == 1) {
            date = "0" + date;
        }

        String monthString = "" + (month.get(Calendar.MONTH) + 1);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }

        return view;
    }

    public void refreshDays() {
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);

        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_WEEK_DAY * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_WEEK_DAY + 1)];
        }

        int j = FIRST_WEEK_DAY;
        if (firstDay > 1) {
            for(j = 0; j < firstDay - FIRST_WEEK_DAY; j++){
                days[j] = "";
            }
        } else {
            for(j = 0; j < FIRST_WEEK_DAY * 6; j++){
                days[j] = "";
            }
            j = FIRST_WEEK_DAY * 6 + 1;
        }

        int dayNumber = 1;
        for(int i = j - 1; i < days.length; i++){
            days[i] = "" + dayNumber;
            dayNumber++;
        }
    }

}

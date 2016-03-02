package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tristan on 3/1/2016.
 */
public class ScheduleItemsMenuFragment extends Fragment implements AppStatics{

    private ArrayList<ScheduleItems> mScheduleItems;
    ListView mScheduleList;
    ScheduleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mScheduleItems = PDASingleton.get(getActivity()).getScheduleNames();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_items_menu, container, false);

        mScheduleList = (ListView) view.findViewById(R.id.list);

        adapter = new ScheduleAdapter(mScheduleItems);
        mScheduleList.setAdapter(adapter);

        //create the onItemClickListener here

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

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

    /**
     *
     */
    private class ScheduleAdapter extends ArrayAdapter<ScheduleItems> {
        /**
         *
         * @param scheduleItems
         */
        public ScheduleAdapter(ArrayList<ScheduleItems> scheduleItems) {
            super(getActivity(), 0, scheduleItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater()
                            .inflate(R.layout.schedule_detail_line, null);
            }
            ScheduleItems schItems = getItem(position);

            TextView mTaskName = (TextView) convertView.findViewById(R.id.taskName);
            TextView mTaskDate = (TextView) convertView.findViewById(R.id.taskDate);

            if(mTaskName != null){
                mTaskName.setText(schItems.getTaskName());
            }
            if(mTaskDate != null){
                mTaskDate.setText("temp data");
            }

            return convertView;
        }
    }
}

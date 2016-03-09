package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tristan on 3/1/2016.
 */
public class ScheduleItemsMenuFragment extends Fragment implements AppStatics{

    private ArrayList<ScheduleItems> mScheduleItems;
    ListView mScheduleList;
    ScheduleAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_schedule_items_menu, container, false);

        mScheduleList = (ListView) view.findViewById(R.id.list);

        adapter = new ScheduleAdapter(mScheduleItems);
        mScheduleList.setAdapter(adapter);

        mScheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item clicked, do something");

                SingleScheduleItemFragment currentItem = new SingleScheduleItemFragment();

                Bundle args = new Bundle();
                args.putInt("currentItemPosition", position);

                currentItem.setArguments(args);
                swapFragmentHandler(currentItem);

            }
        });

        return view;
    }

    /**
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_new_task).setVisible(true);
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

        /**
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater()
                            .inflate(R.layout.schedule_detail_line, null);
            }
            ScheduleItems schItems = getItem(position);

            TextView mTaskName = (TextView) convertView.findViewById(R.id.task_name);
            TextView mTaskDate = (TextView) convertView.findViewById(R.id.task_date);
            RelativeLayout mDetailLine = (RelativeLayout) convertView.findViewById(R.id.detail_line);
            if(position % 2 == 0){
                mDetailLine.setBackgroundResource(R.color.colorDetailLineOne);
            }else{
                mDetailLine.setBackgroundResource(R.color.colorDetailLineTwo);
            }


            if(mTaskName != null){
                mTaskName.setText(schItems.getTaskName());
            }
            if(mTaskDate != null){
                mTaskDate.setText(schItems.getTaskDate());
            }

            return convertView;
        }
    }
}

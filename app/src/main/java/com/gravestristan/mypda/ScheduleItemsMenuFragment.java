package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Tristan on 3/1/2016.
 */
public class ScheduleItemsMenuFragment extends Fragment implements AppStatics{

    private ArrayList<ScheduleItems> mScheduleItems;
    private RecyclerView mScheduleList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        mScheduleList = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mScheduleList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mScheduleList.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduleRecyclerViewAdapter(mScheduleItems);

        mScheduleList.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mScheduleList.addItemDecoration(itemDecoration);

        ((ScheduleRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
                        ScheduleRecyclerViewAdapter.ScheduleClickListener() {
                            @Override
                            public void onItemClick(int position, View v){
                                SingleScheduleItemFragment currentItem = new SingleScheduleItemFragment();

                                Bundle args = new Bundle();
                                args.putInt("currentItemPosition", position);

                                currentItem.setArguments(args);
                                swapFragmentHandler(currentItem);
                            }
                        });

        ((ScheduleRecyclerViewAdapter) mAdapter).setOnItemLongClickListener(new
                        ScheduleRecyclerViewAdapter.ScheduleLongClickListener(){
                            @Override
                            public void onItemLongClick(int position, View v){
                                Log.d(TAG, "pressed long");
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
}

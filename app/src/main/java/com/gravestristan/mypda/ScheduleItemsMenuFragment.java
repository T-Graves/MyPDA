package com.gravestristan.mypda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
                            /**
                             *
                             * @param position
                             * @param v
                             */
                            @Override
                            public void onItemClick(int position, View v) {
                                SingleScheduleItemFragment currentItem = new SingleScheduleItemFragment();

                                Bundle args = new Bundle();
                                args.putInt("currentItemPosition", position);

                                currentItem.setArguments(args);
                                swapFragmentHandler(currentItem);
                            }
                        });

        ((ScheduleRecyclerViewAdapter) mAdapter).setOnItemLongClickListener(new
                        ScheduleRecyclerViewAdapter.ScheduleLongClickListener(){
                            /**
                             *
                             * @param position
                             * @param v
                             */
                            @Override
                            public void onItemLongClick(int position, View v){
                                final int itemPosition = position;
                                final View view = v;
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setCancelable(true);
                                builder.setTitle("Delete Item");
                                builder.setMessage("Delete This Schedule Item?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    /**
                                     *
                                     * @param dialog
                                     * @param which
                                     */
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
                                        dbHandler.deleteItemFromTaskTable(mScheduleItems.get(itemPosition));
                                        dbHandler.close();
                                        mScheduleItems.remove(itemPosition);
                                        ((ScheduleRecyclerViewAdapter) mAdapter).deleteItem(itemPosition);
                                        itemDeletedSnackBar(view);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    /**
                                     *
                                     * @param dialog
                                     * @param which
                                     */
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });

        return view;
    }

    /**
     *
     * @param view
     */
    public void itemDeletedSnackBar(View view){
        Snackbar snackbar = Snackbar
                .make(view, "Schedule Item Deleted", Snackbar.LENGTH_SHORT);

        snackbar.show();
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

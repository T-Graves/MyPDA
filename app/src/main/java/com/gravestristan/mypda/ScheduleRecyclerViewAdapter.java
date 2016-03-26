package com.gravestristan.mypda;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is the RecyclerView adapter for the schedule items list
 */
public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleItemHolder> implements AppStatics{

    private ArrayList<ScheduleItems> mScheduleItemsArray;
    private static ScheduleClickListener scheduleClickListener;
    private static ScheduleLongClickListener scheduleLongClickListener;

    /**
     *
     */
    public static class ScheduleItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView taskName;
        TextView taskDate;

        /**
         *
         * @param itemView
         */
        public ScheduleItemHolder(View itemView){
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.task_name);
            taskDate = (TextView) itemView.findViewById(R.id.task_date);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        /**
         *
         * @param v
         */
        @Override
        public void onClick(View v){
            scheduleClickListener.onItemClick(getAdapterPosition(), v);
        }

        /**
         *
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v) {
            scheduleLongClickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
    }



    /**
     *
     * @param scheduleClickListener
     */
    public void setOnItemClickListener(ScheduleClickListener scheduleClickListener){
        this.scheduleClickListener = scheduleClickListener;
    }

    /**
     *
     * @param scheduleLongClickListener
     */
    public void setOnItemLongClickListener(ScheduleLongClickListener scheduleLongClickListener){
        this.scheduleLongClickListener = scheduleLongClickListener;
    }

    /**
     *
     * @param dataSet
     */
    public ScheduleRecyclerViewAdapter(ArrayList<ScheduleItems> dataSet) {
        mScheduleItemsArray = dataSet;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ScheduleItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_detail_line, parent, false);

        ScheduleItemHolder scheduleItemHolder = new ScheduleItemHolder(view);
        return scheduleItemHolder;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ScheduleItemHolder holder, int position){
        holder.taskName.setText(mScheduleItemsArray.get(position).getTaskName());
        holder.taskDate.setText(mScheduleItemsArray.get(position).getTaskDate());
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mScheduleItemsArray.size();
    }

    /**
     *
     */
    public interface ScheduleClickListener {
        public void onItemClick(int position, View v);
    }

    /**
     *
     */
    public interface ScheduleLongClickListener {
        public void onItemLongClick(int position, View v);
    }
}

package com.gravestristan.mypda;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tristan on 2/20/2016.
 */
public class ScheduleItems {

    private UUID mId;
    private String mTaskName = "";
    private String mTaskDate = "";
    private String mTaskNote = "";

    /**
     * The constructor for each item. It randomly generates a UUID for each item.
     */
    public ScheduleItems(){
        mId = UUID.randomUUID();
    }

    /**
     * The get method for the UUID.
     * @return returns mId (the UUID) for the item.
     */
    public UUID getId(){
        return mId;
    }

    /**
     * The set method for the UUID.
     * This method will most likely not be used, but it's here just in case.
     * @param mId The desired UUID for this item.
     */
    public void setId(UUID mId){
        this.mId = mId;
    }

    /**
     * The get method for the task name.
     * @return returns mTaskName for this item.
     */
    public String getTaskName(){
        return mTaskName;
    }

    /**
     * The set method for the task name.
     * @param mTaskName The desired task name.
     */
    public void setTaskName(String mTaskName){
        this.mTaskName = mTaskName;
    }

    /**
     *
     * @return
     */
    public String getTaskDate(){
        return mTaskDate;
    }

    /**
     *
     * @param mTaskDate
     */
    public void setTaskDate(String mTaskDate){
        this.mTaskDate = mTaskDate;
    }

    /**
     * Method to return the note for this task.
     * @return The note to return
     */
    public String getTaskNote(){
        return mTaskNote;
    }

    /**
     * Method to set the task items note.
     * @param mTaskNote The users note for the task item
     */
    public void setTaskNote(String mTaskNote){
        this.mTaskNote = mTaskNote;
    }

    /**
     * Overridden to string method for debugging if needed.
     *
     * @return returns the mID (UUID) and the mTaskName.
     */
    @Override
    public String toString(){
        return mId + " " + mTaskName; // Add mTaskDate later
    }


}

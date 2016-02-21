package com.gravestristan.mypda;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tristan on 2/20/2016.
 *
 * TODO create getters and setters for the mTaskDate variable if it will work for this app
 */
public class ScheduleItem {

    private UUID mId;
    private String mTaskName;
    private Date mTaskDate;

    /**
     * The constructor for each item. It randomly generates a UUID for each item.
     */
    public ScheduleItem(){
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

    /*

    // Im not sure if the Date variable type will work for this
    mTaskDate getter and setter

     */

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

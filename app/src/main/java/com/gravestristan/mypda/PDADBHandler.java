package com.gravestristan.mypda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by student on 3/2/2016.
 */
public class PDADBHandler extends SQLiteOpenHelper implements AppStatics{

    ArrayList<ScheduleItems> mScheduleItems;

    private static final String DATABASE_NAME = "pdaDB.db";
    private static final String TABLE_TASKS = "tasks";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_UUID = "task_uuid";
    private static final String COLUMN_TASKNAME = "task_name";
    private static final String COLUMN_TASKDATE = "task_date";
    private static final String COLUMN_TASKNOTE = "task_note";

    /**
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public PDADBHandler(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        mScheduleItems = PDASingleton.get(context).getScheduleItems();
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " +
                TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_TASKNAME + " TEXT,"
                + COLUMN_TASKDATE + " TEXT,"
                + COLUMN_TASKNOTE + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    /**
     *
     * @param scheduleItem
     */
    public void addTask(ScheduleItems scheduleItem){
        ContentValues values = new ContentValues();

        values.put(COLUMN_UUID, scheduleItem.getId().toString());
        values.put(COLUMN_TASKNAME, scheduleItem.getTaskName());
        values.put(COLUMN_TASKDATE, scheduleItem.getTaskDate());
        values.put(COLUMN_TASKNOTE, scheduleItem.getTaskNote());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    /**
     *
     * @param scheduleItem
     */
    public void updateTask(ScheduleItems scheduleItem){
        SQLiteDatabase db = this.getWritableDatabase();

        String UUID = scheduleItem.getId().toString();

        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_TASKNAME, scheduleItem.getTaskName());
        updateValues.put(COLUMN_TASKDATE, scheduleItem.getTaskDate());
        updateValues.put(COLUMN_TASKNOTE, scheduleItem.getTaskNote());

        db.update(TABLE_TASKS, updateValues, COLUMN_UUID + " = '" + UUID + "'", null);

        db.close();
    }

    /**
     *
     */
    public void getAllScheduleItems(){
        String query = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            while(cursor.getPosition() != cursor.getCount()){
                ScheduleItems scheduleItem = new ScheduleItems();
                scheduleItem.setId(UUID.fromString(cursor.getString(1)));
                scheduleItem.setTaskName(cursor.getString(2));
                scheduleItem.setTaskDate(cursor.getString(3));
                scheduleItem.setTaskNote(cursor.getString(4));
                mScheduleItems.add(scheduleItem);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }

    /**
     *
     */
    public void getThreeClosestTaskItems(){
        String query = "SELECT * FROM " + TABLE_TASKS + " ORDER BY date(" + COLUMN_TASKDATE + ") ASC Limit 3";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            while(cursor.getPosition() != cursor.getCount()){
                try {
                    Date date = dateFormat.parse(cursor.getString(3));
                    Date currentDate = new Date();
                    if(date.after(currentDate) || date.equals(currentDate)){
                        Log.d(TAG, "date :" + cursor.getString(3));
                    }else{
                        Log.d(TAG, "date passed");
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }
}

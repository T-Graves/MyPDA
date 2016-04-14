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
 * SQLite database handler class for the MyPDA app. Handles all database queries for the app.
 */
public class PDADBHandler extends SQLiteOpenHelper implements AppStatics{

    ArrayList<ScheduleItems> mScheduleItems;

    // Database name and table names
    private static final String DATABASE_NAME = "pdaDB.db";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_NOTES = "notes";

    // Columns for the task table
    private static final String TASKS_COLUMN_ID = "_id";
    private static final String TASKS_COLUMN_UUID = "task_uuid";
    private static final String TASKS_COLUMN_TASKNAME = "task_name";
    private static final String TASKS_COLUMN_TASKDATE = "task_date";
    private static final String TASKS_COLUMN_TASKNOTE = "task_note";

    // Columns for the notes table
    private static final String NOTES_COLUMN_ID = "_id";
    private static final String NOTES_COLUMN_UUID = "task_uuid";
    private static final String NOTES_COLUMN_NOTECONTENTS = "note_contents";

    /**
     * The constructor for the PDADBHandler class.
     * @param context The app context being passed in.
     * @param name The name being passed in.
     * @param factory The cursor factory being passed in.
     * @param version The database version being passed in.
     */
    public PDADBHandler(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        mScheduleItems = PDASingleton.get(context).getScheduleItems();
    }

    /**
     * The onCreate method for PDADBHandler. Creates the tables to be used any time it is called
     * which should only be when the database is first created or when the database version is
     * changed.
     * @param db The database being passed in.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE "
                + TABLE_TASKS + "("
                + TASKS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + TASKS_COLUMN_UUID + " TEXT,"
                + TASKS_COLUMN_TASKNAME + " TEXT,"
                + TASKS_COLUMN_TASKDATE + " TEXT,"
                + TASKS_COLUMN_TASKNOTE + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
        String CREATE_NOTES_TABLE = "CREATE TABLE "
                + TABLE_NOTES + "("
                + NOTES_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + NOTES_COLUMN_UUID + " TEXT,"
                + NOTES_COLUMN_NOTECONTENTS + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    /**
     * The onUpgrade method for PDADBHandler. It drops any tables in the database and calles the
     * onCreate method to recreate them. It will only be run if the database verison is changed.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    //Scheduling function database control starts here

    /**
     * This method deletes a task from the tasks table. The item to delete is determined by the
     * UUID of an object that is passed in.
     * @param scheduleItem The task item to delete.
     */
    public void deleteItemFromTaskTable(ScheduleItems scheduleItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE " + TASKS_COLUMN_UUID + " = '" + scheduleItem.getTaskId() + "'");
        db.close();
    }

    /**
     * This method adds a task into the tasks table. All the info to store is taken out of an
     * object that is passed in.
     * @param scheduleItem The task item to add.
     */
    public void addTask(ScheduleItems scheduleItem){
        ContentValues values = new ContentValues();

        values.put(TASKS_COLUMN_UUID, scheduleItem.getTaskId().toString());
        values.put(TASKS_COLUMN_TASKNAME, scheduleItem.getTaskName());
        values.put(TASKS_COLUMN_TASKDATE, scheduleItem.getTaskDate());
        values.put(TASKS_COLUMN_TASKNOTE, scheduleItem.getTaskNote());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    /**
     * This method updates a task that is already in the tasks table. Which item to update is
     * determined by the UUID of an object that is passed in.
     * @param scheduleItem The task item to update.
     */
    public void updateTask(ScheduleItems scheduleItem){
        SQLiteDatabase db = this.getWritableDatabase();

        String UUID = scheduleItem.getTaskId().toString();

        ContentValues updateValues = new ContentValues();
        updateValues.put(TASKS_COLUMN_TASKNAME, scheduleItem.getTaskName());
        updateValues.put(TASKS_COLUMN_TASKDATE, scheduleItem.getTaskDate());
        updateValues.put(TASKS_COLUMN_TASKNOTE, scheduleItem.getTaskNote());

        db.update(TABLE_TASKS, updateValues, TASKS_COLUMN_UUID + " = '" + UUID + "'", null);

        db.close();
    }

    /**
     * This method gets all tasks from the tasks table. It stores each rows data into a new
     * ScheduleItem object and stores that object in the PDASingletons ArrayList for ScheduleItems.
     */
    public void getAllTaskItems(){
        String query = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            while(cursor.getPosition() != cursor.getCount()){
                ScheduleItems scheduleItem = new ScheduleItems();
                scheduleItem.setTaskId(UUID.fromString(cursor.getString(1)));
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
     * This method finds all tasks ordered by date. It then grabs the top three items and stores
     * them as ScheduleItems objects and puts those into an ArrayList. the array list is then
     * returned.
     * @return
     */
    public ArrayList getThreeClosestTaskItems(){
        ArrayList<ScheduleItems> returnArray = new ArrayList<ScheduleItems>();
        String query = "SELECT * FROM " + TABLE_TASKS + " ORDER BY date(" + TASKS_COLUMN_TASKDATE + ") ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            int numberOfItemsGrabbed = 0;
            while(numberOfItemsGrabbed != NUMBER_OF_TASK_ITEMS_TO_GRAB){
                try {
                    if(cursor.getPosition() == cursor.getCount()){
                        break;
                    }else{
                        Date date = dateFormat.parse(cursor.getString(3));
                        if(date.after(dateFormat.parse(currentDate)) || date.equals(dateFormat.parse(currentDate))){
                            ScheduleItems scheduleItem = new ScheduleItems();
                            scheduleItem.setTaskId(UUID.fromString(cursor.getString(1)));
                            scheduleItem.setTaskName(cursor.getString(2));
                            scheduleItem.setTaskDate(cursor.getString(3));
                            scheduleItem.setTaskNote(cursor.getString(4));
                            returnArray.add(scheduleItem);
                            numberOfItemsGrabbed++;
                        }else{
                            //TODO have the database delete the old entry here maybe only after a specific amount of time after due date
                        }
                    }

                } catch (ParseException e){
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

        return returnArray;
    }

    /**
     *
     * @return
     */
    public boolean checkForUpcomingTasks(){
        String query = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            while(cursor.getPosition() != cursor.getCount()){
                try {
                    Date date = dateFormat.parse(cursor.getString(3));
                    if(date.equals(dateFormat.parse(currentDate))){
                        cursor.close();
                        db.close();
                        return true;
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

        return false;
    }

    //Scheduling function database control ends here

    //Notes function database control starts here

    /**
     *
     * @param noteObject
     */
    public void deleteNoteFromNoteTable(NoteObjects noteObject){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NOTES + " WHERE " + NOTES_COLUMN_UUID + " = '" + noteObject.getNoteId() + "'");
    }

    /**
     *
     * @param currentUUID
     * @return
     */
    public NoteObjects getNote(UUID currentUUID){
        NoteObjects noteObject = new NoteObjects();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + NOTES_COLUMN_UUID + " = '" + currentUUID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();

            noteObject.setNoteId(UUID.fromString(cursor.getString(1)));
            noteObject.setNoteContents(cursor.getString(2));

            cursor.close();
        }

        db.close();

        return noteObject;
    }

    /**
     *
     * @param noteObject
     */
    public void addNote(NoteObjects noteObject){
        ContentValues values = new ContentValues();

        values.put(NOTES_COLUMN_UUID, noteObject.getNoteId().toString());
        values.put(NOTES_COLUMN_NOTECONTENTS, noteObject.getNoteContents());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    /**
     *
     * @param noteObject
     */
    public void updateNote(NoteObjects noteObject){
        SQLiteDatabase db = this.getWritableDatabase();

        String UUID = noteObject.getNoteId().toString();

        ContentValues updateValues = new ContentValues();
        updateValues.put(NOTES_COLUMN_NOTECONTENTS, noteObject.getNoteContents());

        db.update(TABLE_NOTES, updateValues, NOTES_COLUMN_UUID + " = '" + UUID + "'", null);

        db.close();
    }

    /**
     *
     */
    public ArrayList<NoteObjects> getAllNoteItems(){
        ArrayList<NoteObjects> mNoteObjects = new ArrayList<NoteObjects>();
        String query = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            while(cursor.getPosition() != cursor.getCount()){
                NoteObjects noteObject = new NoteObjects();
                noteObject.setNoteId(UUID.fromString(cursor.getString(1)));
                noteObject.setNoteContents(cursor.getString(2));
                mNoteObjects.add(noteObject);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return mNoteObjects;
    }

}

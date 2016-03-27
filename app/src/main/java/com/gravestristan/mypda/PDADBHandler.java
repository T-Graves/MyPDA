package com.gravestristan.mypda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String DATABASE_NAME = "pdaDB.db";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_NOTES = "notes";

    private static final String TASKS_COLUMN_ID = "_id";
    private static final String TASKS_COLUMN_UUID = "task_uuid";
    private static final String TASKS_COLUMN_TASKNAME = "task_name";
    private static final String TASKS_COLUMN_TASKDATE = "task_date";
    private static final String TASKS_COLUMN_TASKNOTE = "task_note";

    private static final String NOTES_COLUMN_ID = "_id";
    private static final String NOTES_COLUMN_UUID = "task_uuid";
    private static final String NOTES_COLUMN_NOTENAME = "note_name";
    private static final String NOTES_COLUMN_NOTECONTENTS = "note_contents";

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
                + NOTES_COLUMN_NOTENAME + " TEXT,"
                + NOTES_COLUMN_NOTECONTENTS + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    //Scheduling function database control starts here

    /**
     *
     * @param scheduleItem
     */
    public void deleteItemFromTaskTable(ScheduleItems scheduleItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE " + TASKS_COLUMN_UUID + " = '" + scheduleItem.getTaskId() + "'");
    }

    /**
     *
     * @param scheduleItem
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
     *
     * @param scheduleItem
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
     *
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
     *
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

    public NoteObjects getNote(UUID currentUUID){
        NoteObjects noteObject = new NoteObjects();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + NOTES_COLUMN_UUID + " = '" + currentUUID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();

            noteObject.setNoteId(UUID.fromString(cursor.getString(1)));
            noteObject.setNoteTitle(cursor.getString(2));
            noteObject.setNoteContents(cursor.getString(3));

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
        values.put(NOTES_COLUMN_NOTENAME, noteObject.getNoteTitle());
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
        updateValues.put(NOTES_COLUMN_NOTENAME, noteObject.getNoteTitle());
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
                noteObject.setNoteTitle(cursor.getString(2));
                noteObject.setNoteContents(cursor.getString(3));
                mNoteObjects.add(noteObject);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return mNoteObjects;
    }

}

package com.example.adminto.mytasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DataBase extends SQLiteOpenHelper implements IDatabaseHandler{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TaskManager";
    private static final String TABLE_TASKS = "Tasks";
    private static final String KEY_TIME = "Time";
    private static final String KEY_DATE = "Date";
    private static final String KEY_TASK = "Task";
    private static final String KEY_DAILY = "Daily";
    private static final String KEY_ID = "ID";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIME + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_TASK + " TEXT," + KEY_DAILY + "BOOLEAN" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void addTask(Task _task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, _task.getTime());
        values.put(KEY_DATE, _task.getDate());
        values.put(KEY_TASK, _task.getNote());
        values.put(KEY_DAILY, _task.getDaily());
        values.put(KEY_ID, _task.getID());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    @Override
    public List<Task> getCurrDateTasks() {
        List<Task> TaskList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task _task = new Task();
                _task.setID(Integer.parseInt(cursor.getString(0)));
                _task.setTime(cursor.getString(1));
                _task.setNote(cursor.getString(2));
                _task.setDaily(cursor.getString(3));
                TaskList.add(_task);
            } while (cursor.moveToNext());
        }
        return TaskList;
    }

    @Override
    public void deleteTask(Task _task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[] { String.valueOf(_task.getID()) });
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

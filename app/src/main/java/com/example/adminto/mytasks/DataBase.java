package com.example.adminto.mytasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_db";
    public static final String TABLE_TASKS = "Tasks";
    public static final String KEY_TIME = "Time";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TASK = "Task";
    public static final String KEY_DAILY = "Daily";
    public static final String KEY_ID = "_id";
    ContentValues contentValues;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_TIME + " text," + KEY_DATE + " text,"
                + KEY_TASK + " text," + KEY_DAILY + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TASKS);
        onCreate(db);
    }

    SQLiteDatabase DB;
    Task t;
    ArrayList<Task> tasks;
    Cursor cursor;

    public void insertTask(Task task) {
        contentValues = new ContentValues();
        DB = this.getWritableDatabase();
      //  contentValues.put(KEY_ID, task.getID());
        contentValues.put(KEY_DAILY, task.getDaily());
        contentValues.put(KEY_TASK, task.getNote());
        contentValues.put(KEY_TIME, task.getTime());
        contentValues.put(KEY_DATE, task.getDate());
        DB.insert(DataBase.TABLE_TASKS, null, contentValues);
        DB.close();

    }
    public List<Task> getAllTasks() {
        List<Task> contactList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task t = new Task();
                t.setTime(cursor.getString(cursor.getColumnIndex(DataBase.KEY_TIME)));
                t.setNote(cursor.getString(cursor.getColumnIndex(DataBase.KEY_TASK)));
                t.setDaily(cursor.getString(cursor.getColumnIndex(DataBase.KEY_DAILY)));
                t.setDate(cursor.getString(cursor.getColumnIndex(DataBase.KEY_DATE)));
                t.setID(cursor.getInt(cursor.getColumnIndex(DataBase.KEY_ID)));
                contactList.add(t);
            } while (cursor.moveToNext());
            cursor.moveToFirst();
        }

        return contactList;

    }
    public void deleteTask(Task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[] { String.valueOf(t.getID()) });
        String selectQuery = "DELETE FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = " + t.getID();
        db.rawQuery(selectQuery,null);
        db.close();
    }

}



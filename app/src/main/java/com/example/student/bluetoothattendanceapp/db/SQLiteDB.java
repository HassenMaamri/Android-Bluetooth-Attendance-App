package com.example.student.bluetoothattendanceapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.student.bluetoothattendanceapp.constant.MeetingField;
import com.example.student.bluetoothattendanceapp.model.Meeting;

public class SQLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Meeting.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MeetingField.TABLE_NAME + " (" +
                    MeetingField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MeetingField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    MeetingField.COLUMN_PHONE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MeetingField.TABLE_NAME;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Meeting meeting){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MeetingField.COLUMN_NAME, meeting.getName());
        values.put(MeetingField.COLUMN_PHONE, meeting.getPhone());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MeetingField.TABLE_NAME,
                null,
                values);
    }

    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MeetingField.COLUMN_ID,
                MeetingField.COLUMN_NAME,
                MeetingField.COLUMN_PHONE };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MeetingField.COLUMN_NAME + " ASC";

        Cursor c = db.query(
                MeetingField.TABLE_NAME,                    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        return c;
    }

    public void update(Meeting meeting){
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(MeetingField.COLUMN_NAME, meeting.getName());
        values.put(MeetingField.COLUMN_PHONE, meeting.getPhone());

        // Which row to update, based on the ID
        String selection = MeetingField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(meeting.getId()) };

        int count = db.update(
                MeetingField.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = MeetingField.COLUMN_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        db.delete(MeetingField.TABLE_NAME, selection, selectionArgs);
    }
}

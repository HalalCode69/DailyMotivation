package com.example.dailymotivation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Motivation.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "entries";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TEXT = "text";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int textIndex = cursor.getColumnIndex(COLUMN_TEXT);

        if (cursor.moveToFirst()) {
            do {
                if (idIndex != -1 && titleIndex != -1 && textIndex != -1) {
                    Entry entry = new Entry(
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            cursor.getString(textIndex));
                    entries.add(entry);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return entries;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEntry(String title, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TEXT, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
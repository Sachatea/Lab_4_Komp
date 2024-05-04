package com.example.lab_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_P = "Performer";
    private static final String COLUMN_N = "Name";
    private static final String COLUMN_DATE = "Date";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_P + " TEXT, " +
                COLUMN_N + " TEXT, " +
                COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void add(String Performer, String Name){
        Date date = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P, Performer);
        cv.put(COLUMN_N, Name);
        cv.put(COLUMN_DATE, String.valueOf(date));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor readAllData (){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void addIf (String Performer, String Name) {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();

        if (!Objects.equals(cursor.getString(1), Performer) | !Objects.equals(cursor.getString(2), Name)) {
            add(Performer, Name);
        }
    }

}

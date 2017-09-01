package com.example.ehsanullah.loginandregistration.Graph;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by SZ on 10/20/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    Context context;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "heartRateValues";

    // Table Names
    private static final String TABLE_HEART_VALUES = "heartValues";


    // Common column names
    private static final String KEY_ID = "id";

    // HEART VALUES Table - column nmaes
    private static final String KEY_DATE = "date";
    private static final String KEY_VALUES_STRING = "valueString";


    // Table Create Statements

    // HEART VALUES table create statement
    private static final String CREATE_TABLE_HEART_VALUES = "CREATE TABLE "
            + TABLE_HEART_VALUES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " DATETIME," + KEY_VALUES_STRING + " TEXT" + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HEART_VALUES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEART_VALUES);

        onCreate(db);

    }
    public void insertHeartReading(String date, String reading) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE, date);
        cv.put(KEY_VALUES_STRING, reading);
        db.insert(TABLE_HEART_VALUES, null, cv);

    }


    ArrayList<Reading> getHeartReadings() {
        ArrayList<Reading> readings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_HEART_VALUES;

        Cursor cursor2 = db.rawQuery(query, null);
        for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {
            Reading reading = new Reading();
            reading.setDate(cursor2.getString(cursor2.getColumnIndex(KEY_DATE)));
            reading.setReading(findMedian(cursor2.getString(cursor2.getColumnIndex(KEY_VALUES_STRING))));
            readings.add(reading);


        }

        cursor2.close();
        return readings;


    }

    int findMedian(String reading) {
        String[] readings = reading.split(",");
        int readingsInInt[] = new int[readings.length];
        for (int i = 0; i < readings.length; i++) {
            readingsInInt[i] = Integer.parseInt(readings[i]);
        }
        Arrays.sort(readingsInInt);

        return readingsInInt[readingsInInt.length/2];
    }


}
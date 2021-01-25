package com.tolegensapps.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;


public class WeatherDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String DB_NAME = "weathers.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "my_weathers";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "city_name";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TEMP = "temperature";
    private static final String COLUMN_TEMP_MIN = "temp_min";
    private static final String COLUMN_TEMP_MAX = "temp_max";
    private static final String COLUMN_PRESSURE = "pressure";

    public WeatherDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TIME + " NUMERIC, " +
                COLUMN_TEMP + " REAL, " +
                COLUMN_TEMP_MIN + " REAL, " +
                COLUMN_TEMP_MAX + " REAL, " +
                COLUMN_PRESSURE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addWeather(String name, Date time, Double temperature, Double tempMin, Double tempMax, int pressure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME, String.valueOf(time));
        cv.put(COLUMN_TEMP, temperature);
        cv.put(COLUMN_TEMP_MIN, tempMin);
        cv.put(COLUMN_TEMP_MAX, tempMax);
        cv.put(COLUMN_PRESSURE, pressure);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mContext, "Added Succesfully", Toast.LENGTH_SHORT).show();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}

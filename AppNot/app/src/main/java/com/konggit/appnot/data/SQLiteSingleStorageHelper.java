package com.konggit.appnot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.konggit.appnot.Results;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

public class SQLiteSingleStorageHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DATA_JSON_STRING";

    private static final String TABLE_DATA = "DATA";

    private static final String KEY_DATE = "date";
    private static final String KEY_RESULTS = "results";

    public SQLiteSingleStorageHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "(" + KEY_DATE + " TEXT PRIMARY KEY, " + KEY_RESULTS + " TEXT" + ")";
        db.execSQL(CREATE_DATA_TABLE);

    }

    //updating table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        onCreate(db);

    }

    //CRUD

    public void addResults(Map<Date, Results> data) {

        SQLiteDatabase db = this.getWritableDatabase();

        //drop table to rewrite all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        //recreate table
        onCreate(db);

        for (Date date : data.keySet()) {

            Results results = data.get(date);
            addResult(date.toString(), results);

        }

    }

    private void addResult(String date, Results result) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_RESULTS, resultToJson(result).toString());

        db.insert(TABLE_DATA, null, values);
        db.close();

    }

    private JSONObject resultToJson(Results result) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectInner = new JSONObject();

        try {

            for (Date timestamp : result.getPath().keySet()) {

                jsonObjectInner.put(timestamp.toString(), result.getPath().get(timestamp));

            }

            jsonObject.put("dayDefaultNumber", result.getDayDefaultNumber());
            jsonObject.put("numbers", result.getNumbers());
            jsonObject.put("path", jsonObjectInner);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("SQLite json", jsonObject.toString());

        return jsonObject;
    }


    //todo READ READALL UPDATE DELETE
}

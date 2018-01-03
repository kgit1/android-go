package com.konggit.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//join(full - inner join) - shows results from both tables which have same anchor in both tables
//outer join - shows all results from both tables - putting NULL on the place of missing anchor field
//left and right joins(full - left outer join and right outer join) outer joins where
// on left will be results from joining table - or joined table - if right join
//for tables users and departments bounded on d_id field - sql queries will be:
//select u.id, u.name, d.name as d_name
//from users u
//inner join departments d on u.d_id = d.id
//or
//select u.id, u.name, d.name as d_name
//from users u
//left join departments d on u.d_id = d.id
//or
//select u.id, u.name, d.name as d_name
//from users u
//right join departments d on u.d_id = d.id

public class JoinTablesActivity extends AppCompatActivity {

    private final String LOG_TAG = "JoinTables activity";
    private final String MY_DATABASE_NAME = "myDataBaseDual";
    private final String MY_FIRST_TABLE_NAME = "position";
    private final String MY_SECOND_TABLE_NAME = "people";

    //data for table position
    int[] position_id = {1, 2, 3, 4};
    String[] position_name = {"Manager", "Programmer", "Accountant", "Security"};
    int[] position_salary = {15000, 13000, 11000, 8000};

    //data for table people
    String[] people_name = {"Rob", "Peter", "Rich", "Marry", "Ingrid", "Sally", "Sam"};
    int[] people_position_id = {2, 3, 2, 3, 4, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tables);

        setTitle("Join tables activity");

        //connect to database
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //create cursor
        Cursor cursor;

        //data from table - position
        Log.d(LOG_TAG, "--Table position--");

        cursor = database.query(MY_FIRST_TABLE_NAME, null, null, null, null, null, null);
        printCursor(cursor);
        cursor.close();

        Log.d(LOG_TAG, "--Cursor closed--");

        //data from table people
        Log.d(LOG_TAG, "--Table people--");

        cursor = database.query(MY_SECOND_TABLE_NAME, null, null, null, null, null, null);

        printCursor(cursor);
        cursor.close();

        Log.d(LOG_TAG, "--Cursor closed--");

        //data from join(inner join) tables position and people with selected salary
        Log.d(LOG_TAG, "--Inner join with rawQuery--");

        String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
                + "from people as PL "
                + "inner join position as PS "
                + "on PL.position_id = PS.id "
                + "where salary > ?";

        cursor = database.rawQuery(sqlQuery, new String[]{"11000"});

        printCursor(cursor);
        cursor.close();

        Log.d(LOG_TAG, "--Cursor closed--");

        //data from join(inner join) tables position and people with selected salary
        Log.d(LOG_TAG, "--Inner join with query--");

        String table = "people as PL inner join position as PS on PL.position_id = PS.id";
        String[] columns = {"PL.name as Name", "PS.name as Position", "salary as Salary"};
        String selection = "salary < ?";
        String[] selectionArgs = {"12000"};

        cursor = database.query(table, columns, selection, selectionArgs, null, null, null);

        printCursor(cursor);
        cursor.close();

        Log.d(LOG_TAG, "--Cursor closed--");

        Log.d(LOG_TAG, "--Join(Inner Join) all data--");

        String orderBy = "salary DESC";

        cursor = database.query(table, columns, null, null, null, null, orderBy);

        printCursor(cursor);
        cursor.close();

        Log.d(LOG_TAG, "--Cursor closed--");

        database.close();

        Log.d(LOG_TAG, "--Database closed--");

    }

    private void printCursor(Cursor cursor) {

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                String data;
                do {

                    data = "";

                    for (String columnName : cursor.getColumnNames()) {

                        data = data.concat(columnName + " = " + cursor.getString(cursor.getColumnIndex(columnName)) + "; ");

                    }

                    Log.d(LOG_TAG, data);

                } while (cursor.moveToNext());
            }
        } else {

            Log.d(LOG_TAG, "-- Cursor is null--");

        }

    }


    //class for work with database
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {

            super(context, MY_DATABASE_NAME, null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(LOG_TAG, "--Create database--");

            ContentValues contentValues = new ContentValues();

            //create table positions
            db.execSQL("create table " + MY_FIRST_TABLE_NAME + "("
                    + "id integer primary key,"
                    + "name text,"
                    + "salary integer"
                    + ");");

            //fill table positions
            for (int i = 0; i < position_id.length; i++) {

                contentValues.clear();
                contentValues.put("id", position_id[i]);
                contentValues.put("name", position_name[i]);
                contentValues.put("salary", position_salary[i]);
                db.insert(MY_FIRST_TABLE_NAME, null, contentValues);

            }

            //create table people
            db.execSQL("create table " + MY_SECOND_TABLE_NAME + "("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "position_id integer"
                    + ");");

            //fill table people
            for (int i = 0; i < people_name.length; i++) {

                contentValues.clear();
                contentValues.put("name", people_name[i]);
                contentValues.put("position_id", people_position_id[i]);
                db.insert(MY_SECOND_TABLE_NAME, null, contentValues);

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}

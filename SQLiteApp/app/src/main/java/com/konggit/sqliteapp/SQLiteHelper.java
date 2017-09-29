package com.konggit.sqliteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by erik on 12.09.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper databaseInstance = null;

    private static final String LOG_TAG = "SQLiteHelper Logs";
    private static final String MY_DATABASE_NAME = "myDataBase";
    private static final int DB_VERSION = 2;
    private static final String MY_TABLE_NAME = "mytable";

    //singelton to avoid db leeks
    //You should not initialize your helper object using with new SQLiteHelper(context).
    //Instead, always use  SQLiteHelper.getInstance(context), as it guarantees that only
    // one database helper will exist across the entire application's lifecycle
    public static SQLiteHelper getInstance(Context context){

        if(databaseInstance == null){

            databaseInstance = new SQLiteHelper(context);

        }
        return databaseInstance;
    }

    private SQLiteHelper(Context context) {

        super(context,MY_DATABASE_NAME, null, DB_VERSION);

        Log.d(LOG_TAG, "--- constructor database ---");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG, "--- onCreate database ---");

        db.execSQL("create table " + MY_TABLE_NAME + " ("
                + "id integer primary key autoincrement,"
                + "name text, "
                + "email text, "
                + "age text"
                + ");");

    }


    //when upgrade database table - change onCreate method to create same database structure
    // as altered - than new customers will get new database structure without update
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //add column to the table
        String upgradeQuery = "alter table " + MY_TABLE_NAME + " add column age int";

        if (oldVersion == 1 && newVersion == 2) {

            db.execSQL(upgradeQuery);

        }

        //remove column from the table
        //if we need to delete some column we need to write all data to temporary table - than
        // drop table - than recreate table with needed structure and feel it with data from
        // temporary table - than drop temporary table

        //db.execSQL("create temporary table people_tmp ("
        //        + "id integer, name text, position text, posid integer);");
        //
        //db.execSQL("insert into people_tmp select id, name, position, posid from people;");
        //db.execSQL("drop table people;");
        //
        //db.execSQL("create table people ("
        //        + "id integer primary key autoincrement,"
        //        + "name text, posid integer);");
        //
        //db.execSQL("insert into people select id, name, posid from people_tmp;");
        //db.execSQL("drop table people_tmp;");


        //multiple transactions
        //if multiple transactions - queries can be wrapped in try finally db transaction -
        // than if in multiple transactions some transaction fails - all transactions returned
        // to previous state

        //db.beginTransaction();
        //
        //try{
        //
        //db.execSQL("query1");
        //db.execSQL("query2");
        // db.execSQL("query3");
        //
        //db.setTransactionSuccessful();
        //
        //}finally {
        //
        //db.endTransaction();
        //
        //}


    }
}

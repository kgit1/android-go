package com.konggit.sqliteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "MainActivity Logs";
    private final String MY_DATABASE_NAME = "myDataBase";
    private final String MY_TABLE_NAME = "mytable";

    LinearLayout mainLinearLayout;

    Button addButton;
    Button readButton;
    Button clearButton;
    Button fillDatabaseButton;
    Button killDBButton;
    Button funcButton;
    Button updateButton;
    Button deleteButton;
    Button ageGreaterButton;
    Button nameSelectButton;
    Button nameAgeSumButton;
    Button namesAgeSumButton;
    Button sortReadButton;

    RadioGroup sortRadioGroup;

    EditText nameEditText;
    EditText emailEditText;
    EditText ageEditText;
    EditText funcEditText;
    EditText idEditText;
    EditText ageGreaterEditText;
    EditText nameSelectEditText;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;

    private int timerSeconds;
    private int timerStopStartSeconds;
    Button timerButton;
    Button timerClearButton;
    TextView timerTextView;
    TextView timerStopStartTextView;
    private boolean timerRunning = false;
    private boolean timerStopStartRunning = false;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("SQLite example");

        logs("ON CREATE");

        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);

        addButton = (Button) findViewById(R.id.addButton);
        readButton = (Button) findViewById(R.id.readButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        killDBButton = (Button) findViewById(R.id.killDBButton);
        fillDatabaseButton = (Button) findViewById(R.id.fillDatabaseButton);
        funcButton = (Button) findViewById(R.id.funcReadButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        ageGreaterButton = (Button) findViewById(R.id.ageSelectButton);
        nameSelectButton = (Button) findViewById(R.id.nameSelectButton);
        nameAgeSumButton = (Button) findViewById(R.id.nameAgeSumButton);
        namesAgeSumButton = (Button) findViewById(R.id.namesAgeSumButton);
        sortReadButton = (Button) findViewById(R.id.sortReadButton);

        sortRadioGroup = (RadioGroup) findViewById(R.id.sortReadRadioGroup);

        mainLinearLayout.setOnClickListener(this);
        addButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        killDBButton.setOnClickListener(this);
        fillDatabaseButton.setOnClickListener(this);
        funcButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        ageGreaterButton.setOnClickListener(this);
        nameSelectButton.setOnClickListener(this);
        nameAgeSumButton.setOnClickListener(this);
        namesAgeSumButton.setOnClickListener(this);
        sortReadButton.setOnClickListener(this);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        funcEditText = (EditText) findViewById(R.id.funcEditText);
        idEditText = (EditText) findViewById(R.id.idEditText);
        ageGreaterEditText = (EditText) findViewById(R.id.ageSelectEditText);
        nameSelectEditText = (EditText) findViewById(R.id.nameSelectEditText);

        //create object for data
        contentValues = new ContentValues();

        //timer
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerStopStartTextView = (TextView) findViewById(R.id.timerStopStartTextView);

        timerButton = (Button) findViewById(R.id.timerButton);
        timerClearButton = (Button) findViewById(R.id.timerClearButton);
        timerButton.setOnClickListener(new ButtonTimerStopStartOnclickListener());
        timerClearButton.setOnClickListener(new ButtonTimerClearOnClickListener());

        timerSeconds = 0;
        timerStopStartSeconds = 0;
        timerRunning = true;
        timerStopStartRunning = false;

        runTimer();
    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String func = funcEditText.getText().toString();
        String id = idEditText.getText().toString();
        String ageGreater = ageGreaterEditText.getText().toString();
        String nameSelect = nameSelectEditText.getText().toString();
        String orderBy = null;

        switch (v.getId()) {

            case (R.id.mainLinearLayout):

                hideSoftKeyboard();

                break;

            case R.id.addButton:

                addData(name, email, age);

                break;

            case (R.id.readButton):

                readTable();

                break;

            case (R.id.clearButton):

                clearTable();

                break;


            case (R.id.fillDatabaseButton):

                fillDatabase();

                break;

            case (R.id.killDBButton):

                recreateTable();

                break;

            case (R.id.funcReadButton):

                //using count(*) as Count - for func editText field
                funcReadDB(func);

                break;

            case (R.id.updateButton):

                updateRow(id, name, email, age);

                break;


            case (R.id.deleteButton):

                deleteRow(id);

                break;

            case (R.id.ageSelectButton):

                ageGreater(ageGreater);

                break;

            case (R.id.nameSelectButton):

                nameSelect(nameSelect);

                break;

            case (R.id.nameAgeSumButton):

                nameAgeSum(nameSelect);

                break;

            case (R.id.namesAgeSumButton):

                namesAgeSum();

                break;

            case (R.id.sortReadButton):

                switch (sortRadioGroup.getCheckedRadioButtonId()) {

                    case (R.id.nameRadioButton):

                        orderBy = "name";
                        logsToast(orderBy);

                        break;

                    case (R.id.emailRadioButton):

                        orderBy = "email";
                        logsToast(orderBy);

                        break;

                    case (R.id.ageAscRadioButton):

                        orderBy = "age ASC";
                        logsToast(orderBy);

                        break;

                    case (R.id.ageDescRadioButton):

                        orderBy = "age DESC";
                        logsToast(orderBy);

                        break;
                }

                sortedRead(orderBy);

                break;
        }

    }

    private void logs(String message) {

        Log.d(LOG_TAG, "--- " + message + " ---");

    }

    private void fillDatabase() {

        initDatabase();

        String[] names = {"John", "Barry", "Lia", "Dovakin", "Ava", "Omar", "Maria", "Lissa", "Morty",
                "Ricardo", "John", "Richard", "Esmeralda", "Lars", "John", "Elizabeth",
                "Maximilian", "Erik", "Lars", "Liah", "Lucas", "Barry", "James", "James",
                "Ethan", "Richard", "Emma", "Mia", "Emily", "Lena", "John", "Erik", "Ava"};

        String[] emails = {"john.doe@gmail.com", "barry@yahoo.com", "lia.ellyma@gmail.com",
                "dovak@aol.com", "avavava@gmail.com", "omaraio@gmail.com", " maris@yahoo.com",
                "lissalissa@aol.com", "morty4@gmail.com", "ricardo@yahoo.com", "john2222@aol.com",
                "richi@gmail.com", "esmeralda@gmail.com", "lars@yahoo.com", "jonny@gamil.com",
                "eliza@yahoo.com", "maxnormal@gmail.com", "erikred@gmail.com", "lars1983@gmail.com",
                "liah@mail.com", "lucas@yahoo.com", "barry.alen@gmail.com", "jamesjames@yahoo.com",
                "james1990@gmail.com", "ethan@gmail.com", "rich25@gmail.com",
                "emma.venue@gmail.com", "miamomita@yahoo.com", "emily.larson@gmail.com",
                "lena.g@gmail.com", "john123@yahoo.com", "erik@gmail.com", "ava.gardner@gmail.com"};

        int[] ages = {19, 22, 21, 58, 25, 31, 32, 47, 18, 29, 29, 36, 52, 40, 23, 34, 32, 19,
                44, 27, 18, 25, 30, 36, 34, 28, 30, 46, 32, 40, 51, 20, 24};

        logs("names: " + names.length + " emails: " + emails.length + " ages: " + ages.length);

        try {

            for (int i = 0; i < 33; i++) {

                contentValues.put("name", names[i]);
                contentValues.put("email", emails[i]);
                contentValues.put("age", String.valueOf(ages[i]));

                long rowInserted = sqLiteDatabase.insert(MY_TABLE_NAME, null, contentValues);
                logs(rowInserted + " id row inserted: ");
            }
        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();

        }

    }

    private void addData(String name, String email, String age) {

        initDatabase();

        logs("Insert in mytable");

        //prepare data pairs for table
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("age", age);

        //insert data to table and get back id of the row in which data was inserted
        try {

            long rowID = sqLiteDatabase.insert(MY_TABLE_NAME, null, contentValues);

            logsToast("row inserted\n\nID: " + rowID + " name: " + name + " email: " + email + " age: " + age);

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }

    private void readTable() {

        initDatabase();

        //ask data from db - obtain cursor object
        Cursor cursor;

        try {

            //method query(String table name, String[] columns, String selection, String[] selection args, String groupBy, String having, String orderBy)
            //can have boolean distinct at beginning - which tell eliminate duplicates in result or not
            //can have String limit at the end - which tells to how many rows limit results if contains one number like - 5 - tell limit to 5 results
            //or can have 2 numbers like - 3,5 - tell skip first 3 results and limit to 5 results starting from 4th
            //field colums can be used for function too, like - count(*) as Count - to get count of rows in database
            //select all from mytable
            //cursor = sqLiteDatabase.query(MY_TABLE_NAME, null, null, null, null, null, null);
            String[] columns = new String[]{"name,email"};
            cursor = sqLiteDatabase.query(MY_TABLE_NAME, columns, null, null, null, null, null);

            printCursor(cursor);

            cursor.close();

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }

    private void clearTable() {

        initDatabase();

        logs("Clear my table");

        //removes data from database and returns number of deleted rows
        try {

            int clearCount = sqLiteDatabase.delete(MY_TABLE_NAME, null, null);

            logsToast("deleted rows count: " + clearCount);

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            //recreateTable();
            closeDatabase();

        }
    }

    private void recreateTable() {

        try {

            //sqLiteDatabase.execSQL("drop table mytable");
            getApplicationContext().deleteDatabase(MY_DATABASE_NAME);

            logsToast(MY_DATABASE_NAME + " deleted and recreated");

        } catch (SQLiteException e) {

            e.printStackTrace();

        }

    }

    private void funcReadDB(String func) {

        if (func.equalsIgnoreCase("")) {

            logsToast("Enter func fod DB read");

        } else {

            initDatabase();

            Cursor cursor;

            logs("Read func: " + func + " from database");

            String[] columns = new String[]{func};

            try {

                cursor = sqLiteDatabase.query(MY_TABLE_NAME, columns, null, null, null, null, null);

                printCursor(cursor);

            } catch (SQLiteException e) {

                e.printStackTrace();

            } finally {

                closeDatabase();
                hideSoftKeyboard();

            }


        }

    }

    private void updateRow(String id, String name, String email, String age) {

        if (id.equalsIgnoreCase("")) {

            logsToast("Input ID number to update row");

        } else {

            initDatabase();

            logsToast("Update row ID: " + id);

            //prepare data for insert during update
            contentValues.put("name", name);
            contentValues.put("email", email);
            contentValues.put("age", age);

            //update row and return updated rows count
            try {

                int updateCount = sqLiteDatabase.update(MY_TABLE_NAME, contentValues, "id = ?", new String[]{id});

                logs("rows updated: " + updateCount);

            } catch (SQLiteException e) {

                e.printStackTrace();

            } finally {

                closeDatabase();
                hideSoftKeyboard();

            }

        }

    }

    private void deleteRow(String id) {

        if (id.equalsIgnoreCase("")) {

            logsToast("Input ID number to delete row");

        } else {

            logs("Delete row ID: " + id);

            initDatabase();


            //delete row and return deleted rows count
            try {

                int deleteCount = sqLiteDatabase.delete(MY_TABLE_NAME, "id = " + id, null);

                logs("rows deleted: " + deleteCount);

            } catch (SQLiteException e) {

                e.printStackTrace();

            } finally {

                closeDatabase();
                hideSoftKeyboard();

            }

        }

    }

    private void ageGreater(String ageGreater) {

        logsToast("Age greater than " + ageGreater);

        initDatabase();

        Cursor cursor;

        try {

            String selection = "age > ?";
            String[] selectionArgs = new String[]{ageGreater};

            //select all from mytable where age > selectionArgs
            cursor = sqLiteDatabase.query(MY_TABLE_NAME, null, selection, selectionArgs, null, null, null);

            logsToast(String.valueOf(cursor.getCount()));

            printCursor(cursor);

            cursor.close();

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }

    private void nameSelect(String name) {

        initDatabase();

        Cursor cursor;

        try {

            if (!name.equalsIgnoreCase("")) {

                String selector = "name = ?";
                String[] selection = new String[]{name};

                cursor = sqLiteDatabase.query(MY_TABLE_NAME, null, selector, selection, null, null, null);

                printCursor(cursor);

                cursor.close();

            } else {

                logsToast("CATCH ME OUTSIDE WHAT BOUT DAT?");

            }

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();

        }

    }

    private void nameAgeSum(String name) {

        initDatabase();

        Cursor cursor;

        try {

            if (!name.equalsIgnoreCase("")) {

                String[] columns = new String[]{"name", "sum(age) as age"};
                String groupBy = "name";
                String orderBy = "age DESC";
                String selection = "name = ?";
                String[] selectionArgs = new String[]{name};
                cursor = sqLiteDatabase.query(MY_TABLE_NAME, columns, selection, selectionArgs, groupBy, null, orderBy);


                if (cursor.moveToFirst()) {

                    String cursorData;

                    do {

                        for (String column : cursor.getColumnNames()) {

                            cursorData = column + " = " + cursor.getString(cursor.getColumnIndex(column)) + ";";

                            logs(cursorData);

                            if (column.equalsIgnoreCase("age")) {
                                logsToast(cursor.getString(cursor.getColumnIndex(column)) + " years of ages for name: " + name);
                            }

                        }

                    } while (cursor.moveToNext());

                } else {

                    logsToast("Zero, no such name -> " + name + " <- in database");

                }

                cursor.close();

            } else {

                logsToast("Enter name");

            }

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }

    private void namesAgeSum() {

        initDatabase();

        Cursor cursor = null;

        try {

            String[] columns = new String[]{"name", "sum(age) as age"};
            String groupBy = "name";
            String orderBy = "age DESC";
            cursor = sqLiteDatabase.query(MY_TABLE_NAME, columns, null, null, groupBy, null, orderBy);

            if (cursor.moveToFirst()) {

                String cursorData;

                do {

                    for (String column : cursor.getColumnNames()) {

                        cursorData = column + " = " + cursor.getString(cursor.getColumnIndex(column)) + ";";

                        logs(cursorData);

                    }

                } while (cursor.moveToNext());

            } else {

                logsToast("Zero data");

            }

            cursor.close();

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }


    private void sortedRead(String orderBy) {

        logsToast("Sorted read by: " + orderBy);

        initDatabase();

        Cursor cursor;

        try {

            //select all from mytable orderBy = orderBy
            cursor = sqLiteDatabase.query(MY_TABLE_NAME, null, null, null, null, null, orderBy);

            logsToast(String.valueOf(cursor.getCount()));

            printCursor(cursor);

            cursor.close();

        } catch (SQLiteException e) {

            e.printStackTrace();

        } finally {

            closeDatabase();
            hideSoftKeyboard();

        }

    }

    private void printCursor(Cursor cursor) {

        //put cursor on first row of query if exists
        String[] columnNames = cursor.getColumnNames();

        logs(Arrays.toString(columnNames));
        logsToast("Rows in my table: " + cursor.getCount());

        if (cursor.moveToFirst()) {

            int[] columnsIdNumbers = new int[columnNames.length];

            if (columnNames != null) {

                logs("Columns number not null");


                for (int i = 0; i < columnNames.length; i++) {

                    columnsIdNumbers[i] = cursor.getColumnIndex(columnNames[i]);

                }

                logs(Arrays.toString(columnsIdNumbers));

            } else {

                logs("Columns number not null");

            }

            do {

                String message = new String();

                for (int i = 0; i < columnNames.length; i++) {

                    message += columnNames[i] + " = " + cursor.getString(columnsIdNumbers[i]);

                    if (i + 1 < columnNames.length) {

                        message += ", ";

                    } else {

                        message += " !!!";

                    }

                }

                logs(message);

            }
            //cursor to next row if exists
            while (cursor.moveToNext());

        } else {

            logsToast("0 rows in " + MY_TABLE_NAME);

        }

    }

    private void initDatabase() {

        sqLiteHelper = SQLiteHelper.getInstance(getApplicationContext());

        if (sqLiteDatabase == null) {

            sqLiteDatabase = sqLiteHelper.getWritableDatabase();

            logs("Database initialized");

        } else {

            if (!sqLiteDatabase.isOpen()) {

                sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                logs("Database opened");

            } else {

                logs("Database already initialized and opened");

            }

        }

    }

    private void closeDatabase() {

        //helper close() closes connection and nullifying reference(link) to an object
        sqLiteHelper.close();

//        sqLiteDatabase.close();

        logs("Database closed");

    }

    private void toastShow(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    private void logsToast(String message) {

        logs(message);
        toastShow(message);

    }

    private void hideSoftKeyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    private class TimerRunnable implements Runnable {

        @Override
        public void run() {

            if (timerRunning) {

                int secondsTimer = timerSeconds % 60;
                int minutesTimer = (timerSeconds % 3600) / 60;
                int hoursTimer = timerSeconds / 3600;

                String time = String.format("%02d:%02d:%02d", hoursTimer, minutesTimer, secondsTimer);

                timerTextView.setText(time);

                timerSeconds += 1;

            }

            if (timerStopStartRunning) {

                int secondsTimerStopStart = timerStopStartSeconds % 60;
                int minutesTimerStopStart = (timerStopStartSeconds % 3600) / 60;
                int hoursTimerStopStart = timerStopStartSeconds / 3600;

                String timeStopStart = String.format("%02d:%02d:%02d", hoursTimerStopStart, minutesTimerStopStart, secondsTimerStopStart);

                timerStopStartTextView.setText(timeStopStart);

                timerStopStartSeconds += 1;

            }

            handler.postDelayed(this, 1000);

        }

    }

    private class ButtonTimerStopStartOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (timerStopStartRunning) {

                logs("timer stop");

                timerStopStartRunning = false;

            } else {

                logs("timer go");

                timerStopStartRunning = true;

            }

        }

    }

    private class ButtonTimerClearOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            logs("timer clear");

            timerStopStartSeconds = 0;
            timerStopStartTextView.setText("00:00:00");

        }

    }

    private void runTimer() {

        logs("running");

        handler = new Handler();

        handler.post(new TimerRunnable());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.join_tables:

                Intent intent = new Intent(getApplicationContext(), JoinTablesActivity.class);
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}


//transaction when opens - lock database and you can't establish new connection to database
// before transaction close
//    db.beginTransaction();
//    try {
//        ...
//        db.setTransactionSuccessful();
//    } finally {
//        db.endTransaction();
//    }


//to prevent data loss on orientation change
//disable orientation change by adding to activities description in AndroidManifest.xml
//android:screenOrientation="portrait"

//or
//forbid recreate view on orientation and size change by adding to activities description
// in AndroidManifest.xml
// <activity android:name=".MainActivity"
// android:configChanges="orientation|screenSize">

//or
//pass values to onCreate method on orientation change
//    @Override
//    public void onSaveInstanceState(Bundle bundleOutState) {
//
//        bundleOutState.putInt("seconds", seconds);
//        bundleOutState.putBoolean("running", running);
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        setTitle("SQLite example");
//
//        if(savedInstanceState != null){
//
//            seconds = savedInstanceState.getInt("seconds");
//            running = savedInstanceState.getBoolean("running");
//
//        }
//
//    }






























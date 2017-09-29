package com.konggit.sqliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "MainActivity Logs";
    private final String MY_DATABASE_NAME = "myDataBase";
    private final String MY_TABLE_NAME = "mytable";

    LinearLayout mainLinearLayout;

    Button addButton;
    Button readButton;
    Button clearButton;
    Button updateButton;
    Button deleteButton;
    Button fillDatabaseButton;
    Button killDBButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("SQLite example");

        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);

        addButton = (Button) findViewById(R.id.addButton);
        readButton = (Button) findViewById(R.id.readButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        killDBButton = (Button) findViewById(R.id.killDBButton);
        fillDatabaseButton = (Button) findViewById(R.id.fillDatabaseButton);
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
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        killDBButton.setOnClickListener(this);
        fillDatabaseButton.setOnClickListener(this);
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

//        sqLiteHelper = null;
//        SQLiteDatabase sqLiteDatabase = null;

        logs("11111111Rows in my table");
    }


    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String age = ageEditText.getText().toString();
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

            case (R.id.updateButton):

                updateRow(id, name, email, age);

                break;


            case (R.id.deleteButton):

                deleteRow(id);

                break;

            case (R.id.fillDatabaseButton):

                fillDatabase();

                break;

            case (R.id.killDBButton):

                recreateTable();

                break;

            case (R.id.ageSelectButton):

                ageGreater(ageGreater);

                break;

            //todo

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

        //ask all data from db - obtain cursor object
        Cursor cursor = null;

        try {

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

        //initDatabase();

        try {

            //sqLiteDatabase.execSQL("drop table mytable");
            getApplicationContext().deleteDatabase(MY_DATABASE_NAME);

            logsToast(MY_DATABASE_NAME + " deleted and recreated");

        } catch (SQLiteException e) {

            e.printStackTrace();

        }
//        finally {
//
//            closeDatabase();
//
//        }

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

        sqLiteHelper.close();

        sqLiteDatabase.close();

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


//    db.beginTransaction();
//    try {
//        ...
//        db.setTransactionSuccessful();
//    } finally {
//        db.endTransaction();
//    }

}

































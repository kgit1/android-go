package com.konggit.appnot;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.konggit.appnot.data.Data;
import com.konggit.appnot.data.InternalStorage;
import com.konggit.appnot.data.SQLiteSingleStorageHelper;
import com.konggit.appnot.data.SharedPreferencesStorage;
import com.konggit.appnot.data.TestBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    public static String PREFERENCES_VALUE_NAME = "DATA_JSON_STRING";

    private TextView dateTextView;
    private TextView sumTextView;
    private EditText numbersEditText;
    private Button addButton;
    private Button saveButton;
    private Button readButton;
    private Button sharedPreferencesListButton;
    private Button internalStorageButton;

    private Data sharedPreferencesStorage;
    private Data internalStorage;

    private ListView listView;
    private ResultListAdapter adapter;

    private Map<Date, Results> info;

    private int defaultNumber = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //In the AndroidManifest.xml:
        //<activity android:name="com.your.package.ActivityName"
        //android:windowSoftInputMode="stateHidden"  />
        //       or try
        //Use this attributes in your layout tag in XML file:
        //android:focusable="true"
        //android:focusableInTouchMode="true"
        //As reported by other members in comments it doesn't works on ScrollView therefore you need to add these attributes to the main child of ScrollView.
        //      or try this in onCreate
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setOnClickListener(new LayoutOnClickListener());

        sharedPreferencesStorage = new SharedPreferencesStorage(getApplicationContext());
        internalStorage = new InternalStorage(getApplicationContext());

        dateTextView = (TextView) findViewById(R.id.dateTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);

        //to change button on EditText from next to DONE
        //android:imeOptions="actionDone"
        // or numbersEditText..setImeOptions(EditorInfo.IME_ACTION_DONE);
        numbersEditText = (EditText) findViewById(R.id.numbersEditText);

        addButton = (Button) findViewById(R.id.addButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        readButton = (Button) findViewById(R.id.readButton);
        sharedPreferencesListButton = (Button) findViewById(R.id.sharedPreferencesListButton);
        internalStorageButton = (Button) findViewById(R.id.internalStorageButton);

        info = new TreeMap<>();
        feelMap();

        addButton.setOnClickListener(new AddButtonOnClickListener());
        saveButton.setOnClickListener(new SaveButtonOnClickListener());
        readButton.setOnClickListener(new ReadButtonOnClickListener());
        sharedPreferencesListButton.setOnClickListener(new SharedPreferencesListButtonOnClickListener());
        internalStorageButton.setOnClickListener(new InternalStorageButtonOnClickListener());
        //external storage - uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"


        dateTextView.setText(formattedDate(currentDate()));

        listView = (ListView) findViewById(R.id.testList);
        adapter = new ResultListAdapter(getApplicationContext(), info);
        listView.setAdapter(adapter);

//        TestBuilder testBuilder = new TestBuilder.Builder(12, 22)
//                .thirdValue("3")
//                .fourthValue("4")
//                .fifthValue("5")
//                .sixthValue("6")
//                .build();
//
//        Log.i("BUILDER", testBuilder.toString());

    }

    private void updateActivity(int number) {

        sumTextView.setText(String.valueOf(number));
        numbersEditText.setText("");

    }

    ////////////date methods

    private Date currentDate() {

        Date currentDate = Calendar.getInstance().getTime();

        Log.i("Info", "1 " + currentDate.toString());

        return currentDate;
    }

    private String formattedDate(Date currentDate) {

        String formattedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        formattedDate = formatter.format(currentDate);

        return formattedDate;
    }

    private Date normalizedDate() {

        String formattedDate = formattedDate(currentDate());

        Log.i("NoooooooooormalDateInfo", "2 " + formattedDate);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ERA, 0);
        Date normalizedDate = calendar.getTime();

        //Log.i("Info", "2 " + normalizedDate.toString());

        try {
            normalizedDate = formatter.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Log.i("Info", "3 " + normalizedDate.toString());

        return normalizedDate;
    }

    private Date fakeDate(String date) {

        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.ERA, 0);
        Date normalizedDate = calendar.getTime();

        try {
            normalizedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return normalizedDate;
    }

    private void feelMap() {

        //28 Aug 2017
        Map<Date, Integer> path = new TreeMap<>();

        info.put(fakeDate("28 Aug 2017"), new Results(defaultNumber, 122, path));
        info.put(fakeDate("26 Aug 2017"), new Results(defaultNumber, 222, path));
        info.put(fakeDate("25 Aug 2017"), new Results(defaultNumber, 322, path));
        info.put(fakeDate("24 Aug 2017"), new Results(defaultNumber, 422, path));
        info.put(fakeDate("23 Aug 2017"), new Results(defaultNumber, 522, path));
        info.put(fakeDate("22 Aug 2017"), new Results(defaultNumber, 622, path));
        info.put(fakeDate("12 Aug 2017"), new Results(defaultNumber, 722, path));
        info.put(fakeDate("21 Aug 2016"), new Results(defaultNumber, 12, path));

    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

////////////////////////////////reworked listeners

    private class LayoutOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            hideSoftKeyboard();

        }
    }


    //todo - remake to work with saved content
    private class AddButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (numbersEditText.getText().toString().trim().length() > 0) {

                int number = Integer.valueOf(numbersEditText.getText().toString());

                if (!info.isEmpty()) {

                    //Log.i("Button", "00 " + info);

                    if (!info.containsKey(normalizedDate())) {

                        Map<Date, Integer> path = new TreeMap<>();
                        path.put(currentDate(), number);

                        Results results = new Results(defaultNumber, number, path);

                        info.put(normalizedDate(), results);

                        Log.i("Button", "01 " + number);
                        Log.i("Button", "02 " + path);
                        Log.i("Button", "03 " + results);
                        Log.i("Button", "04 " + info);

                        adapter.notifyDataSetChanged();
                        updateActivity(number);

                    } else {

                        Results results = info.get(normalizedDate());

                        int recalculatedNumber = results.getNumbers() + number;

                        results.setNumbers(recalculatedNumber);

                        results.getPath().put(currentDate(), recalculatedNumber);

                        info.put(normalizedDate(), results);

                        Log.i("Button", "11 " + number);
                        Log.i("Button", "12 " + results.getPath());
                        Log.i("Button", "13 " + results);
                        Log.i("Button", "14 " + info);

                        updateActivity(recalculatedNumber);
                    }

                } else {

                    Map<Date, Integer> path = new TreeMap<>();
                    path.put(currentDate(), number);

                    Results results = new Results(defaultNumber, number, path);

                    info.put(normalizedDate(), results);

                    Log.i("Button", "21 " + number);
                    Log.i("Button", "22 " + path);
                    Log.i("Button", "23 " + results);
                    Log.i("Button", "24 " + info);

                    adapter.notifyDataSetChanged();
                    updateActivity(number);
                }

                hideSoftKeyboard();
                adapter.notifyDataSetChanged();

            } else {

                Toast.makeText(getApplicationContext(), "FIELD IS EMPTY", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private class SaveButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            sharedPreferencesStorage.setData(info, PREFERENCES_VALUE_NAME);

            internalStorage.setData(info, PREFERENCES_VALUE_NAME);

            SQLiteSingleStorageHelper sqlite = new SQLiteSingleStorageHelper(getApplicationContext());

            sqlite.addResults(info);

            Log.i("Save click", "1 " + info.toString());

            Toast.makeText(getApplicationContext(), "SAVED: " + info.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private class ReadButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Map<Date, Results> data = sharedPreferencesStorage.getData(PREFERENCES_VALUE_NAME);

            Map<Date, Results> data1 = internalStorage.getData(PREFERENCES_VALUE_NAME);

            Log.i("Read click", data.toString());

            Toast.makeText(getApplicationContext(), "LOADED: " + data.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "LOADED2: " + data1.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private class SharedPreferencesListButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            changeActivity(SharedPreferencesListActivity.class);

        }
    }

    private class InternalStorageButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            changeActivity(InternalStorageListActivity.class);

        }
    }

    private void changeActivity(Class activityClass) {

        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivity(intent);

    }

}









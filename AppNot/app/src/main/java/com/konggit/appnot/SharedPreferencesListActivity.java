package com.konggit.appnot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.konggit.appnot.data.Data;
import com.konggit.appnot.data.SharedPreferencesStorage;

import java.util.Date;
import java.util.Map;

public class SharedPreferencesListActivity extends AppCompatActivity {

    public static String PREFERENCES_VALUE_NAME = "DATA_JSON_STRING";

    private Data sharedPreferencesStorage;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_list);

        setTitle("SharedPreferences List");

        sharedPreferencesStorage = new SharedPreferencesStorage(getApplicationContext());

        Map<Date, Results> data = sharedPreferencesStorage.getData(PREFERENCES_VALUE_NAME);

        listView = (ListView) findViewById(R.id.sharedPreferencesListView);
        ResultListAdapter adapter = new ResultListAdapter(getApplicationContext(), data);

        listView.setAdapter(adapter);

    }
}

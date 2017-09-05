package com.konggit.appnot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.konggit.appnot.data.Data;
import com.konggit.appnot.data.InternalStorage;

import java.util.Date;
import java.util.Map;

public class InternalStorageListActivity extends AppCompatActivity {

    public static String INTERNAL_STORAGE_NAME = "DATA_JSON_STRING";

    private Data internalStorage;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage_list);

        internalStorage = new InternalStorage(getApplicationContext());

        Map<Date, Results> data = internalStorage.getData(INTERNAL_STORAGE_NAME);

        listView = (ListView) findViewById(R.id.internalStorageListView);

        ResultListAdapter adapter = new ResultListAdapter(getApplicationContext(), data);

        listView.setAdapter(adapter);
    }
}

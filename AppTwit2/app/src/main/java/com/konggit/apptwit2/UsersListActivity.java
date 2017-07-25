package com.konggit.apptwit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    ArrayList<String> usersList=new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        usersList.add("Rob");
        usersList.add("Marry");
        usersList.add("had");
        usersList.add("a");
        usersList.add("little");
        usersList.add("lamb");

        ListView listView = (ListView)findViewById(R.id.listView);

        //allow checkboxes in listView, put multiple choices mode for checkboxes
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        //use adapter with item layout with checkboxes - android.R.layout.simple_list_item_checked
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_checked,usersList);
        listView.setAdapter(adapter);
    }
}

package com.konggit.apptwit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TestList extends AppCompatActivity {

    private List<List<String>> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> box = new ArrayList<>();
            box.add("Mimi " + i);
            box.add("BiBi " + i);
            list.add(box);

        }

        listView = (ListView) findViewById(R.id.contactsList);
        ViewHolderAdapter adapter = new ViewHolderAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);

    }

    private void logout() {

        Log.i("ListLogout", "User : " + ParseUser.getCurrentUser().getUsername());
        ParseUser.logOut();
        mainActivity();

    }

    private void mainActivity() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contacts_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.help:
                Log.i("Menu", "Help selected");
                return true;

            case R.id.log_out:
                Log.i("Menu", "Log Out selected");
                logout();

            default:
                return false;
        }

    }
}

package com.konggit.appwazup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ListView userListView;
    ArrayList<String> userList;
    UserViewHolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User: " + ParseUser.getCurrentUser().getUsername());

        userList = new ArrayList<>();
        feelUserList();

        userListView = (ListView) findViewById(R.id.listVievUserList);
        adapter = new UserViewHolderAdapter(getApplicationContext(), userList);

        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(new UserListOnClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userlist_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.settings:
                return true;

            case R.id.logout:
                logout();
                return true;

            default:
                return false;
        }

    }

    private void feelUserList() {

        userList.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseUser user : objects) {

                            userList.add(user.getUsername());

                        }

                        adapter.notifyDataSetChanged();
                    }

                } else {

                    Log.i("Info userlist", e.getMessage());

                }

            }
        });

    }

    private class UserListOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            redirectToChat(userList.get(position));

        }
    }

    private void redirectToChat(String chatbaddy) {

        Log.i("Info", "Redirect to chat with " + chatbaddy);
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("activeUser", chatbaddy);
        startActivity(intent);

    }

    private void logout() {

        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}

package com.konggit.apptwit2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    ArrayList<String> usersList = new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        setTitle("User List");

        if (ParseUser.getCurrentUser().getList("isFollowing") == null) {

            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing", emptyList);
            ParseUser.getCurrentUser().saveInBackground();
            Log.i("-log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

        }else{

            Log.i("+log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

        }

        usersList.add("Rob");
        usersList.add("Marry");
        usersList.add("had");
        usersList.add("a");
        usersList.add("little");
        usersList.add("lamb");

        listView = (ListView) findViewById(R.id.listView);

        //allow checkboxes in listView, put multiple choices mode for checkboxes
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        //use adapter with item layout with checkboxes - android.R.layout.simple_list_item_checked
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_checked, usersList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if (checkedTextView.isChecked()) {

                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));
                    Log.i("CheckedItem", "Row is checked");

                    ParseUser.getCurrentUser().getList("isFollowing").add(usersList.get(position));
                    ParseUser.getCurrentUser().put("isFollowing",ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                Log.i("Save","Success");

                            }else{

                                Log.i("Save",""+e);

                            }

                        }
                    });
                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));


                } else {

                    Log.i("CheckedItem", "Row is unchecked");
                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().getList("isFollowing").remove(usersList.get(position));
                    ParseUser.getCurrentUser().put("isFollowing",ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                Log.i("Save","Success");

                            }else{

                                Log.i("Save",""+e);

                            }

                        }
                    });

                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

                }

            }
        });

        users();
    }

    private void users() {

        usersList.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseUser user : objects) {

                            usersList.add(user.getUsername());

                        }

                        Collections.sort(usersList);

                        adapter.notifyDataSetChanged();

                       // for(String username : usersList){

                            for(String followedUser: (ArrayList<String>)ParseUser.getCurrentUser().get("isFollowing")){

                                if(usersList.contains(followedUser)){

                                    listView.setItemChecked(usersList.indexOf(followedUser),true);

                                }

                            }

                        //}

                    }

                }

            }
        });

    }

    ///////MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.users_list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                Log.i("Menu", " logout selected");
                return true;
            case R.id.help:
                Log.i("Menu", " help selected");
                return true;
            default:
                return false;
        }
    }

    private void logout() {

        ParseUser.logOut();
        redirectToMain();

    }

    private void redirectToMain() {
        Log.i("Intent", "UserList->Main");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}

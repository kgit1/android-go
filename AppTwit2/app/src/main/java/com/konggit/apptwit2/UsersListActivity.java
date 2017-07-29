package com.konggit.apptwit2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
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

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        currentUser = ParseUser.getCurrentUser().getUsername();

        setTitle("Userslist for user :  " + currentUser);

        if (ParseUser.getCurrentUser().getList("isFollowing") == null) {

            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing", emptyList);
            ParseUser.getCurrentUser().saveInBackground();
            Log.i("-log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

        } else {

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
                    ParseUser.getCurrentUser().put("isFollowing", ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Log.i("Save", "Success");

                            } else {

                                Log.i("Save", "" + e);

                            }

                        }
                    });
                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));


                } else {

                    Log.i("CheckedItem", "Row is unchecked");
                    Log.i("log list", "" + ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().getList("isFollowing").remove(usersList.get(position));
                    ParseUser.getCurrentUser().put("isFollowing", ParseUser.getCurrentUser().getList("isFollowing"));

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Log.i("Save", "Success");

                            } else {

                                Log.i("Save", "" + e);

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

                        for (String followedUser : (ArrayList<String>) ParseUser.getCurrentUser().get("isFollowing")) {

                            if (usersList.contains(followedUser)) {

                                listView.setItemChecked(usersList.indexOf(followedUser), true);

                            }

                        }

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
                Log.i("Menu", " logout selected");
                logout();
                return true;
            case R.id.tweet:
                Log.i("Menu", " tweet selected");
                tweet();
                return true;
            case R.id.feed:
                Log.i("Menu", " feed selected");
                feed();
                return true;
            case R.id.help:
                Log.i("Menu", " help selected");
                return true;
            default:
                return false;
        }
    }

    private void tweet() {

        //create alertDialog for frame from menu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Send a tweet");

        //create edit text for input field
        final EditText tweetContentEditText = new EditText(this);

        //set editeText as view for alertDialog
        builder.setView(tweetContentEditText);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.i("Tweet", "Content "+String.valueOf(tweetContentEditText.getText()));

                ParseObject tweetObject = new ParseObject("Tweet");

                tweetObject.put("username",ParseUser.getCurrentUser().getUsername());

                tweetObject.put("tweet", tweetContentEditText.getText().toString());

                tweetObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            Log.i("Tweet","Saved successfully");
                            Toast.makeText(getApplicationContext(),"Tweet sent successfully", Toast.LENGTH_SHORT).show();

                        }else{

                            Log.i("Tweet","Failed to save");
                            Toast.makeText(getApplicationContext(),"Tweet Failed to sent", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.i("Tweet","Canceled");
                dialog.cancel();

            }
        });

        builder.show();
    }

    private void feed() {

        Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
        startActivity(intent);

    }

    private void logout() {

        Log.i("Logout", "logout user");

        ParseUser.logOut();
        redirectToMain();

    }

    private void redirectToMain() {

        Log.i("Intent", "UserList->Main");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}

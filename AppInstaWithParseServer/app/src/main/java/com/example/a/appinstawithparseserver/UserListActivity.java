package com.example.a.appinstawithparseserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    List<String> usersList;
    ListView usersListView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_);

        usersList = new ArrayList<>();
        usersListView = (ListView) findViewById(R.id.usersListView);

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, usersList);
        usersListView.setAdapter(arrayAdapter);

        //Log In
        /*try {
            ParseUser.logIn("12345","123456");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String user =ParseUser.getCurrentUser().getString("username");
        Log.i("Username",user);*/


        //create querry to obtain set of users from parse server
        ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();

        //if we need not all, but all except current user
        //usersQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        //if we need alphabetical order
        usersQuery.orderByAscending("username");

        //execute query
        usersQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser parseUser : objects) {
                            // Log.i("username", parseUser.getString("username"));
                            Log.i("ID", parseUser.getObjectId());
                            Log.i("username", parseUser.getUsername());
                            // usersList.add(parseUser.getString("username"));
                            usersList.add(parseUser.getUsername());
                        }
                        //update adapter to change listView display
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("Fail", "users query");
                }

            }
        });
        //Pa

        /*ParseQuery<ParseObject> usersQuery = ParseQuery.getQuery("Users");

        usersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for(ParseObject object : objects){
                        usersList.add(object.getString("username"));
                        Log.i("objects","here");
                        Log.i("objects",objects.toString());
                        Log.i("object",object.toString());
                    }
                }else{
                    Log.i("Fail",e.toString());
                }
            }
        });*/
    }
}

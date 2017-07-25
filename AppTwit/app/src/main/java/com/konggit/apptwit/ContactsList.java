package com.konggit.apptwit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ContactsList extends AppCompatActivity {

    private List<ContactListItem> list;
    private List<String> usersList;
    private List<String> userContactsList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        Log.i("ContactList", "Oncreate start");

        usersList();
        userContacts();

        list = checkedContacts(userContactsList, usersList);

        Log.i("UserList", "checkedContacts users1111111111" + usersList);
        Log.i("UserList", "checkedContacts users1111111111" + userContactsList);

        listView = (ListView) findViewById(R.id.contactsList);
        ContactsListViewHolderAdapter adapter = new ContactsListViewHolderAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);

        Log.i("ContactList", "Oncreate finish");
    }

    private void usersList() {

        Log.i("ContactList", "userList start");

        usersList = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground((new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                Log.i("UserList", "parseObjects users" + objects);


                if (e == null && objects != null) {


                    for (ParseUser user : objects) {

                        Log.i("UserList", "//////////////");


                        usersList.add(user.getUsername());

                    }

                } else {

                    Log.i("ParseQuery", "Error : " + String.valueOf(e));

                }

            }
        }));

        Log.i("UserList", "checkedContacts users" + usersList);
    }

    private void userContacts() {

        Log.i("ContactList", "userContacts start");
        userContactsList = new ArrayList<>();
        if (ParseUser.getCurrentUser().get("contacts") != null) {

            userContactsList = (ArrayList<String>) ParseUser.getCurrentUser().get("contacts");

        } else {

            Log.i("UserContacts", "Can't find any contacts for " + ParseUser.getCurrentUser().getUsername() + " user");

        }
    }

    private ArrayList<ContactListItem> checkedContacts(List<String> contacts, List<String> users) {

        Log.i("ContactList", "checkedContacts start");
        ArrayList<ContactListItem> checkedContacts = new ArrayList<>();
        for (String username : users) {

            Log.i("ContactList", "checkedContacts users");
            ContactListItem item = new ContactListItem();
            item.setUsername(username);
            Log.i("ContactList", "checkedContacts user added " + username);

            if (contacts.contains(username)) {

                item.setChecked(true);

            }

            checkedContacts.add(item);

        }

        for (ContactListItem item : checkedContacts) {

            Log.i("Item", "" + item);

        }

        return checkedContacts;
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

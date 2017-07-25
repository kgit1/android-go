package com.konggit.apptwit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

//http://ec2-34-212-136-147.us-west-2.compute.amazonaws.com:80/apps
public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new ButtonLoginListener());

        loggedCheck();
    }

    private class ButtonLoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            signupOrLogin(String.valueOf(username.getText()), String.valueOf(password.getText()));

        }
    }

    private void loggedCheck() {

        if (ParseUser.getCurrentUser() != null) {

            contactList();
            Log.i("LogChecked", ParseUser.getCurrentUser().getUsername());

        }

    }

    private void login(final String username, final String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {

                if (user != null) {

                    contactList();
                    Log.i("Login", "Successful login user: " + username + " : " + password);

                } else {

                    Log.i("Login", "Error! login user: " + username + " : " + password);
                    Log.i("Login", String.valueOf(e));

                }
            }
        });

    }


    private void signupOrLogin(final String username, final String password) {

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    contactList();
                    Log.i("Signup", "Success! New user: " + username + " : " + password);

                } else {

                    if (e.getCode() == ParseException.USERNAME_TAKEN) {

                        login(username, password);

                    } else {

                        Log.i("Signup", "Error, user not created");
                        Log.i("Signup", username);
                        Log.i("Signup", password);
                        Log.i("Signup", String.valueOf(e));

                    }
                }

            }
        });

    }

    private void contactList() {

        Intent intent = new Intent(getApplicationContext(), ContactsList.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help) {

            Log.i("Menu", "Help selected");

        }

        return super.onOptionsItemSelected(item);
    }

    //    private void userExists(final String username, final String password) {
//
//        ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();
//        usersQuery.whereExists(username);
//        usersQuery.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> objects, ParseException e) {
//
//                if (e == null) {
//                    if (objects != null) {
//
//                        Log.i("CheckUser", "Exists");
//                        login(username, password);
//
//                    } else {
//
//                        Log.i("CheckUser", "Doesn't exist");
//                        signUp(username, password);
//
//                    }
//                }
//
//            }
//        });
//    }

//    private void signUp(final String username, final String password) {
//
//        ParseUser user = new ParseUser();
//        user.setUsername(username);
//        user.setPassword(password);
//
//        //just adding some field with some value if needed
//        user.put("phone", " 62-62-62");
//
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//
//                if (e == null) {
//
//                    Log.i("Signup", "Success! New user: " + username + " : " + password);
//                    contactList();
//
//                } else {
//
//                    Log.i("Signup", "Error, user not created");
//                    Log.i("Signup", String.valueOf(e));
//
//                }
//
//            }
//        });
//
//    }
}

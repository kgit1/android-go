/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

//http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps
public class MainActivity extends AppCompatActivity {

    EditText editUser;
    EditText editPassword;
    Button buttonLogIn;
    Button buttonSignUp;
    TextView orLogin;
    TextView orSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUser = (EditText) findViewById(R.id.editTextUser);
        editPassword = (EditText) findViewById(R.id.editTextPassword);

        orLogin = (TextView) findViewById(R.id.textOrLogin);
        orSignUp = (TextView) findViewById(R.id.textOrSignUp);
        orLogin.setVisibility(View.INVISIBLE);

        buttonLogIn = (Button) findViewById(R.id.buttonLogin);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setVisibility(View.INVISIBLE);


        buttonLogIn.setOnClickListener(new LoginListener());
        buttonSignUp.setOnClickListener(new SignUpListener());

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    private class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i("login 0", "0");
            if (!String.valueOf(editUser.getText()).equals("")  && !String.valueOf(editPassword.getText()).equals("")) {
                String username = String.valueOf(editUser.getText());
                String password = String.valueOf(editPassword.getText());
                functionButtonLogin(username, password);
            } else {
                toast("Enter both username and password");
            }
        }
    }

    private class SignUpListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i("signup 0", "0");
            if (!String.valueOf(editUser.getText()).equals("")  && !String.valueOf(editPassword.getText()).equals("")) {
                String username = String.valueOf(editUser.getText());
                String password = String.valueOf(editPassword.getText());
                functionButtonSignUp(username, password);
            } else {
                toast("Enter both username and password");
            }
        }
    }

    public void functionSwitchToLogin(View view) {
        orLogin.setVisibility(View.INVISIBLE);
        orSignUp.setVisibility(View.VISIBLE);

        buttonSignUp.setVisibility(View.INVISIBLE);
        buttonLogIn.setVisibility(View.VISIBLE);
    }

    public void functionSwitchToSignUp(View view) {
        orLogin.setVisibility(View.VISIBLE);
        orSignUp.setVisibility(View.INVISIBLE);

        buttonSignUp.setVisibility(View.VISIBLE);
        buttonLogIn.setVisibility(View.INVISIBLE);
    }

    private void toast(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }


    private void functionButtonLogin(final String username, final String password) {
        Log.i("login 1", "1");

        ParseQuery<ParseObject> queryUsers = ParseQuery.getQuery("Users");
        //queryUsers.whereContains("username", username);
        //queryUsers.whereContains("password", password);
        queryUsers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                boolean login = false;
                Log.i("login 2", "2");
                Log.i("login username", username);
                Log.i("login password", password);
                if (e == null && objects != null) {
                    for (ParseObject object : objects) {
                        if (object.getString("username").equals(username)) {
                            if (object.getString("password").equals(password)) {
                                Log.i("Success LogIn", objects.toString());
                                toast("Success: You logged in");
                                login = true;
                            }
                        }
                    }

                } else {
                    Log.i("Failed LogIn", e.toString());
                }
                if (!login) {
                    Log.i("Failed LogIn", "wrong user or password");
                    toast("Fail: Wrong user or password");
                }
            }
        });
    }

    private void functionButtonSignUp(final String username, final String password) {
        Log.i("signup 1", "1");

        ParseQuery<ParseObject> queryUsers = ParseQuery.getQuery("Users");
        //queryUsers.whereContains("username", username);
        //queryUsers.whereContains("password", password);
        queryUsers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                boolean login = false;
                Log.i("signup 2", "2");
                Log.i("signup username", username);
                Log.i("signup password", password);
                if (e == null && objects != null) {
                    for (ParseObject object : objects) {
                        if (object.getString("username").equals(username)) {
                            Log.i("Username already exists", objects.toString());
                            toast("Fail: Username already exists");
                            login = true;
                        }
                    }

                } else {
                    Log.i("Failed signup", e.toString());
                }
                if (!login) {
                    Log.i("Signup process", "Username free");
                    ParseObject user = new ParseObject("Users");
                    user.put("username", username);
                    user.put("password", password);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("Successful", "new user created");
                                toast("Success: New user created");
                            } else {
                                Log.i("Failed", "new user not created");
                                toast("Fail: New user not created");
                            }
                        }
                    });
                }
            }
        });
    }

}
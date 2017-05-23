/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

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

        RelativeLayout relativeLayout =(RelativeLayout)findViewById(R.id.relativelayout);

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
            Log.i("login listener", "0");
            String username = String.valueOf(editUser.getText());
            String password = String.valueOf(editPassword.getText());
            if (!username.equals("")  && !password.equals("")) {
            //if (!String.valueOf(editUser.getText()).equals("")  && !String.valueOf(editPassword.getText()).equals("")) {
                //if()

                functionButtonLogin(username, password);
            } else {
                toast("A username and password required");
            }
        }
    }

    private class SignUpListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i("signup listener", "0");
           // if (!String.valueOf(editUser.getText()).equals("")  && !String.valueOf(editPassword.getText()).equals("")) {
            String username = String.valueOf(editUser.getText());
            String password = String.valueOf(editPassword.getText());
            if (!username.equals("")  && !password.equals("")) {
                if(username.length()>4 && username.length()<15){
                    if(password.length()>5 && password.length()<20){
                        functionButtonSignUp(username, password);
                    } else{
                        toast("Password must be longer than 5 and shorter than 20 symbols");
                    }
                }else{
                    toast("Username must be longer than 4 ans shorter than 15 symbols");
                }
            } else {
                toast("A username and password required");
            }
        }
    }

    public void functionSwitchToLogin(View view) {
        orLogin.setVisibility(View.INVISIBLE);
        orSignUp.setVisibility(View.VISIBLE);

        buttonSignUp.setVisibility(View.INVISIBLE);
        buttonLogIn.setVisibility(View.VISIBLE);
       // hideSoftKeyboard(this);
    }

    public void functionSwitchToSignUp(View view) {
        orLogin.setVisibility(View.VISIBLE);
        orSignUp.setVisibility(View.INVISIBLE);

        buttonSignUp.setVisibility(View.VISIBLE);
        buttonLogIn.setVisibility(View.INVISIBLE);
       // hideSoftKeyboard(this);
    }

    private void toast(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }


    private void functionButtonLogin(final String username, final String password) {
        Log.i("login 1", "1");

        //use of buildin class ParseUser
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null){
                    Log.i("Login", "Successful");
                    toast("Login successful");
                }else {
                    toast(e.getMessage().toString());
                }
            }
        });



        /*//simple way with own class
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
        });*/
    }

    private void functionButtonSignUp(final String username, final String password) {
        Log.i("signup function", "1");

        //use of buildin class ParseUser
        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("SignUp", "Successful");
                    toast("SignUp successful");
                }else{
                    toast(e.getMessage().toString());
                }
            }
        });

        /*//simple way with own class
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
        });*/
    }

    //hide keyboard
    public  void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void functionHideSoftKeyboard(View view){
        hideSoftKeyboard(this);
    }

}

/*
* Make the parent view(content view of your activity) clickable and focusable by adding the following attributes
    android:clickable="true"
    android:focusableInTouchMode="true"
Implement a hideKeyboard() method
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
Lastly, set the onFocusChangeListener of your edittext.
    edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    });
As pointed out in one of the comments below, this might not work if the parent view is a ScrollView. For such case, the clickable and focusableInTouchMode may be added on the view directly under the ScrollView.

*/

/*public void setupUI(View view) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (!(view instanceof EditText)) {
        view.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(MyActivity.this);
                return false;
            }
        });
    }

    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View innerView = ((ViewGroup) view).getChildAt(i);
            setupUI(innerView);
        }
    }
}*/
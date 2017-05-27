package com.example.a.appinstaparseserverrebuild;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        editUser = (EditText) findViewById(R.id.editTextUser);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        Log.i("onCreate","main");

        //two realisations of keyboard Done(Enter) key listeners
        //editPassword.setOnEditorActionListener(new KeyboardDoneListener());
        editPassword.setOnKeyListener(new KeyboardDoneKeyListener());

        orLogin = (TextView) findViewById(R.id.textOrLogin);
        orSignUp = (TextView) findViewById(R.id.textOrSignUp);
        orLogin.setVisibility(View.INVISIBLE);

        buttonLogIn = (Button) findViewById(R.id.buttonLogin);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setVisibility(View.INVISIBLE);

        //check if user already logged in adn if true - forward him to userList intent
        /*(if (ParseUser.getCurrentUser() != null) {
            changeIntent();
        }*/


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
            if (!username.equals("") && !password.equals("")) {

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
            if (!username.equals("") && !password.equals("")) {
                if (username.length() > 4 && username.length() < 15) {
                    if (password.length() > 5 && password.length() < 20) {
                        functionButtonSignUp(username, password);
                    } else {
                        toast("Password must be longer than 5 and shorter than 20 symbols");
                    }
                } else {
                    toast("Username must be longer than 4 ans shorter than 15 symbols");
                }
            } else {
                toast("A username and password required");
            }
        }
    }

    //this listener called twice on button pres and than or button release
    private class KeyboardDoneKeyListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //this listener called twice on button pres and than or button release
                //to avoid this we check if action was ACTION_DOWN
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.i("editor listener1", "done pressed");
                }

            }

            return false;
        }
    }

    /*private class KeyboardDoneListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                Log.i("editor listener2", "done pressed");

            }

            return false;
        }
    }*/


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
                if (user != null) {
                    Log.i("Login", "Successful");
                    toast("Login successful");
                    changeIntent();
                } else {
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
                if (e == null) {
                    Log.i("SignUp", "Successful");
                    toast("SignUp successful");
                    changeIntent();
                } else {
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

    //hide keyboard 2 realisations
    /*public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }*/

    public void hideSoftKeyboard2() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void functionHideSoftKeyboard(View view) {
        Log.i("function", "hide keyboard");
        //hideSoftKeyboard(this);
        hideSoftKeyboard2();
    }

    public void changeIntent() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
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


/*// Enable Local Datastore.
Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xxx", "yyy");
        into onCreate() of class which extends android.app.Application then don't forget to add line bellow into Manifest file:

<application
    android:name=".NameOfYourApplicationClass"
            ...
            because onCreate won't be called.*/


//Parse SDK for Android
//A library that gives you access to the powerful Parse cloud platform from your Android app.

//Bolts
//Bolts is a collection of low-level libraries designed to make developing mobile apps easier. Bolts was designed by Parse and Facebook for our own internal use, and we have decided to open source these libraries to make them available to others. Using these libraries does not require using any Parse services. Nor do they require having a Parse or Facebook developer account.

//Bolts includes:
//"Tasks", which make organization of complex asynchronous code more manageable. A task is kind of like a JavaScript Promise, but available for iOS and Android.
//An implementation of the App Links protocol, helping you link to content in other apps and handle incoming deep-links.
//gradle parse new
//compile 'com.parse.bolts:bolts-tasks:1.4.0'
//compile 'com.parse:parse-android:1.15.7'

//gradle parse old
//compile 'com.parse.bolts:bolts-tasks:1.3.0'
//compile 'com.parse:parse-android:1.13.0'


///////PARSE SERVER TO APPLICATION
//1. add to build.gradle
//compile 'com.parse.bolts:bolts-tasks:1.4.0'
//compile 'com.parse:parse-android:1.15.7'
//2. add to AndroidManifest.xml
//<uses-permission android:name="android.permission.INTERNET" />
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
//<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> ?

//and
//<application
//android:name=".StarterApplication"   because onCreate in StarterApplication won't be called.

//3. add StarterApplication with this code
//package com.example.a.appinstawithparseserver;

// import android.app.Application;

// import com.parse.Parse;
// import com.parse.ParseACL;
// import com.parse.ParseUser;

//public class StarterApplication extends Application {

// @Override
//public void onCreate() {
//  super.onCreate();

// // Enable Local Datastore.
// Parse.enableLocalDatastore(this);

//// Add your initialization code here
//Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
// .applicationId("bc3714433327b5c543445a16306839973a960de1")
// .clientKey("c2201ad73627e846a81a1fe580469b9bb47698c4")
//.server("http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com:80/parse/")
// .build()
// );

///*ParseObject object = new ParseObject("ExampleObject");
// object.put("myNumber", "123");
//object.put("myString", "rob");

//object.saveInBackground(new SaveCallback () {
// @Override
// public void done(ParseException ex) {
// if (ex == null) {
//   Log.i("Parse Result", "Successful!");
// } else {
//  Log.i("Parse Result", "Failed" + ex.toString());
// }
//}
//});*/


// ParseUser.enableAutomaticUser();

//  ParseACL defaultACL = new ParseACL();
//defaultACL.setPublicReadAccess(true);
//defaultACL.setPublicWriteAccess(true);
// ParseACL.setDefaultACL(defaultACL, true);

// }

///*@Override
// protected void onCreate(Bundle savedInstanceState) {
//   super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_starter_application);
// }*/
//}


//FULLSCREEN + STATUS BAR REMOVE
//working
//or in styles.xml modify theme like
/*<!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="windowNoTitle">true</item>
        <item name="windowActionBar">false</item>
        <item name="android:windowFullscreen">true</item>
    </style>*/


//working partialy, only fullscreen line removes status bar
//FULLSCREEN
//add to onCreate()
//requestWindowFeature(Window.FEATURE_NO_TITLE);
//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//WindowManager.LayoutParams.FLAG_FULLSCREEN);

//example
/*@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_main);
}*/

//not working crashes app - you need to change not here, but in styles.xml
//Or you can do it via your AndroidManifest.xml file:
/*<activity android:name=".ActivityName"
    android:label="@string/app_name"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen"/>*/


//not working
//FULLSCREEN + status line
//add to onCreate()
//requestWindowFeature(Window.FEATURE_NO_TITLE);

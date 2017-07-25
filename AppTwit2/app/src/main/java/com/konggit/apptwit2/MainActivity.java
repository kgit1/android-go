package com.konggit.apptwit2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Twit: Login");
    }

    public void signupLogin(View view) {

        final String username = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
        final String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null) {

                    Log.i("Login", "Success! User " + username + " logged in");
                    usersList();

                } else {

                    Log.i("LoginError", "" + e.getCode() + " = " + e.toString());
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(username);
                    parseUser.setPassword(password);

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Log.i("Signup", "Success! User " + username + " signed up");
                                usersList();

                            } else {

                                if (e.getCode() == ParseException.USERNAME_TAKEN) {

                                    Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG).show();

                                } else {

                                    //cutting pretty error message substring from error message by substring it to first blank space
                                    Toast.makeText(getApplicationContext(), e.getCode() + " " + e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                                   // Toast.makeText(getApplicationContext(), e.getCode() + " " + e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });

                }

            }
        });

    }

    private void usersList(){
        Log.i("Intent","Start");

        Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
        startActivity(intent);

    }
}

package com.konggit.appwazup;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

//http://ec2-54-191-135-84.us-west-2.compute.amazonaws.com/apps/
public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView textLoginOrSignup;
    private boolean loginMode = true;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Wazup Login Activity");
        redirectIfLoggedIn();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard();

            }
        });

        editTextUsername = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new ButtonLoginOnClickListener());

        textLoginOrSignup = (TextView) findViewById(R.id.loginOrSignup);
        textLoginOrSignup.setOnClickListener(new TextLoginOnClickListener());

    }

    private class ButtonLoginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            username = String.valueOf(editTextUsername.getText());
            password = String.valueOf(editTextPassword.getText());

            if (loginMode) {

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (e == null) {

                            Log.i("Login", "Login success");
                            Toast.makeText(getApplicationContext(), "Login: success", Toast.LENGTH_SHORT).show();
                            redirectToUserList();

                        } else {

                            errorMessage(e);

                        }

                    }
                });

            } else {

                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(username);
                parseUser.setPassword(password);
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override

                    public void done(ParseException e) {

                        if (e == null) {

                            Log.i("Login", "Signup success");
                            Toast.makeText(getApplicationContext(), "Signup: success", Toast.LENGTH_SHORT).show();
                            redirectToUserList();

                        } else {

                            errorMessage(e);

                        }

                    }
                });

            }

        }

    }

    private class TextLoginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (loginMode) {

                buttonLogin.setText("Signup");
                textLoginOrSignup.setText("or Login");
                loginMode = false;

            } else {

                buttonLogin.setText("Login");
                textLoginOrSignup.setText("or Signup");
                loginMode = true;

            }

        }

    }

    private void errorMessage( ParseException e){

        String message = e.getMessage();

        if(message.toLowerCase().contains("java")){

            e.getMessage().substring(e.getMessage().indexOf(" "));

        }

        Log.i("Login", "Fail: " + e.getCode() + " " + message);
        Toast.makeText(getApplicationContext(), "Signup Fail: " + e.getCode() + " " + message, Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    private void redirectToUserList(){

        Log.i("Info", "Redirecting to userList");
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);

    }

    private void redirectIfLoggedIn(){

        if(ParseUser.getCurrentUser()!=null){

            Log.i("Info", "Already logged");
            redirectToUserList();

        }

    }
}

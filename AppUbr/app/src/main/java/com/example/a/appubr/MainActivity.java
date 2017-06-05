package com.example.a.appubr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

//http://ec2-54-218-40-215.us-west-2.compute.amazonaws.com/apps
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        getSupportActionBar().hide();

        //if not logged in - log as anonymous user
        if (ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.i("Login", "Anonymous login successful");
                    } else {
                        Log.i("Login", "Anonymous login failed");
                    }
                }
            });
        }
        //if  already logged in
        else {
            if(ParseUser.getCurrentUser().get("riderOrDriver")!=null){
                Log.i("Info","Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"));
            }
        }
    }

    //if logged as anonymous user - save user choice rider or driver and redirect
    public void functionLogin(View view) {
        Switch switchMode = (Switch) findViewById(R.id.switchMode);

        String userType = "rider";

        if (switchMode.isChecked()) {
            userType = "driver";
        }

        Log.i("Login", userType);
        ParseUser.getCurrentUser().put("riderOrDriver", userType);
        Log.i("Info","Redirecting as "+ userType);

        Intent intent;

        if(userType.equals("rider")){
            intent = new Intent(getApplicationContext(),RiderActivity.class);
        }else{
            intent = new Intent(getApplicationContext(),DriverActivity.class);
        }
        startActivity(intent);
    }
}

//i-07152ef1be1fd1a3c
//ec2-54-218-40-215.us-west-2.compute.amazonaws.com
//3c60334e299611ce00f6e8d2145ff50e1c14b8f2
//f94308b48971562b7d7be2ff815a0bcdf269cbe7

package com.example.a.appubr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

//http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps
//http://ec2-54-218-40-215.us-west-2.compute.amazonaws.com/apps
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        getSupportActionBar().hide();

        Log.i("####",ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())+" !");


    }


    public void functionLogin(View view){
        Switch switchMode = (Switch) findViewById(R.id.switchMode);
        if(switchMode.isChecked()){
            Log.i("Login","User");
        }else{
            Log.i("Login","Driver");
        }
    }
}

//i-07152ef1be1fd1a3c
//ec2-54-218-40-215.us-west-2.compute.amazonaws.com
//3c60334e299611ce00f6e8d2145ff50e1c14b8f2
//f94308b48971562b7d7be2ff815a0bcdf269cbe7

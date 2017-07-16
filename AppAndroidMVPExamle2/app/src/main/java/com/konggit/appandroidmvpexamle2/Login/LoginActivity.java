/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.konggit.appandroidmvpexamle2.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.konggit.appandroidmvpexamle2.R;
import com.konggit.appandroidmvpexamle2.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;
    private ProgressBar progressBar6;

    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress3);
        progressBar4 = (ProgressBar) findViewById(R.id.progress4);
        progressBar5 = (ProgressBar) findViewById(R.id.progress5);
        progressBar6 = (ProgressBar) findViewById(R.id.progress6);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);

//        progressBarExample.setCallback(new ProgressWheel.ProgressCallback() {
//            @Override
//            public void onProgressUpdate(float progress) {
//                //TODO
//            }
//        });

//        progressbar.setBarColor(Color.RED);
//        progressbar.setRimColor(Color.GRAY);

        findViewById(R.id.buttonProgress).setOnClickListener(new View.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(View v) {
                                                                     showProgress();
                                                                     //wheel progress background color
                                                                     progressBar1.setBackgroundColor(Color.GREEN);
                                                                     //wheel progress color
                                                                     progressBar1.getIndeterminateDrawable().setColorFilter(Color.BLUE,android.graphics.PorterDuff.Mode.MULTIPLY);

                                                                     //horizontal progress color
                                                                     progressBar2.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
                                                                     progressBar2.setVisibility(View.VISIBLE);
                                                                     progressBar2.setBackgroundColor(Color.BLACK);
                                                                     progressBar2.setMax(100);
                                                                     progressBar2.setProgress(20);


                                                                     progressBar3.setVisibility(View.VISIBLE);

                                                                     progressBar4.setVisibility(View.VISIBLE);
                                                                     progressBar4.getIndeterminateDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
                                                                     progressBar4.setMax(100);
                                                                     progressBar4.setProgress(20);

                                                                     progressBar5.setVisibility(View.VISIBLE);
                                                                     progressBar5.setPivotX(50);

                                                                     progressBar6.setVisibility(View.VISIBLE);
                                                                     progressBar6.setMax(220);
                                                                     progressBar6.setProgress(150);
                                                                     //progressBar6.setRotation(1000);
                                                                 }});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        //to make icons visible on menu list
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        ///////////////////////////

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        Log.i("menuItem",item.toString() +" "+ String.valueOf(item.getItemId()) +" - "+String.valueOf(item.getTitle()));
        Log.e("e menu",String.valueOf(R.id.action_settings9));
        Log.d("d menu ",String.valueOf(R.id.action_settings9));
        Log.v("v menu",String.valueOf(R.id.action_settings9));
        Log.w("w menu",String.valueOf(R.id.action_settings9));
        Log.wtf("wtf menu",String.valueOf(R.id.action_settings9));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
                progressBar1.setBackgroundColor(Color.BLUE);
        progressBar1.setCameraDistance(2222);
        progressBar1.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar1.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }
}

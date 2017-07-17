package com.konggit.appprogressbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Button dialog;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;
    private ProgressBar progressBar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress3);
        progressBar4 = (ProgressBar) findViewById(R.id.progress4);
        progressBar5 = (ProgressBar) findViewById(R.id.progress5);
        progressBar6 = (ProgressBar) findViewById(R.id.progress6);

        findViewById(R.id.buttonProgress).setOnClickListener(new ProgressButtonListener());
        findViewById(R.id.buttonDialog).setOnClickListener(new ProgressDialogButtonListener());
        findViewById(R.id.buttonProgressPage).setOnClickListener(new ProgressPageButtonListener());

    }

    private class ProgressDialogButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {



        }

    }

    private class ProgressButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            progressBar1.setVisibility(View.VISIBLE);
            progressBar1.setSecondaryProgress(90);
            //wheel progress background color
            progressBar1.setBackgroundColor(Color.GREEN);
            //wheel progress color
            progressBar1.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY);

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

        }
    }

    private class ProgressPageButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(),ProgressActivity.class);
            startActivity(intent);

        }

    }
}

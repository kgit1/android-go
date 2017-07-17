package com.konggit.appprogressbar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private Handler handler = new Handler();
    private TextView textView;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        progressBar = (ProgressBar) findViewById(R.id.circularProgressbar);
        progressBar2 = (ProgressBar) findViewById(R.id.circularProgressbar2);

        progressBar.setProgress(0);   // Main Progress
        progressBar.setSecondaryProgress(100); // Secondary Progress
        progressBar.setMax(100); // Maximum Progress
        progressBar.setProgressDrawable(drawable);

        progressBar2.setProgress(0);   // Main Progress
        progressBar2.setSecondaryProgress(100); // Secondary Progress
        progressBar2.setMax(100); // Maximum Progress

      /*  ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

        textView = (TextView) findViewById(R.id.textView);

        new Thread(() ->
        {

            while (progressStatus <= 100) {

                progressStatus += 1;

                handler.post(() -> {

                    progressBar.setProgress(progressStatus);
                    textView.setText(progressStatus - 1 + "%");
                    Log.i("Info", "Progress: " + progressStatus);
                });
                try {

                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        ).start();

        new Thread(() ->
        {

            while (progressStatus <= 100) {

//                progressStatus += 1;

                handler.post(() -> {

                    progressBar2.setProgress(progressStatus);
//                    textView.setText(progressStatus + "%");

                });
                try {

                    Thread.sleep(116);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        ).start();


        new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                while (progressStatus <= 100) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (progressBar2.getVisibility() == View.VISIBLE) {
                                //progressBar2.setProgress(progressStatus);
                                Log.i("Info", "Progress2: " + progressStatus);
                                //progressStatus++;
                            }
                        }
                    });

                    try {
                        Thread.sleep(params[0]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                Log.i("Info", "ZE END");

            }
        }.execute(50);

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while (progressStatus < 100) {
//
//                    progressStatus += 1;
//
//                    handler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus + "%");
//
//                        }
//                    });
//                    try {
//
//                        Thread.sleep(116);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();


    }
}
//        Here is my two solution.
//
//        Short answer:
//
//        Instead of creating a layer-list, I separated it in two files. One for ProgressBar and one for its background.
//
//        This is the ProgressDrawable file (@drawable folder): circular_progress_bar.xml
//
//<?xml version="1.0" encoding="utf-8"?>
//<rotate xmlns:android="http://schemas.android.com/apk/res/android"
//        android:fromDegrees="270"
//        android:toDegrees="270">
//<shape
//        android:innerRadiusRatio="2.5"
//                android:shape="ring"
//                android:thickness="1dp"
//                android:useLevel="true"><!-- this line fixes the issue for lollipop api 21 -->
//
//<gradient
//            android:angle="0"
//                    android:endColor="#007DD6"
//                    android:startColor="#007DD6"
//                    android:type="sweep"
//                    android:useLevel="false" />
//</shape>
//</rotate>
//        And this is for its background(@drawable folder): circle_shape.xml
//
//<?xml version="1.0" encoding="utf-8"?>
//<shape
//    xmlns:android="http://schemas.android.com/apk/res/android"
//            android:shape="ring"
//            android:innerRadiusRatio="2.5"
//            android:thickness="1dp"
//            android:useLevel="false">
//
//<solid android:color="#CCC" />
//
//</shape>
//        And at the End, inside the layout that you're working:
//
//<ProgressBar
//        android:id="@+id/progressBar"
//                android:layout_width="200dp"
//                android:layout_height="200dp"
//                android:indeterminate="false"
//                android:progressDrawable="@drawable/circular_progress_bar"
//                android:background="@drawable/circle_shape"
//                style="?android:attr/progressBarStyleHorizontal"
//                android:max="100"
//                android:progress="65" />


//another
//CustomProgressBarActivity.java:
//
//public class CustomProgressBarActivity extends AppCompatActivity {
//
//    private TextView txtProgress;
//    private ProgressBar progressBar;
//    private int pStatus = 0;
//    private Handler handler = new Handler();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_progressbar);
//
//        txtProgress = (TextView) findViewById(R.id.txtProgress);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (pStatus <= 100) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(pStatus);
//                            txtProgress.setText(pStatus + " %");
//                        }
//                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    pStatus++;
//                }
//            }
//        }).start();
//
//    }
//}
//activity_custom_progressbar.xml:
//
//<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:paddingBottom="@dimen/activity_vertical_margin"
//        android:paddingLeft="@dimen/activity_horizontal_margin"
//        android:paddingRight="@dimen/activity_horizontal_margin"
//        android:paddingTop="@dimen/activity_vertical_margin"
//        tools:context="com.skholingua.android.custom_progressbar_circular.MainActivity" >
//
//
//<RelativeLayout
//        android:layout_width="wrap_content"
//                android:layout_centerInParent="true"
//                android:layout_height="wrap_content">
//
//<ProgressBar
//            android:id="@+id/progressBar"
//                    style="?android:attr/progressBarStyleHorizontal"
//                    android:layout_width="250dp"
//                    android:layout_height="250dp"
//                    android:layout_centerInParent="true"
//                    android:indeterminate="false"
//                    android:max="100"
//                    android:progress="0"
//                    android:progressDrawable="@drawable/custom_progressbar_drawable"
//                    android:secondaryProgress="0" />
//
//
//<TextView
//            android:id="@+id/txtProgress"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:layout_alignBottom="@+id/progressBar"
//                    android:layout_centerInParent="true"
//                    android:textAppearance="?android:attr/textAppearanceSmall" />
//</RelativeLayout>
//
//
//
//</RelativeLayout>
//        custom_progressbar_drawable.xml:
//
//<?xml version="1.0" encoding="utf-8"?>
//<rotate xmlns:android="http://schemas.android.com/apk/res/android"
//        android:fromDegrees="-90"
//        android:pivotX="50%"
//        android:pivotY="50%"
//        android:toDegrees="270" >
//
//<shape
//        android:shape="ring"
//                android:useLevel="false" >
//<gradient
//            android:centerY="0.5"
//                    android:endColor="#FA5858"
//                    android:startColor="#0099CC"
//                    android:type="sweep"
//                    android:useLevel="false" />
//</shape>
//
//</rotate>
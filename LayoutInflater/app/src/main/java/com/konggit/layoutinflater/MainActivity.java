package com.konggit.layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get inflater
        LayoutInflater inflater = getLayoutInflater();

        //create view element with inflate() method of inflater , where params:
        // first param - view on which structure we creating new view,
        // second - id of parent view of new element,
        // third - boolean - will be new view attached to parent or not
        View inflatedView = inflater.inflate(R.layout.text, null, false);

        //get layoutParams of new view to check them - they will match params of view from which created
        LayoutParams layoutParams = inflatedView.getLayoutParams();

        Log.d(LOG_TAG, "Class of view1: " + inflatedView.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams of view1 is null: " + (layoutParams == null));
        Log.d(LOG_TAG, "Text of view1: " + ((TextView) inflatedView).getText());

        //get linear layout
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        //add newly created view to linear layout
        linearLayout.addView(inflatedView);

        //check layoutParams of new view after attaching to layout - they will match parent element
        Log.d(LOG_TAG, "Class of view1: " + inflatedView.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams of view1 is null: " + (layoutParams == null));
        //Log.d(LOG_TAG, "LayoutParams of view1: " + layoutParams.getClass().toString());

        //get linear layout
        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.linLayout2);
        //create new view showing it his parent element linearlayout, but attach flag - false
        View inflatedViewSecond = inflater.inflate(R.layout.text, linearLayout2, false);
        //check layoutParams of second new view - they will match parent element even with out attach
        layoutParams = inflatedViewSecond.getLayoutParams();

        Log.d(LOG_TAG, "Class of view2: " + inflatedViewSecond.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams of view2 is null: " + (layoutParams == null));
        Log.d(LOG_TAG, "LayoutParams of view2: " + layoutParams.getClass().toString());
        Log.d(LOG_TAG, "Text of view2: " + ((TextView) inflatedViewSecond).getText());

        //get another layout, this time relative for diversity
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);
        //create new view and instantly add it to relative layout by giving him parent and turn attach flag to true
        View inflatedViewThird = inflater.inflate(R.layout.text, relativeLayout, true);

        //check layoutParams of third new view - they will match LinearLayout - because it is parent
        // of RelativeLayout and Relativelayout taking layoutparams From parent
        LayoutParams layoutParams3 = inflatedViewThird.getLayoutParams();

        Log.d(LOG_TAG, "Class of view3: " + inflatedViewThird.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams3 of view3 is null: " + (layoutParams3 == null));
        Log.d(LOG_TAG, "LayoutParams3 of view3: " + layoutParams3.getClass().toString());
    }
}

package com.konggit.appwear;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mTextView;
    private TextView countText;

    private int counter;

    private Button button;
    private Button resetButton;
    private Button plusOneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        countText = (TextView) findViewById(R.id.textCount);

        counter = 0;

        button = (Button) findViewById(R.id.button);
        resetButton = (Button) findViewById(R.id.buttonReset);
        plusOneButton = (Button) findViewById(R.id.buttonPlusOne);
        //button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        //buttonOBJ.getBackground().setColorFilter(Color.parseColor("#YOUR_HEX_COLOR_CODE"), PorterDuff.Mode.MULTIPLY);

        button.setOnClickListener(new ButtonListener());
        resetButton.setOnClickListener(new ResetButtonListener());
        plusOneButton.setOnClickListener(new IncrementButtonListener());


    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (findViewById(R.id.rect_layout) != null) {

                Log.i("Info", "Rectangular layout");
                Toast.makeText(getApplicationContext(), "Hello SQUARE!", Toast.LENGTH_SHORT).show();

            } else {

                Log.i("Info", "Round layout");
                Toast.makeText(getApplicationContext(), "Hello ROUND!", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private class ResetButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            counter = 0;
            countText.setText(String.valueOf(counter));

        }
    }

    private class IncrementButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            counter++;
            countText.setText(String.valueOf(counter));

        }
    }
}


/*
custom_button.xml
<?xml version="1.0" encoding="utf-8"?>
<selector
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:state_pressed="true" >
        <shape>
            <gradient
                android:startColor="@color/yellow1"
                android:endColor="@color/yellow2"
                android:angle="270" />
            <stroke
                android:width="3dp"
                android:color="@color/grey05" />
            <corners
                android:radius="3dp" />
            <padding
                android:left="10dp"
                android:top="10dp"
                android:right="10dp"
                android:bottom="10dp" />
        </shape>
    </item>

    <item android:state_focused="true" >
        <shape>
            <gradient
                android:endColor="@color/orange4"
                android:startColor="@color/orange5"
                android:angle="270" />
            <stroke
                android:width="3dp"
                android:color="@color/grey05" />
            <corners
                android:radius="3dp" />
            <padding
                android:left="10dp"
                android:top="10dp"
                android:right="10dp"
                android:bottom="10dp" />
        </shape>
    </item>

    <item>
        <shape>
            <gradient
                android:endColor="@color/blue2"
                android:startColor="@color/blue25"
                android:angle="270" />
            <stroke
                android:width="3dp"
                android:color="@color/grey05" />
            <corners
                android:radius="3dp" />
            <padding
                android:left="10dp"
                android:top="10dp"
                android:right="10dp"
                android:bottom="10dp" />
        </shape>
    </item>
</selector>
*/

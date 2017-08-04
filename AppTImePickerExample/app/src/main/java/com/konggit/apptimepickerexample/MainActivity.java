package com.konggit.apptimepickerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    TextView textViewDisplayTime;
    Button buttonChangeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        textViewDisplayTime = (TextView) findViewById(R.id.textViewDisplayTime);
        buttonChangeTime = (Button) findViewById(R.id.buttonChangeTime);

        timePicker.setIs24HourView(true);
        textViewDisplayTime.setText(currentTime());

        buttonChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewDisplayTime.setText(currentTime());

            }
        });
    }

    private String currentTime() {

        return "Time: " + timePicker.getHour() + " : " + timePicker.getMinute();
    }
}

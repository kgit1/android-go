package com.konggit.appclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.konggit.appclock.CustomClock.ClockWithCanvas.CustomAnalogClockActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonDeprecatedClocks = (Button)findViewById(R.id.buttonAnalogClock);
        buttonDeprecatedClocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),DeprecatedAnalogAndDigitalClockActivity.class);
                startActivity(intent);

            }
        });

        Button buttonTextClock = (Button) findViewById(R.id.buttonTextClock);
        buttonTextClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),TextClockActivity.class);
                startActivity(intent);

            }
        });

        Button buttonCustomAnalogClock=(Button)findViewById(R.id.buttonCustomAnalogClock);
        buttonCustomAnalogClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CustomAnalogClockActivity.class);
                startActivity(intent);

            }
        });

    }
}

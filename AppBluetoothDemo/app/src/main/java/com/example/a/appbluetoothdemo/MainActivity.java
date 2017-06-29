package com.example.a.appbluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(BA.isEnabled()){

            Toast.makeText(getApplicationContext(), "Bluetooth: On", Toast.LENGTH_SHORT).show();

        }else {

           Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
                if(BA.isEnabled()){

                    Toast.makeText(getApplicationContext(), "Bluetooth tuned On", Toast.LENGTH_SHORT).show();

                }
        }
    }
}

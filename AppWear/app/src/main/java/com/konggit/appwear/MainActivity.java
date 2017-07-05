package com.konggit.appwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        Button button =(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(findViewById(R.id.rect_layout)!=null){

                    Log.i("Info","Rectangular layout");
                    Toast.makeText(getApplicationContext(),"Hello SQUARE!",Toast.LENGTH_SHORT).show();

                }else{

                    Log.i("Info","Round layout");
                    Toast.makeText(getApplicationContext(),"Hello ROUND!",Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}

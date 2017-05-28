package com.example.a.appinstaparseserverrebuild;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        Log.i("Images", "Start");
        //add main layout programmatically?
        //View view = (View) findViewById(R.id.constraintLayout);
        linearLayout = (LinearLayout) findViewById(R.id.imagesLinearlayout);
        /*linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.layout(0,0,100,100);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(100,100));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ((ViewGroup)view).addView(linearLayout);*/
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        //put title for actionBar
        //doesn't work
        //getActionBar().setTitle("Images "+username);
        getSupportActionBar().setTitle("Images " + username);

        //This method just controls whether to show the Activity icon/logo or not.
        //getSupportActionBar().setHomeButtonEnabled(true);

        //This method makes the icon and title in the action bar clickable so that “up” (ancestral)
        // navigation can be provided by onOptionsItemSelected
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gives you possibility to change logo - but doesn't works
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_mr_button_connected_00_dark);

        //change ActionBar's color
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_red_dark, getApplicationContext().getTheme())));
        getSupportActionBar().show();

        Log.i("Images", "user " + username);
        Log.i("Images", "Active");

        addImages();
    }

    public void addImages() {

        Log.i("Images", "Function");
        Log.i("Images", "User " + ParseUser.getCurrentUser().getUsername());

        //create query for parse server for class Image
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Image");
        //limit to ParseObjects with username of needed user
        parseQuery.whereEqualTo("username", username);
        //add descending order to objects by field createdAt
        parseQuery.orderByDescending("createdAt");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                Log.i("Images", "InQuery1");
                Log.i("Images", "e " + e);
                Log.i("Images", "objects " + objects);
                if (e == null && objects.size()>0) {
                    for (ParseObject parseObject : objects) {
                        ParseFile imageFile = parseObject.getParseFile("image");

                        Log.i("Images", "inQuery2");
                        //get image file of the object from parse server
                        imageFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null && data != null) {
                                    Log.i("Images", "inQuery3");
                                    //create image in bitmap by decoding byte array from parse server
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    //add images to LinearLayout programmatically
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    imageView.setImageBitmap(bitmap);
                                    linearLayout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (id == android.R.id.home) {
            Log.d("HomeButton", "action bar clicked");
        }

        return super.onOptionsItemSelected(item);
    }

    /*public ImageView imageCreator(){
        ImageView imageView=null;



        return imageView;
    }*/
}

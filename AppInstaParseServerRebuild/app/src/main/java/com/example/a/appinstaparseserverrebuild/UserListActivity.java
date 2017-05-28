package com.example.a.appinstaparseserverrebuild;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    List<String> usersList;
    ListView usersListView;
    ArrayAdapter arrayAdapter;
    int imageNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_);

        usersList = new ArrayList<>();
        usersListView = (ListView) findViewById(R.id.usersListView);

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, usersList);
        usersListView.setAdapter(arrayAdapter);

        //Log In
        /*try {
            ParseUser.logIn("12345","123456");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String user =ParseUser.getCurrentUser().getString("username");
        Log.i("Username",user);*/


        //create querry to obtain set of users from parse server
        ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();

        //if we need not all, but all except current user
        //usersQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        //if we need alphabetical order
        usersQuery.orderByAscending("username");

        //execute query
        usersQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser parseUser : objects) {
                            // Log.i("username", parseUser.getString("username"));
                            Log.i("ID", parseUser.getObjectId());
                            Log.i("username", parseUser.getUsername());
                            // usersList.add(parseUser.getString("username"));
                            usersList.add(parseUser.getUsername());
                        }
                        //update adapter to change listView display
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("Fail", "users query");
                }

            }
        });

        /*ParseQuery<ParseObject> usersQuery = ParseQuery.getQuery("Users");

        usersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for(ParseObject object : objects){
                        usersList.add(object.getString("username"));
                        Log.i("objects","here");
                        Log.i("objects",objects.toString());
                        Log.i("object",object.toString());
                    }
                }else{
                    Log.i("Fail",e.toString());
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.share) {
            //we need to add  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
            //in AndroidManifest.xml and than ask user for permission actively
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    getPhoto();
                }
            } else {
                getPhoto();
            }
        }
        if (item.getItemId() == R.id.logOut) {
            /*ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Log.i("LogOut", "Successful");
                        ParseUser.logOut();
                    }else{
                        Log.i("LogOut", "Failed");
                    }
                }
            });*/
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Log.i("UserBefore", ParseUser.getCurrentUser().toString());
            Log.i("UserBefore", ParseUser.getCurrentUser().getUsername() + 1);
            Log.i("UserBefore", ParseUser.getCurrentUser().getUsername() + " 1");
            ParseUser.logOut();
            Log.i("UserAfter", ParseUser.getCurrentUser().toString());
            if (ParseUser.getCurrentUser().getUsername() != null) {
                Log.i("UserAfter", ParseUser.getCurrentUser().getUsername());
            } else {
                Log.i("UserAfter", "null");
                Log.i("UserAfter", ParseUser.getCurrentUser().getUsername() + " 1");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //to do some on users permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }

    //to do some on result - onActivityResultMethod
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //data returns us uri to our particular image in the phone storage
            Uri selectedImage = data.getData();
            try {
                //use uri to create bitmap of our image
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Log.i("Photo", "received");

                //this will allow us to convert image into parse file which we can add to a
                // parseObject which upload at a parse server
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //convert bitmap to PNG file with 100% quality as a part of the stream

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //convert stream to byteArray
                byte[] byteArray = stream.toByteArray();

                //convert byteArray to parseFile with name "image.png"
                ParseFile parseFile = new ParseFile("1111image"+imageNumber+".png", byteArray);
                imageNumber++;
                Log.i("Filename",parseFile.getName()+" - ");

                //create parse object of class Image where we will store all images
                ParseObject object = new ParseObject("Image");

                object.put("image", parseFile);

                object.put("username", ParseUser.getCurrentUser().getUsername());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Image Shared", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Image could not be shared - please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //to import photos from gallery create new intent which will go to MediaStore
    // where all media data on device are available
    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
}

/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.awareness.fence.LocationFence;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

//http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////CRUD PARSE OBJECTS//////////////////////////

//CREATE////////
/* //parse object - classname will be Score taken by constructor
      ParseObject score = new ParseObject("Score");
      //put data to score
      //value 1 -> key + data
      score.put("username", "rob");
      //value 2 -> key + data
      score.put("score", 86);
      //to save save in background just to save or save eventually to save when be opportunity(like connection lol)
      //both with callback gives us opportunity to see was save successful or not
      score.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {

              if (e == null) {
                  Log.i("SaveInBackground", "Successful");
              } else {
                  Log.i("SaveInBackground", "Unsuccessful: "+e.toString());
              }
          }
      });*/

        ////READ//
      /*//object from ParseServer
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      //object id for query - from parseServer page (http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps/My%20Bitnami%20Parse%20API/browser/_Session)
      query.getInBackground("gZ9L2cQfQu", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {

              if(e==null && object!=null){
                  Log.i("Query", "Successful");
                  Log.i("ObjectValue username",object.getString("username"));
                  Log.i("ObjectValue score",Integer.toString(object.getInt("score")));
              }else{
                  Log.i("Query",object.getString("username"));

              }
          }
      });*/


        ////UPDATE//
     /* //object from ParseServer
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      //object id for query - from parseServer page (http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps/My%20Bitnami%20Parse%20API/browser/_Session)
      query.getInBackground("gZ9L2cQfQu", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {

              if(e==null && object!=null){
                  Log.i("Query", "Successful");

                  object.put("score",200);
                  object.saveInBackground();
                  Log.i("ObjectValue username",object.getString("username"));
                  Log.i("ObjectValue score",Integer.toString(object.getInt("score")));
              }else{
                  Log.i("Query",object.getString("username"));

              }
          }
      });*/

        ////DELETE//
     /* //object from ParseServer
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      //object id for query - from parseServer page (http://ec2-54-245-63-66.us-west-2.compute.amazonaws.com/apps/My%20Bitnami%20Parse%20API/browser/_Session)
      query.getInBackground("gZ9L2cQfQu", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {

              if(e==null && object!=null){
                  Log.i("Query", "Successful");

                   try {
                      object.delete();
                  } catch (ParseException e1) {
                      e1.printStackTrace();
                  }
                  object.saveInBackground();
              }else{
                  Log.i("Query",object.getString("username"));

              }
          }
      });*/

        //retrieve set of objects from parse server
        //retrieve all objects of Score class
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    Log.i("findInBackground", "Retrieved " + objects.size() + " objects");
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            //Log.i("findInBackground", object.toString());
                            Log.i("objectId", object.getObjectId() + ": username " + object.getString("username") + " - score " + Integer.toString(object.getInt("score")));
                        }
                    }
                }
            }
        });

        //retrieve all objects of Score class
        ParseQuery<ParseObject> queryTommy = ParseQuery.getQuery("Score");
        //cut query by username tommy
        queryTommy.whereEqualTo("username", "tommy");
        //limit query by limit 1
        queryTommy.setLimit(1);
        queryTommy.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    Log.i("1findInBackground", "Retrieved " + objects.size() + " objects");
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            //Log.i("1findInBackground", object.toString());
                            Log.i("1objectId", object.getObjectId() + ": username " + object.getString("username") + " - score " + Integer.toString(object.getInt("score")));
                        }
                    }
                }
            }
        });


/////////////////////////////////////////////////////////////////////////////
        //training
        //Create Tweet class, contains - username tweet
     /*ParseObject tweet = new ParseObject("Tweet");

      tweet.put("username","robert1");
      tweet.put("tweet","PARSE CRUD1");

      tweet.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if(e==null){
                  Log.i("Tweet save","Successful");
              }else{
                  Log.i("Tweet save","Unsuccessful");
              }
          }
      });*/

//query tweet and update
      /*ParseQuery<ParseObject> tweetQuery =ParseQuery.getQuery("Tweet");
      tweetQuery.getInBackground("xYuhPA9o06",new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if(e== null && object!=null){


                  Log.i("QueryTweet","Successful");
                  Log.i("QueryTweet user",object.getString("username"));
                  //Log.i("QueryTweet tweet",object.getString("tweet"));
                  //object.put("tweet", "UPDATED TWEET");
                  //object.remove("tweet");
                  try {
                      object.delete();
                  } catch (ParseException e1) {
                      e1.printStackTrace();
                  }
                  Log.i("QueryUpdatedTweet user",object.getString("username"));
                  //Log.i("QueryUpdatedTweet tweet",object.getString("tweet"));
                  object.saveInBackground();
                  Log.i("QueryUpdatedTweet user",object.getString("username"));
                  //Log.i("QueryUpdatedTweet tweet",object.getString("tweet"));

              }else{
                  Log.i("Query tweet","Unsuccessful");
              }
          }
      });*/


        //retrieve set of Score class objects
        ParseQuery<ParseObject> queryTrain = ParseQuery.getQuery("Score");
        //cut query to greater than 30 by score field
        queryTrain.whereGreaterThan("score", 30);

        //
        queryTrain.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects != null) {
                    //go through list of objects with foreach
                    for (ParseObject object : objects) {

                        //print object's data
                        Log.i("2objectId", object.getObjectId() + " username: " + object.getString("username") + " - " + Integer.toString(object.getInt("score")));
                        //put object's score value into the temp int
                        int tempScore = object.getInt("score");
                        //use temp int to put to object field score with +50 value
                        object.put("score", tempScore + 50);
                        //save object
                        object.saveInBackground();
                    }
                }
            }
        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}
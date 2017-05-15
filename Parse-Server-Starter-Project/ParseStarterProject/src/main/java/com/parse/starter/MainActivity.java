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


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


/*//create parse object - classname will be Score taken by constructor
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

        //get object from ParseServer
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
        });





      ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}
package com.konggit.apptwit2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private List<Tweet> tweets;
    private List<String> followingUsers;
    private TweetAdapter adapter;
    private ListView listViewFeed;

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        currentUser = ParseUser.getCurrentUser().getUsername();

        setTitle("Feed for user : " + currentUser);

        listViewFeed = (ListView) findViewById(R.id.listViewFeed);
        tweets = new ArrayList<>();

        adapter = new TweetAdapter(tweets, getApplicationContext());
        listViewFeed.setAdapter(adapter);
        // feelDummy();

        //feelFeedFromParse();
        //feelFeedFromParse2();

        feelUsers();

        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.tweet_item,new ArrayList(Arrays.asList("first","second")));
        //listViewFeed.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout) {

            logout();
            Log.i("Menu", "logout feed");
            return true;
        }

        if (item.getItemId() == R.id.users) {

            Log.i("Menu", "Feed->UsersList");

            Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
            startActivity(intent);

            return true;
        }

        return false;
    }

    private void redirectToMain() {

        Log.i("Logout", "logout user");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    private void logout() {

        Log.i("Intent", "Feed->Main");

        ParseUser.logOut();
        redirectToMain();

    }

    private void feelFeedFromParse() {

        tweets.clear();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Tweet");
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    Log.i("Tweets", objects.toString());
                    for (ParseObject tweetObject : objects) {

                        Tweet tweet = new Tweet();
                        tweet.setUsername(String.valueOf(tweetObject.get("username")));
                        tweet.setTweet(String.valueOf(tweetObject.get("tweet")));
                        tweet.setCreatedAt(String.valueOf(tweetObject.getCreatedAt()));
                        tweet.setChangedAt(String.valueOf(tweetObject.getUpdatedAt()));

                        tweets.add(tweet);

                    }


                    adapter.notifyDataSetChanged();

                } else {

                    Log.i("Tweets", "Fail " + e.getMessage());

                }

            }
        });
    }

    private void feelFeedFromParse2() {


        //feelUsers();
        Log.i("feelFeedFromParse2", "" + followingUsers.size());

        tweets.clear();

        if (followingUsers.size() > 0) {

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Tweet");
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        Log.i("Tweets", objects.toString());
                        for (ParseObject tweetObject : objects) {

                            if (followingUsers.contains(tweetObject.get("username"))) {
                                Tweet tweet = new Tweet();

                                tweet.setUsername(String.valueOf(tweetObject.get("username")));
                                tweet.setTweet(String.valueOf(tweetObject.get("tweet")));
                                tweet.setCreatedAt(String.valueOf(tweetObject.getCreatedAt()));
                                tweet.setChangedAt(String.valueOf(tweetObject.getUpdatedAt()));

                                Log.i("Tweet", tweet.toString());
                                tweets.add(tweet);

                            }
                        }


                        adapter.notifyDataSetChanged();

                    } else {

                        Log.i("Tweets", "Fail " + e.getMessage());

                    }

                }
            });
        }
    }

    private void feelUsers() {

        followingUsers = ParseUser.getCurrentUser().getList("isFollowing");
        Log.i("Following", "Users" + followingUsers.toString());

        feelFeedFromParse2();

    }


    private void feelDummy() {

        Tweet tweet1 = new Tweet();
        tweet1.setUsername("user1");
        tweet1.setTweet("This is the first tweet");
        tweet1.setCreatedAt("Created at 20202");
        tweet1.setChangedAt("Changed 200404");

        Tweet tweet2 = new Tweet();
        tweet2.setUsername("user2");
        tweet2.setTweet("11111111111111111This is the first tweet");
        tweet2.setCreatedAt("Created at 20213202");
        tweet2.setChangedAt("Changed 202130404");

        Tweet tweet3 = new Tweet();
        tweet3.setUsername("user3");
        tweet3.setTweet("222222222222222222This is the first tweet");
        tweet3.setCreatedAt("Created at 22310202");
        tweet3.setChangedAt("Changed 20023404");

        Tweet tweet4 = new Tweet();
        tweet4.setUsername("user4");
        tweet4.setTweet("33333333333333This is the first tweet");
        tweet4.setCreatedAt("Created at 210202");
        tweet4.setChangedAt("Changed 203404");

        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        tweets.add(tweet4);
        tweets.add(tweet4);
        tweets.add(tweet4);
        tweets.add(tweet4);
        tweets.add(tweet4);
        tweets.add(tweet4);

    }
}

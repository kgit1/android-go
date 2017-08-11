package com.konggit.appwazup;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String activeUser;
    ListView chatView;
    List<String> messagesList;
    ArrayAdapter adapter;
    EditText chatEditText;
    Button chatSendButton;

    Handler handler;
    Runnable runnable;
    int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        activeUser = intent.getStringExtra("activeUser");

        chatView = (ListView) findViewById(R.id.chatListView);

        messagesList = new ArrayList<>();
        marryList();

        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, messagesList);

        chatView.setAdapter(adapter);


        chatEditText = (EditText) findViewById(R.id.chatEditText);
        chatSendButton = (Button) findViewById(R.id.chatSendButton);
        chatSendButton.setOnClickListener(new ChatSendButtonOnClickListener());

        setTitle("Chat with " + activeUser);

        queryChat();

        //hide keyboard by touching list view(not item)
        chatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideSoftKeyboard();
                return false;
            }
        });

    }

    @Override
    protected void onStart() {

        refreshChat();

        super.onStart();
    }

    //stop handler when activity not visible
    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private class ChatSendButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (chatEditText.getText() != null && String.valueOf(chatEditText.getText()).length() > 0) {

                ParseObject message = new ParseObject("Message");
                message.put("sender", ParseUser.getCurrentUser().getUsername());
                message.put("recipient", activeUser);
                message.put("message", String.valueOf(chatEditText.getText()));

                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplicationContext(), "Message sent :" + String.valueOf(chatEditText.getText()), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), "Send error :" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }

            chatEditText.setText("");
            queryChat();
            hideSoftKeyboard();

        }
    }

    //two parseQueries assembled to one by list and used together by third query
    private void queryChat() {

        Log.i("Info", " Query chat method");

        ParseQuery<ParseObject> querySender = new ParseQuery<ParseObject>("Message");

        querySender.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        querySender.whereEqualTo("recipient", activeUser);

        ParseQuery<ParseObject> queryRecipient = new ParseQuery<ParseObject>("Message");

        queryRecipient.whereEqualTo("recipient", ParseUser.getCurrentUser().getUsername());
        queryRecipient.whereEqualTo("sender", activeUser);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();

        queries.add(querySender);
        queries.add(queryRecipient);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        messagesList.clear();

                        for (ParseObject message : objects) {

                            String messageContent = message.getString("message");

                            if (message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {

                                messageContent = "me: " + messageContent;

                            } else {

                                messageContent = activeUser + ": " + messageContent;

                            }

                            messagesList.add(messageContent);

                        }

                        //Log.i("Messages", messagesList.toString());
                        adapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }

    private void refreshChat() {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runnable = this;

                queryChat();

                handler.postDelayed(runnable, delay);

            }
        }, delay);


        //with Handler
        //Handler h = new Handler();
        //int delay = 15000; //15 seconds
        //Runnable runnable;
        // @Override
        //protected void onStart() {
        ////start handler as activity become visible
        //
        //h.postDelayed(new Runnable() {
        //public void run() {
        //yourMethod()
        //
        //runnable=this;
        //
        //h.postDelayed(runnable, delay);
        //}
        //}, delay);
        //
        //super.onStart();
        //}
        //
        //@Override
        //protected void onPause() {
        //h.removeCallbacks(runnable); //stop handler when activity not visible
        //super.onPause();
        //}

        //with Timer
        //Timer myTimer = new Timer();
        // myTimer.schedule(new TimerTask() {
        //    @Override
        //    public void run() {
        //        //yourMethod()
        //   }
        //}, 10000);

        //with CountDownTimer
        //private  CountDownTimer countDownTimer;
        //private void startTimer(final Context context) {
        //
        //if (countDownTimer != null) {
        //countDownTimer.cancel();
        //countDownTimer = null;
        // }
        //
        //countDownTimer = new CountDownTimer(20 * 1000, 1 * 1000) {
        //
        // public void onTick(long millisUntilFinished) {
        //
        //
        //}
        //
        //public void onFinish() {
        //
        //yourMethod();
        //
        //startTimer(context);
        //
        //}
        //}
        //
        //}.start();
        //}

    }

    private void hideSoftKeyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        //Disable the keyboard on activity window startup
        //this .getWindow().setSoftInputMode(WindowManager.LayoutParams. SOFT_INPUT_STATE_ALWAYS_HIDDEN );

        //<!-- to prevent keyboard over editText field - android:windowSoftInputMode="adjustPan
        //resizes window - but keyboard covers editText again... - android:windowSoftInputMode="stateHidden|adjustResize"-->
        //<activity android:name=".ChatActivity"
        //android:windowSoftInputMode="adjustPan">
        //</activity>

        //https://android-developers.googleblog.com/2009/04/updating-applications-for-on-screen.html

    }

    private void marryList() {

        messagesList.add("Mary had a little lamb");
        messagesList.add("Little lamb, little lamb");
        messagesList.add("Mary had a little lamb");
        messagesList.add("Its fleece was white as snow");
        messagesList.add("And everywhere that Mary went");
        messagesList.add("Mary went, Mary went");
        messagesList.add("The lamb was sure to go");

        messagesList.add("He followed her to school one day");
        messagesList.add("School one day, school one day");
        messagesList.add("He followed her to school one day");
        messagesList.add("Which was against the rule");
        messagesList.add("It made the children laugh and play");
        messagesList.add("Laugh and play, laugh and play");
        messagesList.add("It made the children laugh and play");
        messagesList.add("To see a lamb at school");

        messagesList.add("And so the teacher turned him out\n" +
                "Turned him out, turned him out\n" +
                "And so the teacher turned him out\n" +
                "But still he lingered near\n" +
                "And waited patiently\n" +
                "Patiently, patiently\n" +
                "And waited patiently\n" +
                "Til Mary did appear");

        messagesList.add("Mary had a little lamb" +
                "Little lamb, little lamb" +
                "Mary had a little lamb" +
                "Its fleece was white as snow" +
                "And everywhere that Mary went" +
                "Mary went, Mary went" +
                "Everywhere that Mary went" +
                "The lamb was sure to go");

    }


}

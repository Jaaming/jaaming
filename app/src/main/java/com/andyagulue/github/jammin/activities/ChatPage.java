package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ChatPage extends AppCompatActivity {
    String TAG = "ChatPage";

    private ChatAdapter chatAdapter;
    private final ArrayList<Message> messageArrayList = new ArrayList<>();
    String username;
    AuthUser authUser = Amplify.Auth.getCurrentUser();
    Handler chatPageHandler;
    Musician[] musicianList;
    Musician currentMusician;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        musicianList = new Musician[1];
        getCurrentUser();


        Log.i(TAG, "onCreate: authUser username: " + authUser.getUsername());
//        Log.i(TAG, "onCreate: currentUser Musician: " + currentMusician.getUsername());

        username = getIntent().getStringExtra("username");

        ListView listView = findViewById(R.id.messageListView);
        chatAdapter = new ChatAdapter(getApplicationContext(), R.id.messageListView, messageArrayList);
        listView.setAdapter(chatAdapter);
        getPreviousMessages();


        Amplify.DataStore.observe(
                Message.class,
                cancelable -> Log.i(TAG, "Amplify Observation began:"),
                this::onNewMessageReceived,
                failure -> Log.i(TAG, "Observation failed"),
                () -> Log.i(TAG, "Observation completed:")
        );

        chatPageHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull android.os.Message msg) {
                super.handleMessage(msg);
                if(msg.what == 141){
                    currentMusician = musicianList[0];
                    chatAdapter.notifyDataSetChanged();
                }
            }
        };

    }

    private void onNewMessageReceived(DataStoreItemChange<Message> messageChanged) {
        if (messageChanged.type().equals(DataStoreItemChange.Type.CREATE)) {
            Message message = messageChanged.item();
            Log.i(TAG, "onNewMessageReceived: " + message);
            messageArrayList.add(message);
            runOnUiThread(()-> chatAdapter.notifyDataSetChanged());
        }
    }

    private void getPreviousMessages() {
        Log.i(TAG, "getPreviousMessages: start of get Previous messages");
        Amplify.DataStore.query(
                Message.class,
                Where.sorted(Message.DATE.ascending()),
                result -> {
                    while (result.hasNext()) {
                        Message message = result.next();
                        messageArrayList.add(message);
                        chatPageHandler.sendEmptyMessage(141);
                    }
                },
                Throwable::printStackTrace
        );
    }




    public void onClickSendMessage(View view){
        EditText messageContentTxt = findViewById(R.id.messageContentEditText);
        String messageContent = messageContentTxt.getText().toString();

        Log.i(TAG, "onClickSendMessage: " + currentMusician);

        Date date = new Date();
        int offsetMillis = TimeZone.getDefault().getOffset(date.getTime());
        int offsetSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(offsetMillis);
        Temporal.DateTime temporalDateTime = new Temporal.DateTime(date, offsetSeconds);


        if(!messageContent.isEmpty()){

            Message message = Message.builder()
                    .recipient(username)
                    .date(temporalDateTime)
                    .content(messageContent)
                    .musician(currentMusician)
                    .build();

            Log.i(TAG, "onClickSendMessage: " + message);

            Amplify.DataStore.save(
                    message,
                    result -> {
                        Log.i(TAG, "successful addition of message");
                    },
                    error -> {
                        Log.i(TAG, "unsucessful addition of message" + error.getMessage());
                    }
            );
        }

    }
    public void getCurrentUser(){
        authUser = Amplify.Auth.getCurrentUser();
        Amplify.API.query(
                ModelQuery.list(Musician.class, Musician.USERNAME.eq(authUser.getUsername())),
                r ->{
                    Log.i(TAG, "getCurrentUser: " + r.getData().iterator().next());
                    musicianList[0] = r.getData().iterator().next();
                    chatPageHandler.sendEmptyMessage(141);
                },
                e ->{
                    Log.i(TAG, "getCurrentUser: " + e);
                }
        );
        Log.i(TAG, "getCurrentUser2: " + musicianList[0]);

    }

}
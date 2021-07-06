package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.MessagingAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MessagingActivity extends AppCompatActivity {
    String TAG = "MessagingActivity";

    String userName;
    ArrayList<Message> messageArrayList;
    Handler messagingActivityHandler;
    String authUser = Amplify.Auth.getCurrentUser().getUsername();



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);



        createMessagesList();
        buildRecyclerView();
        Log.i(TAG, "onCreate: Page was created");
        Log.i(TAG, "onCreate: MessageArrayListSize " + messageArrayList.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createMessagesList() {
        messageArrayList = new ArrayList<>();
        HashMap<String, String> messageHashMap = new HashMap<>();
        Amplify.DataStore.query(
                Message.class,
                Where.sorted(Message.DATE.descending()),
                result -> {
                    while (result.hasNext()) {
                        Log.i(TAG, "getPreviousMessages: result: " + result);

                        Message message = result.next();
                        if(message.getRecipient().equals(authUser)){
                            int size = messageHashMap.size();
                            messageHashMap.putIfAbsent(message.getMusician().getUsername(), message.getContent());
                            if(messageHashMap.size() > size){
                                messageArrayList.add(message);
                            }
                        }
                        if(message.getMusician().getUsername().equals(authUser)){
                            int size = messageHashMap.size();
                            messageHashMap.putIfAbsent(message.getRecipient(),message.getContent());
                            if(messageHashMap.size() > size){
                                messageArrayList.add(message);
                            }
                        }
                    }
                    messagingActivityHandler.sendEmptyMessage(151);
                },
                Throwable::printStackTrace
        );
    }

    private void buildRecyclerView() {
        Log.i(TAG, "buildRecyclerView: ");
        RecyclerView messagesRecyclerView = findViewById(R.id.messagingRecyclerView);
        messagesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        MessagingAdapter rvAdapter = new MessagingAdapter(messageArrayList);
        messagesRecyclerView.setLayoutManager(rvLayoutManager);
        messagesRecyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnMessagePreviewClickListener(new MessagingAdapter.onMessagePreviewClickListener() {
            @Override
            public void onMessageClick(int position) {
                Intent intent = new Intent(MessagingActivity.this, ChatPage.class);
                if(messageArrayList.get(position).getRecipient().equals(authUser)){
                    intent.putExtra("username", messageArrayList.get(position).getMusician().getUsername());
                }else{
                    intent.putExtra("username", messageArrayList.get(position).getRecipient());
                }
                Log.i(TAG, "onMessageClick: musician" + messageArrayList.get(position).getMusician());
                Log.i(TAG, "onMessageClick: recipient" + messageArrayList.get(position).getRecipient());
                startActivity(intent);
            }

        });
        messagingActivityHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull android.os.Message msg) {
                super.handleMessage(msg);
                if(msg.what == 151){
                    Objects.requireNonNull(messagesRecyclerView.getAdapter()).notifyDataSetChanged();
                    Log.i(TAG, "onCreate: messageArrayListSize" + messageArrayList.size());
//                    Log.i(TAG, "handleMessage: " + messageArrayList.get(0).getMusician());

                }
            }
        };

    }




}
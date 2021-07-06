package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
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
    String userName;
    AuthUser authUser = Amplify.Auth.getCurrentUser();
    Handler chatPageHandler;
    Musician[] musicianList;
    Musician currentMusician;
    ImageButton chatBackButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        musicianList = new Musician[1];
        getCurrentUser();

        chatBackButton = findViewById(R.id.chatBackButton);
        chatBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatPage.this, MessagingActivity.class);
            startActivity(intent);
        });




        Log.i(TAG, "onCreate: authUser username: " + authUser.getUsername());
//        Log.i(TAG, "onCreate: currentUser Musician: " + currentMusician.getUsername());

        username = getIntent().getStringExtra("username");
        userName = authUser.getUsername();


        downloadImageFromS3(username);
        TextView profileName = findViewById(R.id.chatUsername);
        profileName.setText(getIntent().getStringExtra("fullName"));

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
            Log.i(TAG, "onNewMessageReceived: " + messageChanged);
            Message message = messageChanged.item();
            if(message.getRecipient().equals(authUser.getUsername()) ||
                    message.getMusician().getUsername().equals(authUser.getUsername())){
                Log.i(TAG, "onNewMessageReceived: " + message);
                messageArrayList.add(message);
            }
//            runOnUiThread(()-> chatAdapter.notifyDataSetChanged());
            chatPageHandler.sendEmptyMessage(141);
        }
    }

    private void getPreviousMessages() {
        Log.i(TAG, "getPreviousMessages: start of get Previous messages");
        Amplify.DataStore.query(
                Message.class,
                Where.sorted(Message.DATE.ascending()),
                result -> {
                    while (result.hasNext()) {
                        Log.i(TAG, "getPreviousMessages: result: " + result);
//                        Log.i(TAG, "getPreviousMessages: result" + result.next());
//                        Log.i(TAG, "getPreviousMessages: getRecipient : " + result.next().getRecipient());
//                        Log.i(TAG, "getPreviousMessages: getMusician: " + result.next().getMusician().getUsername());
//                        Log.i(TAG, "getPreviousMessages: currentUser: " + authUser.getUsername());
//                        Log.i(TAG, "getPreviousMessages: expected Recipient: " + username);

                            Message message = result.next();
                            if(message.getRecipient().equals(username) &&
                                    message.getMusician().getUsername().equals(authUser.getUsername()) ||
                                        message.getRecipient().equals(authUser.getUsername()) &&
                                                message.getMusician().getUsername().equals(username)){
                                messageArrayList.add(message);
                            }
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
        messageContentTxt.setText("");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "clicked profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PublicMusicianProfilePage.class );
                intent.putExtra("username", userName);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "clicked home", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), DiscoverPage.class );
                startActivity(intent2);
                return true;
            case R.id.item3:
                Toast.makeText(this, "clicked favorites", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), MyFavoritesPage.class );
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    void downloadImageFromS3(String key) {
        ImageView profilePic = findViewById(R.id.chatProfilePic);
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir(), key),
                r -> {
                    profilePic.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                e -> {
                    profilePic.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
        );
    }

}
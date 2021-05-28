package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

public class LogInPage extends AppCompatActivity {
    String TAG = "loginPage";
    TextView username1;
    String username2;
    Musician musician;
    Handler loginPageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        String username = getIntent().getStringExtra("username");
        username1 = findViewById(R.id.loginUserNameTextView);
        username1.setText(username);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String password = ((TextView) findViewById(R.id.loginPasswordEditText)).getText().toString();
            username2 = username1.getText().toString();

            Amplify.Auth.signIn(
                    username2,
                    password,
                    response -> {
                        Log.i(TAG, "Successful login of user" + response);
                        loginPageHandler.sendEmptyMessage(127);

                    },
                    error -> {
                        Log.i(TAG, "unsuccessful login of user" + error.toString());
                    }
            );

        });
        loginPageHandler = new Handler(this.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 127) {
                    Amplify.API.query(
                            ModelQuery.list(Musician.class, Musician.USERNAME.eq(username2)),
                            resp -> {
                                Log.i(TAG, "handleMessage:" + resp);
                                if(resp.getData().getItems().iterator().hasNext()) {
                                    Intent intent = new Intent(LogInPage.this, DiscoverPage.class);
                                    intent.putExtra("username", username2);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LogInPage.this, CreateProfilePage.class);
                                    intent.putExtra("username", username2);
                                    startActivity(intent);
                                }


                            },
                            error -> {}
                    );
                }
            }
        };
    }
}






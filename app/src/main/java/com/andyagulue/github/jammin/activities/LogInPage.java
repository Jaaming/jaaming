package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;

public class LogInPage extends AppCompatActivity {
    String TAG = "loginPage";
    TextView username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        String username = getIntent().getStringExtra("username");
        username1 = findViewById(R.id.loginUserNameTextView);
        username1.setText(username);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String password = ((TextView) findViewById(R.id.loginPasswordEditText)).getText().toString();
            String username2= username1.getText().toString();

            Amplify.Auth.signIn(
                    username2,
                    password,
                    response -> {
                        Log.i(TAG, "Successful login of user" + response);
                        Intent intent = new Intent(LogInPage.this, CreateProfilePage.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    },
                    error -> {
                        Log.i(TAG, "unsuccessful login of user" + error.toString());
                    }
            );

        });
    }
}
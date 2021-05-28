package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;

public class SignUpPage extends AppCompatActivity {
    String TAG = "SignupPage";

    Handler signupHandler;
    String newUsername;
    String newUserEmail;
    String newUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Button signUpButton = findViewById(R.id.newUserSignUpButton);
        signUpButton.setOnClickListener(v -> {
           newUsername = ((TextView) findViewById(R.id.newUsername)).getText().toString();
           newUserEmail = ((TextView) findViewById(R.id.newUserEmail)).getText().toString();
           newUserPassword = ((TextView) findViewById(R.id.newUserPassword)).getText().toString();

            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), newUserEmail)
                    .build();
            Amplify.Auth.signUp(newUsername, newUserPassword, options,
                    result -> {
                        Log.i("AuthQuickStart", "Result: " + result.toString());

                        signupHandler.sendEmptyMessage(125);
                    },
                    error -> {
                        Log.e("AuthQuickStart", "Sign up failed", error);
                        signupHandler.sendEmptyMessage(126);
                    }

            );
        });

        signupHandler = new Handler(this.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 125) {
                    Intent intent = new Intent(SignUpPage.this, ConfirmationPage.class);
                    intent.putExtra("username", newUsername);
                    startActivity(intent);
                }
                if(msg.what == 126){
                    Toast.makeText(getApplicationContext(), "There was an error signing up, try a different username", Toast.LENGTH_SHORT).show();
                }
            }


        };
    }


}
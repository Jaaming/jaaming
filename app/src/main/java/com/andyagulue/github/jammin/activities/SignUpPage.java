package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;

public class SignUpPage extends AppCompatActivity {
    String TAG = "SignupPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        String newUsername = ((TextView)findViewById(R.id.newUsername)).getText().toString();
        String newUserEmail = ((TextView)findViewById(R.id.newUserEmail)).getText().toString();
        String newUserPassword = ((TextView)findViewById(R.id.newUserPassword)).getText().toString();


        Amplify.Auth.signUp(
                newUserEmail,
                newUserPassword,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(),newUserEmail)
            .build(),
                response -> {
                    Intent intent = new Intent(SignUpPage.this, ConfirmationPage.class);
                    intent.putExtra("username", newUsername);
                    startActivity(intent);
                    Log.i(TAG, "onCreate: created new user" + response);
                },
                error -> {
                    Log.i(TAG, "onCreate: did not create new user" + error);
                }
        );
    }
}
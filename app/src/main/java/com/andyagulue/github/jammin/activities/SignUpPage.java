package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Button signUpButton = findViewById(R.id.newUserSignUpButton);
        signUpButton.setOnClickListener(v -> {
            String newUsername = ((TextView)findViewById(R.id.newUsername)).getText().toString();
            String newUserEmail = ((TextView)findViewById(R.id.newUserEmail)).getText().toString();
            String newUserPassword = ((TextView)findViewById(R.id.newUserPassword)).getText().toString();

            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), newUserEmail)
                    .build();
            Amplify.Auth.signUp(newUsername, newUserPassword, options,
                    result -> {
                        Log.i("AuthQuickStart", "Result: " + result.toString());
                        Intent intent = new Intent(SignUpPage.this, ConfirmationPage.class);
                        intent.putExtra("username", newUsername);
                        startActivity(intent);
                    },
                    error -> {
                        Log.e("AuthQuickStart", "Sign up failed", error);
//                        Toast.makeText(this, "There was an error signing you in", Toast.LENGTH_SHORT).show();
                    }

            );
        });


    }
}
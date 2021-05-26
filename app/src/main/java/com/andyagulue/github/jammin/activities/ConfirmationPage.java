package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;

public class ConfirmationPage extends AppCompatActivity {
    String TAG = "confimationPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        String username = getIntent().getStringExtra("username");
        ((TextView)findViewById(R.id.confirmPageUserNameTextView)).setText(username);

        findViewById(R.id.confirmButton).setOnClickListener(v -> {
        String confirmation_code = ((TextView)findViewById(R.id.confirmCodeEditText)).getText().toString();


            Amplify.Auth.confirmSignUp(
                    username,
                    confirmation_code,
                    response -> {
                        Log.i(TAG, "confirmed user" + response);
                        Intent intent = new Intent(ConfirmationPage.this, LogInPage.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    },
                    error -> {

                        Log.i(TAG, "did not confirm user" + error.toString());
//                        Toast.makeText(this, "There was an error confirming your account", Toast.LENGTH_SHORT).show();
                    }
            );
        });
    }
}
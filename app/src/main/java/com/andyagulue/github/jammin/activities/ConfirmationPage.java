package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;
import com.google.android.material.snackbar.Snackbar;

public class ConfirmationPage extends AppCompatActivity {
    String TAG = "confirmationPage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        View confirmpage = findViewById(R.id.confirmationLayout);

        Toast.makeText(getApplicationContext(), "Confirmation code sent! Check your email!", Toast.LENGTH_SHORT).show();

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
                        Log.i(TAG, "unsuccessful login of user" + error.toString());
                        Snackbar confirmErrorSnackbar = Snackbar.make(confirmpage, error.getMessage(), Snackbar.LENGTH_LONG);
                        confirmErrorSnackbar.setAction("Resend Confirmation Code", v1 -> {
                                Amplify.Auth.resendUserAttributeConfirmationCode(AuthUserAttributeKey.email(),
                                        result -> {
                                            Log.i(TAG, "successfull resend" + result.toString());
                                            Snackbar successfulResendSnackBar = Snackbar.make(confirmpage, "Code Resent!", Snackbar.LENGTH_LONG);
                                            successfulResendSnackBar.show();

                                        },
                                        err -> {
                                            Log.i(TAG, "unsuccessful resend" + err);
                                            Snackbar confirmErrorSnackbar2 = Snackbar.make(confirmpage, err.getMessage(), Snackbar.LENGTH_LONG);
                                            confirmErrorSnackbar2.show();
                                        }

                                );
                        });

                        confirmErrorSnackbar.show();
                    }
            );
        });
    }
}
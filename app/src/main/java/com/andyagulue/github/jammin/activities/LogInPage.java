package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LogInPage extends AppCompatActivity {
    String TAG = "loginPage";
    TextView username1;
    String username2;
    Musician musician;
    Handler loginPageHandler;
    TextInputLayout loginPasswordLayout;
    TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        View loginLayout = findViewById(R.id.loginLayout);
        loginPasswordLayout = findViewById(R.id.loginPasswordTextInputLayout);
        loginPassword = findViewById(R.id.loginPasswordEditText);



        String username = getIntent().getStringExtra("username");
        username1 = findViewById(R.id.loginUserNameTextView);
        username1.setText(username);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String password = loginPassword.getText().toString();
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
                        String message1 = error.getCause().getLocalizedMessage().substring(0,
                                Objects.requireNonNull(error.getCause().getMessage()).indexOf('(') - 1);
                        Snackbar loginErrorSnackbar = Snackbar.make(loginLayout, message1, Snackbar.LENGTH_LONG);
                        if(error.getMessage().contains("User not confirmed in the system.")){
                        loginErrorSnackbar.setAction("Resend Confirmation Code", v1 -> {
                            Amplify.Auth.resendUserAttributeConfirmationCode(AuthUserAttributeKey.email(),
                                    result -> {
                                        Log.i(TAG, "successful resend" + result.toString());
                                        Intent intent = new Intent(LogInPage.this, ConfirmationPage.class);
                                        intent.putExtra("username", username2);
                                        startActivity(intent);
                                    },
                                    err -> {
                                        Log.i(TAG, "unsuccessful resend" + err);
                                        String message = err.getMessage() + ". " + Objects.requireNonNull(
                                            Objects.requireNonNull(
                                                err.getCause()).getMessage()).substring(0,
                                                    Objects.requireNonNull(err.getCause().getMessage()).indexOf('(') - 1);
                                        Snackbar errorSendingCodeSnackBar = Snackbar.make(loginLayout, message, Snackbar.LENGTH_LONG);
                                        errorSendingCodeSnackBar.show();
                                    }

                                );
                            });
                        }

                        loginErrorSnackbar.show();

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
        setTextWatchers();
    }

    public void setTextWatchers(){
        loginPasswordLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 8){
                    loginPasswordLayout.setError("Password length must be at least 8!");
                    loginPasswordLayout.setErrorEnabled(true);
                }else{
                    loginPasswordLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}






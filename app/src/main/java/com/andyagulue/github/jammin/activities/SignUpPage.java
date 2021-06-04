package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.services.cognitoidentityprovider.model.UsernameAttributeType;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SignUpPage extends AppCompatActivity {
    String TAG = "SignupPage";

    Handler signupHandler;
    String newUsername;
    String newUserEmail;
    String newUserPassword;
    TextView usernameError;
    TextView emailError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        usernameError = findViewById(R.id.usernameExistsError);
        emailError = findViewById(R.id.userEmailError);

//        log out of amplify
//        Amplify.Auth.signOut(
//                () -> Log.i(TAG, "The user was signed out"),
//                error -> Log.i(TAG, "the user was not signed out")
//
//        );

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


                        Log.i("AuthQuickStart", "Sign up failed", error);
                        Log.i(TAG, "error handling***" + error.getCause().getMessage());

//                        signupHandler.sendEmptyMessage(126);
                        Message message = new Message();
                        message.obj = error.getCause().getMessage().substring(0, error.getCause().getMessage().indexOf('(') -1);
                        signupHandler.sendMessage(message);
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
                if (msg.what == 126) {

                } else {
                    Log.i(TAG, "handleMessage:" + msg.obj + "*");
                    if (msg.obj.toString().contains("User already exists")) {
                        Toast.makeText(getApplicationContext(), "There was an error signing up, try a different username", Toast.LENGTH_LONG).show();
                        TextView usernameError = findViewById(R.id.usernameExistsError);
                        usernameError.setVisibility(View.VISIBLE);
                    }
                    if (msg.obj.toString().contains("Invalid email address format.")) {
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
                        TextView emailError = findViewById(R.id.userEmailError);
                        emailError.setVisibility(View.VISIBLE);
                        Log.i(TAG, "handleMessage: email " + msg);

                    }
                    Log.i(TAG, "handleMessage: everything else " + msg);
                }
            }


        };
        setupFloatingUsernameError();
    }

    private void setupFloatingUsernameError(){
        final TextInputLayout floatingUsernameErrorLabel = (TextInputLayout) findViewById(R.id.newUsername_text_input_layout);
//        floatingUsernameErrorLabel.getEditText().addTextChangedListener(new TextWatcher() {
        final TextInputEditText userNameEditText = (TextInputEditText) findViewById(R.id.newUsername);
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: ");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
               usernameError.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    Log.i(TAG, "afterTextChanged: the user has typed their username" );
                    Amplify.Auth.fetchUserAttributes(
                            attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
                            error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                    );

                }

            }
        });
        final TextInputLayout floatingEmailErrorLabel = (TextInputLayout) findViewById(R.id.newUserEmail_text_input_layout);
//        floatingEmailErrorLabel.getEditText().addTextChangedListener(new TextWatcher() {
        final TextInputEditText userEmailEditText = (TextInputEditText) findViewById(R.id.newUserEmail);
        userEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: ");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
                emailError.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    Log.i(TAG, "afterTextChanged: the user has typed their username" );

                }

            }
        });

    }




}
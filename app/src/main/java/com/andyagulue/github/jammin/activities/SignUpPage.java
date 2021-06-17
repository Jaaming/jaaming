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
import java.util.Objects;

public class SignUpPage extends AppCompatActivity {
    String TAG = "SignupPage";

    Handler signupHandler;
    String newUsername;
    String newUserEmail;
    String newUserPassword;
    String newUserPassword2;
    Button submitButton;
    TextInputLayout emailLayout;
    TextInputLayout usernameLayout;
    TextInputEditText userNameEditText;
    TextInputEditText userEmailEditText;
    TextInputLayout userPasswordLayout;
    TextInputLayout userPassword2Layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        submitButton = findViewById(R.id.newUserSignUpButton);
        emailLayout = findViewById(R.id.newUserEmail_text_input_layout);
        usernameLayout = findViewById(R.id.newUsername_text_input_layout);
        userNameEditText = findViewById(R.id.newUsername);
        userEmailEditText = findViewById(R.id.newUserEmail);
        userPasswordLayout = findViewById(R.id.newUserPassword_text_input_layout);
        userPassword2Layout = findViewById(R.id.newUserPassword2_text_input_layout);

//        log out of amplify
//        Amplify.Auth.signOut(
//                () -> Log.i(TAG, "The user was signed out"),
//                error -> Log.i(TAG, "the user was not signed out")
//
//        );


        submitButton.setOnClickListener(v -> {
           newUsername = Objects.requireNonNull(userNameEditText.getText()).toString();
           newUserEmail = Objects.requireNonNull(userEmailEditText.getText()).toString();
           newUserPassword = Objects.requireNonNull(userPasswordLayout.getEditText()).getText().toString();
           newUserPassword2 = Objects.requireNonNull(userPassword2Layout.getEditText()).getText().toString();

            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), newUserEmail)
                    .build();

            if(!newUsername.isEmpty() && !newUserEmail.isEmpty() &&
                    !userPasswordLayout.isErrorEnabled() && !userPassword2Layout.isErrorEnabled()) {
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
                            message.obj = error.getCause().getMessage().substring(0, error.getCause().getMessage().indexOf('(') - 1);
                            signupHandler.sendMessage(message);
                        }

                );
            }
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
                    if (msg.obj != null) {
                        if (msg.obj.toString().contains("User already exists")) {
                            Toast.makeText(getApplicationContext(), "There was an error signing up, try a different username", Toast.LENGTH_LONG).show();
//                        TextView usernameError = findViewById(R.id.usernameExistsError);
//                        usernameError.setVisibility(View.VISIBLE);
                            usernameLayout.setError("User already exists");
                            usernameLayout.setErrorEnabled(true);

                        }
                        if (msg.obj.toString().contains("Invalid email address format.")) {
                            Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
//                        TextView emailError = findViewById(R.id.userEmailError);
//                        emailError.setVisibility(View.VISIBLE);
                            emailLayout.setError("Invalid email address format");
                            emailLayout.setErrorEnabled(true);
                            Log.i(TAG, "handleMessage: email " + msg);

                        }
                        Log.i(TAG, "handleMessage: everything else " + msg);
                    }
                }
            }


        };
        setupTextWatchers();
    }

    private void setupTextWatchers(){
        Objects.requireNonNull(usernameLayout.getEditText()).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: ");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
                usernameLayout.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){

                    Log.i(TAG, "afterTextChanged: the user has typed their username" );
//                    Amplify.Auth.fetchUserAttributes(
//                            attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
//                            error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
//                    );
                }
            }
        });


        Objects.requireNonNull(emailLayout.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: ");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
                emailLayout.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){

                    Log.i(TAG, "afterTextChanged: the user has typed their username" );
                }
            }
        });

        Objects.requireNonNull(userPasswordLayout.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && s.length() < 8){
                    userPasswordLayout.setError("Password length must be at least 8!");
                    userPasswordLayout.setErrorEnabled(true);
                }else{
                    userPasswordLayout.setErrorEnabled(false);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Objects.requireNonNull(userPassword2Layout.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "password1" + userPasswordLayout.getEditText().getText());
                if(!s.toString().equals(userPasswordLayout.getEditText().getText().toString())){
                    userPassword2Layout.setError("Password must match!");
                    userPassword2Layout.setErrorEnabled(true);
                }else{
                    userPassword2Layout.setErrorEnabled(false);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




}
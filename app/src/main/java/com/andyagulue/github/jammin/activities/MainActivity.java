package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.R;

public class MainActivity extends AppCompatActivity {
    String TAG = "mainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try{
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("JammingApp","Initialized Amplify");
        }catch(AmplifyException error){
            Log.e("JammingApp", "onCreate: Could not create");
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickStart", "Success" + result.toString()),
                error -> Log.e("AmplifyQuickStart", "Failure" + error.toString())
        );

        signupCognito();


//        Todo todo = Todo.builder()
//                .name("My fire todo")
//                .description("todo description")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(todo),
//                response -> Log.i("JammingAPP", "onCreate: Added Todo with id: " + response.getData()),
//                error -> Log.e("JammingApp", "onCreate: Create failed",error )
//        );


        findViewById(R.id.logInNavButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LogInPage.class);
            startActivity(intent);
        });

        findViewById(R.id.signUpNavButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
            startActivity(intent);
        });
//====================Temp buttons, will delete later===================================
        findViewById(R.id.tempConfrimButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConfirmationPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempbandProfileButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BandCreationPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempProfilePageButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyMusicianProfilePage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempFavesButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyFavoritesPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempDiscoverButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DiscoverPage.class);
            startActivity(intent);
        });

        findViewById(R.id.mPublicProfilePage).setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), PublicMusicianProfilePage.class);
            startActivity(intent);


        });
        findViewById(R.id.createProfilePageButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateProfilePage.class);
            startActivity(intent);
        });
        //========================End temp buttons to be deleted=================================
    }

    void signupCognito() {
        Amplify.Auth.signUp(
                "m.parker.simms@gmail.com",
                "password",
                AuthSignUpOptions.builder().build(),
                r -> {
                    Log.i(TAG, "signupCognito: signup successfull" + r.toString());
                },
                r -> {
                    Log.i(TAG, "signupCognito: signup unsuccessfull" + r.toString());
                }
        );
    }


}


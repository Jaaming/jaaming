package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.andyagulue.github.jammin.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    String TAG = "mainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        try{
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("JammingApp","Initialized Amplify");
        }catch(AmplifyException error){
            Log.e("JammingApp", "onCreate: Could not create");
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickStart", "Success" + result.toString()),
                error -> Log.e("AmplifyQuickStart", "Failure" + error.toString())
        );
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null)Log.i(TAG, "onCreate: authUsername" + authUser.getUsername());

        uploadFile();


//        signupCognito();
//        Amplify.API.query(
//                ModelQuery.list(Musician.class, Musician.USERNAME.eq(authUser.getUsername())),
//                response-> {
//                    Log.i(TAG, "queryResponse" + response.getData().toString());
//                    Musician thisMusician = response.getData().getItems().iterator().next();
//                    Log.i(TAG, "musician" + thisMusician);
//
//                },
//                error ->{
//                    Log.i(TAG, "queryerror");
//                }
//        );

//        Amplify.API.mutate(ModelMutation.update(thisMusician))


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
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "m.parker.simms@gmail.com")
                .build();
        Amplify.Auth.signUp("username", "Password123", options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }

    private void uploadFile() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }
        Amplify.Storage.uploadFile(
                "ExampleKey",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }



}

